/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence;


/**
 *
 * @author martin
 */
public class PersisterException extends Exception {
  private static final long serialVersionUID = -8802534385465807864L;

  public PersisterException() {
  }

  public PersisterException(String message) {
    super(message);
  }

  public PersisterException(String message, Throwable cause) {
    super(message, cause);
  }

  public PersisterException(Throwable cause) {
    super(cause);
  }
}
