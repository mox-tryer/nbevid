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


/**
 *
 * @author martin
 */
public class SpendingsDatabase {
  private final String name;
  private final Map<Integer, Item> allItems;
  private final List<Year> years;
  
  public SpendingsDatabase(String name) {
    this(name, new HashMap<Integer, Item>(), new ArrayList<Year>());
  }

  public SpendingsDatabase(String name, Map<Integer, Item> allItems, List<Year> years) {
    this.name = name;
    this.allItems = allItems;
    this.years = years;
  }

  public String getName() {
    return name;
  }

  public Map<Integer, Item> getAllItems() {
    return allItems;
  }

  public List<Year> getYears() {
    return years;
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
