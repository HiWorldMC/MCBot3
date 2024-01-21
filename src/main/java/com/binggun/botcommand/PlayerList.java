package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

//查看在线玩家列表
public class PlayerList extends BotCommand {


    public PlayerList(String CMD) {
        super(CMD);
    }

    @Override
    public void run() throws Exception {
        At At = new At(useID);
        textChain txt = new textChain(getLang("PlayerList"));
        MessageChain me = new MessageChain(At,txt);
        msgEvent.sendMsg(me);
        return;
    }

    @Override
    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
        BotCommand command = initialCommandManage(CMD,args,msgEvent,this);
        return command;
    }
    public String getLang(String key){
        String s= Client.getMcBot().getLang().get(key);
        String ps ="";
        for(Player p:Bukkit.getOnlinePlayers()){
            ps=ps+p.getName()+" ";
        }
        int count = Bukkit.getOnlinePlayers().size();
        s=s.replaceAll("%Player_List%", ps);
         s= s.replaceAll("%Player_Count%", String.valueOf(count));
        return s;
    }

}
