package com.binggun.mcplugin.config;

import com.binggun.botcommand.CommandManage;
import com.binggun.customcmd.CustomCMD;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CommandConfig {

    public static List<String> CMDPrefix= new ArrayList<>();
    public static List<String> forbidden = new ArrayList<>();

    public static void init(ConfigurationSection config){
        CMDPrefix=config.getStringList("CMDPrefix");
        registerBotCommandAlias(config);
        forbiddenCommandAlias(config);
    }

    private static void forbiddenCommandAlias(ConfigurationSection config){
        if(config.getStringList("Forbidden")!=null){
            forbidden=config.getStringList("Forbidden");
        }
    }



    private static void registerBotCommandAlias(ConfigurationSection config){
        ConfigurationSection configurationSection=  config.getConfigurationSection("CMDAlias");
        if(configurationSection!=null){
            for(String cmd:configurationSection.getKeys(false)){
                List<String> list = configurationSection.getStringList(cmd);
                CommandManage.addCMDAlias(cmd,list);
            }
        }
        if(CustomCMDConfig.customCMDList!=null) {
            if (CustomCMDConfig.customCMDList.size() != 0) {
                for (CustomCMD customCMD : CustomCMDConfig.customCMDList) {
                    List<String> list = customCMD.getAlias();
                    CommandManage.addCMDAlias(customCMD.getName(),list);
                }
            }
        }
    }

}
