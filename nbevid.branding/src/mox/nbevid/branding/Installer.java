/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.branding;


import org.openide.modules.ModuleInstall;


public class Installer extends ModuleInstall {

  @Override
  public void restored() {
    System.setProperty("netbeans.buildnumber", "1.0");
  }

}
