package com.binggun.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {
    public static FileConfiguration load(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(new File(path));
    }

    public static FileConfiguration load(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
}
