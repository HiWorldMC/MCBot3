package com.binggun.botwebsocket.receivemsg.bcevent;

import com.binggun.botwebsocket.receivemsg.event.GroupMsgEvent;

//群消息 监听器
public class GroupMsgBcEvent extends MsgBcEvent {

    GroupMsgEvent groupMsgEvent;
    public GroupMsgBcEvent(GroupMsgEvent groupMsgEvent) {
        super();
        this.groupMsgEvent=groupMsgEvent;

    }





}
