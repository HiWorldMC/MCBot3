package com.binggun.mailbox;

import com.binggun.botwebsocket.Client;
import com.binggun.mcplugin.data.sql.table.MailBoxTable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailGui implements Listener {
    public static  Map<String,Map<Integer,Mail>> playerMainData = new HashMap<>();
    public static void setInv(Player player) throws SQLException, ClassNotFoundException {
        MailBoxTable mailBoxTable = new MailBoxTable();
        List<Mail> mailList =mailBoxTable.getMails(player.getName());
        player.closeInventory();
        if(mailList!=null&&mailList.size()!=0){
            Map<Integer,Mail> data = new HashMap<>();
            Inventory inv = Bukkit.createInventory(null,54,getLang("Mail_Title"));
            int slot =0;
            for(Mail mail:mailList){
                if(slot<=44) {
                    ItemStack itemStack = MailAPI.setPlaceholder(mail, player);
                    inv.setItem(slot, itemStack);
                    data.put(slot, mail);
                    slot = slot + 1;
                }else {
                    break;
                }
            }
            playerMainData.put(player.getName(),data);
            player.openInventory(inv);
        }else {
            player.sendMessage(getLang("Mail_Null"));
        }
    }
    public static String getLang(String key) {
        String s = Client.getMcBot().getLang().get(key);
        s=s.replaceAll("&","§");
        return s;
    }
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) throws Exception {
        if(playerMainData.get(event.getWhoClicked().getName())!=null){
            Map<Integer,Mail> map=  playerMainData.get(event.getWhoClicked().getName());
            event.setCancelled(true);
            int slot = event.getSlot();
            Mail mail =map.get(slot);
            if(mail!=null){
                MailAPI.useMail(mail, (Player) event.getWhoClicked());
                setInv((Player) event.getWhoClicked());
            }

        }
    }
    @EventHandler
    public void onInventoryClickEvent(InventoryCloseEvent event){
        if(playerMainData.get(event.getPlayer().getName())!=null){
            playerMainData.remove(event.getPlayer().getName());
        }

    }

}
