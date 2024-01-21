package com.binggun.util;

import com.binggun.mcplugin.MCBotJavaPlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Lang {
    Map<String,String> langData =new HashMap<>();
    public Lang(FileConfiguration config){
        for(String str:config.getKeys(false)){
            langData.put(str,config.getString(str));
        }

    }

    public String get(String key){
        String str=langData.get(key);
        if(str==null){
            MCBotJavaPlugin.mcBotJavaPlugin.getLogger().warning("Lang.yml文件缺少 "+key+" 的配置 请检查文件");
            return null;
        }
        str= str.replaceAll("&","§");
        str= str.replaceAll("%n%","\n");
       return str;
    }

}
