package com.binggun.botwebsocket.receivemsg.event;

import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.McBot;
import com.binggun.util.Permission;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class MemberCardChangeEvent implements  MsgEvent{
String origin;
String current;
Long useId;
Long groupId;
Permission permission;

    public MemberCardChangeEvent(JSONObject sender) {
        this.origin = sender.getString("origin");
        this.current = sender.getString("current");
        this.useId=sender.getJSONObject("member").getLong("id");
        this.groupId=sender.getJSONObject("member").getJSONObject("group").getLong("id");
        String per= sender.getJSONObject("member").getJSONObject("group").getString("permission");
        switch (per){
            case "OWNER":
                permission=Permission.OWNER;
            case  "ADMINISTRATOR":
                permission=Permission.ADMINISTRATOR;
            case "MEMBER":
                permission=Permission.MEMBER;
        }
    }

    @Override
    public void sendMsg(MessageChain m) throws URISyntaxException {

    }

    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {

    }
}
