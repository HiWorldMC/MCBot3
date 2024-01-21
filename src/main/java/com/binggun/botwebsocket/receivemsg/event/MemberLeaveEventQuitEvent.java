package com.binggun.botwebsocket.receivemsg.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.MemberJoinBukkitEvent;
import com.binggun.botwebsocket.receivemsg.bukkitevent.MemberLeaveEventQuitBukkitEvent;
import com.binggun.botwebsocket.receivemsg.chain.ChainJk;
import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.McBot;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class MemberLeaveEventQuitEvent implements MsgType {

    public Long useId;
    public String useName;
    public Long groupId;

    public MemberLeaveEventQuitEvent(JSONObject sender) throws Exception {
        useId =sender.getLong("id");
        useName =sender.getString("memberName");
        groupId = sender.getJSONObject("group").getLong("id");
    }



    @Override
    public void sendMsg(MessageChain m) throws URISyntaxException {
        GroupMessage c=    new GroupMessage(groupId,m);
        BotCommandSend cmd = new BotCommandSend("123",c);
        Client.getClient().send(cmd.toString());
    }

    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {
        if(mcbot.getPlugin().equalsIgnoreCase("JavaPlugin")){
            MemberLeaveEventQuitBukkitEvent memberJoinBukkitEvent = new MemberLeaveEventQuitBukkitEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(memberJoinBukkitEvent);
        }
    }

    @Override
    public Long getUseId() {
        return null;
    }

    @Override
    public Long getGroupId() {
        return null;
    }

    @Override
    public ChainJk[] getChainJks() {
        return new ChainJk[0];
    }

    @Override
    public String getUseName() {
        return null;
    }
}
