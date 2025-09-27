package com.silvics.garden.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "crop_companions")
public class CropCompanion {
  @Id
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "crop_id", nullable = false)
  private Crop crop;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "companion_crop_id", nullable = false)
  private Crop companionCrop;

  @Column(name = "benefit_description", length = 255)
  private String benefitDescription;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public CropCompanion() {}

  public CropCompanion(Crop crop, Crop companionCrop, String benefitDescription) {
  this.crop = crop;
  this.companionCrop = companionCrop;
  this.benefitDescription = benefitDescription;
  }

  @PrePersist
  protected void onCreate() {
  createdAt = LocalDateTime.now();
  }

  public Long getId() {
  return id;
  }

  public void setId(Long id) {
  this.id = id;
  }

  public Crop getCrop() {
  return crop;
  }

  public void setCrop(Crop crop) {
  this.crop = crop;
  }

  public Crop getCompanionCrop() {
  return companionCrop;
  }

  public void setCompanionCrop(Crop companionCrop) {
  this.companionCrop = companionCrop;
  }

  public String getBenefitDescription() {
  return benefitDescription;
  }

  public void setBenefitDescription(String benefitDescription) {
  this.benefitDescription = benefitDescription;
  }

  public LocalDateTime getCreatedAt() {
  return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
  this.createdAt = createdAt;
  }
}