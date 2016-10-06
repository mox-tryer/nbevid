/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.model;


/**
 *
 * @author martin
 */
public class Item {
  private final int itemId;
  private String itemName;
  private final ItemType itemType;

  public Item(int itemId, String itemName, ItemType itemType) {
    this.itemId = itemId;
    this.itemName = itemName;
    this.itemType = itemType;
  }

  public int getItemId() {
    return itemId;
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

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + this.itemId;
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
    final Item other = (Item) obj;
    return this.itemId == other.itemId;
  }

  @Override
  public String toString() {
    return "Item{" + "itemId=" + itemId + ", itemName=" + itemName + ", itemType=" + itemType + '}';
  }
}
