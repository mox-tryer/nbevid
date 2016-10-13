/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.model;


/**
 *
 * @author martin
 */
public class YearInfo implements Comparable<YearInfo> {
  private final int year;

  public YearInfo(int year) {
    this.year = year;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + this.year;
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
    return this.year == other.year;
  }

  @Override
  public int compareTo(YearInfo o) {
    return Integer.compare(this.year, o.year);
  }
}
