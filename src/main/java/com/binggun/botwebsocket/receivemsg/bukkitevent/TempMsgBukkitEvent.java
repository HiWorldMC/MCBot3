package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.TempMsgEvent;

public class TempMsgBukkitEvent extends MsgBukkitEvent {

    public  TempMsgEvent tempMsgEvent;
    public TempMsgBukkitEvent(TempMsgEvent tempMsgEvent) {
        super();
       this.tempMsgEvent=tempMsgEvent;
    }

    @Override
    public MsgEvent getMsgEvent() {
        return tempMsgEvent;
    }
}
