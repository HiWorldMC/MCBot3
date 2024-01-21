package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.GroupMsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;

//群消息 监听器
public class GroupMsgBukkitEvent extends MsgBukkitEvent {

    public GroupMsgEvent groupMsgEvent;
    public GroupMsgBukkitEvent(GroupMsgEvent groupMsgEvent) {
        super();
        this.groupMsgEvent=groupMsgEvent;
    }


    @Override
    public MsgEvent getMsgEvent() {
        return groupMsgEvent;
    }
}
