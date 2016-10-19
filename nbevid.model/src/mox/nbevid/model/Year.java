/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.model;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 *
 * @author martin
 */
public class Year {
  private final SpendingsDatabase db;
  private final int year;
  private final List<Item> yearItems = new ArrayList<>();
  private final Map<Month, YearMonth> months = new EnumMap<>(Month.class);

  public Year(SpendingsDatabase db, int year) {
    this(db, year, null);
  }

  public Year(SpendingsDatabase db, int year, List<Item> yearItems) {
    this.db = db;
    this.year = year;

    this.yearItems.clear();
    if (yearItems != null) {
      this.yearItems.addAll(yearItems);
    }

    months.clear();
    for (Month month : Month.values()) {
      months.put(month, new YearMonth(this, month));
    }
  }

  public SpendingsDatabase getDb() {
    return db;
  }

  public int getYear() {
    return year;
  }

  public List<Item> getYearItems() {
    return yearItems;
  }

  public void setYearItemIds(List<Integer> itemIds) {
    yearItems.clear();
    for (Integer itemId : itemIds) {
      yearItems.add(db.getItem(itemId));
    }
  }

  public Map<Month, YearMonth> getMonths() {
    return months;
  }

  public void setMonths(List<YearMonth> months) {
    this.months.clear();
    for (YearMonth month : months) {
      this.months.put(month.getMonth(), month);
    }
  }
  
  public YearMonth getMonth(Month month) {
    return months.get(month);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + Objects.hashCode(this.db);
    hash = 17 * hash + this.year;
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
    if (this.year != other.year) {
      return false;
    }
    return Objects.equals(this.db, other.db);
  }

  @Override
  public String toString() {
    return "Year{" + "year=" + year + '}';
  }

  YearInfo createYearInfo() {
    return new YearInfo(db, year);
  }
}
