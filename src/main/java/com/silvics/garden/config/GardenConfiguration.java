package com.silvics.garden.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "garden.utilization")
public class GardenConfiguration {

  private double targetPercentage = 70.0;

  public double getTargetPercentage() {
    return targetPercentage;
  }

  public void setTargetPercentage(double targetPercentage) {
    this.targetPercentage = targetPercentage;
  }
  
}