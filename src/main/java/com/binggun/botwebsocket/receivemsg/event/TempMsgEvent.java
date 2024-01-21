package com.binggun.botwebsocket.receivemsg.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.TempMsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.chain.ChainJk;
import com.binggun.botwebsocket.receivemsg.chain.Plain;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.TempMessage;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.McBot;
import com.binggun.util.BotUtil;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class TempMsgEvent implements MsgType {
    public Long useId;
    public String useName;
    public Long groupId;
    public  ChainJk[] chainJks;
    public String text;
    public TempMsgEvent(JSONObject sender, JSONArray messageChain){
        super();
        useId =sender.getLong("id");
        useName =sender.getString("memberName");
        groupId = sender.getJSONObject("group").getLong("id");
        chainJks =  ChainJk.GetChainJk(messageChain);
        text= Plain.GetMsg(chainJks);

    }



    public void sendMsg(MessageChain me) throws URISyntaxException {
        me = BotUtil.clearAt(me);
        TempMessage c=    new TempMessage(useId,groupId,me);
        BotCommandSend cmd = new BotCommandSend("123",c);
        MCBotJavaPlugin.mcBotJavaPlugin.getLogger().info("机器人Debug: "+cmd.toString());
        Client.getClient().send(cmd.toString());
    }




    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {
        if(mcbot.getPlugin().equalsIgnoreCase("JavaPlugin")){
            TempMsgBukkitEvent tempMsgBukkitEvent = new TempMsgBukkitEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(tempMsgBukkitEvent);
        }else {
//            TempMsgBcEvent tempMsgBcEvent = new TempMsgBcEvent(this);
//            McBotBcPlugin.mCBotBcPlugin.getProxy().getPluginManager().callEvent(tempMsgBcEvent);
        }
    }

    @Override
    public Long getUseId() {
        return useId;
    }

    @Override
    public String getUseName() {
        return useName;
    }
    @Override
    public Long getGroupId() {
        return groupId;
    }
    @Override
    public ChainJk[] getChainJks() {
        return chainJks;
    }
}
