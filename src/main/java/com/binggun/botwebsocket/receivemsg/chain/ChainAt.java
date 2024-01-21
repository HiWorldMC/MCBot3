package com.binggun.botwebsocket.receivemsg.chain;

public class ChainAt extends ChainJk {
    Long target;
    public ChainAt(String type,Long target) {
        super(type);
        this.target=target;
    }

    public Long getTarget() {
        return target;
    }
}
