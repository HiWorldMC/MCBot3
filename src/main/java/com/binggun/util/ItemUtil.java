package com.binggun.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public  static  ItemStack toItemStack(String str){
        String name = null;
        String type = "Stone";
        List<String> lores = new ArrayList<>();
        Material material = null;
        for(String args:str.split("\n")){
            if(args.startsWith("Name")){
                name=args.replace("Name:","");
            }
            if(args.startsWith("Type")){
                type=args.replace("Type:","");
                if(type!=null){
                    material= Material.valueOf(type);
                }
            }
            if(args.startsWith("Lore")){
                String lore=args.replace("Lore:","");
                lores= JavaUtil.toList(lore,"%d%");
            }
        }
        if(material!=null) {
            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(name!=null){
                itemMeta.setDisplayName(name);
            }
            if(lores!=null&&lores.size()!=0){
                itemMeta.setLore(lores);
            }
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        return null;
    }


    public  static  String toString(ItemStack itemStack){
        String name = "";
        String type = itemStack.getType().toString();
        String lore= "";
        if(itemStack.getItemMeta()!=null){
            if(itemStack.getItemMeta().getLore()!=null){
                lore=JavaUtil.listToString(itemStack.getItemMeta().getLore(),"%d%");
            }
            if(itemStack.getItemMeta().getDisplayName()!=null){
                name=itemStack.getItemMeta().getDisplayName();
            }

        }
        String str = "Name:"+name+"\nType:"+type+"\nLore:"+lore;
        return str;
    }
    //替换物品中的变量
    public static ItemStack setPlaceholder(ItemStack itemStack, Player player){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta!=null){
            String name = itemMeta.getDisplayName();
            if(name!=null){
                name=name.replaceAll("&","§");
                name = PlaceholderAPI.setPlaceholders(player,name);
            }
            List<String> lore = itemMeta.getLore();
            if(lore!=null&&lore.size()!=0){
                List<String> lore2 = new ArrayList<>();
                for(String str:lore){
                    str=str.replaceAll("&","§");
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
