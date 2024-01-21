package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.sun.istack.internal.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MessageSyncBukkitEvent  extends Event {
    String msg;
    MsgEvent msgEvent;
    SyncType syncType;
    private boolean cancel = false;
    Player player;
    public MessageSyncBukkitEvent(String msg, MsgEvent msgEvent) {
        super(true);
        this.msg = msg;
        this.msgEvent = msgEvent;
        this.syncType = SyncType.GROUP;
    }

    public MessageSyncBukkitEvent(String msg, Player player) {
        super(true);
        this.msg = msg;
        this.syncType = SyncType.SERVER;
        this.player=player;
    }

    public String getMsg() {
        return msg;
    }
    @NotNull
    public MsgEvent getMsgEvent() {
        return msgEvent;
    }
    @NotNull
    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return cancel ;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {

        return handlers;
    }
static enum SyncType{
        GROUP,SERVER;
}
}
