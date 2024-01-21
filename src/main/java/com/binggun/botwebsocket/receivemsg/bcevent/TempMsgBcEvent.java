package com.binggun.botwebsocket.receivemsg.bcevent;

import com.binggun.botwebsocket.receivemsg.chain.ChainJk;
import com.binggun.botwebsocket.receivemsg.event.TempMsgEvent;

public class TempMsgBcEvent extends MsgBcEvent {
    public Long UseId;
    public String Usename;
    public Long GroupId;
    public  ChainJk[] Chain;
    public String text;
    public  TempMsgEvent tempMsgEvent;
    public TempMsgBcEvent(TempMsgEvent tempMsgEvent) {
        super();
    this.tempMsgEvent=tempMsgEvent;
    }

}
