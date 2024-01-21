package com.binggun.botwebsocket.receivemsg.bcevent;

import com.binggun.botwebsocket.receivemsg.event.FiendMsgEvent;

//接收到好友消息
public class FiendMsgBcEvent extends MsgBcEvent {


    public FiendMsgEvent fiendMsgEvent;
    public FiendMsgBcEvent(FiendMsgEvent fiendMsgEvent) {
        super();
        this.fiendMsgEvent=fiendMsgEvent;

    }




}
