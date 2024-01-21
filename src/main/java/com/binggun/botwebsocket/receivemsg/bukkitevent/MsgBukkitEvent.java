package com.binggun.botwebsocket.receivemsg.bukkitevent;

import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class MsgBukkitEvent extends Event {
    public MsgBukkitEvent() {
        super(true);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {

        return handlers;
    }

    public abstract MsgEvent getMsgEvent() ;
}
