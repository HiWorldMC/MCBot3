package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import org.bukkit.ChatColor;

import java.net.URISyntaxException;

public class NuBind extends BotCommand{
    String ID;
    QQDDataTable qqdDataTable;

    public NuBind(String CMD) {
        super(CMD);
    }

    @Override
    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
        this.CMD = CMD;
        this.args = args;
        this.msgEvent=msgEvent;
        if(msgEvent instanceof MsgType) {
            MsgType msgType= (MsgType) msgEvent;
            this.useID = msgType.getUseId();
            this.groupID = msgType.getGroupId();
        }
        ID=args[0];
        qqdDataTable=new QQDDataTable();
        return this;
    }

    @Override
    public void run() throws Exception {
        if(BotConfig.adminList.contains(useID)){
            if(args[0]!=null){
                if(qqdDataTable.getPlayerQQ(args[0])!=null) {
                    qqdDataTable.unBindQQ(args[0]);
                    At At = new At(useID);
                    String lang ="解除成功";
                    lang= ChatColor.stripColor(lang);
                    textChain txt = new textChain(lang);
                    MessageChain me = new MessageChain(At,txt);
                    msgEvent.sendMsg(me);
                }else {
                    At At = new At(useID);
                    String lang ="此ID未绑定 QQ";
                    lang= ChatColor.stripColor(lang);
                    textChain txt = new textChain(lang);
                    MessageChain me = new MessageChain(At,txt);
                    msgEvent.sendMsg(me);
                }
            }

        }else {
            sendMsg(useID,"Group_NotPermission");
        }
    }

    private void sendMsg(Long QQ,String key) throws URISyntaxException {
        At At = new At(useID);
        String lang =getLang(key);
        lang= ChatColor.stripColor(lang);
        textChain txt = new textChain(lang);
        MessageChain me = new MessageChain(At,txt);
        msgEvent.sendMsg(me);
    }
    protected   String getLang(String key){
        String s= Client.getMcBot().getLang().get(key);
        return s;
    }
}
