package com.binggun.botwebsocket.sendmsg;

import com.binggun.botwebsocket.Content;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;

public class TempMessage extends Content {

        Long qq;
        Long group;
          MessageChain messageChain;

    public TempMessage(Long qq, Long group, MessageChain messageChain) {
        this.qq = qq;
        this.group = group;
        this.messageChain = messageChain;
    }

    public String toString() {
        String s ="{\n    \"qq\":"+qq+",\n" +
                "group:" +group+",\n    \"messageChain\":" +
                messageChain.toString()+"}";
        return s;
    }


    @Override
    public String getCommand() {
        return "sendTempMessage";
    }
}
