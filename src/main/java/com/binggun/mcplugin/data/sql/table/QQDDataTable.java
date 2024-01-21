package com.binggun.mcplugin.data.sql.table;

import com.FBinggun.MySQl.*;
import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.config.Config;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class QQDDataTable {
    Table qqData;

    public QQDDataTable() {
        try {
            qqData = new Table(Client.mcBot.getSQL(), Config.tablePrefix+"QQData");
            if (!qqData.IsTableNull()) {
                qqData.CreateTable(
                        new Formula("ID", "TEXT"),
                        new Formula("UUID", "TEXT"),
                        new Formula("QQ", "BIGINT", 20),
                        new Formula("REG", "TEXT"),
                        new Formula("LOGIN", "TEXT")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPlayerName(Long qq) throws SQLException, ClassNotFoundException {
        String player = String.valueOf(getQqData().getObject("QQ", String.valueOf(qq), "ID"));
        if(player.equalsIgnoreCase("null")){
            return null;
        }
        return player;
    }

    public UUID getPlayerUUID(Long qq) throws SQLException, ClassNotFoundException {
        String uuid =  String.valueOf(getQqData().getObject("QQ", String.valueOf(qq), "UUID"));
        if(uuid==null||uuid.equalsIgnoreCase("null")){
            return null;
        }
        return UUID.fromString(uuid);
    }
    public UUID getPlayerUUID(String player) throws SQLException, ClassNotFoundException {
        String uuid =  String.valueOf(getQqData().getObject("ID", player, "UUID"));
        if(uuid==null||uuid.equalsIgnoreCase("null")){
            return null;
        }
        return UUID.fromString(uuid);
    }
    public Long getPlayerQQ(String player) throws SQLException, ClassNotFoundException {
        String qq = String.valueOf(getQqData().getObject("ID", player, "QQ"));
        if(qq==null||qq.isEmpty()||qq.equalsIgnoreCase("null")){
            return null;
        }
        return Long.valueOf(qq);
    }

    public Long getPlayerQQ(UUID uuid) throws SQLException, ClassNotFoundException {
        String qq = String.valueOf( getQqData().getObject("UUID", uuid.toString(), "QQ"));
        if(qq==null||qq.isEmpty()||qq.equalsIgnoreCase("null")){
            return null;
        }
        return Long.valueOf(qq);
    }
    public String gerRegTiem(Long qq) throws SQLException, ClassNotFoundException {
        String regTiem =  String.valueOf(getQqData().getObject("QQ", String.valueOf(qq), "REG"));
        if(regTiem.equalsIgnoreCase("null")){
            return null;
        }
        return regTiem;
    }
    public String gerRegTiem(String playerName) throws SQLException, ClassNotFoundException {
        String regTiem =  String.valueOf(getQqData().getObject("ID", String.valueOf(playerName), "REG"));
        if(regTiem.equalsIgnoreCase("null")){
            return null;
        }
        return regTiem;
    }
    public String gerRegTiem(UUID uuid) throws SQLException, ClassNotFoundException {
        String regTiem =  String.valueOf(getQqData().getObject("UUID", uuid.toString(), "REG"));
        if(regTiem.equalsIgnoreCase("null")){
            return null;
        }
        return regTiem;
    }
    public String gerLoginTiem(Long qq) throws SQLException, ClassNotFoundException {
        String loginTiem =  String.valueOf(getQqData().getObject("QQ", String.valueOf(qq), "LOGIN"));
        if(loginTiem.equalsIgnoreCase("null")){
            return null;
        }
        return loginTiem;
    }
    public String gerLoginTiem(String playerName) throws SQLException, ClassNotFoundException {
        String loginTiem =  String.valueOf(getQqData().getObject("ID", String.valueOf(playerName), "LOGIN"));
        if(loginTiem.equalsIgnoreCase("null")){
            return null;
        }
        return loginTiem;
    }
    public String gerLoginTiem(UUID uuid) throws SQLException, ClassNotFoundException {
        String loginTiem =   String.valueOf(getQqData().getObject("UUID", uuid.toString(), "LOGIN"));
        if(loginTiem.equalsIgnoreCase("null")){
            return null;
        }
        return loginTiem;
    }
    //绑定QQ
    public void bindQQ(UUID uuid,Long qq) throws SQLException, ClassNotFoundException {
        getQqData().setObject("UUID", uuid.toString(),"QQ",qq);
    }
    public void bindQQ(String player,Long qq) throws SQLException, ClassNotFoundException {
        getQqData().setObject("ID", player.toString(),"QQ",qq);
    }
    public void unBindQQ(String player) throws SQLException, ClassNotFoundException {
        getQqData().setObject("ID", player.toString(),"QQ","");
    }

    //注册玩家
    public void registerPlayer(Player player) throws Exception {
        String tiem = Util.stampToTime(Util.date(),true);
        getQqData().insertObject("UUID", player.getUniqueId().toString(),
                new ColValue("ID",player.getName()),
                new ColValue("REG",tiem),
                new ColValue("LOGIN",tiem)
                );
    }
    //更新登录
    public void updateLogin(Player player) throws Exception {
        String tiem = Util.stampToTime(Util.date(),true);
        getQqData().setObject("UUID", player.getUniqueId().toString(),"LOGIN",tiem);
    }
    public boolean isBind(String player) throws SQLException, ClassNotFoundException {
        if(getPlayerQQ(player)!=null){
            return  true;
        }else {
            return false;
        }
    }


    public Table getQqData() {
        return qqData;
    }
}
