package com.binggun.botwebsocket;


import java.net.URI;
import java.net.URISyntaxException;


import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.alibaba.fastjson.JSONObject;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.McBot;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.util.Callback;
import com.binggun.util.CallbackUtil;
import com.binggun.util.groupmanage.GroupUserInfoUtil;
import org.bukkit.scheduler.BukkitRunnable;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

//ws 客户端 负责接收机器人消息
public class Client extends WebSocketClient {

public Client(URI serverUri) {
 super(serverUri);
 this.connect();
}
 public static McBot mcBot;

 public static McBot getMcBot() {
  return mcBot;
 }

 public static void setMcBot(McBot mcbot) {
  Client.mcBot = mcbot;
 }

 @Override
 public void onMessage(String arg0) {
  // TODO Auto-generated method stub
  //System.out.println("-------- 接收到服务端数据： " + arg0 + "--------");
  JSONObject json =JSONObject.parseObject(arg0);
  if(json.getJSONObject("data")!=null){
   JSONObject data= json.getJSONObject("data");
   String syncID =json.getString("syncId");
   if(syncID!=null){
    CallbackUtil.notifyCallback(syncID,data);
   }

   if(data.getString("type")!=null){
    String msgType =data.getString("type");
    try {
     MsgEvent msgEvent = BotEventInit.init(msgType,data);
     if(msgEvent!=null) {
      msgEvent.run(getMcBot());
     }
    } catch (Exception e) {
     throw new RuntimeException(e);
    }

   }}
 }


 @Override
public void onOpen(ServerHandshake arg0) {
// TODO Auto-generated method stub
 System.out.println("------ 机器人已连接 ------");
  for(Long g:BotConfig.groupList) {
   String start =MCBotJavaPlugin.mcBotJavaPlugin.getLang().get("Bot_Start");
   if(start!=null) {
    textChain txt = new textChain(start);
    MessageChain me = new MessageChain(txt);
    GroupMessage c = new GroupMessage(g, me);
    BotCommandSend cmd = new BotCommandSend("123", "sendGroupMessage", null, c);
    try {
     Client.getClient().send(cmd.toString());
    } catch (URISyntaxException e) {
     throw new RuntimeException(e);
    }
   }
  }
}
 
@Override
public void onClose(int arg0, String arg1, boolean arg2) {
 // TODO Auto-generated method stub
 System.out.println("------ 机器人连接关闭 ------");

}
 
@Override
public void onError(Exception arg0) {
 // TODO Auto-generated method stub
 System.out.println("------ 机器人连接错误 请检查机器人IP 端口 QQ 密钥  ------");
}




 public static   JSONObject callV;


 public static  void runs() throws URISyntaxException {
  //ws://0.0.0.0:8081/all?verifyKey=728482805&qq=728482805
  client =new Client(new URI("ws://"+mcBot.getIP()+"/all?verifyKey="+ mcBot.getVerifyKey() +"&qq="+ mcBot.getBotQQ()));
 }

// public static String Getm() {
//  textChain[] t = {new textChain("Plain","冰棍好帅"),new textChain("Plain","真帅")};
//  MessageChain m = new MessageChain(t);
//  GroupMessage c=    new GroupMessage(761859818l,m);
//  CMD cmd = new CMD("123","sendGroupMessage",null,c);
//  return  cmd.toString();
// }

 private static Client client;
 public static Client getClient() throws URISyntaxException {
  if(client==null){
   runs();
  }
  if(client.isClosed()){
   runs();
  }
  return client;
 }



}
