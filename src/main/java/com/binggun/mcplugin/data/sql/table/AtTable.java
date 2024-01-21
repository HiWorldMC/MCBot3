package com.binggun.mcplugin.data.sql.table;

import com.FBinggun.MySQl.Formula;
import com.FBinggun.MySQl.Table;
import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.config.Config;
import com.binggun.mcplugin.config.GroupAt;
import com.binggun.util.TiemUtil;

import java.sql.SQLException;

public class AtTable {

    Table table;
    public AtTable() throws SQLException {
        table = new Table(Client.getMcBot().getSQL(), Config.tablePrefix+"AtTable");
        if(!table.IsTableNull()){
            table.CreateTable(
                    new Formula("ID","VARCHAR",25),
                    new Formula("Use","VARCHAR",25),
                    new Formula("Day","BIGINT",10),
                    new Formula("Time","VARCHAR",25)
            );
        }
    }

    public boolean isUse(String player) throws SQLException, ClassNotFoundException {
        String use =String.valueOf(table.getObject("ID",player,"Use"));
        if(use==null||use.equalsIgnoreCase("null")){
            return true;
        }
        if(use.equalsIgnoreCase("true")){
            return true;
        }else {
            return false;
        }
    }

    public void setUse(String player,boolean t) throws SQLException, ClassNotFoundException {
        table.setObject("ID",player,"Use",t);
    }
    public int getDay(String player) throws SQLException, ClassNotFoundException {
        String day = String.valueOf(table.getObject("ID", player, "Day"));
        if(day==null){
            return 0;
        }
        return Integer.parseInt(day);
    }


    public void setDay(String player,int t) throws SQLException, ClassNotFoundException {
        table.setObject("ID",player,"Day",t);
    }
    public void addDay(String player,int t) throws SQLException, ClassNotFoundException {
        table.setObject("ID",player,"Day", getDay(player)-t);
    }
    public String getTiem(String player) throws SQLException, ClassNotFoundException {
        String tiem =String.valueOf(table.getObject("ID",player,"Time"));
        if(tiem==null||tiem.equalsIgnoreCase("null")){
            return null;
        }else {
            return tiem;
        }
    }

    public void upTime(String player) throws Exception {
        String tiem = getTiem(player);
        if (tiem != null) {
            if (tiem.equalsIgnoreCase(TiemUtil.getCurrentTime())) {

            }else {
                setDay(player, GroupAt.getDayMaxAtCount());
                table.setObject("ID",player,"Time",TiemUtil.getCurrentTime());
            }
        }else {
            setDay(player, GroupAt.getDayMaxAtCount());
             table.setObject("ID",player,"Time",TiemUtil.getCurrentTime());
        }

    }


}
