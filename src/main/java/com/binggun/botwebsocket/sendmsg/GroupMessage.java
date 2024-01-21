package com.binggun.botwebsocket.sendmsg;

import com.binggun.botwebsocket.Content;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;

//发送群消息构造 s
public class GroupMessage extends Content {
    //目标
    Long target;
    //消息链
    public MessageChain messageChain;
    public GroupMessage(Long target, MessageChain messageChain){
        this.messageChain=messageChain;
        this.target=target;
    }

    public String toString() {
        String s ="{\n    \"target\":"+target+",\n" +
                "    \"messageChain\":" +
                messageChain.toString()+"}";
        return s;
    }

    @Override
    public String getCommand() {
        return "sendGroupMessage";
    }
}
