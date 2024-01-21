package com.binggun.mcplugin.config;

import com.binggun.util.ConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;

public class MailConfig {
    public static FileConfiguration mailConfig;


         public static void init(FileConfiguration config){
             MailConfig.mailConfig= config;
            }
}
