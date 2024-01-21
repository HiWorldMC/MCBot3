package com.binggun.mcplugin.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class BotConfig {

    public static List<Long> groupList;
    public static List<Long> adminList;
    public static Long botQQ;
    public  static  Long verifyKey;
    public  static String webSocketIP;
    public static boolean enableBot;
    public static void init(ConfigurationSection config){
        verifyKey=config.getLong("VerifyKey");
        botQQ = config.getLong("BotQQ");
        webSocketIP = config.getString("WebSocketIP");
        enableBot =config.getBoolean("EnableBot");
        adminList =config.getLongList("Admin");
        groupList=config.getLongList("Group");
    }

}
