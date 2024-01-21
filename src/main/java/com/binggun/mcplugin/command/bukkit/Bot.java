package com.binggun.mcplugin.command.bukkit;

import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.config.*;
import com.binggun.mcplugin.runnablebukkit.InitialBotBukkit;
import com.binggun.util.BotUtil;
import com.binggun.util.ConfigUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Bot implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
       if(sender.isOp()&&args==null){
           sender.sendMessage("/bot open 打开机器人");
           sender.sendMessage("/bot close 关闭机器人");
           sender.sendMessage("/bot reload 重新加载配置文件");
           sender.sendMessage("/bot send [文本] 向群内发送文本");
           sender.sendMessage("/bind 绑定QQ");
           sender.sendMessage("/bind [玩家名] [QQ] 强制绑定QQ");
           sender.sendMessage("/nubind 取消绑定QQ");
           sender.sendMessage("/mail 打开邮箱(/yj)");
           sender.sendMessage("/mail give [玩家名] [邮箱ID] 发送邮件给玩家");
           sender.sendMessage("/sign 签到(/qd)");
       }


        if(sender.isOp()&&args.length==1){
        if(args[0].equalsIgnoreCase("open")) {
            Client.mcBot.setEnable(true);
            InitialBotBukkit.init();
            sender.sendMessage("机器人开启");
            return false;
        }
        if(args[0].equalsIgnoreCase("close")) {
            Client.mcBot.setEnable(false);
            try {
                InitialBotBukkit.close();
                sender.sendMessage("机器人关闭");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

            if(args[0].equalsIgnoreCase("reload")) {
                File file = new File(MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class).getDataFolder(), "config.yml");
                if (!file.exists()) {
                    MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class).saveResource("config.yml", true);
                }
                File fileLang = new File(MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class).getDataFolder(), "Lang.yml");
                if (!fileLang.exists()) {
                    MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class). saveResource("Lang.yml", true);
                }
                File mailConfig = new File(MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class).getDataFolder(), "MailTemplate.yml");
                if (!mailConfig.exists()) {
                    MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class).saveResource("MailTemplate.yml", true);
                }
                MailConfig.init(ConfigUtil.load(mailConfig));
                try {
                    Config.init(ConfigUtil.load(file));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                LangConfig.init(ConfigUtil.load(fileLang));
                sender.sendMessage("重载成功");
            }

    }

        if(args.length>=2) {
            if(!sender.hasPermission("McBot.sand")){
                sender.sendMessage("你无权运行");
                return false;
            }
            if (args[0].equalsIgnoreCase("send")) {
                String text = "";
                for(String str:args){
                    if(!args[0].equalsIgnoreCase(str)){
                        text=text+str+" ";
                    }
                }
                if(sender instanceof Player) {
                    text = PlaceholderAPI.setPlaceholders((OfflinePlayer) sender, text);
                }else {
                    text = PlaceholderAPI.setPlaceholders(null, text);
                }
               for(Long id:GroupChatConfig.getGroupList()) {
                   try {
                       BotUtil.sendGroupMsg(text,id);
                   } catch (URISyntaxException e) {
                       throw new RuntimeException(e);
                   }
               }
               sender.sendMessage("发送成功");
            return false;

            }
        }

        return false;
    }
}
