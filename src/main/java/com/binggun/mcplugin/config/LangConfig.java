package com.binggun.mcplugin.config;

import com.binggun.util.ConfigUtil;
import com.binggun.util.Lang;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class LangConfig {
    public static Lang lang;
    public static void init(FileConfiguration config) {
        lang = new Lang(config);
    }

}