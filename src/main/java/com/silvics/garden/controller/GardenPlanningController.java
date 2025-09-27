package com.silvics.garden.controller;

import com.silvics.garden.dto.GardenOptimizationRequest;
import com.silvics.garden.model.Crop;
import com.silvics.garden.model.Garden;
import com.silvics.garden.model.PlantingPlan;
import com.silvics.garden.repository.CropRepository;
import com.silvics.garden.service.GardenOptimizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garden")
public class GardenPlanningController {

  private static final Logger LOG = LoggerFactory.getLogger(GardenPlanningController.class);

  private final GardenOptimizerService gardenOptimizerService;
  private final CropRepository cropRepository;

  @Autowired
  public GardenPlanningController(GardenOptimizerService gardenOptimizerService, CropRepository cropRepository) {
    this.gardenOptimizerService = gardenOptimizerService;
    this.cropRepository = cropRepository;
  }

  @PostMapping("/optimize")
  public ResponseEntity<PlantingPlan> optimizeGarden(@RequestBody GardenOptimizationRequest request) {
    LOG.info("Received optimization request for garden {}x{} with crops: {}",
      request.getGardenWidth(), request.getGardenLength(), request.getCrops());

  try {
      Garden garden = new Garden(request.getGardenWidth(), request.getGardenLength());
      PlantingPlan plan = gardenOptimizerService.optimizeGardenLayout(garden, request.getCrops());
      LOG.info("Garden optimization completed successfully with {:.1f}% utilization",plan.getUtilizationRate());
      return ResponseEntity.ok(plan);
  }
  catch (Exception e) {
      LOG.error("Garden optimization failed", e);
      return ResponseEntity.badRequest().build();
  }
  }

  @PostMapping("/simple")
  public ResponseEntity<PlantingPlan> createSimpleGarden(@RequestBody GardenOptimizationRequest request) {
  LOG.info("Received simple garden request for garden {}x{} with crops: {}",
      request.getGardenWidth(), request.getGardenLength(), request.getCrops());

  try {
      Garden garden = new Garden(request.getGardenWidth(), request.getGardenLength());
      PlantingPlan plan = gardenOptimizerService.createSimpleGardenLayout(garden, request.getCrops());
      LOG.info("Simple garden layout completed successfully with {:.1f}% utilization",
          plan.getUtilizationRate());
      return ResponseEntity.ok(plan);
  }
  catch (Exception e) {
      LOG.error("Simple garden layout failed", e);
      return ResponseEntity.badRequest().build();
  }
  }

  @GetMapping("/crops")
  public ResponseEntity<List<Crop>> getAvailableCrops() {
  LOG.info("Received request to get available crops");

  try {
      List<Crop> crops = cropRepository.findAll();
      LOG.info("Retrieved {} crops from database", crops.size());
      return ResponseEntity.ok(crops);
  }
  catch (Exception e) {
      LOG.error("Failed to retrieve crops from database", e);
      return ResponseEntity.badRequest().build();
  }
  }
}