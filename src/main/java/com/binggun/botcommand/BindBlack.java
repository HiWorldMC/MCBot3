package com.binggun.botcommand;

import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.config.BindConfig;
import com.binggun.mcplugin.config.BotConfig;
import org.bukkit.ChatColor;

import java.net.URISyntaxException;

//BindBlack QQ remove 删除黑名单
//BindBlack QQ 加入黑名单

public class BindBlack extends BotCommand {

    @Override
    public void run() throws Exception {
        if(BotConfig.adminList.contains(useID)) {
            if (args[0] != null) {
                try {
                    Long qq = Long.valueOf(args[0]);
                    boolean remove = false;
                    if (args[1] != null) {
                        if (args[1].equalsIgnoreCase("remove")) {
                            remove = true;
                        }
                    }
                    if (remove) {
                        BindConfig.removeBlack(qq);
                        sendMsg(useID, "删除黑名单成功");
                    } else {
                        BindConfig.addBlack(qq);
                        sendMsg(useID, "添加黑名单成功");
                    }
                } catch (Exception e) {
                    sendMsg(useID, "CMD_Error");
                }

            }
        }else {
            sendMsg(useID,"Group_NotPermission");
        }
    }
    MsgEvent msgEvent;
    public BindBlack(String 绑定ID) {
        super(绑定ID);
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
        return this;
    }
    private void sendMsg(Long QQ,String key) throws URISyntaxException {
        At At = new At(QQ);
        String lang =getLang(key);
        lang= ChatColor.stripColor(lang);
        textChain txt = new textChain(lang);
        MessageChain me = new MessageChain(At,txt);
        msgEvent.sendMsg(me);
    }

}
