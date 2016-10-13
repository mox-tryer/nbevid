/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence.model;


import mox.nbevid.model.YearInfo;


/**
 *
 * @author martin
 */
public class YearInfoExt implements Comparable<YearInfoExt> {
  private int year;

  static YearInfoExt createFromModel(YearInfo yearInfo) {
    YearInfoExt tmp = new YearInfoExt();
    tmp.setYear(yearInfo.getYear());
    
    return tmp;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 11 * hash + this.year;
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
    final YearInfoExt other = (YearInfoExt) obj;
    return this.year == other.year;
  }

  @Override
  public int compareTo(YearInfoExt o) {
    return Integer.compare(this.year, o.year);
  }
  
  YearInfo toModel() {
    return new YearInfo(year);
  }
}
