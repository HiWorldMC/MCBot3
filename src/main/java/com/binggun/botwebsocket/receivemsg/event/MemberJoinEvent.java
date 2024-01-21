package com.binggun.botwebsocket.receivemsg.event;

import com.FBinggun.MySQl.Util;
import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.MemberJoinBukkitEvent;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.McBot;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class MemberJoinEvent implements MsgEvent {
    public Long groupId;
    public Long useId;
    public Long invitorId;
    public String joinTimestamp;
    public String lastSpeakTimestamp;

    public MemberJoinEvent(JSONObject member, JSONObject Invitor) throws Exception {
        super();
        useId = member.getLong("id");
        groupId =member.getJSONObject("group").getLong("id");
        if(Invitor!=null) {
            invitorId = Invitor.getLong("id");
        }
        joinTimestamp = Util.stampToTime(member.getLong("joinTimestamp"));

        Long t =member.getLong("lastSpeakTimestamp");
        if(t==null){
            lastSpeakTimestamp=joinTimestamp;
        }else {
            lastSpeakTimestamp=Util.stampToTime(t);
        }

    }





    public void sendMsg(MessageChain me) throws URISyntaxException {
        GroupMessage c=    new GroupMessage(groupId,me);
        BotCommandSend cmd = new BotCommandSend("123",c);
        Client.getClient().send(cmd.toString());
    }

    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {
        if(mcbot.getPlugin().equalsIgnoreCase("JavaPlugin")){
            MemberJoinBukkitEvent memberJoinBukkitEvent = new MemberJoinBukkitEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(memberJoinBukkitEvent);
        }else {
//            MemberJoinBcEvent memberJoinBcEvent = new MemberJoinBcEvent(this);
//            McBotBcPlugin.mCBotBcPlugin.getProxy().getPluginManager().callEvent(memberJoinBcEvent);
        }
    }


    public Long getGroupId() {
        return groupId;
    }

    public Long getUseId() {
        return useId;
    }

    public Long getInvitorId() {
        return invitorId;
    }

    public String getJoinTimestamp() {
        return joinTimestamp;
    }

    public String getLastSpeakTimestamp() {
        return lastSpeakTimestamp;
    }
}
