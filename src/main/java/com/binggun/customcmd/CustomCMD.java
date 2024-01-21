package com.binggun.customcmd;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CustomCMD {
    String name;
    List<String> alias =new ArrayList<>();
    String msg;
    List<String> commands = new ArrayList<>();
    public CustomCMD(String name,ConfigurationSection config){
       this.name=name;
        this.alias=config.getStringList("Alias");
        this. commands=config.getStringList("Command");
        this.msg=config.getString("Msg");
    }

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public String getMsg() {
        return msg;
    }

    public List<String> getCommands() {
        return commands;
    }
    public List<String> getCommands(Long QQ,String... args) {
        if(commands==null||commands.size()==0){
            return commands;
        }
        List<String> list =commands;
        for(String cmd:commands){
            cmd= cmd.replaceAll("%QQ%", String.valueOf(QQ));
            int x =0;
            for(String str:args){
                cmd= cmd.replaceAll("%args%"+x,args[x]);
               x=x+1;
            }
            x=0;
            list.add(cmd);
        }
        return commands;
    }
}
