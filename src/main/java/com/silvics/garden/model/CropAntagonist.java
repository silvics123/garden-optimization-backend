package com.silvics.garden.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "crop_antagonists")
public class CropAntagonist {
  @Id
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "crop_id", nullable = false)
  private Crop crop;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "antagonist_crop_id", nullable = false)
  private Crop antagonistCrop;

  @Column(name = "negative_effect", length = 255)
  private String negativeEffect;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public CropAntagonist() {}

  public CropAntagonist(Crop crop, Crop antagonistCrop, String negativeEffect) {
  this.crop = crop;
  this.antagonistCrop = antagonistCrop;
  this.negativeEffect = negativeEffect;
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

  public Crop getAntagonistCrop() {
  return antagonistCrop;
  }

  public void setAntagonistCrop(Crop antagonistCrop) {
  this.antagonistCrop = antagonistCrop;
  }

  public String getNegativeEffect() {
  return negativeEffect;
  }

  public void setNegativeEffect(String negativeEffect) {
  this.negativeEffect = negativeEffect;
  }

  public LocalDateTime getCreatedAt() {
  return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
  this.createdAt = createdAt;
  }
}