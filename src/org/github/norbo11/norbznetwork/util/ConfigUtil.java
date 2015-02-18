package org.github.norbo11.norbznetwork.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    public static final String CONFIG_FILENAME = "config.properties";
    private static Properties properties = new Properties();
    
    public static void load() {
        try {
            File file = new File(CONFIG_FILENAME);
            
            if (!file.exists()) file.createNewFile();
            properties.load(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void save() {
        try {
            properties.store(new FileWriter(new File(CONFIG_FILENAME)), "Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
    
    public static void set(String key, String value) {
        properties.setProperty(key, value);
    }
}
