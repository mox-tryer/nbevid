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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.persistence.model.SpendingsDatabaseExt;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;


/**
 *
 * @author martin
 */
public class SpendingsDbPersister {
  private static final byte[] SALT = new byte[] {(byte) 0xfd, (byte) 0x8a, (byte) 0x06, (byte) 0x5f, (byte) 0xd3, (byte) 0xa0, (byte) 0x1c, (byte) 0x34};

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

  public static FileFilter getFileFilter() {
    return new FileNameExtensionFilter("Evid Database", "eviddb");
  }

  public static File getDbFile(File dbFolder, String name) {
    return new File(dbFolder, name + ".eviddb");
  }

  private static void backupFile(File f) throws IOException {
    if (!f.exists()) {
      return;
    }

    final FileObject fo = FileUtil.toFileObject(f);
    final FileObject bkpFo = FileUtil.findBrother(fo, "bak");
    if (bkpFo != null) {
      bkpFo.delete();
    }

    FileUtil.copyFile(fo, fo.getParent(), fo.getName(), "bak");
  }

  public void save(SpendingsDatabase db, File dbFile, char[] password) throws PersisterException {
    try {
      backupFile(dbFile);
      
      try (Writer writer = createWriter(dbFile, password)) {
        mapper.writeValue(writer, SpendingsDatabaseExt.createFromModel(db));
      }
    } catch (IOException | GeneralSecurityException ex) {
      throw new PersisterException(ex);
    }
  }

  public SpendingsDatabase load(File dbFile, char[] password) throws PersisterException {
    try (Reader reader = createReader(dbFile, password)) {
      return mapper.readValue(reader, SpendingsDatabaseExt.class).toModel();
    } catch (IOException | GeneralSecurityException ex) {
      throw new PersisterException(ex);
    }
  }

  private static Writer createWriter(File dbFile, char[] password) throws IOException, GeneralSecurityException {
    OutputStream os = new FileOutputStream(dbFile);

    if ((password != null) && (password.length > 0)) {
      os = Base64.getMimeEncoder().wrap(os);

      PBEParameterSpec pbeParamSpec = new PBEParameterSpec(SALT, 65535);
      PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
      SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");

      SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
      Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
      pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

      os = new CipherOutputStream(os, pbeCipher);
    }

    return new OutputStreamWriter(os, "UTF-8");
  }

  private static Reader createReader(File dbFile, char[] password) throws IOException, GeneralSecurityException {
    InputStream is = new FileInputStream(dbFile);

    if ((password != null) && (password.length > 0)) {
      is = Base64.getMimeDecoder().wrap(is);

      PBEParameterSpec pbeParamSpec = new PBEParameterSpec(SALT, 65535);
      PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
      SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");

      SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
      Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
      pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

      is = new CipherInputStream(is, pbeCipher);
    }

    return new InputStreamReader(is, "UTF-8");
  }
}
