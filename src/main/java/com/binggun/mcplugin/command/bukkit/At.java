package com.binggun.mcplugin.command.bukkit;

import com.binggun.mcplugin.data.sql.table.AtTable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;

public class At implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0){
            sender.sendMessage("/at on 可以被其他人@At");
            sender.sendMessage("/at off 不可以被其他人@At");
            return false;
        }
        if(args[0].equals("on")){
            try {
                new AtTable().setUse(sender.getName(),true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage("§a设置成功");
            return false;
        }
        if(args[0].equals("off")){
            try {
                new AtTable().setUse(sender.getName(),false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage("§a设置成功");
            return false;
        }
        if(args.length==2){
            if(sender.isOp()){
                String p = args[1];
                try {
                    new AtTable().setUse(p,false);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage("§a设置成功");
                return false;
            }
        }

        return false;
    }
}
