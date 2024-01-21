package com.binggun.mcplugin.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class GroupChatConfig {
    public static boolean start;
    public static String groupNews;
    public static String serveNews;
    public static String prefix;
    public static List<Long> groupList;
    public static String joinServer;
    public static String quit;
    public static MemberName memberName;

    public static void init(ConfigurationSection config) {
        start = config.getBoolean("Start");
        groupNews = config.getString("GroupNews");
        serveNews = config.getString("ServeNews");
        prefix = config.getString("Prefix", "Null");
        groupList = config.getLongList("Group");
        joinServer = config.getString("JoinServer");
        quit = config.getString("QuitServer");
        memberName=new MemberName(config.getConfigurationSection("MemberName"));
    }

    public static boolean isStart() {
        return start;
    }

    public static String getGroupNews() {
        return groupNews;
    }

    public static String getServeNews() {
        return serveNews;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static MemberName getMemberName() {
        return memberName;
    }

    //t=true 服务器消息 false 群消息
    public static String replace(String msg, String name, String t) {
        String msgO;
        if (t.equalsIgnoreCase("serve")) {
            msgO = serveNews;
        } else if (t.equalsIgnoreCase("group")) {
            msgO = groupNews;
        } else {
            msgO = msg;
        }
        msgO = msgO.replace(prefix, "");
        msgO = msgO.replaceAll("%name%", name);
        msgO = msgO.replaceAll("%Msg%", msg);
        return msgO;

    }

    public static List<Long> getGroupList() {
        return groupList;
    }

    public static String getJoinServer() {
        return joinServer;
    }

    public static String getQuit() {
        return quit;
    }

    public static class MemberName{
        boolean coerce;
        String format;
        String warning;

        public MemberName(ConfigurationSection config) {
            coerce = config.getBoolean("Coerce");
            format = config.getString("Format");
            warning = config.getString("Warning");
        }

        public boolean isCoerce() {
            return coerce;
        }

        public String getFormat() {
            return format;
        }

        public String getWarning() {
            return warning;
        }
    }

}
