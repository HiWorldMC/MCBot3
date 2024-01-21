package com.binggun.mcplugin.listener;

import com.binggun.botcommand.Bind;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.FiendMsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.bukkitevent.GroupMsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.bukkitevent.MemberLeaveEventQuitBukkitEvent;
import com.binggun.botwebsocket.receivemsg.bukkitevent.TempMsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.chain.Plain;
import com.binggun.botwebsocket.receivemsg.event.MemberLeaveEventQuitEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.config.BindConfig;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class BindListener implements Listener {
    @EventHandler
    public void bind(GroupMsgBukkitEvent event) throws SQLException, ClassNotFoundException, URISyntaxException {
        if(BotConfig.groupList.contains(event.groupMsgEvent.getGroupId())){
            String msg =Plain.GetMsg(event.groupMsgEvent.chainJks);
            if(msg==null){
                return;
            }
            try {
                int v = Integer.parseInt(Plain.GetMsg(event.groupMsgEvent.chainJks));
                if(Bind.TempVerificationData.get(v)!=null){
                    Long QQ=event.groupMsgEvent.useId;
                    QQDDataTable  qqdDataTable= new QQDDataTable();
                    if(qqdDataTable.getPlayerName(QQ)!=null){
                        sendMsg(event.groupMsgEvent,"Bind_Repetition");
                        return;
                    }
                        String ID =Bind.TempVerificationData.get(v);
                        String name= Bind.TempVerificationData.get(v);
                        if(qqdDataTable.getPlayerQQ(name)!=null){
                            if(qqdDataTable.getPlayerQQ(name)==QQ){
                                sendMsg(event.groupMsgEvent,"Bind_Repetition");
                            }else {
                                sendMsg(event.groupMsgEvent,"Bind_Repetition2");
                            }
                            return;
                        }
                        if(qqdDataTable.isBind(name)){
                            sendMsg(event.groupMsgEvent,"Bind_Repetition");
                            return;
                        }
                        Bind.TempVerificationData.remove(v);
                        qqdDataTable.bindQQ(name,QQ);
                        for(Long group: BotConfig.groupList){
                            MemberNameListener.setName(group,QQ,name);
                        }
                        sendMsg(event.groupMsgEvent,"Bind_Success");
                }
            }catch (Exception e){
                return;
            }

        }

    }



    @EventHandler
    public void bind(TempMsgBukkitEvent event) throws SQLException, ClassNotFoundException, URISyntaxException {
        if(BotConfig.groupList.contains(event.tempMsgEvent.groupId)){
            String msg =Plain.GetMsg(event.tempMsgEvent.chainJks);
            if(msg==null){
                return;
            }
            try {
                int v = Integer.parseInt(Plain.GetMsg(event.tempMsgEvent.chainJks));
                if(Bind.TempVerificationData.get(v)!=null){
                    Long QQ=event.tempMsgEvent.useId;
                    QQDDataTable  qqdDataTable= new QQDDataTable();
                    if(qqdDataTable.getPlayerName(QQ)!=null){
                        sendMsg(event.tempMsgEvent,"Bind_Repetition");
                        return;
                    }
                    String ID =Bind.TempVerificationData.get(v);
                    String name= Bind.TempVerificationData.get(v);
                    if(qqdDataTable.getPlayerQQ(name)!=null){
                        if(qqdDataTable.getPlayerQQ(name)==QQ){
                            sendMsg(event.tempMsgEvent,"Bind_Repetition");
                        }else {
                            sendMsg(event.tempMsgEvent,"Bind_Repetition2");
                        }
                        return;
                    }
                    if(qqdDataTable.isBind(name)){
                        sendMsg(event.tempMsgEvent,"Bind_Repetition");
                        return;
                    }
                    Bind.TempVerificationData.remove(v);
                    qqdDataTable.bindQQ(name,QQ);
                    for(Long group: BotConfig.groupList){
                        MemberNameListener.setName(group,QQ,name);
                    }
                    sendMsg(event.tempMsgEvent,"Bind_Success");
                }
            }catch (Exception e){
                return;
            }

        }

    }



    @EventHandler
    public void bind(FiendMsgBukkitEvent event) throws SQLException, ClassNotFoundException, URISyntaxException {
            String msg =Plain.GetMsg(event.fiendMsgEvent.chainJks);
            if(msg==null){
                return;
            }
            try {
                int v = Integer.parseInt(Plain.GetMsg(event.fiendMsgEvent.chainJks));
                if(Bind.TempVerificationData.get(v)!=null){
                    Long QQ=event.fiendMsgEvent.useId;
                    QQDDataTable  qqdDataTable= new QQDDataTable();
                    if(qqdDataTable.getPlayerName(QQ)!=null){
                        sendMsg(event.fiendMsgEvent,"Bind_Repetition");
                        return;
                    }
                    String ID =Bind.TempVerificationData.get(v);
                    String name= Bind.TempVerificationData.get(v);
                    if(qqdDataTable.getPlayerQQ(name)!=null){
                        if(qqdDataTable.getPlayerQQ(name)==QQ){
                            sendMsg(event.fiendMsgEvent,"Bind_Repetition");
                        }else {
                            sendMsg(event.fiendMsgEvent,"Bind_Repetition2");
                        }
                        return;
                    }
                    if(qqdDataTable.isBind(name)){
                        sendMsg(event.fiendMsgEvent,"Bind_Repetition");
                        return;
                    }
                    Bind.TempVerificationData.remove(v);
                    qqdDataTable.bindQQ(name,QQ);
                    for(Long group: BotConfig.groupList){
                        MemberNameListener.setName(group,QQ,name);
                    }
                    sendMsg(event.fiendMsgEvent,"Bind_Success");
                }
            }catch (Exception e){
                return;
            }
    }
    @EventHandler
    public void onQuitGroup(MemberLeaveEventQuitBukkitEvent event) throws SQLException, ClassNotFoundException {
       if(BindConfig.isQuitNuBind()) {
           if (!BotConfig.groupList.contains(event.msgEvent.groupId)) {
               return;
           }
           QQDDataTable qqdDataTable = new QQDDataTable();
           String player = qqdDataTable.getPlayerName(event.msgEvent.useId);
           if (player != null) {
               qqdDataTable.unBindQQ(player);
           }
       }
    }

    public   String getLang(String key,long QQ){
        String s= Client.getMcBot().getLang().get(key);
        s= s.replaceAll("%QQ%", String.valueOf(QQ));
        s=  s.replaceAll("%qq%", String.valueOf(QQ));
        return s;
    }

    private void sendMsg(MsgType event,String key) throws URISyntaxException {
        long QQ= event.getUseId();
        At At = new At(QQ);
        String lang =getLang(key,QQ);
        lang= ChatColor.stripColor(lang);
        textChain txt = new textChain(lang);
        MessageChain me = new MessageChain(At,txt);
        event.sendMsg(me);
    }

}
