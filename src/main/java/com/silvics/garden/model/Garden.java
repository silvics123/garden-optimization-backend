package com.silvics.garden.model;

public class Garden {
  private int width;
  private int length;

  public Garden() {}

  public Garden(int width, int length) {
  this.width = width;
  this.length = length;
  }

  public int getWidth() {
  return width;
  }

  public void setWidth(int width) {
  this.width = width;
  }

  public int getLength() {
  return length;
  }

  public void setLength(int length) {
  this.length = length;
  }

  public int getTotalArea() {
  return width * length;
  }
}