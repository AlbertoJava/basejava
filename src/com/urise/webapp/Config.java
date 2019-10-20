package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File(".\\storage\\resume.properties");
    private static final Config INSTANCE = new Config();
    private Properties props = new Properties();
    private File storageDir;

    private Config() {
        try (InputStream is = new FileInputStream("./config/resumes.properties")) {
            props.load(is);
            storageDir = new File (props.getProperty("storage.dir"));

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " +PROPS.getAbsolutePath());
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }
    public String getDbUrl (){
        return props.getProperty("db.url");
    }
    public String getDbUser (){
        return props.getProperty("db.user");
    }
    public String getDbPassword (){
        return props.getProperty("db.password");
    }
}
