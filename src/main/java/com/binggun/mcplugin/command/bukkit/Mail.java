package com.binggun.mcplugin.command.bukkit;

import com.binggun.mailbox.MailAPI;
import com.binggun.mailbox.MailGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class Mail implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0) {
            try {
                MailGui.setInv((Player) sender);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        //yj give [玩家名] [邮件ID]
       if(args[0].equalsIgnoreCase("give")) {
           if (sender.isOp()) {
               try {
                   MailAPI.giveMail(args[2], args[1], null);
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
               sender.sendMessage("发送成功");
           }
           return false;
       }




        return false;
    }

}
