package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mailbox.MailAPI;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.mcplugin.config.MailConfig;
import com.binggun.util.BotUtil;
import com.binggun.util.BukkitUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.net.URISyntaxException;

//离线邮箱发送
public class SendMail extends BotCommand {

    public SendMail(String cmd){
        super(cmd);
    }

    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
        return initialCommandManage(CMD,args,msgEvent,this);
    }


    public SendMail(String CMD, String[] args, MsgEvent msgEvent) {
        super(CMD,args,msgEvent);

    }

    @Override
    public void run() throws Exception{
    if(args.length>=2) {
        if(Client.getMcBot().getPlugin().equalsIgnoreCase("BcPlugin")){
            sendMsg("MailGroup_Not");
              return;
        }
        if(BotConfig.adminList.contains(getUseID())){
                giveMail();
        }else {
            sendMsg("Group_NotPermission");
        }
    }
    }

    private void giveMail() throws Exception {
        ConfigurationSection configurationSection = MailConfig.mailConfig.getConfigurationSection(args[0]);
        if(configurationSection==null){
            sendMsg("Mail_ConfigNull");
            return;
        }
        Long at = BotUtil.getAt(args[1]);
        if(at==null) {
            MailAPI.giveMail(configurationSection, args[1], args);
            sendMsg("Mail_send");
        }else {
            MailAPI.giveMail(configurationSection, args,at,msgEvent);
        }
    }


    public String getLang(String key){
        String s= Client.getMcBot().getLang().get(key);
        s= BukkitUtil.stripColor(s);
        return s;
    }

    private void sendMsg(String key) throws URISyntaxException {
    At At = new At(getUseID());
    textChain txt = new textChain(getLang(key));
    MessageChain me = new MessageChain(At,txt);
    getMsgEvent().sendMsg(me);
    }
}
