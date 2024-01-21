package com.binggun.botwebsocket.receivemsg.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.FiendMsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.chain.ChainJk;
import com.binggun.botwebsocket.receivemsg.chain.Plain;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.sendmsg.FriendMessage;
import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.Chain;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.McBot;
import com.binggun.util.BotUtil;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//接收到好友消息
public class FiendMsgEvent implements MsgType {

    public Long useId;
    public String useName;
    public ChainJk[] chainJks;
    public String text;
    public Long groupId;

    public FiendMsgEvent(JSONObject sender, JSONArray messageChain) {
        super();
        useId = sender.getLong("id");
        useName = sender.getString("memberName");
        chainJks = ChainJk.GetChainJk(messageChain);
        text = Plain.GetMsg(chainJks);

    }

    //回复消息
    @Override
    public void sendMsg(MessageChain me) throws URISyntaxException {
        me = BotUtil.clearAt(me);
        FriendMessage c = new FriendMessage(useId, me);
        BotCommandSend cmd = new BotCommandSend("123", c);
        MCBotJavaPlugin.mcBotJavaPlugin.getLogger().info("机器人Debug: " + cmd.toString());
        Client.getClient().send(cmd.toString());
    }

    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {
        if (mcbot.getPlugin().equalsIgnoreCase("JavaPlugin")) {
            FiendMsgBukkitEvent fiendMsgBukkitEvent = new FiendMsgBukkitEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(fiendMsgBukkitEvent);
        } else {
//        FiendMsgBcEvent fiendMsgBcEvent = new FiendMsgBcEvent(this);
//        McBotBcPlugin.mCBotBcPlugin.getProxy().getPluginManager().callEvent(fiendMsgBcEvent);
        }
    }

    @Override
    public Long getUseId() {
        return useId;
    }

    @Override
    public Long getGroupId() {
        return null;
    }

    @Override
    public String getUseName() {
        return useName;
    }

    @Override
    public ChainJk[] getChainJks() {
        return chainJks;
    }
}

