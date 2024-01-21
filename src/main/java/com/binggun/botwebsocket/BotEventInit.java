package com.binggun.botwebsocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.receivemsg.event.*;

public class BotEventInit {


    public static MsgEvent init(String type, JSONObject data) throws Exception {

        if(type.equalsIgnoreCase("GroupMessage")){
            JSONArray arrayGroup = data.getJSONArray("messageChain");
            JSONObject senderGroup = data.getJSONObject("sender");
            return new GroupMsgEvent(senderGroup,arrayGroup);
        }
        if(type.equalsIgnoreCase("FriendMessage")){
            JSONArray arrayFriend = data.getJSONArray("messageChain");
            JSONObject senderFriend = data.getJSONObject("sender");
            return new FiendMsgEvent(senderFriend,arrayFriend);
        }
        if(type.equalsIgnoreCase("TempMessage")){
            JSONArray arrayTemp = data.getJSONArray("messageChain");
            JSONObject senderTemp = data.getJSONObject("sender");
            return new TempMsgEvent(senderTemp,arrayTemp);
        }
        if(type.equalsIgnoreCase("MemberJoinEvent")){
            JSONObject member = data.getJSONObject("member");
            JSONObject invitor=null;
            if(data.getJSONObject("invitor")!=null) {
                invitor = data.getJSONObject("invitor");
            }
            return new MemberJoinEvent(member,invitor);
        }
        if(type.equalsIgnoreCase("MemberLeaveEventQuit")){
            System.out.println(data);
            JSONObject members = data.getJSONObject("member");
            return new MemberLeaveEventQuitEvent(members);
        }
        if(type.equalsIgnoreCase("BotOfflineEventForce")){
            return new BotOfflineEvent(data.getLong("qq"));
        }
        if(type.equalsIgnoreCase("MemberLeaveEventKick")){
            return  new MemberCardChangeEvent(data);
        }

      return null;
    }



}
