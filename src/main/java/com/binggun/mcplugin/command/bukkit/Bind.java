package com.binggun.mcplugin.command.bukkit;

import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class Bind implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()){
            if(args.length==2){
                String player = args[0];
                Long qq = Long.valueOf(args[1]);
                QQDDataTable qqdDataTable=new QQDDataTable();
                try {
                    qqdDataTable.bindQQ(player,qq);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage("绑定成功");
            }
        }

        if(com.binggun.botcommand.Bind.TempData.get(sender.getName())!=null){
            Long qq =com.binggun.botcommand.Bind.TempData.get(sender.getName());
            if(qq==null){
                sender.sendMessage(getLang("Bind_NotApply"));
            }
            try {
               com.binggun.botcommand.Bind.BindQQ(qq, (Player) sender);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
        sender.sendMessage(getLang("Bind_Success"));
        }
        return false;
    }

    public String getLang(String key){
        String s= Client.getMcBot().getLang().get(key);
        s= s.replaceAll("&", "§");
        return s;
    }
}
