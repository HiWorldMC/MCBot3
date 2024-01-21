package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.FiendMsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;

//接收到好友消息
public class FiendMsgBukkitEvent extends MsgBukkitEvent {


    public FiendMsgEvent fiendMsgEvent;
    public FiendMsgBukkitEvent(FiendMsgEvent fiendMsgEvent) {
        super();
        this.fiendMsgEvent=fiendMsgEvent;
    }


    @Override
    public MsgEvent getMsgEvent() {
        return fiendMsgEvent;
    }
}
