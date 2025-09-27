package com.silvics.garden.dto;

import java.util.List;

public class GardenOptimizationRequest {
  private int gardenWidth;
  private int gardenLength;
  private List<String> crops;

  public GardenOptimizationRequest() {}

  public GardenOptimizationRequest(int gardenWidth, int gardenLength, List<String> crops) {
  this.gardenWidth = gardenWidth;
  this.gardenLength = gardenLength;
  this.crops = crops;
  }

  public int getGardenWidth() {
  return gardenWidth;
  }

  public void setGardenWidth(int gardenWidth) {
  this.gardenWidth = gardenWidth;
  }

  public int getGardenLength() {
  return gardenLength;
  }

  public void setGardenLength(int gardenLength) {
  this.gardenLength = gardenLength;
  }

  public List<String> getCrops() {
  return crops;
  }

  public void setCrops(List<String> crops) {
  this.crops = crops;
  }
}