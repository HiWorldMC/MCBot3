package com.binggun.botcommand;

import com.FBinggun.MySQl.Util;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mailbox.Mail;
import com.binggun.mailbox.MailAPI;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.mcplugin.config.MailConfig;
import com.binggun.mcplugin.data.sql.table.MailBoxTable;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.util.BotUtil;
import com.binggun.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//发物品 [玩家] [物品] 数量 参数1[N:10 10天内在线的玩家 F:10 10天为在线的玩家]
public class GiveItem extends BotCommand{



    public GiveItem(String cmd){
        super(cmd);
    }

    public BotCommand getCMDManage(String CMD, String[] args, MsgEvent msgEvent) {
        return initialCommandManage(CMD,args,msgEvent,this);
    }


    public GiveItem(String CMD, String[] args, MsgEvent msgEvent) {
        super(CMD,args,msgEvent);

    }

    @Override
    public void run() throws Exception {
        if(args.length<3){
            return;
        }
        if(msgEvent instanceof MsgType) {
            if (!BotConfig.adminList.contains(useID)){
                sendMsg("Group_NotPermission",0);
                return;
            }
        }
        new BukkitRunnable(){
            @Override
            public void run() {

                QQDDataTable qqdDataTable=   new QQDDataTable();
                try {
                    ConfigurationSection configurationSection = MailConfig.mailConfig.getConfigurationSection("物品");
                    if(configurationSection==null){
                        sendMsg("Mail_ConfigNull",0);
                        return;
                    }
                    Long at = BotUtil.getAt(args[1]);
                    String p;
                    if(at==null) {
                        p=args[0];
                    }else {
                       p =qqdDataTable.getPlayerName(at);
                       if(p==null){
                           sendMsg("Bind_Null",0);
                           return;
                       }
                    }
                    List<String> players = getGivePlayer(p);
                    if(players.size()!=0) {
                        for (String player : players) {
                            Mail mail = MailAPI.createMail(configurationSection, player);
                            if(mail==null){
                                continue;
                            }
                            List<String> command = mail.getCommand();
                            List<String> command2 = new ArrayList<>();
                            for (String str : command) {
                                str = str.replaceAll("%Player%", player);
                                str = str.replaceAll("%args1%", args[1]);
                                str = str.replaceAll("%args2%", args[2]);
                                command2.add(str);
                            }
                            mail.setCommand(command2);
                            new MailBoxTable().setMails(mail);
                        }
                        if(args[0].equalsIgnoreCase("*")) {
                            if (args[3] != null) {
                                if (args[3].startsWith("N")) {
                                    int day = Integer.parseInt(args[3].replace("N:",""));
                                    sendMsg("Give_SuccessAllN",day);
                                    return;
                                }
                                if (args[3].startsWith("F")) {
                                    int day = Integer.parseInt(args[3].replace("F:",""));
                                    sendMsg("Give_SuccessAllF",day);
                                    return;
                                }
                                if (args[3].startsWith("Z")) {
                                    sendMsg("Give_SuccessAllZ",0);
                                }

                            }else {
                                sendMsg("Give_SuccessAll",0);
                            }
                        }else {
                            sendMsg("Give_Success",0);
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(MCBotJavaPlugin.getProvidingPlugin(MCBotJavaPlugin.class));

    }
    public List<String> getGivePlayer(String p) throws Exception {
        List<String> list = new ArrayList<>();
        String player = p;
        if(player.equalsIgnoreCase("*")){
            if(args[3]!=null){
            if(args[3].startsWith("N")){
                int day = Integer.parseInt(args[3].replace("N:",""));
                List<String> players = getPlayerTiem(day,true);
                if(players.size()!=0) {
                    list.addAll(players);
                }
            }else if(args[3].startsWith("F")){
                int day = Integer.parseInt(args[3].replace("F:",""));
                List<String> players = getPlayerTiem(day,false);
                if(players.size()!=0) {
                    list.addAll(players);
                }
            }else if(args[3].startsWith("Z")){
                for(Player player1: Bukkit.getOnlinePlayers()){
                    list.add(player1.getName());
                }
            }else  {
                List<String> players =   getPlayerTiem(999,true);
                if(players.size()!=0) {
                    list.addAll(players);
                }
            }
            }else {
                List<String> players =   getPlayerTiem(999,true);
                if(players.size()!=0) {
                    list.addAll(players);
                }

            }

        }else {
            list.add(player);
        }
        return list;
    }



    //全部在线玩家
    public static List<String> getPlayerTiem(int ve,boolean te) throws Exception {
        List<String> list = new ArrayList<>();
        String t = "select * from "+new QQDDataTable().getQqData().getTablename();
        ResultSet s = new QQDDataTable().getQqData().getSql().getStatement().executeQuery(t);
        while (s.next()){
            String p = s.getString("ID");
            Long qq = s.getLong("QQ");
            if(qq!=null&&qq!=0){
                Long date = Util.date()-(86400000*ve);
                String r =s.getString("LOGIN");
                if(r!=null){
                    if(Util.ISTimeLimit(Util.getDateTiemString(r),date,te)){
                        list.add(p);
                    }
                }

            }
        }
        return list;
    }
    private String getLang(String key,int day){
        String s= Client.getMcBot().getLang().get(key);
        s=s.replaceAll("%player%",args[0]);
        s=s.replaceAll("%Tiem%",String.valueOf(day));
        s= BukkitUtil.stripColor(s);
        return s;
    }

    private void sendMsg(String key,int day) throws URISyntaxException {
        At At = new At(getUseID());
        textChain txt = new textChain(getLang(key,day));
        MessageChain me = new MessageChain(At,txt);
        getMsgEvent().sendMsg(me);
    }
}
