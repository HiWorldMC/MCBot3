package com.binggun.botwebsocket.sendmsg.chain;

//发送消息类型 艾特@
public class At implements Chain {
    String type ="At";
    Long target;

    public At(Long target){
        this.target=target;

    }
    public String toString(){
        String c ="      {\"type\":\""+type+"\"," +
                "\"target\":"+target+"}";
        return c;
    }

}
