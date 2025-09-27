package com.silvics.garden.service;

import com.silvics.garden.config.GardenConfiguration;
import com.silvics.garden.model.Crop;
import com.silvics.garden.model.Garden;
import com.silvics.garden.model.PlantingPlan;
import com.silvics.garden.repository.CropRepository;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Service
public class GardenOptimizerService {

  private static final Logger LOG = LoggerFactory.getLogger(GardenOptimizerService.class);

  private final Map<String, Crop> cropDatabase = new HashMap<>();
  private final GardenConfiguration gardenConfiguration;
  private final CropRepository cropRepository;

  @Autowired
  public GardenOptimizerService(GardenConfiguration gardenConfiguration, CropRepository cropRepository) {
  this.gardenConfiguration = gardenConfiguration;
  this.cropRepository = cropRepository;
  }

  @PostConstruct
  public void loadCropsFromDatabase() {
  LOG.info("Loading crops from database...");
  List<Crop> crops = cropRepository.findAll();
  LOG.debug("Found {} crops in database", crops.size());

  for (Crop crop : crops) {
      cropDatabase.put(crop.getName().toLowerCase(), crop);
      LOG.debug("Loaded crop: {} (spacing: {}, size: {})",
          crop.getName(), crop.getSpacingRequirement(), crop.getSizeInSquares());
  }

  LOG.info("Successfully loaded {} crops into crop database", crops.size());
  }

  public PlantingPlan optimizeGardenLayout(Garden garden, List<String> requestedCrops) {
  LOG.info("Starting garden optimization for garden {}x{} with crops: {}",
      garden.getWidth(), garden.getLength(), requestedCrops);

  List<Crop> crops = requestedCrops.stream()
          .map(cropName -> cropDatabase.get(cropName.toLowerCase()))
          .filter(Objects::nonNull)
          .toList();

  LOG.debug("Mapped {} requested crops to {} available crops", requestedCrops.size(), crops.size());

  if (crops.isEmpty()) {
      LOG.warn("No valid crops found for optimization, returning empty plan");
      return createEmptyPlan(garden);
  }

  Model model = new Model("Garden Optimization");

  IntVar[] xPositions = new IntVar[crops.size()];
  IntVar[] yPositions = new IntVar[crops.size()];

  for (int i = 0; i < crops.size(); i++) {
      xPositions[i] = model.intVar("x_" + i, 0, garden.getWidth() - 1);
      yPositions[i] = model.intVar("y_" + i, 0, garden.getLength() - 1);
  }

  addBoundaryConstraints(model, xPositions, yPositions, crops, garden);
  addSpacingConstraints(model, xPositions, yPositions, crops);
  addCompanionPlantingConstraints(model, xPositions, yPositions, crops);

  IntVar utilizationVar = model.intVar("utilization", 0, garden.getTotalArea());

  IntVar[] sizeVars = new IntVar[crops.size()];
  for (int i = 0; i < crops.size(); i++) {
      sizeVars[i] = model.intVar("size_" + i, crops.get(i).getSizeInSquares());
  }
  model.sum(sizeVars, "=", utilizationVar).post();

  model.setObjective(Model.MAXIMIZE, utilizationVar);

  LOG.debug("Solving garden optimization model...");
  Solution solution = model.getSolver().findSolution();

  if (solution != null) {
      LOG.info("Garden optimization successful, creating planting plan");
      return createPlantingPlan(solution, xPositions, yPositions, crops, garden);
  } else {
      LOG.warn("Garden optimization failed to find solution, returning empty plan");
      return createEmptyPlan(garden);
  }
  }

  public PlantingPlan createSimpleGardenLayout(Garden garden, List<String> requestedCrops) {
  LOG.info("Creating simple garden layout for garden {}x{} with crops: {}",
      garden.getWidth(), garden.getLength(), requestedCrops);

  List<Crop> crops = requestedCrops.stream()
          .map(cropName -> cropDatabase.get(cropName.toLowerCase()))
          .filter(Objects::nonNull)
          .toList();

  LOG.debug("Mapped {} requested crops to {} available crops", requestedCrops.size(), crops.size());

  if (crops.isEmpty()) {
      LOG.warn("No valid crops found for simple layout, returning empty plan");
      return createEmptyPlan(garden);
  }

  String[][] layout = new String[garden.getLength()][garden.getWidth()];
  Map<String, List<PlantingPlan.Position>> instructions = new HashMap<>();

  for (int i = 0; i < layout.length; i++) {
      Arrays.fill(layout[i], ".");
  }

  double targetUtilization = gardenConfiguration.getTargetPercentage();
  int targetCropsToPlace = (int) Math.ceil(garden.getTotalArea() * targetUtilization / 100.0);

  List<Crop> cropsToPlant = new ArrayList<>();
  int minCropsPerType = Math.max(2, targetCropsToPlace / crops.size());

  for (Crop crop : crops) {
      for (int i = 0; i < minCropsPerType; i++) {
          cropsToPlant.add(crop);
          if (cropsToPlant.size() >= targetCropsToPlace) break;
      }
      if (cropsToPlant.size() >= targetCropsToPlace) break;
  }

  while (cropsToPlant.size() < targetCropsToPlace && cropsToPlant.size() < garden.getTotalArea()) {
      for (Crop crop : crops) {
          cropsToPlant.add(crop);
          if (cropsToPlant.size() >= targetCropsToPlace) break;
      }
      if (cropsToPlant.size() >= targetCropsToPlace) break;
  }

  int currentX = 0;
  int currentY = 0;
  int planted = 0;

  for (Crop crop : cropsToPlant) {
      if (currentY >= garden.getLength()) break;

      layout[currentY][currentX] = crop.getName().substring(0, 1).toUpperCase();

      instructions.computeIfAbsent(crop.getName(), k -> new ArrayList<>())
                 .add(new PlantingPlan.Position(currentX, currentY));

      planted++;
      currentX++;
      if (currentX >= garden.getWidth()) {
          currentX = 0;
          currentY++;
      }
  }

  double utilizationRate = (double) planted / garden.getTotalArea() * 100;

  LOG.info("Simple garden layout complete: planted {} crops, utilization: {:.1f}%",
      planted, utilizationRate);

  return new PlantingPlan(layout, instructions, utilizationRate);
  }

