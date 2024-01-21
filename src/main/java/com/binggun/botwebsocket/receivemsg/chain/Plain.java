package com.binggun.botwebsocket.receivemsg.chain;
//文本消息
public class Plain extends ChainJk {
    public String text;
    public Plain(String type,String text) {
        super("Plain");
        this.text=text;
    }

    public static String GetMsg(ChainJk[] p){
        String s = "";
        for(ChainJk c:p){
            if(c instanceof  Plain){
                Plain ce = (Plain) c;
                s=s+ce.text;
            }
            if(c instanceof ChainAt){
                s=s+"At:"+((ChainAt) c).target;
            }
        }
        return s;
    }
}
