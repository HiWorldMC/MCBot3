package com.binggun.mcplugin.runnablebukkit;

import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.MCBotJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.net.URISyntaxException;

public class InitialBotBukkit extends BukkitRunnable {
    public static int id;
    @Override
    public void run() {
        try {
            id =getTaskId();
            //ws客户端连接 机器人服务器
            Client.runs();
            Client.setMcBot(MCBotJavaPlugin.getMcBot());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static void init(){
        new InitialBotBukkit().runTaskLaterAsynchronously(MCBotJavaPlugin.getPlugin(MCBotJavaPlugin.class),20*3);
    }

    public static void close() throws URISyntaxException {
        Bukkit.getScheduler().cancelTask(id);
        Client.getClient().close();
    }



}
