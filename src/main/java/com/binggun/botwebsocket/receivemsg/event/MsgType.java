package com.binggun.botwebsocket.receivemsg.event;

import com.binggun.botwebsocket.receivemsg.chain.ChainJk;

public  interface MsgType extends MsgEvent {
    public Long getUseId();
    public Long getGroupId();
    public ChainJk[] getChainJks();
    public String getUseName();

}
