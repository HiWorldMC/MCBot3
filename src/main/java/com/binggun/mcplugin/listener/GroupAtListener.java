package com.binggun.mcplugin.listener;

import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.Content;
import com.binggun.botwebsocket.receivemsg.bukkitevent.GroupMsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.chain.ChainAt;
import com.binggun.botwebsocket.receivemsg.chain.ChainJk;
import com.binggun.botwebsocket.sendmsg.*;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.mcplugin.config.GroupAt;
import com.binggun.mcplugin.data.sql.table.AtTable;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.util.BukkitUtil;
import com.binggun.util.groupmanage.GroupUserInfoUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GroupAtListener implements Listener {

    public Map<String,Integer> cooling = new HashMap<>();
    @EventHandler
    public void onGroupMsgBukkitEvent(GroupMsgBukkitEvent event) throws Exception {
        //指令运行
        if(GroupAt.isEnable()){
            if(BotConfig.groupList.contains(event.groupMsgEvent.getGroupId())){
            ChainJk[] chainJk = event.groupMsgEvent.getChainJks();
            int count =0;
            for(ChainJk jk:chainJk){
                if(jk instanceof ChainAt){
                    ChainAt at = (ChainAt) jk;
                    Long UseID = event.groupMsgEvent.getUseId();
                    Long target =at.getTarget();
                    QQDDataTable qqdDataTable = new QQDDataTable();
                    String player = qqdDataTable.getPlayerName(UseID);
                    String playerTarget = qqdDataTable.getPlayerName(target);
                    if(player!=null&&playerTarget!=null){
                       new AtTable().upTime(player);
                        new AtTable().upTime(playerTarget);
                        Player p = Bukkit.getPlayer(playerTarget);
                        if(p!=null){
                            //在冷却时间内
                            if(cooling.get(player)!=null){
                                return;
                            }else {
                                cooling.put(player,GroupAt.getCooling());
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        cooling.remove(player);
                                    }
                                }.runTaskLaterAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin,GroupAt.getCooling());
                            }
                            //超过当天最高AT数量
                            if(new AtTable().getDay(player)<=0){
                                return;
                            }
                            //超过最多AT数量
                            if(GroupAt.getMaxAtCount()<=count){
                                return;
                            }
                            //拒绝AT
                            if(!new AtTable().isUse(playerTarget)){
                                return;
                            }
                            String Msg= GroupAt.getServerMsg();
                            Msg=Msg.replaceAll("&","§");
                            Msg=Msg.replaceAll("%player%",player);
                            count=count+1;
                            new AtTable().addDay(player,1);
                            if(GroupAt.isTitleDisplay()) {
                                BukkitUtil.sendTitleMsg(p, "", Msg);
                            }
                            if(GroupAt.isChatDisplay()) {
                                p.sendMessage(Msg);
                            }
                            if(GroupAt.getSound()!=null){
                                p.playSound(p.getLocation(), Sound.valueOf(GroupAt.getSound()),3,1);
                            }
                            return;

                        }
                    }

                }
            }
        }}

    }


    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) throws Exception {
        if(GroupAt.isEnable()) {
            String msg = event.getMessage();
            msg= org.bukkit.ChatColor.stripColor(msg);
           String playerName =   event.getPlayer().getName();
            int count =0;
            if (msg.contains("@")) {
                String[] at = msg.split(" ");
                for (String str : at) {
                    if (str.startsWith("@")) {
                        //获得目标ID
                        String playerTarget = str.replaceAll("@", "");
                        QQDDataTable qqdDataTable = new QQDDataTable();
                        //获取目标QQ
                        Long qq = qqdDataTable.getPlayerQQ(playerTarget);
                        if (qq != null) {
                            //检查数据
                            String m = GroupAt.getGroupMsg();
                            m = PlaceholderAPI.setPlaceholders(event.getPlayer(), m);
                            //更新数据
                            new AtTable().upTime(playerName);
                            new AtTable().upTime(playerTarget);
                            //判断是否在冷却内
                            if(cooling.get(playerName)!=null){
                                return;
                            }else {
                                cooling.put(playerName,GroupAt.getCooling());
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        cooling.remove(playerName);
                                    }
                                }.runTaskLaterAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin,GroupAt.getCooling());
                            }
                            //超过当天最高AT数量
                            if(new AtTable().getDay(playerName)<=0){
                                return;
                            }
                            //超过最多AT数量
                            if(GroupAt.getMaxAtCount()<=count){
                                return;
                            }
                            //拒绝AT
                            if(!new AtTable().isUse(playerTarget)){
                                return;
                            }
                            //添加@次数
                            new AtTable().addDay(playerName,1);
                            for (Long group : BotConfig.groupList) {
                                String finalM = m;
                                //发送消息
                                new BukkitRunnable() {
                                   @Override
                                   public void run() {
                                       try {
                                           GroupUserInfoUtil giu = new GroupUserInfoUtil(group,qq);
                                           if(giu.isGroupUserExist()) {
                                                   sendGroupMsg(finalM, qq, group);
                                           }
                                       } catch (URISyntaxException e) {
                                           throw new RuntimeException(e);
                                       } catch (InterruptedException e) {
                                           throw new RuntimeException(e);
                                       } catch (ExecutionException e) {
                                           throw new RuntimeException(e);
                                       }
                                   }
                               }.runTaskAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin);

                            }
                        }


                    }
                }

            }
        }
    }

    public static void sendGroupMsg(String msg,Long qq, Long group) throws URISyntaxException {
        At at =new At(qq);
        textChain txt = new textChain(msg);
        MessageChain me = new MessageChain(at,txt);
        Content c=   new GroupMessage(group,me);
        BotCommandSend cmd = new BotCommandSend("123","sendGroupMessage",null,c);
        Client.getClient().send(cmd.toString());
    }
}
