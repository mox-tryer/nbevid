/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.branding;


import org.openide.modules.ModuleInstall;


public class Installer extends ModuleInstall {
  private static final long serialVersionUID = 1L;

  @Override
  public void restored() {
    System.setProperty("netbeans.buildnumber", "0.1.2");
  }

}
