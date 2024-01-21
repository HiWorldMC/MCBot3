package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.MemberCardChangeEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;

public class MemberCardChangeBukkitEvent extends MsgBukkitEvent {
    public  MemberCardChangeEvent memberCardChangeEvent;

    public MemberCardChangeBukkitEvent(MemberCardChangeEvent memberCardChangeEvent) {
  this.memberCardChangeEvent=memberCardChangeEvent;
    }


    @Override
    public MsgEvent getMsgEvent() {
        return memberCardChangeEvent;
    }
}