  private void addBoundaryConstraints(Model model, IntVar[] xPositions, IntVar[] yPositions,
                                List<Crop> crops, Garden garden) {
  for (int i = 0; i < crops.size(); i++) {
      model.arithm(xPositions[i], ">=", 0).post();
      model.arithm(xPositions[i], "<", garden.getWidth()).post();
      model.arithm(yPositions[i], ">=", 0).post();
      model.arithm(yPositions[i], "<", garden.getLength()).post();
  }
  }

  private void addSpacingConstraints(Model model, IntVar[] xPositions, IntVar[] yPositions,
                               List<Crop> crops) {
  for (int i = 0; i < crops.size(); i++) {
      for (int j = i + 1; j < crops.size(); j++) {
          int minSpacing = Math.max(crops.get(i).getSpacingRequirement(),
                                  crops.get(j).getSpacingRequirement());

          IntVar xDiff = model.intVar("xDiff_" + i + "_" + j, -1000, 1000);
          IntVar yDiff = model.intVar("yDiff_" + i + "_" + j, -1000, 1000);
          IntVar xAbs = model.intVar("xAbs_" + i + "_" + j, 0, 1000);
          IntVar yAbs = model.intVar("yAbs_" + i + "_" + j, 0, 1000);
          IntVar distance = model.intVar("dist_" + i + "_" + j, 0, 2000);

          model.arithm(xDiff, "=", xPositions[i], "-", xPositions[j]).post();
          model.arithm(yDiff, "=", yPositions[i], "-", yPositions[j]).post();
          model.absolute(xAbs, xDiff).post();
          model.absolute(yAbs, yDiff).post();
          model.arithm(distance, "=", xAbs, "+", yAbs).post();
          model.arithm(distance, ">=", minSpacing).post();
      }
  }
  }

  private void addCompanionPlantingConstraints(Model model, IntVar[] xPositions, IntVar[] yPositions,
                                         List<Crop> crops) {
  for (int i = 0; i < crops.size(); i++) {
      for (int j = i + 1; j < crops.size(); j++) {
          Crop crop1 = crops.get(i);
          Crop crop2 = crops.get(j);

          if (crop1.getAntagonisticPlants().contains(crop2.getName()) ||
              crop2.getAntagonisticPlants().contains(crop1.getName())) {

              IntVar xDiff = model.intVar("antag_xDiff_" + i + "_" + j, -1000, 1000);
              IntVar yDiff = model.intVar("antag_yDiff_" + i + "_" + j, -1000, 1000);
              IntVar xAbs = model.intVar("antag_xAbs_" + i + "_" + j, 0, 1000);
              IntVar yAbs = model.intVar("antag_yAbs_" + i + "_" + j, 0, 1000);
              IntVar distance = model.intVar("antag_dist_" + i + "_" + j, 0, 2000);

              model.arithm(xDiff, "=", xPositions[i], "-", xPositions[j]).post();
              model.arithm(yDiff, "=", yPositions[i], "-", yPositions[j]).post();
              model.absolute(xAbs, xDiff).post();
              model.absolute(yAbs, yDiff).post();
              model.arithm(distance, "=", xAbs, "+", yAbs).post();
              model.arithm(distance, ">=", 3).post();
          }
      }
  }
  }

  private PlantingPlan createPlantingPlan(Solution solution, IntVar[] xPositions, IntVar[] yPositions,
                                    List<Crop> crops, Garden garden) {
  String[][] layout = new String[garden.getLength()][garden.getWidth()];
  Map<String, List<PlantingPlan.Position>> instructions = new HashMap<>();

  for (int i = 0; i < layout.length; i++) {
      Arrays.fill(layout[i], ".");
  }

  for (int i = 0; i < crops.size(); i++) {
      int x = solution.getIntVal(xPositions[i]);
      int y = solution.getIntVal(yPositions[i]);
      String cropName = crops.get(i).getName();

      layout[y][x] = cropName.substring(0, 1).toUpperCase();

      instructions.computeIfAbsent(cropName, k -> new ArrayList<>())
                 .add(new PlantingPlan.Position(x, y));
  }

  int usedSquares = crops.stream().mapToInt(Crop::getSizeInSquares).sum();
  double utilizationRate = (double) usedSquares / garden.getTotalArea() * 100;

  return new PlantingPlan(layout, instructions, utilizationRate);
  }

  private PlantingPlan createEmptyPlan(Garden garden) {
  String[][] layout = new String[garden.getLength()][garden.getWidth()];
  for (int i = 0; i < layout.length; i++) {
      Arrays.fill(layout[i], ".");
  }
  return new PlantingPlan(layout, new HashMap<>(), 0.0);
  }

}