/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mox.nbevid.model.Item;
import mox.nbevid.model.Month;
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.model.Year;
import mox.nbevid.model.YearMonth;


/**
 *
 * @author martin
 */
public class YearExt implements Comparable<YearExt> {
  private int year;
  private List<Integer> yearItems = new ArrayList<>();
  private List<YearMonthExt> months = new ArrayList<>();
  
  public static YearExt createFromModel(Year year) {
    YearExt tmp = new YearExt();
    tmp.setYear(year.getYear());
    tmp.setYearItems(createYearItemsListExt(year.getYearItems()));
    tmp.setMonths(createYearMonthListExt(year.getMonths()));
    
    return tmp;
  }
  
  private static List<Integer> createYearItemsListExt(List<Item> yearItems) {
    ArrayList<Integer> list = new ArrayList<>();
    for (Item lastYearItem : yearItems) {
      list.add(lastYearItem.getItemId());
    }
    return list;
  }

  private static List<YearMonthExt> createYearMonthListExt(Map<Month, YearMonth> months) {
    ArrayList<YearMonthExt> list = new ArrayList<>();
    for (YearMonth ym : months.values()) {
      list.add(YearMonthExt.createFromModel(ym));
    }
    list.sort(null);
    return list;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public List<Integer> getYearItems() {
    return yearItems;
  }

  public void setYearItems(List<Integer> yearItems) {
    this.yearItems = yearItems;
  }

  public List<YearMonthExt> getMonths() {
    return months;
  }

  public void setMonths(List<YearMonthExt> months) {
    this.months = months;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 89 * hash + this.year;
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
    final YearExt other = (YearExt) obj;
    return this.year == other.year;
  }

  @Override
  public int compareTo(YearExt o) {
    return Integer.compare(this.year, o.year);
  }
  
  public Year toModel(SpendingsDatabase db) {
    Year y = new Year(db, year);
    y.setYearItemIds(yearItems);
    
    ArrayList<YearMonth> ymonths = new ArrayList<>();
    for (YearMonthExt ext : months) {
      ymonths.add(ext.toModel(y));
    }
    y.setMonths(ymonths);
    
    return y;
  }
}
