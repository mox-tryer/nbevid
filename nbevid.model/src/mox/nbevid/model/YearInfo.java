/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.model;


import java.util.Objects;


/**
 *
 * @author martin
 */
public class YearInfo implements Comparable<YearInfo> {
  private final SpendingsDatabase db;
  private final int year;

  public YearInfo(SpendingsDatabase db, int year) {
    this.db = db;
    this.year = year;
  }

  public SpendingsDatabase getDb() {
    return db;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.db);
    hash = 71 * hash + this.year;
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
    final YearInfo other = (YearInfo) obj;
    if (this.year != other.year) {
      return false;
    }
    return Objects.equals(this.db, other.db);
  }

  

  @Override
  public int compareTo(YearInfo o) {
    return Integer.compare(this.year, o.year);
  }
}
