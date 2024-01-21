package com.binggun.mcplugin.command.bukkit;

import com.binggun.signs.SignAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Sign implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            SignAPI.sign((Player) sender);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}