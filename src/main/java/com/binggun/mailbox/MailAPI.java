package com.binggun.mailbox;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.config.MailConfig;
import com.binggun.mcplugin.data.sql.table.MailBoxTable;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.util.BukkitUtil;
import com.binggun.util.TiemUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MailAPI {



    public static void giveMail(ConfigurationSection config, String player,String[] args) throws Exception {
        Mail mail = createMail(config, player);
        MailBoxTable mailBoxTable = new MailBoxTable();
        if(args!=null&&args.length!=0){
            List<String> command = mail.getCommand();
            List<String> command2 = new ArrayList<>();

                for (String str : command) {
                    int argLength = 1;
                    for (String arg : args) {
                        if (!arg.equalsIgnoreCase(args[0]) && !arg.equalsIgnoreCase(args[1])) {
                            str = str.replaceAll("%args" + argLength + "%", arg);
                            argLength = argLength + 1;
                        }

                    }
                    command2.add(str);
                }
            mail.setCommand(command2);
        }
        mailBoxTable.setMails(mail);
    }

    public  static void giveMail(String id,String player,String[] args) throws Exception {
        giveMail(MailConfig.mailConfig.getConfigurationSection(id),player,args);
    }

    public static void useMail(Mail mail, Player player) throws Exception {
        if (player.getName().equalsIgnoreCase(mail.playerName)) {
            BukkitUtil.runCommands(mail.getCommand(), player);
            MailBoxTable mailBoxTable = new MailBoxTable();
            mailBoxTable.delMail(mail);
        }
    }


    public static void giveMail(ConfigurationSection config,String[] args, Long qq, MsgEvent msgType) throws Exception {
        String player = new QQDDataTable().getPlayerName(qq);
        if (player != null) {
            giveMail(config, player,args);
            sendMsg(true, qq, msgType);
        } else {
            sendMsg(false, qq, msgType);
        }
    }

    private static void sendMsg(boolean t, Long qq, MsgEvent msgType) throws URISyntaxException {
        if (t) {
            At At = new At(qq);
            String m = getLang("Mail_send");
            textChain txt = new textChain(m);
            MessageChain me = new MessageChain(At, txt);
            msgType.sendMsg(me);
        } else {
            At At = new At(qq);
            String m = getLang("Mail_PlayerNonEntity");
            textChain txt = new textChain(m);
            MessageChain me = new MessageChain(At, txt);
            msgType.sendMsg(me);
        }
    }


    public static String getLang(String key) {
        String s = Client.getMcBot().getLang().get(key);
        return s;
    }


    public static Mail createMail(ConfigurationSection config, String player) throws Exception {
        if(config==null){
            return null;
        }
        ConfigurationSection itemConfig = config.getConfigurationSection("Item");
        List<String> commands = config.getStringList("command");
        String msg = config.getString("Msg");
        String id = UUID.randomUUID().toString();
        String playerName = player;
        if (new QQDDataTable().getPlayerUUID(playerName) == null) {
            return null;
        }
        String playerUUID = new QQDDataTable().getPlayerUUID(playerName).toString();
        String tiem = TiemUtil.getCurrentTime();
        ItemStack item = getMailItem(itemConfig);
        return new Mail(id, playerUUID, playerName, commands, item, msg, tiem);

    }

    private static ItemStack getMailItem(ConfigurationSection config) {
        String type = config.getString("Type", "STONE");
        String name = config.getString("Name", "§a邮件");
        name = name.replaceAll("&", "§");
        List<String> lore = config.getStringList("lore");
      //  int customModelData = config.getInt("CustomModelData");
        ItemStack itemStack = new ItemStack(Material.getMaterial(type));
        ItemMeta itemMeta = itemStack.getItemMeta();
//        if (customModelData != 0) {
//            itemMeta.setCustomModelData(customModelData);
//        }
        itemMeta.setDisplayName(name);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    //替换物品中的变量
    public static ItemStack setPlaceholder(Mail mail, Player player){
        ItemStack itemStack = new ItemStack(mail.getItem());
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta!=null){
            String name = itemMeta.getDisplayName();
            if(name!=null){
                name=name.replaceAll("&","§");
                name=name.replaceAll("%Mail_SendTiem%",mail.tiem);
                name = PlaceholderAPI.setPlaceholders(player,name);
            }
            List<String> lore = itemMeta.getLore();
            if(lore!=null&&lore.size()!=0){
                List<String> lore2 = new ArrayList<>();
                for(String str:lore){
                    str=str.replaceAll("&","§");
                    str=str.replaceAll("%Mail_SendTiem%",mail.tiem);
                    str = PlaceholderAPI.setPlaceholders(player,str);
                    lore2.add(str);
                }
                itemMeta.setLore(lore2);
            }
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }else {
            return itemStack;
        }
    }

}
