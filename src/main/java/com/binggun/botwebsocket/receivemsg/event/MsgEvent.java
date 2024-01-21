package com.binggun.botwebsocket.receivemsg.event;

import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.McBot;


import java.net.URISyntaxException;
import java.sql.SQLException;

public interface MsgEvent{
    public abstract void sendMsg(MessageChain m) throws URISyntaxException;
    public void run(McBot mcbot) throws SQLException, URISyntaxException;



}
