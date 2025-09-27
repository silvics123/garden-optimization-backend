package com.silvics.garden.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class PlantingPlan {
    
  @JsonProperty("layout")
  private String[][] layout;

  @JsonProperty("plantingInstructions")
  private Map<String, List<Position>> plantingInstructions;

  @JsonProperty("utilizationRate")
  private double utilizationRate;

  public PlantingPlan() {}

  public PlantingPlan(String[][] layout, Map<String, List<Position>> plantingInstructions, double utilizationRate) {
    this.layout = layout;
    this.plantingInstructions = plantingInstructions;
    this.utilizationRate = utilizationRate;
  }

  public String[][] getLayout() {
    return layout;
  }

  public void setLayout(String[][] layout) {
    this.layout = layout;
  }

  public Map<String, List<Position>> getPlantingInstructions() {
    return plantingInstructions;
  }

  public void setPlantingInstructions(Map<String, List<Position>> plantingInstructions) {
    this.plantingInstructions = plantingInstructions;
  }

  public double getUtilizationRate() {
    return utilizationRate;
  }

  public void setUtilizationRate(double utilizationRate) {
    this.utilizationRate = utilizationRate;
  }

  public static class Position {

    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;

    public Position() {}

    public Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
    }
  }
  
}