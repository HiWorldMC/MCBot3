package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.BotOfflineEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;

public class BotOfflineBukkitEvent extends MsgBukkitEvent {
    public  Long botQQ;
    public BotOfflineEvent botOfflineEvent;
    public BotOfflineBukkitEvent(BotOfflineEvent botOfflineEvent) {
        this.botOfflineEvent=botOfflineEvent;
    }

    @Override
    public MsgEvent getMsgEvent() {
        return botOfflineEvent;
    }
}
