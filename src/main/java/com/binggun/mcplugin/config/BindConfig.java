package com.binggun.mcplugin.config;

import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.MCBotJavaPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BindConfig {
    public static   boolean refusedJoin;
    public static   boolean  online;
    public static ConfigurationSection rewardMail;
    public static int overdueTime;
    public static List<String> verificationLore =new ArrayList<>();
    public static List<Long> blacklist =new ArrayList<>();
    public static boolean quitNuBind;
    public static void init(ConfigurationSection config){
        refusedJoin=config.getBoolean("RefusedJoin");
        online = config.getBoolean("Online");
        rewardMail = config.getConfigurationSection("RewardMail");
        overdueTime=config.getInt("OverdueTime");
        verificationLore = config.getStringList("VerificationLore");
        blacklist=config.getLongList("Blacklist");
        quitNuBind=config.getBoolean("QuitNuBind");
    }


    public static List<Long> getBlacklist(){
        return blacklist;
    }

    public static boolean isQuitNuBind() {
        return quitNuBind;
    }

    public static void addBlack(Long qq){
        blacklist.add(qq);
        rewardMail.set("Blacklist",blacklist);
       MCBotJavaPlugin.mcBotJavaPlugin.saveConfig();
    }
    public static void removeBlack(Long qq){
        blacklist.remove(qq);
        rewardMail.set("Blacklist",blacklist);
        MCBotJavaPlugin.mcBotJavaPlugin.saveConfig();
    }

    public static String getVerificationLore(Player player,int verification){
        if(verificationLore.size()==0){
            return   Client.getMcBot().getLang().get("Bind_Null");
        }
        String s="";
        for(String lore:verificationLore){
            lore= lore.replaceAll("&","§");
            lore= lore.replaceAll("%n%","\n");
            lore=lore.replaceAll("%Verification%", String.valueOf(verification));
            lore= PlaceholderAPI.setPlaceholders(player,lore);
           s=s+"\n"+lore;
        }
        return s;
    }


}
