package com.binggun.botwebsocket.sendmsg.chain;

import com.binggun.botwebsocket.sendmsg.chain.Chain;

import java.util.List;

//消息链 构造
public class MessageChain {
    Chain[] messageChain;

    public Chain[] getMessageChain() {
        return messageChain;
    }
    public void setMessageChain(List<Chain> c) {
        Chain[] chains= new Chain[c.size()];
        int x=0;
        for(Chain chain:c){
            chains[x] =chain;
            x=x+1;
        }
        messageChain =chains;
    }


    public MessageChain(Chain... chains){
        this.messageChain=chains;
    }
    public String toString(){
        String s="[\n";
        int v= messageChain.length;
        int x=1;
        for(Chain c:messageChain){
            String e= c.toString();
            s=s+e;
            if(v!=x){
                s=s+",\n";
            }
                x=x+1;
        }
        s=s+"\n]";
        return s;
    }

}
