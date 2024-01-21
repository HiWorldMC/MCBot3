package com.binggun.botwebsocket.receivemsg.chain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

//消息链类型 父类
public abstract class ChainJk {
    public String type;
    public ChainJk(String type){
        this.type=type;
    }

    public static ChainJk[] GetChainJk(JSONArray msg){
        ChainJk[] c = new ChainJk[msg.size()];
        for(int x =1;x<=msg.size();x++){
            JSONObject json = msg.getJSONObject(x-1);
            String type = json.getString("type");
           if(type.equals("Plain")){
               String  text=  json.getString("text");
              c[x-1]=new Plain(type,text);
           }else if(type.equals("At")){
               Long  atQQ=  json.getLong("target");
               c[x-1]=new ChainAt(type,atQQ);
           }else

           {

               c[x-1]=new NullChain(type);
           }
        }
        return c;
    }
}
