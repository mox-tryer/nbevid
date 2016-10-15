/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.model;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 *
 * @author martin
 */
public class YearMonth {
  private final Year year;
  private final Month month;
  private final Map<Item, BigDecimal> values = new HashMap<>();
  
  public YearMonth(Year year, Month month) {
    this.year = year;
    this.month = month;
  }

  public Year getYear() {
    return year;
  }

  public Month getMonth() {
    return month;
  }

  public Map<Item, BigDecimal> getValues() {
    return values;
  }
  
  public void setValuesByIds(Map<Integer, BigDecimal> values) {
    this.values.clear();
    for (Map.Entry<Integer, BigDecimal> entry : values.entrySet()) {
      this.values.put(year.getDb().getItem(entry.getKey()), entry.getValue());
    }
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 71 * hash + Objects.hashCode(this.year);
    hash = 71 * hash + Objects.hashCode(this.month);
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
    final YearMonth other = (YearMonth) obj;
    if (!Objects.equals(this.year, other.year)) {
      return false;
    }
    return this.month == other.month;
  }

  @Override
  public String toString() {
    return "YearMonth{" + "year=" + year + ", month=" + month + '}';
  }
}
