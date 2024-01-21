package com.binggun.botwebsocket.sendmsg;

import com.binggun.botwebsocket.Content;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;

//好友消息
public class FriendMessage extends Content {

    //目标
    Long target;
    //消息链
    public MessageChain messageChain;
    public String cmd ="sendFriendMessage";
    public String toString() {
        String s ="{\n    \"qq\":"+target+",\n" +
                "    \"messageChain\":" +
                messageChain.toString()+"}";
        return s;
    }

    @Override
    public String getCommand() {
        return "sendFriendMessage";
    }

    public FriendMessage(Long target, MessageChain messageChain) {
        this.target = target;
        this.messageChain = messageChain;
    }
}
