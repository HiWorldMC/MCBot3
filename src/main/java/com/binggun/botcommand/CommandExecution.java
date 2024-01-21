package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.util.BukkitUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URISyntaxException;

//执行指令
public class CommandExecution extends BotCommand{
    public CommandExecution(String cmd){
        super(cmd);
    }
    public CommandExecution(String CMD, String[] args, MsgEvent msgEvent) {
        super(CMD,args,msgEvent);

    }
    @Override
    public void run() throws Exception {
        if(BotConfig.adminList.contains(getUseID())){
            if(args.length!=0) {
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        String command = "";
                        for (String str : args) {
                            command=command+str+" ";
                        }
                        BukkitUtil.runCommand(command);
                        try {
                            sendMsg("Group_CommandRun");
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }.runTask(MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class));

            }else {
                sendMsg("Group_CommandError");
            }
        }else {
            sendMsg("Group_NotPermission");
        }
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


    protected String getLang(String key){
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
