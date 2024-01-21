package com.binggun.signs;

import com.binggun.botcommand.YaoQing;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mailbox.Mail;
import com.binggun.mailbox.MailAPI;
import com.binggun.mcplugin.config.Config;
import com.binggun.mcplugin.data.sql.table.MailBoxTable;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.mcplugin.data.sql.table.QdTable;
import com.binggun.util.BukkitUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignAPI {

    public static void sign(Player player) throws Exception {
        if( new QQDDataTable().getPlayerQQ(player.getName())!=null) {
            QdTable qdTable = new QdTable();
            if (qdTable.isQd(player.getName())) {
                qdTable.upGroup(player);
                qdTable.upQd(player.getName());
                String group = qdTable.getGroup(player.getName());
                runCommand(qdTable.getGroup(player.getName()), player);
                player.sendMessage(getLang(getMsg(group), player));
            } else {
                player.sendMessage(getLang("Sign_Repetition", player));
            }
        }else {
            player.sendMessage(getLang("Bind_Null",player));
        }
    }

    public static void sign(MsgType msgType) throws Exception {
                Long QQ =msgType.getUseId();
                String player = new QQDDataTable().getPlayerName(QQ);
                 QdTable qdTable =new QdTable();
                if(player!=null){
                    if (qdTable.isQd(player)) {
                        qdTable.upQd(player);
                        String group = qdTable.getGroup(player);
                        giveMail(player, group);
                        At At = new At(QQ);
                        textChain txt = new textChain(getLang(getMsg(group), QQ));
                        MessageChain me = new MessageChain(At, txt);
                        msgType.sendMsg(me);
                    }else {
                        At At = new At(QQ);
                        textChain txt = new textChain(getLang("Sign_Repetition", QQ));
                        MessageChain me = new MessageChain(At, txt);
                        msgType.sendMsg(me);
                    }
                }else {
                    At At = new At(QQ);
                    textChain txt = new textChain(getLang("Bind_Null",QQ));
                    MessageChain me = new MessageChain(At,txt);
                    msgType.sendMsg(me);
                }
    }
    private static String getMsg(String group){
        if(group!=null&&!group.equalsIgnoreCase("Default")) {
           return Config.qdConfig.getString("Group."+group+".Msg");
        }else {
            return Config.qdConfig.getString("Group.Default.Msg");
        }
    }


    private static void giveMail(String player,String group) throws Exception {
        List<String> commands=new ArrayList<>();
        if(group!=null) {
            commands  = Config.qdConfig.getStringList("Group."+group+".Command");
            if (commands == null||commands.size()==0) {
                commands = Config.qdConfig.getStringList("Group."+"Default.Command");
            }
        }else {
            commands = Config.qdConfig.getStringList("Group."+"Default.Command");
        }
        List<String> command2 = new ArrayList<>();
        command2.addAll(commands);
        for(String str:commands){
            if(str.contains("%Bot_Invitation%")){
                Long QQ =new QQDDataTable().getPlayerQQ(player);
                if(YaoQing.getInvitation(QQ)==0){
                    command2.remove(str);
                }else {
                    command2.remove(str);
                    str=str.replaceAll("%Bot_Invitation%",String.valueOf(YaoQing.getInvitation(QQ)));
                    command2.add(str);
                }
            }
        }

        Mail mail =   MailAPI.createMail(Config.qdConfig.getConfigurationSection("Mail"),player);
        mail.setCommand(command2);
        new MailBoxTable().setMails(mail);
    }


    private static String getLang(String key, Player player){
        String s= Client.getMcBot().getLang().get(key);
        if(s==null){
            s=key;
        }
        s = PlaceholderAPI.setPlaceholders(player, s);
        return s;
    }
    private static String getLang(String key, Long QQ) throws SQLException, ClassNotFoundException {
        String player = new QQDDataTable().getPlayerName(QQ);
        String s= Client.getMcBot().getLang().get(key);
        if(s==null){
            s=key;
        }
        s=org.bukkit.ChatColor.stripColor(s);
        s = s.replaceAll("%Bot_Invitation%", String.valueOf(YaoQing.getInvitation(QQ)));
        return s;
    }



    private static void runCommand(String Group,Player player) throws SQLException, ClassNotFoundException {
       List<String> commands = Config.qdConfig.getStringList("Group."+Group+".Command");
       if(commands==null||commands.size()==0){
           commands = Config.qdConfig.getStringList("Group."+"Default.Command");
       }
        List<String> command2 = new ArrayList<>();
        command2.addAll(commands);
       for(String str:commands){
           if(str.contains("%Bot_Invitation%")){
               Long QQ =new QQDDataTable().getPlayerQQ(player.getName());
               if(YaoQing.getInvitation(QQ)==0){
                   command2.remove(str);
               }
           }
       }

        BukkitUtil.runCommands(command2,player);
    }

}
