/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import mox.nbevid.model.SpendingsDatabase;


/**
 *
 * @author martin
 */
public class SpendingsDbPersister {
  private static final SpendingsDbPersister instance = new SpendingsDbPersister(); // v buducnosti nahradit service providerom
  
  private final ObjectMapper mapper;
  
  private SpendingsDbPersister() {
    this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
  }
  
  public static SpendingsDbPersister getDefault() {
    return instance;
  }
  
  public void save(SpendingsDatabase db, File directory) throws IOException {
    File dbFile = new File(directory, "eviddb.json");
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(dbFile), "UTF-8")) {
      mapper.writeValue(writer, SpDbInternal.createFrom(db));
    }
  }
  
  public SpendingsDatabase load(File directory) throws IOException {
    File dbFile = new File(directory, "eviddb.json");
    try (Reader reader = new InputStreamReader(new FileInputStream(dbFile), "UTF-8")) {
      return mapper.readValue(reader, SpDbInternal.class).toExternal();
    }
  }
  
  public static final class SpDbInternal {
    private String name;
    
    private static SpDbInternal createFrom(SpendingsDatabase db) {
      SpDbInternal dbi = new SpDbInternal();
      dbi.setName(db.getName());
      return dbi;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
    
    public SpendingsDatabase toExternal() {
      SpendingsDatabase db = new SpendingsDatabase(name);
      return db;
    }
  }
}
