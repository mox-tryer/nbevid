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


/**
 *
 * @author martin
 */
public class SpendingsDatabaseExt {
  private String name;
  private List<ItemExt> items = new ArrayList<>();

  public static SpendingsDatabaseExt createFromModel(SpendingsDatabase db) {
    SpendingsDatabaseExt tmp = new SpendingsDatabaseExt();
    tmp.setName(db.getName());
    tmp.setItems(createItemListExt(db.getAllItems()));
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

  public SpendingsDatabase toModel() {
    SpendingsDatabase db = new SpendingsDatabase(name);
    for (ItemExt ext : items) {
      db.addItem(ext.toModel());
    }
    return db;
  }
}
