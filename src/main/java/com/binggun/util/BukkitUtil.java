package com.binggun.util;

import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.MCBotJavaPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;

public class BukkitUtil {


    public static void runCommands(List<String> commands,Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(commands!=null&&commands.size()!=0) {
                    for (String command : commands) {
                        command = command.replaceAll("&", "§");
                        command = PlaceholderAPI.setPlaceholders(player, command);
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                    }
                }
            }
        }.runTaskLater(MCBotJavaPlugin.mcBotJavaPlugin,1);

    }
    public static void runCommand(String command){
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),command);
    }
    public static String stripColor(String str){

        if(Client.getMcBot().getPlugin().equalsIgnoreCase("BcPlugin")){
         //   str=net.md_5.bungee.api.ChatColor.stripColor(str);
    }else {
            str=org.bukkit.ChatColor.stripColor(str);
    }
        return str;
    }
    public static String getPlayerGroup(Player player){
        net.milkbowl.vault.permission.Permission permission =Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class).getProvider();
       return permission.getPrimaryGroup(player);
    }


    //发送Title 并聊天栏返送消息
    public static void sendPlayerListTitleMsg(Collection<Player> p, String title, String msg){
        for(Player ps:p){
            ps.sendTitle(title, msg, 20, 140, 20);
            ps.sendMessage(msg);
        }
    }

    //发送Title 并聊天栏返送消息
    public static void sendTitleMsg(Player p, String title, String msg){
        title=title.replaceAll("&","§");
        msg=msg.replaceAll("&","§");
        title= PlaceholderAPI.setPlaceholders(p,title);
        msg=PlaceholderAPI.setPlaceholders(p,msg);
        p.sendTitle(title, msg, 20, 140, 20);
    }

}
