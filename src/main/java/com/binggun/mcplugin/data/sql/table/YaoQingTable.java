package com.binggun.mcplugin.data.sql.table;

import com.FBinggun.MySQl.Formula;
import com.FBinggun.MySQl.Table;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MemberJoinEvent;
import com.binggun.mcplugin.config.Config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YaoQingTable {
    Table table;
    public YaoQingTable(){
        try {
            table = new Table(Client.mcBot.getSQL(), Config.tablePrefix+"YaoQing");
            if(!table.IsTableNull()){
                table.CreateTable(
                        new Formula("QQ","BIGINT",20),
                        new Formula("YAOQING","TEXT"),
                        new Formula("InvitorId","BIGINT",20),
                        new Formula("joinTimestamp","TEXT"),
                        new Formula("lastSpeakTimestamp","TEXT")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInGroup(Long QQ) throws SQLException, ClassNotFoundException {
        if (table.IsIndex("QQ", String.valueOf(QQ))) {
            return true;
        }else {
            return false;
        }
    }
    public List<String> getYaoQingList(Long QQ) throws SQLException, ClassNotFoundException {
        List<String> YaoQing= new ArrayList<>();
        if (isInGroup(QQ)) {
            String y = String.valueOf(table.getObject("QQ",String.valueOf(QQ),"YAOQING"));
            if(y==null||y.equalsIgnoreCase("null")){
                return YaoQing;
            }
            String[] ys =y.split("\n");
            for(String x:ys){
                if(new QQDDataTable().getPlayerName(Long.valueOf(x))!=null) {
                    YaoQing.add(x);
                }
            }
        }
        return YaoQing;
    }

    public void upData(MemberJoinEvent event) throws SQLException, ClassNotFoundException {
        table.setObject("QQ",event.getUseId().toString(),"joinTimestamp",event.getJoinTimestamp());
        table.setObject("QQ",event.getUseId().toString(),"lastSpeakTimestamp",event.getLastSpeakTimestamp());
    }

    public void joinGroup(MemberJoinEvent event) throws SQLException, ClassNotFoundException {
        table.setObject("QQ",event.getUseId().toString(),"joinTimestamp",event.joinTimestamp);
        table.setObject("QQ",event.getUseId().toString(),"lastSpeakTimestamp",event.lastSpeakTimestamp);
    if(event.invitorId!=null){
        table.setObject("QQ",event.getUseId().toString(),"InvitorId",event.invitorId);
        addInvitorId(event.invitorId,event.getUseId());
    }
    }

    public void addInvitorId(Long invitorId,Long qq) throws SQLException, ClassNotFoundException {
        List<String> invitorList =getYaoQingList(invitorId);
        if(invitorList==null){
            invitorList =new ArrayList<>();
        }
        invitorList.add(String.valueOf(qq));
        String y ="";
        for(String x:invitorList){
            y=y+x+"\n";
        }
        table.setObject("QQ",invitorId.toString(),"YAOQING",y);
    }


}
