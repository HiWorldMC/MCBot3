package com.binggun.mcplugin.config;

import com.binggun.customcmd.CustomCMD;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CustomCMDConfig {
    public static List<CustomCMD> customCMDList =new ArrayList<>();
    public static void init(ConfigurationSection config){
        if(config!=null){
            for(String name:config.getKeys(false)){
              CustomCMD customCMD = new CustomCMD(name,config.getConfigurationSection(name)) ;
              customCMDList.add(customCMD);
            }
        }
    }
}
