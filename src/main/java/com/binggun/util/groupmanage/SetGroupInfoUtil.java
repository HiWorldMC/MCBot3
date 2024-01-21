package com.binggun.util.groupmanage;

import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.sendmsg.groupmanage.SetGroupInfo;
import com.binggun.util.Callback;
import com.binggun.util.CallbackUtil;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class SetGroupInfoUtil {
    UUID uuid;
    Long group;
    Long qq;
    String name;
    String specialTitle;
    SetGroupInfo setGroupInfo;
    JSONObject call;
    private Callback<JSONObject, String> callbackUtil;
    public SetGroupInfoUtil(Long group, Long qq,String name,String specialTitle) {
        this.group = group;
        this.qq = qq;
        this.name=name;
        this.specialTitle=specialTitle;
        setGroupInfo=new SetGroupInfo(group,qq,name,specialTitle);
        uuid=UUID.randomUUID();
        this.callbackUtil = new CallbackUtil<>();

    }
    public boolean send() throws InterruptedException, URISyntaxException, ExecutionException {
        BotCommandSend botCommandSend = new BotCommandSend(uuid.toString(),setGroupInfo);
        botCommandSend.send();
       call= callbackUtil.callback(uuid.toString()).get();
        if(call!=null) {
            if (call.getInteger("code") == 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
