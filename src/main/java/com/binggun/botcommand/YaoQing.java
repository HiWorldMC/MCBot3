package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MemberJoinEvent;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.mcplugin.data.sql.table.YaoQingTable;

import java.sql.SQLException;
import java.util.List;

//邀请功能 不为指令 特殊类
public class YaoQing {

    YaoQingTable yaoQingTable;
    ;
    public YaoQing() {
        yaoQingTable = new YaoQingTable();
    }

    private String getLang(String key,MemberJoinEvent event) throws SQLException, ClassNotFoundException {
        String s= Client.getMcBot().getLang().get(key);
        String playerName =new QQDDataTable().getPlayerName(event.getUseId());
        if(playerName!=null) {
           s= s.replaceAll("%Player_Name%", playerName);
        }
        if(event.getInvitorId()!=null) {
            String playerInvitorId = new QQDDataTable().getPlayerName(event.getInvitorId());
            if (playerInvitorId != null) {
              s=  s.replaceAll("%Player_InvitorId%", String.valueOf(playerInvitorId));
            }else {
              s=  s.replaceAll("%Player_InvitorId%", String.valueOf(event.getInvitorId()));
            }
        }
        return s;
    }

    public void run(MemberJoinEvent event) throws Exception {
        if (yaoQingTable.isInGroup(event.getUseId())) {
            yaoQingTable.upData(event);
            At At = new At(event.getUseId());
            String playerName =new QQDDataTable().getPlayerName(event.getUseId());
            textChain txt;
            if(playerName!=null) {
                txt = new textChain(getLang("JoinGroup_Return2", event));
            }else {
                txt = new textChain(getLang("JoinGroup_Return1", event));
            }
            MessageChain me = new MessageChain(At, txt);
            event.sendMsg(me);
        } else {
            yaoQingTable.joinGroup(event);
            textChain txt;
            At At = new At(event.getUseId());
            if (event.getInvitorId() == null) {
                 txt = new textChain(getLang("JoinGroup_New1", event));
            } else {

                    txt = new textChain(getLang("JoinGroup_New2", event));

            }
            MessageChain me = new MessageChain(At, txt);
            event.sendMsg(me);
        }
            return;
        }
    public static int getInvitation(Long qq) throws SQLException, ClassNotFoundException {
        YaoQing yaoQing = new YaoQing();
        if(qq==null){
            return 0;
        }
       List<String> list= yaoQing.yaoQingTable.getYaoQingList(qq);
       if(list==null) {
           return 0;
       }
       return list.size();
    }

}





