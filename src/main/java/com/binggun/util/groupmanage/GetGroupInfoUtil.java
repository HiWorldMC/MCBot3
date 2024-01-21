package com.binggun.util.groupmanage;

import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.sendmsg.groupmanage.GetGroupInfo;
import com.binggun.util.Callback;
import com.binggun.util.CallbackUtil;
import com.binggun.util.Permission;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class GetGroupInfoUtil{

    UUID uuid;
    Long group;
    Long qq;
    String name;
    String specialTitle;
    GetGroupInfo getGroupInfo;
    JSONObject call;
    private Callback<JSONObject, String> callbackUtil;
    public GetGroupInfoUtil(Long group, Long qq) {
        this.group = group;
        this.qq = qq;
        getGroupInfo= new GetGroupInfo(group,qq);
        uuid= UUID.randomUUID();
        this.callbackUtil = new CallbackUtil<>();
    }

    public Info send() throws URISyntaxException, InterruptedException, ExecutionException {
        BotCommandSend botCommandSend = new BotCommandSend(uuid.toString(),getGroupInfo);
        botCommandSend.send();
        call=callbackUtil.callback(uuid.toString()).get();
        if(call!=null){
        if(call.getString("id")!=null){
            return new Info(call);
        }}
        return null;
    }


    public  class Info{
        String memberName;
        String specialTitle;
        Permission permission;
        Long joinTimestamp;
        Long lastSpeakTimestamp;
        Long muteTimeRemaining;
        Info(JSONObject call){
            memberName=   call.getString("memberName");
            specialTitle=   call.getString("specialTitle");
            permission=   Permission.valueOf(call.getString("permission").toUpperCase());
            joinTimestamp=call.getLong("joinTimestamp");
            lastSpeakTimestamp=call.getLong("joinTimestamp");
            muteTimeRemaining=call.getLong("joinTimestamp");
        }

        public String getMemberName() {
            return memberName;
        }

        public String getSpecialTitle() {
            return specialTitle;
        }

        public Permission getPermission() {
            return permission;
        }

        public Long getJoinTimestamp() {
            return joinTimestamp;
        }

        public Long getLastSpeakTimestamp() {
            return lastSpeakTimestamp;
        }

        public Long getMuteTimeRemaining() {
            return muteTimeRemaining;
        }
    }
}
