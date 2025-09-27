package com.silvics.garden.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "crops")
public class Crop {
  @Id
  private Long id;

  @Column(unique = true, nullable = false, length = 100)
  private String name;

  @Column(name = "spacing_requirement", nullable = false)
  private int spacingRequirement;

  @Column(name = "size_in_squares", nullable = false)
  private int sizeInSquares;

  @Column(length = 500)
  private String description;

  @Column(name = "growing_season", length = 50)
  private String growingSeason;

  @Column(name = "harvest_time_days")
  private Integer harvestTimeDays;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "crop", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<CropCompanion> companions;

  @OneToMany(mappedBy = "crop", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<CropAntagonist> antagonists;

  public Crop() {}

  public Crop(String name, int spacingRequirement, int sizeInSquares,
    List<String> companionPlants, List<String> antagonisticPlants) {
        this.name = name;
        this.spacingRequirement = spacingRequirement;
        this.sizeInSquares = sizeInSquares;
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
    protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSpacingRequirement() {
    return spacingRequirement;
  }

  public void setSpacingRequirement(int spacingRequirement) {
    this.spacingRequirement = spacingRequirement;
  }

  public int getSizeInSquares() {
    return sizeInSquares;
  }

  public void setSizeInSquares(int sizeInSquares) {
    this.sizeInSquares = sizeInSquares;
  }

  public List<String> getCompanionPlants() {
  return companions != null ?
    companions.stream().map(c -> c.getCompanionCrop().getName()).collect(Collectors.toList()) :
    List.of();
  }

  public List<String> getAntagonisticPlants() {
  return antagonists != null ?
    antagonists.stream().map(a -> a.getAntagonistCrop().getName()).collect(Collectors.toList()) :
    List.of();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGrowingSeason() {
    return growingSeason;
  }

  public void setGrowingSeason(String growingSeason) {
    this.growingSeason = growingSeason;
  }

  public Integer getHarvestTimeDays() {
    return harvestTimeDays;
  }

  public void setHarvestTimeDays(Integer harvestTimeDays) {
    this.harvestTimeDays = harvestTimeDays;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<CropCompanion> getCompanions() {
    return companions;
  }

  public void setCompanions(List<CropCompanion> companions) {
    this.companions = companions;
  }

  public List<CropAntagonist> getAntagonists() {
    return antagonists;
  }

  public void setAntagonists(List<CropAntagonist> antagonists) {
    this.antagonists = antagonists;
  }
  
}