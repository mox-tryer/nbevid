/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.event.ChangeListener;
import org.openide.util.ChangeSupport;


/**
 *
 * @author martin
 */
public class SpendingsDatabase {
  private final String name;
  private final Map<Integer, Item> allItems = new HashMap<>();
  private final List<Item> lastYearItems = new ArrayList<>();
  private final List<YearInfo> yearInfos = new ArrayList<>();
  private final Map<Integer, Year> years = new HashMap<>();
  
  private final ChangeSupport yearsChangeSupport = new ChangeSupport(this);
  
  public SpendingsDatabase(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Map<Integer, Item> getAllItems() {
    return allItems;
  }
  
  public void addItem(Item item) {
    allItems.put(item.getItemId(), item);
  }
  
  public void removeItem(Item item) {
    allItems.remove(item.getItemId());
  }
  
  public Item getItem(int itemId) {
    return allItems.get(itemId);
  }

  public List<Item> getLastYearItems() {
    return lastYearItems;
  }
  
  public void setLastYearItemIds(List<Integer> itemIds) {
    lastYearItems.clear();
    for (Integer itemId : itemIds) {
      lastYearItems.add(getItem(itemId));
    }
  }

  public List<YearInfo> getYearInfos() {
    return yearInfos;
  }
  
  public void addYearInfo(YearInfo yearInfo) {
    this.yearInfos.add(yearInfo);
    this.yearInfos.sort(null);
  }

  public Map<Integer, Year> getYears() {
    return years;
  }
  
  public void addYear(Year year) {
    if (years.containsKey(year.getYear())) {
      // TODO: throw exception
      return;
    }
    
    years.put(year.getYear(), year);
    addYearInfo(year.createYearInfo());
    lastYearItems.clear();
    lastYearItems.addAll(year.getYearItems());
    
    for (Item yearItem : year.getYearItems()) {
      yearItem.incYearUsageCount();
    }
    
    yearsChangeSupport.fireChange();
  }
  
  public void addYearsChangeListener(ChangeListener listener) {
    yearsChangeSupport.addChangeListener(listener);
  }
  
  public void removeYearsChangeListener(ChangeListener listener) {
    yearsChangeSupport.removeChangeListener(listener);
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + Objects.hashCode(this.name);
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
    final SpendingsDatabase other = (SpendingsDatabase) obj;
    return Objects.equals(this.name, other.name);
  }

  @Override
  public String toString() {
    return "SpendingsDatabase{" + "name=" + name + '}';
  }
}
