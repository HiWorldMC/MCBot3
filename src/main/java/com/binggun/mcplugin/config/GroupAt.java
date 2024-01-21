package com.binggun.mcplugin.config;

import org.bukkit.configuration.ConfigurationSection;

import java.sql.SQLException;

public class GroupAt {
    static boolean enable;
    static String groupMsg;
    static String serverMsg;
    static boolean titleDisplay;
    static boolean chatDisplay;
    static String sound;
    static int cooling;
    static int  maxAtCount;
    static int dayMaxAtCount;
    public static void init(ConfigurationSection config) throws SQLException, ClassNotFoundException {
        enable =config.getBoolean("Enable");
        titleDisplay =config.getBoolean("TitleDisplay");
        chatDisplay =config.getBoolean("ChatDisplay");
        groupMsg =config.getString("GroupMsg");
        serverMsg =config.getString("ServerMsg");
        sound =config.getString("Sound");
        cooling =config.getInt("Cooling");
        maxAtCount =config.getInt("MaxAtCount");
        dayMaxAtCount =config.getInt("DayMaxAtCount");
    }

    public static boolean isEnable() {
        return enable;
    }

    public static String getGroupMsg() {
        return groupMsg;
    }

    public static int getCooling() {
        return cooling;
    }

    public static int getMaxAtCount() {
        return maxAtCount;
    }

    public static int getDayMaxAtCount() {
        return dayMaxAtCount;
    }

    public static String getServerMsg() {
        return serverMsg;
    }

    public static boolean isTitleDisplay() {
        return titleDisplay;
    }

    public static boolean isChatDisplay() {
        return chatDisplay;
    }

    public static String getSound() {
        return sound;
    }
}
