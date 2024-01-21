package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.MemberLeaveEventQuitEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;

public class MemberLeaveEventQuitBukkitEvent extends MsgBukkitEvent{


    public MemberLeaveEventQuitEvent msgEvent;

    public MemberLeaveEventQuitBukkitEvent(MemberLeaveEventQuitEvent msgBcEvent) {
        super();
        this. msgEvent=msgBcEvent;
    }
    @Override
    public MsgEvent getMsgEvent() {
        return msgEvent;
    }
}
