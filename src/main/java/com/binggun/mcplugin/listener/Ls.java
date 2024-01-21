package com.binggun.mcplugin.listener;

import com.binggun.botcommand.Bind;
import com.binggun.botcommand.CommandManage;
import com.binggun.botcommand.YaoQing;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.*;
import com.binggun.botwebsocket.receivemsg.chain.Plain;
import com.binggun.botwebsocket.receivemsg.event.MemberJoinEvent;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.config.BindConfig;
import com.binggun.mcplugin.config.GroupChatConfig;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.mcplugin.data.sql.table.QdTable;
import com.binggun.util.BotUtil;
import com.binggun.util.JavaUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URISyntaxException;

public class Ls implements Listener {


    @EventHandler
    public void onFiendMsgBukkitEvent(FiendMsgBukkitEvent event) throws Exception {
        CommandManage.runCMD(event.getMsgEvent());
    }
    @EventHandler
    public void onTempMsgBukkitEvent(TempMsgBukkitEvent event) throws Exception {
        if(BotUtil.isEnableGroup(event.tempMsgEvent.groupId)) {
            CommandManage.runCMD(event.getMsgEvent());
        }
    }

    @EventHandler
    public void onGroupMsgBukkitEvent(GroupMsgBukkitEvent event) throws Exception {
        //指令运行
        if(BotUtil.isEnableGroup(event.groupMsgEvent.groupId)) {
            CommandManage.runCMD(event.getMsgEvent());
        }
        if(GroupChatConfig.start){
            if(GroupChatConfig.groupList.contains(event.groupMsgEvent.groupId)){
                String msg =Plain.GetMsg(event.groupMsgEvent.chainJks);
                if(msg.startsWith(GroupChatConfig.getPrefix())||GroupChatConfig.prefix.equalsIgnoreCase("Null")) {
                    MessageSyncBukkitEvent messageSyncBukkitEvent =new MessageSyncBukkitEvent(msg, event.groupMsgEvent);
                    Bukkit.getServer().getPluginManager().callEvent(messageSyncBukkitEvent);
                    if(!messageSyncBukkitEvent.isCancelled()) {
                        Bukkit.broadcastMessage(GroupChatConfig.replace(msg, event.groupMsgEvent.useName, "serve"));
                    }

                    return;
                }
            }
        }

    }
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) throws URISyntaxException {
        if(GroupChatConfig.start) {
            String msg = event.getMessage();
            msg=org.bukkit.ChatColor.stripColor(msg);
            if (msg.startsWith(GroupChatConfig.getPrefix()) || GroupChatConfig.prefix.equalsIgnoreCase("Null")) {
                for(Long group:GroupChatConfig.groupList) {
                    MessageSyncBukkitEvent messageSyncBukkitEvent = new MessageSyncBukkitEvent(msg, event.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(messageSyncBukkitEvent);
                    if (!messageSyncBukkitEvent.isCancelled()) {
                        BotUtil.sendGroupMsg(GroupChatConfig.replace(msg, event.getPlayer().getName(), "group"), group);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onMemberJoinBukkitEvent(MemberJoinBukkitEvent event) throws Exception {
        if(BotUtil.isEnableGroup(event.msgEvent.groupId)) {
            YaoQing yaoQing = new YaoQing();
            //加群欢迎
            yaoQing.run((MemberJoinEvent) event.getMsgEvent());
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) throws Exception {
        if(GroupChatConfig.start) {
            if(GroupChatConfig.getJoinServer()!=null){
                for(Long group:GroupChatConfig.groupList) {
                    BotUtil.sendGroupMsg(GroupChatConfig.replace(GroupChatConfig.getJoinServer(), event.getPlayer().getName(), "false"), group);
                }
            }
        }

        QQDDataTable qqdDataTable = new  QQDDataTable();
        if(qqdDataTable.getPlayerUUID(event.getPlayer().getName())==null){
            qqdDataTable.registerPlayer(event.getPlayer());
        }else {
            qqdDataTable.updateLogin(event.getPlayer());
        }
        //更新签到信息
        new QdTable().upInfo(event.getPlayer());
        if(BindConfig.refusedJoin){
            if(!new QQDDataTable().isBind(event.getPlayer().getName())){
                int v = JavaUtil.getIntRandom(9999,1000);
                Bind.TempVerificationData.put(v,event.getPlayer().getName());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bind.TempVerificationData.remove(v);
                    }
                }.runTaskLaterAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin,BindConfig.overdueTime);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getPlayer().kickPlayer(BindConfig.getVerificationLore(event.getPlayer(),v));
                    }
                }.runTaskLater(MCBotJavaPlugin.mcBotJavaPlugin,60);
                return;
            }

        }
    }
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) throws Exception {
        if(GroupChatConfig.start) {
            if(GroupChatConfig.getQuit()!=null){
                for(Long group:GroupChatConfig.groupList) {
                    BotUtil.sendGroupMsg(GroupChatConfig.replace(GroupChatConfig.getQuit(), event.getPlayer().getName(), "false"), group);
                }
            }
        }

    }


    public String getLang(String key){
        String s= Client.getMcBot().getLang().get(key);
        return s;
    }
}
