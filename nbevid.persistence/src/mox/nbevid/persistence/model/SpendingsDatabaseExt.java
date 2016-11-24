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
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.model.Year;


/**
 *
 * @author martin
 */
public class SpendingsDatabaseExt {
  private int version;
  private String name;
  private List<ItemExt> items = new ArrayList<>();
  private List<Integer> lastYearItems = new ArrayList<>();
  private List<YearExt> years = new ArrayList<>();

  public static SpendingsDatabaseExt createFromModel(SpendingsDatabase db) {
    SpendingsDatabaseExt tmp = new SpendingsDatabaseExt();
    tmp.setVersion(1);
    tmp.setName(db.getName());
    tmp.setItems(createItemListExt(db.getAllItems()));
    tmp.setLastYearItems(createLastYearItemsListExt(db.getLastYearItems()));
    tmp.setYears(createYearListExt(db.getYears()));
    return tmp;
  }
  
  private static List<ItemExt> createItemListExt(Map<Integer, Item> allItems) {
    ArrayList<ItemExt> exts = new ArrayList<>();
    for (Item item : allItems.values()) {
      exts.add(ItemExt.createFromModel(item));
    }
    exts.sort(null);
    return exts;
  }

  private static List<Integer> createLastYearItemsListExt(List<Item> lastYearItems) {
    ArrayList<Integer> list = new ArrayList<>();
    for (Item lastYearItem : lastYearItems) {
      list.add(lastYearItem.getItemId());
    }
    return list;
  }

  private static List<YearExt> createYearListExt(Map<Integer, Year> years) {
    ArrayList<YearExt> exts = new ArrayList<>();
    for (Year year : years.values()) {
      exts.add(YearExt.createFromModel(year));
    }
    exts.sort(null);
    return exts;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ItemExt> getItems() {
    return items;
  }

  public void setItems(List<ItemExt> items) {
    this.items = items;
  }

  public List<Integer> getLastYearItems() {
    return lastYearItems;
  }

  public void setLastYearItems(List<Integer> lastYearItems) {
    this.lastYearItems = lastYearItems;
  }

  public List<YearExt> getYears() {
    return years;
  }

  public void setYears(List<YearExt> years) {
    this.years = years;
  }

  public SpendingsDatabase toModel() {
    SpendingsDatabase db = new SpendingsDatabase(name);
    for (ItemExt ext : items) {
      db.addItem(ext.toModel());
    }
    db.setLastYearItemIds(lastYearItems);
    for (YearExt ext : years) {
      db.addYear(ext.toModel(db));
    }
    return db;
  }
}
