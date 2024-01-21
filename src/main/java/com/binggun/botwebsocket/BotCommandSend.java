package com.binggun.botwebsocket;

import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;


import java.net.URISyntaxException;

//ws服务器指令构造

public class BotCommandSend {
    String syncId ;
    String command;
    String subCommand;
    Content cntent;

    public BotCommandSend(String syncId, String command, String subCommand, Content cntent){
        this.subCommand=subCommand;
        this.cntent=cntent;
        this.command=command;
        this.syncId=syncId;
    }
    public BotCommandSend(String syncId, Content cntent){
        this.subCommand=cntent.getSubCommand();
        this.cntent=cntent;
        this.command=cntent.getCommand();
        this.syncId=syncId;
    }


    public String toString(){
        if(subCommand==null){
            subCommand="null";
        }
        if(syncId==null){
            syncId="";
        }
        String s ="{\n" +
                "  \"syncId\":"+syncId+",\n" +
                "  \"command\":\"" +command+"\",\n" +
                "  \"subCommand\":"+subCommand+",\n" +
                "  \"content\":"+cntent.toString()+"}";
        return s;
    }

public  void send() throws URISyntaxException {
    Client.getClient().send(this.toString());
}

    //在这写个json示例
    public static String Getm() {
        textChain[] t = {new textChain("冰棍好帅"),new textChain("真帅")};
        MessageChain m = new MessageChain(t);
        GroupMessage c=    new GroupMessage(761859818l,m);
        BotCommandSend cmd = new BotCommandSend("123","sendGroupMessage",null,c);
    return  cmd.toString();
    }

}
