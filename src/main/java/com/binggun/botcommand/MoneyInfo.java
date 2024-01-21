package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.net.URISyntaxException;

public class MoneyInfo extends BotCommand {


    public MoneyInfo(String CMD) {
        super(CMD);
    }

    @Override
    public void run() throws Exception {
        Long QQ =useID;
        String player = new QQDDataTable().getPlayerName(QQ);
        if(player!=null){
            send(getLang("Money_Info",player));
        }else {
            send(getLang("Bind_Null",null));
        }
    }

    void  send(String msg) throws URISyntaxException {
        At At = new At(useID);
        textChain txt = new textChain(msg);
        MessageChain me = new MessageChain(At,txt);
        msgEvent.sendMsg(me);
    }

    @Override
    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
        BotCommand command = initialCommandManage(CMD,args,msgEvent,this);
        return command;
    }
    public String getLang(String key,String player){
        String s= Client.getMcBot().getLang().get(key);
        setupEconomy();
        if(player!=null) {
            s = s.replaceAll("%Money%", String.valueOf(eco.getBalance(player)));
        }
        return s;
    }
    static Economy eco;
    public static void setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider =  Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            eco = economyProvider.getProvider();
        }
    }
}
