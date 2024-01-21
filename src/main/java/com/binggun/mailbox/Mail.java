package com.binggun.mailbox;


import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Mail {
    String id;
    String playerUUID;
    String playerName;
    List<String> command;
    ItemStack item;
    String Msg;
    String tiem;

    public Mail(String id, String playerUUID, String playerName, List<String> command, ItemStack item, String msg,String tiem) {
        this.id = id;
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.command = command;
        this.item = item;
        this.Msg = msg;
        this.tiem=tiem;
    }

    public String getId() {
        return id;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<String> getCommand() {
        return command;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getMsg() {
        return Msg;
    }

    public String getTiem() {
        return tiem;
    }
    public void setCommand(List<String> command) {
        this.command=command;
    }

}
