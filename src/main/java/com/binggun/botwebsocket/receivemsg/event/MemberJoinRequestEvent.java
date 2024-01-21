package com.binggun.botwebsocket.receivemsg.event;

import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.receivemsg.chain.ChainJk;
import com.binggun.botwebsocket.receivemsg.chain.Plain;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.McBot;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class MemberJoinRequestEvent implements MsgType {
    Long useId;
    Long group;
    String message;
    Long invitorId;
    public MemberJoinRequestEvent(JSONObject data) {
        useId=data.getLong("fromId");
        group=data.getLong("groupId");
        message=data.getString("message");
        invitorId=data.getLong("invitorId");

    }

    @Override
    public void sendMsg(MessageChain m) throws URISyntaxException {

    }

    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {

    }

    @Override
    public Long getUseId() {
        return null;
    }

    @Override
    public Long getGroupId() {
        return group;
    }

    @Override
    public ChainJk[] getChainJks() {
        ChainJk[] chainJks =new ChainJk[1];
        Plain plain= new Plain("Plain",message);
        chainJks[0] =plain;
        return chainJks;
    }

    @Override
    public String getUseName() {
        return null;
    }
}
