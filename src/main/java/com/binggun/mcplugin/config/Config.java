package com.binggun.mcplugin.config;

import com.FBinggun.MySQl.MySql;
import com.FBinggun.MySQl.SQLite;
import com.binggun.mcplugin.MCBotJavaPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static String tablePrefix="";
    public static SQLite sql;
    public static ConfigurationSection qdConfig;
    public static void init(FileConfiguration config) throws SQLException, ClassNotFoundException {
        BotConfig.init(config.getConfigurationSection("Bot"));
        BindConfig.init(config.getConfigurationSection("Bind"));
        GroupChatConfig.init(config.getConfigurationSection("GroupChat"));
        sql =getSQL(config.getConfigurationSection("MySQL"));
        CustomCMDConfig.init(config.getConfigurationSection("CustomCommand"));
        CommandConfig.init(config.getConfigurationSection("Command"));
        qdConfig=config.getConfigurationSection("Sign");
        GroupAt.init(config.getConfigurationSection("GroupAt"));
    }

    private static SQLite getSQL(ConfigurationSection configSQL) throws SQLException, ClassNotFoundException {
        JavaPlugin javaPlugin=  MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class);
        boolean use = configSQL.getBoolean("Enable");
        if(use) {
            String ip = configSQL.getString("hostname");
            String password = configSQL.getString("password");
            String username = configSQL.getString("username");
            String database = configSQL.getString("database");
            String additional = configSQL.getString("additional");
            if(additional!=null) {
                sql = new MySql(ip, username, password, database, additional, javaPlugin);
            }else {
                sql = new MySql(ip, username, password, database, false, javaPlugin);
            }
            Config.tablePrefix=configSQL.getString("tablePrefix","");
            //SQL = new MySql(ip, username, password, database, false, this);
        }else {
            sql = new SQLite(javaPlugin, javaPlugin.getDataFolder().getPath()+"/data.db");
        }
        return sql;
    }
}
