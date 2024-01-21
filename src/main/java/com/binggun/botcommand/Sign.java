package com.binggun.botcommand;

import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.signs.SignAPI;
import org.bukkit.entity.Player;

public class Sign  extends BotCommand {

    public Sign(String cmd){
        super(cmd);
    }

    @Override
    public void run() throws Exception {
        if(getMsgEvent() instanceof MsgType) {
            SignAPI.sign((MsgType) getMsgEvent());
        }
    }

    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
        return initialCommandManage(CMD,args,msgEvent,this);
    }


    public Sign(String CMD, String[] args, MsgEvent msgEvent) {
        super(CMD,args,msgEvent);

    }
}
