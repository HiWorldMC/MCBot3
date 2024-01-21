package com.binggun.mcplugin.data.sql.table;

import com.FBinggun.MySQl.ColValue;
import com.FBinggun.MySQl.Formula;
import com.FBinggun.MySQl.Table;
import com.binggun.botwebsocket.Client;
import com.binggun.mailbox.Mail;
import com.binggun.mcplugin.config.Config;
import com.binggun.util.ItemUtil;
import com.binggun.util.JavaUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailBoxTable {
    Table table;
    public MailBoxTable() throws SQLException {
        table = new Table(Client.getMcBot().getSQL(), Config.tablePrefix+"Mail");
        if(!table.IsTableNull()){
            table.CreateTable(
                    new Formula("ID","TEXT"),
                    new Formula("Player","TEXT"),
                    new Formula("Player_UUID","TEXT"),
                    new Formula("Tiem","TEXT"),
                    new Formula("Command","TEXT"),
                    new Formula("Item","TEXT"),
                    new Formula("Msg","TEXT")
            );
        }
    }

    public List<Mail> getMails(String player) throws SQLException, ClassNotFoundException {
        String t = "select * from " + table.getTablename() + " where  Player='" + player + "'";
        if (table.getSql().IsConnection()) {
            table.getSql().connection();
        }
        List<Mail> mailList = new ArrayList<>();
        ResultSet resultSet = table.getSql().getStatement().executeQuery(t);
        while (resultSet.next()) {
            String ID = resultSet.getString("ID");
            String playerUUid = resultSet.getString("Player_UUID");
            String tiem = resultSet.getString("Tiem");
            String commands = resultSet.getString("Command");
            String item = resultSet.getString("Item");
            String msg = resultSet.getString("Msg");
            Mail mail = new Mail(ID,playerUUid,player, JavaUtil.toList(commands,"\n"), ItemUtil.toItemStack(item),msg,tiem);
            mailList.add(mail);
        }
        return mailList;
    }

    public void setMails(Mail mail) throws Exception{
            String id =mail.getId();
            String player =mail.getPlayerName();
            String uuid = mail.getPlayerUUID();
            String command =JavaUtil.listToString(mail.getCommand(),"\n");
            String tiem = mail.getTiem();
            String item =ItemUtil.toString(mail.getItem());
            String msg =mail.getMsg();
            table.insertObject("ID",id,
                    new ColValue("Player",player),
                    new ColValue("Player_UUID",uuid),
                    new ColValue("Command",command),
                    new ColValue("Tiem",tiem),
                    new ColValue("Item",item),
                    new ColValue("Msg",msg)
                    );
    }

    public void  delMail(Mail mail) throws SQLException, ClassNotFoundException {
        if(table.IsIndex("ID",mail.getId(),"ID")) {
            String str = "DELETE FROM " + table.getTablename() + " WHERE ID = '" + mail.getId()+"'";
            table.getSql().getStatement().executeUpdate(str);
        }
    }

}
