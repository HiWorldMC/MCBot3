package com.binggun.botwebsocket.receivemsg.event;

import com.FBinggun.MySQl.Util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.GroupMsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.chain.ChainJk;
import com.binggun.botwebsocket.receivemsg.chain.Plain;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.McBot;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;
import java.sql.SQLException;

//群消息 监听器
public class GroupMsgEvent implements MsgType {
    public Long useId;
    public String useName;
    public Long groupId;
    public  ChainJk[] chainJks;
    public String text;
    public String lastSpeakTimestamp;
    public String joinTimestamp;
    public GroupMsgEvent(JSONObject sender, JSONArray messageChain) throws Exception {
        super();
        useId =sender.getLong("id");
        useName =sender.getString("memberName");
        groupId = sender.getJSONObject("group").getLong("id");
        chainJks =  ChainJk.GetChainJk(messageChain);
        text= Plain.GetMsg(chainJks);
        lastSpeakTimestamp=Util.stampToTime(sender.getLong("lastSpeakTimestamp"));
        joinTimestamp=Util.stampToTime(sender.getLong("joinTimestamp"));

    }



    //发消息
    @Override
    public void sendMsg(MessageChain me) throws URISyntaxException {
            GroupMessage c=    new GroupMessage(groupId,me);
            BotCommandSend cmd = new BotCommandSend("123",c);
            Client.getClient().send(cmd.toString());
        }




    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {
        if(mcbot.getPlugin().equalsIgnoreCase("JavaPlugin")){
            GroupMsgBukkitEvent fiendMsgBukkitEvent = new GroupMsgBukkitEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(fiendMsgBukkitEvent);
        }else {
//                              bh
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
