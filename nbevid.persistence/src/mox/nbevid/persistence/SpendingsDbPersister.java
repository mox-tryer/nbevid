/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.persistence;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import mox.nbevid.model.Year;
import mox.nbevid.model.YearInfo;
import mox.nbevid.persistence.model.SpendingsDatabaseExt;
import mox.nbevid.persistence.model.YearExt;


/**
 *
 * @author martin
 */
public class SpendingsDbPersister {
  private static final SpendingsDbPersister instance = new SpendingsDbPersister(); // v buducnosti nahradit service providerom
  
  private final ObjectMapper mapper;
  
  private SpendingsDbPersister() {
    this.mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
  }
  
  public static SpendingsDbPersister getDefault() {
    return instance;
  }

  public File mainDbFile(File directory) {
    return new File(directory, "eviddb.json");
  }
  
  public File yearDbFile(File directory, Year year) {
    return new File(directory, String.format("year%04d.json", year.getYear()));
  }
  
  public File yearDbFile(File directory, YearInfo yearInfo) {
    return new File(directory, String.format("year%04d.json", yearInfo.getYear()));
  }
  
  public void save(SpendingsDatabase db, File directory) throws IOException {
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(mainDbFile(directory)), "UTF-8")) {
      mapper.writeValue(writer, SpendingsDatabaseExt.createFromModel(db));
    }
    
    for (Year year : db.getYears().values()) {
      save(year, directory);
    }
  }

  public void save(Year year, File directory) throws IOException {
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(yearDbFile(directory, year)), "UTF-8")) {
      mapper.writeValue(writer, YearExt.createFromModel(year));
    }
  }
  
  public SpendingsDatabase load(File directory) throws IOException {
    try (Reader reader = new InputStreamReader(new FileInputStream(mainDbFile(directory)), "UTF-8")) {
      return mapper.readValue(reader, SpendingsDatabaseExt.class).toModel();
    }
  }
  
  public Year load(File directory, YearInfo yearInfo) throws IOException {
    try (Reader reader = new InputStreamReader(new FileInputStream(yearDbFile(directory, yearInfo)), "UTF-8")) {
      return mapper.readValue(reader, YearExt.class).toModel(yearInfo.getDb());
    }
  }
}
