/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence.model;


import mox.nbevid.model.SpendingsDatabase;


/**
 *
 * @author martin
 */
public class SpendingsDatabaseExt {
  private String name;

  public static SpendingsDatabaseExt createFromModel(SpendingsDatabase db) {
    SpendingsDatabaseExt tmp = new SpendingsDatabaseExt();
    tmp.setName(db.getName());
    return tmp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SpendingsDatabase toModel() {
    SpendingsDatabase db = new SpendingsDatabase(name);
    return db;
  }
}
