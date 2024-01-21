package com.binggun.mcplugin.data.sql.table;

import com.FBinggun.MySQl.ColValue;
import com.FBinggun.MySQl.Formula;
import com.FBinggun.MySQl.Table;
import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.config.Config;
import com.binggun.util.BukkitUtil;
import com.binggun.util.TiemUtil;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class QdTable {
    Table table;
    public QdTable() throws SQLException {
        table = new Table(Client.getMcBot().getSQL(), Config.tablePrefix+"Sign");
        if(!table.IsTableNull()){
            table.CreateTable(
                    new Formula("ID", "VARCHAR", 25),
                    new Formula("UUID", "VARCHAR", 50),
                    new Formula("Group", "VARCHAR", 20),
                    new Formula("QD", "VARCHAR", 20),
                    new Formula("SignNumber", "BIGINT", 10),
                    new Formula("Continuous", "BIGINT", 10));
        }
    }



    public String getQDTiem(String player) throws SQLException, ClassNotFoundException {
        String qdTiem =String.valueOf(table.getObject("ID",player,"QD"));
        if(qdTiem==null||qdTiem.equalsIgnoreCase("null")){
            return null;
        }else {
            return qdTiem;
        }
    }
    public Long getSignNumber(String player) throws SQLException, ClassNotFoundException {
        String qdTiem = String.valueOf(table.getObject("ID",player,"SignNumber"));
        if(qdTiem==null||qdTiem.equalsIgnoreCase("null")){
            return 0l;
        }else {
            return Long.valueOf(qdTiem);
        }
    }
    public Long getContinuous(String player) throws SQLException, ClassNotFoundException {
        String qdTiem = String.valueOf(table.getObject("ID",player,"Continuous"));
        if(qdTiem==null||qdTiem.equalsIgnoreCase("null")){
            return null;
        }else {
            return Long.valueOf(qdTiem);
        }
    }



    public String getGroup(String player) throws SQLException, ClassNotFoundException {
        String group = String.valueOf(table.getObject("ID",player,"Group"));
        if(group==null||group.equalsIgnoreCase("null")){
            return null;
        }else {
            return group;
        }
    }
    public boolean isQd(String player) throws Exception {
        String qdTiem = getQDTiem(player);
        if (qdTiem != null) {
            if (qdTiem.equalsIgnoreCase(TiemUtil.getCurrentTime())) {
                return false;
            }
        }
        return true;
    }
    public void upQd(String player) throws Exception {
        table.setObject("ID",player,"QD",TiemUtil.getCurrentTime());
        table.setObject("ID",player,"SignNumber",getSignNumber(player)+1);
        if(isContinuous(player)){
            table.setObject("ID",player,"Continuous",getContinuous(player)+1);
        }else {
            table.setObject("ID",player,"Continuous",1);
        }
    }

    public void upGroup(Player player) throws Exception {
        table.setObject("ID",player.getName(),"Group", BukkitUtil.getPlayerGroup(player));
    }
    public void upInfo(Player player) throws Exception {
        if(!table.IsIndex("ID",player.getName())){
                  table.insertObject("ID",player.getName(),
                    new ColValue("UUID",player.getUniqueId().toString())
            );
        }
        table.setObject("ID",player.getName(),"Group", BukkitUtil.getPlayerGroup(player));
    }
    public boolean isContinuous(String player) throws Exception {
        String qdTiem = getQDTiem(player);
        if (qdTiem != null) {
            String yesterday = TiemUtil.stampToTimeMillisecond(TiemUtil.date() + (24 * 60 * 60 * 1000));
            if (yesterday.equalsIgnoreCase(qdTiem)) {
                return true;
            }
        }
        return false;
    }


}
