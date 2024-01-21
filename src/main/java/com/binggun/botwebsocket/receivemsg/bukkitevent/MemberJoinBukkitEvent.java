package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.MemberJoinEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;

public class MemberJoinBukkitEvent extends MsgBukkitEvent {
    public MemberJoinEvent msgEvent;

    public MemberJoinBukkitEvent(MemberJoinEvent msgBcEvent) {
        super();
       this. msgEvent=msgBcEvent;
    }


    @Override
    public MsgEvent getMsgEvent() {
        return msgEvent;
    }
}
