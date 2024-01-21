package com.binggun.botcommand;


import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mailbox.Mail;
import com.binggun.mailbox.MailAPI;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.config.BindConfig;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.mcplugin.data.sql.table.MailBoxTable;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.mcplugin.listener.MemberNameListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//绑定QQ
public class Bind extends BotCommand {
    public static Map<String,Long> TempData = new HashMap<>();
    public static Map<Integer,String> TempVerificationData = new HashMap<>();

    String ID;
     Long QQ;
     QQDDataTable qqdDataTable;
    MsgEvent msgEvent;
    public Bind(String 绑定ID) {
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
        ID=args[0];
        QQ=useID;
        qqdDataTable=new QQDDataTable();
        return this;
    }

    public Bind(String CMD, String[] args, MsgEvent msgEvent) {
        super(CMD,args,msgEvent);
        ID=args[0];
        QQ=useID;
    }


    @Override
    public void run() throws SQLException, URISyntaxException {
        try {
            if(!BindConfig.refusedJoin) {
                bindIDReceive(msgEvent);
            }else {
                bindOffline(msgEvent);
            }
        } catch (ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    private void bindOffline(MsgEvent e) throws SQLException, ClassNotFoundException, URISyntaxException, InterruptedException, ExecutionException {
        if(args[0]==null){
            return;
        }
        if(qqdDataTable.getPlayerName(QQ)!=null){
            sendMsg(QQ,"Bind_Repetition");
            return;
        }
        int verification;
        try {
            verification  = Integer.parseInt(args[0]);
        }catch (Exception exception){
            sendMsg(QQ,"Bind_Verification_Error");
            return;
        }
        if(TempVerificationData.get(verification)!=null){
            ID =TempVerificationData.get(verification);
            String name= TempVerificationData.get(verification);
            if(qqdDataTable.getPlayerQQ(name)!=null){
                if(qqdDataTable.getPlayerQQ(name)==QQ){
                    sendMsg(QQ,"Bind_Repetition");
                }else {
                    sendMsg(QQ,"Bind_Repetition2");
                }
                return;
            }
            if(qqdDataTable.isBind(name)){
                sendMsg(QQ,"Bind_Repetition");
                return;
            }
            TempVerificationData.remove(verification);
            qqdDataTable.bindQQ(name,QQ);
            sendMsg(QQ,"Bind_Success2");
            new BukkitRunnable() {
                @Override
                public void run() {
                    for(Long group: BotConfig.groupList){
                        try {
                            MemberNameListener.setName(group,QQ,name);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        } catch (URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        } catch (ExecutionException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }.runTaskTimerAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin,10,10);

        }else {
              sendMsg(QQ,"Bind_Verification_Error");
        }
    }

    //绑定QQ
    private void bindIDReceive(MsgEvent e) throws SQLException, URISyntaxException, ClassNotFoundException {
        if(Bukkit.getPlayer(ID)==null){
            sendMsg(QQ,"Bind_GroupOffline");
            return;
        }
        if(qqdDataTable.getPlayerName(QQ)!=null){
            sendMsg(QQ,"Bind_Repetition");
            return;
        }
        Player player =Bukkit.getPlayer(ID);
        if(qqdDataTable.getPlayerQQ(player.getUniqueId())!=null){
            if(qqdDataTable.getPlayerQQ(player.getUniqueId())==QQ){
                sendMsg(QQ,"Bind_Repetition");
            }else {
                sendMsg(QQ,"Bind_Repetition2");
            }
            return;
        }
        if(qqdDataTable.isBind(player.getName())){
            sendMsg(QQ,"Bind_Repetition");
            return;
        }
        TempData.put(ID,QQ);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bind.TempVerificationData.remove(ID);
            }
        }.runTaskLaterAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin,BindConfig.overdueTime);

        Bukkit.getPlayer(ID).sendMessage(getLang("Bind_Receive"));
        sendMsg(QQ,"Bind_GroupReceive");
    }
    private void sendMsg(Long QQ,String key) throws URISyntaxException {
        At At = new At(QQ);
        String lang =getLang(key);
        lang= ChatColor.stripColor(lang);
        textChain txt = new textChain(lang);
        MessageChain me = new MessageChain(At,txt);
        msgEvent.sendMsg(me);
    }


    //绑定QQ
    public static void BindQQ(Long QQ, Player p) throws Exception {
        QQDDataTable    qqdDataTable=new QQDDataTable();
        qqdDataTable.bindQQ(p.getName(),QQ);
        TempData.remove(p.getName());
        p.sendMessage(getLang("Bind_Success",QQ));
        new BukkitRunnable() {
            @Override
            public void run() {
                Mail mail= null;
                try {
                    for(Long group: BotConfig.groupList){
                        MemberNameListener.setName(group,QQ,p.getName());
                    }
                    mail = MailAPI.createMail(BindConfig.rewardMail, p.getName());
                    if(mail==null){
                        return;
                    }
                    new MailBoxTable().setMails(mail);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }.runTaskLaterAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin,40);

    }
    private static String getLang(String key,Long qq){
       String s= Client.getMcBot().getLang().get(key);
      s= s.replaceAll("%QQ%", String.valueOf(qq));
      s=  s.replaceAll("%qq%", String.valueOf(qq));
        return s;
    }
    public   String getLang(String key){
        String s= Client.getMcBot().getLang().get(key);
        s= s.replaceAll("%QQ%", String.valueOf(QQ));
        s=  s.replaceAll("%qq%", String.valueOf(QQ));
        return s;
    }

}
