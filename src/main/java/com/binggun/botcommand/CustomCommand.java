package com.binggun.botcommand;

import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.customcmd.CustomCMD;
import com.binggun.util.BukkitUtil;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;

public class CustomCommand  extends BotCommand {
    public  CustomCMD customCMD;

    public CustomCommand(String CMD,CustomCMD customCMD) {
        super(CMD);
       this.customCMD=customCMD;
    }

    @Override
    public void run() throws Exception {
        if(customCMD.getCommands()!=null){
            List<String> cmd=new ArrayList<>();
            List<String> cmd2=new ArrayList<>();
            if(args!=null&&args.length!=0){
                cmd  = customCMD.getCommands(useID,args);
            }else {
                cmd=customCMD.getCommands();
            }
            for(String cm:cmd){
             cm= cm.replaceAll("%QQ%", String.valueOf(useID));
                cmd2.add(cm);
            }
            BukkitUtil.runCommands(cmd2,null);
        }
        At At = new At(useID);
        textChain txt = new textChain(getMsg());
        MessageChain me = new MessageChain(At,txt);
        msgEvent.sendMsg(me);
        return;
    }

    @Override
    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
        BotCommand command = initialCommandManage(CMD,args,msgEvent,this);
        this.args = args;
        this.msgEvent=msgEvent;
        if(msgEvent instanceof MsgType) {
            MsgType msgType= (MsgType) msgEvent;
            this.useID = msgType.getUseId();
            this.groupID = msgType.getGroupId();
        }
        return command;
    }
    public String getMsg(){
        String s= customCMD.getMsg();
        s=s.replaceAll("%n%","\n");
        s= s.replaceAll("%QQ%", String.valueOf(useID));
        int x=0;
        if(args!=null) {
            for (String str : args) {
                s = s.replaceAll("%args%" + x, args[x]);
                x = x + 1;
            }
        }
        s= PlaceholderAPI.setPlaceholders(null,s);
//        QQDDataTable qqdDataTable = new QQDDataTable();
//        s=s.replaceAll("%player_name%","\n");
        return s;
    }
}
