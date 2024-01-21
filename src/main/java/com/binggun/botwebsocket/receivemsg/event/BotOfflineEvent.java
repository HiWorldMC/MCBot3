package com.binggun.botwebsocket.receivemsg.event;

import com.binggun.botwebsocket.receivemsg.bukkitevent.BotOfflineBukkitEvent;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.mcplugin.McBot;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;
import java.sql.SQLException;

public class BotOfflineEvent implements MsgEvent{
    Long botQQ;

    public BotOfflineEvent(Long botQQ) {
        this.botQQ = botQQ;
    }

    @Override
    public void sendMsg(MessageChain m) throws URISyntaxException {

    }
    @Override
    public void run(McBot mcbot) throws SQLException, URISyntaxException {
        if(mcbot.getPlugin().equalsIgnoreCase("JavaPlugin")){
            BotOfflineBukkitEvent botOfflineBukkitEvent = new BotOfflineBukkitEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(botOfflineBukkitEvent);
        }else {
       //bc 监听
        }
    }
}
