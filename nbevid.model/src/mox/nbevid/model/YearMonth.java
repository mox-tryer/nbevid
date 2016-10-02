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
  private final int year;
  private final Month month;
  private final Map<Item, BigDecimal> values;
  
  public YearMonth(int year, Month month) {
    this(year, month, new HashMap<Item, BigDecimal>());
  }

  public YearMonth(int year, Month month, Map<Item, BigDecimal> values) {
    this.year = year;
    this.month = month;
    this.values = values;
  }

  public int getYear() {
    return year;
  }

  public Month getMonth() {
    return month;
  }

  public Map<Item, BigDecimal> getValues() {
    return values;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 79 * hash + this.year;
    hash = 79 * hash + Objects.hashCode(this.month);
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
    if (this.year != other.year) {
      return false;
    }
    return this.month == other.month;
  }

  @Override
  public String toString() {
    return "YearMonth{" + "year=" + year + ", month=" + month + '}';
  }
}
