/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.model;


import java.util.Map;


/**
 *
 * @author martin
 */
public class Year {
  private final int year;
  private final Map<Month, YearMonth> months;

  public Year(int year, Map<Month, YearMonth> months) {
    this.year = year;
    this.months = months;
  }

  public int getYear() {
    return year;
  }

  public Map<Month, YearMonth> getMonths() {
    return months;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 41 * hash + this.year;
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Year other = (Year) obj;
    return this.year == other.year;
  }

  @Override
  public String toString() {
    return "Year{" + "year=" + year + '}';
  }
}
