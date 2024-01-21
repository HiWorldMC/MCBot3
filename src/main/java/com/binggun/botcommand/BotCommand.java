package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.bukkitevent.MsgBukkitEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
//指令父类
public abstract class BotCommand {

    public String CMD;
    public String[] args;
    public Long useID;
    public Long groupID;
    public MsgEvent msgEvent;

    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
         return initialCommandManage(CMD,args,msgEvent,this);
    }



    public BotCommand(String CMD, String[] args, MsgEvent msgEvent) {
       this.CMD = CMD;
       this.args = args;
       this.msgEvent=msgEvent;
       if(msgEvent instanceof MsgType) {
           MsgType msgType= (MsgType) msgEvent;
           this.useID = msgType.getUseId();
           this.groupID = msgType.getGroupId();
       }
    }

    public BotCommand(String CMD) {
        this.CMD = CMD;
    }

    public abstract void run() throws Exception;
    public BotCommand initialCommandManage(String CMD, String[] args, MsgEvent msgEvent,BotCommand botCommand){
        botCommand.CMD = CMD;
        botCommand.args = args;
        botCommand.msgEvent=msgEvent;
        if(msgEvent instanceof MsgType) {
            MsgType msgType= (MsgType) msgEvent;
            botCommand.useID = msgType.getUseId();
            botCommand.groupID = msgType.getGroupId();
        }
        return botCommand;
    }

    public String getCMD() {
        return CMD;
    }

    public String[] getArgs() {
        return args;
    }

    public Long getUseID() {
        return useID;
    }

    public Long getGroupID() {
        return groupID;
    }

    public MsgEvent getMsgEvent() {
        return msgEvent;
    }

    protected   String getLang(String key){
        String s= Client.getMcBot().getLang().get(key);
        return s;
    }

}
