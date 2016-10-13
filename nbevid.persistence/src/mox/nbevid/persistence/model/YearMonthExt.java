/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence.model;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import mox.nbevid.model.Item;
import mox.nbevid.model.Month;
import mox.nbevid.model.Year;
import mox.nbevid.model.YearMonth;


/**
 *
 * @author martin
 */
public class YearMonthExt implements Comparable<YearMonthExt> {
  private Month month;
  private Map<Integer, BigDecimal> values;
  
  static YearMonthExt createFromModel(YearMonth yearMonth) {
    YearMonthExt tmp = new YearMonthExt();
    tmp.setMonth(yearMonth.getMonth());
    tmp.setValues(createValuesExt(yearMonth.getValues()));
    
    return tmp;
  }

  private static Map<Integer, BigDecimal> createValuesExt(Map<Item, BigDecimal> values) {
    HashMap<Integer, BigDecimal> tmp = new HashMap<>();
    for (Map.Entry<Item, BigDecimal> entry : values.entrySet()) {
      tmp.put(entry.getKey().getItemId(), entry.getValue());
    }
    
    return tmp;
  }

  public Month getMonth() {
    return month;
  }

  public void setMonth(Month month) {
    this.month = month;
  }

  public Map<Integer, BigDecimal> getValues() {
    return values;
  }

  public void setValues(Map<Integer, BigDecimal> values) {
    this.values = values;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + Objects.hashCode(this.month);
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
    final YearMonthExt other = (YearMonthExt) obj;
    return this.month == other.month;
  }

  @Override
  public int compareTo(YearMonthExt o) {
    return this.month.compareTo(o.month);
  }
  
  public YearMonth toModel(Year year) {
    YearMonth ym = new YearMonth(year, month);
    ym.setValuesByIds(values);
    return ym;
  }
}
