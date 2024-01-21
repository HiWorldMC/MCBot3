package com.binggun.botwebsocket.sendmsg.chain;

public class textChain implements Chain {
    String type;
    String text;
    public textChain(String text){
        this.text=text;
        this.type="Plain";
    }
    public String toString(){
        String c ="      {\"type\":\""+type+"\"," +
                "\"text\":\""+text+"\"}";
        return c;
    }
}