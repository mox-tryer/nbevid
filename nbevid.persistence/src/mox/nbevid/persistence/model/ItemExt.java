/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence.model;


import mox.nbevid.model.Item;
import mox.nbevid.model.ItemType;


/**
 *
 * @author martin
 */
public class ItemExt implements Comparable<ItemExt> {
  private int itemId;
  private String itemName;
  private ItemType itemType;
  private int yearUsageCount;

  static ItemExt createFromModel(Item item) {
    ItemExt tmp = new ItemExt();
    tmp.setItemId(item.getItemId());
    tmp.setItemName(item.getItemName());
    tmp.setItemType(item.getItemType());
    tmp.setYearUsageCount(item.getYearUsageCount());
    
    return tmp;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public ItemType getItemType() {
    return itemType;
  }

  public void setItemType(ItemType itemType) {
    this.itemType = itemType;
  }

  public int getYearUsageCount() {
    return yearUsageCount;
  }

  public void setYearUsageCount(int yearUsageCount) {
    this.yearUsageCount = yearUsageCount;
  }

  @Override
  public int compareTo(ItemExt o) {
    return Integer.compare(this.itemId, o.itemId);
  }

  Item toModel() {
    return new Item(itemId, itemName, itemType, yearUsageCount);
  }
}
