package com.binggun.botcommand;

import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.receivemsg.chain.Plain;
import com.binggun.botwebsocket.receivemsg.event.MsgEvent;
import com.binggun.botwebsocket.receivemsg.event.MsgType;
import com.binggun.mcplugin.config.CommandConfig;
import com.binggun.mcplugin.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//指令管理器
public class CommandManage {
    private static Map<String, BotCommand> CMDDate =new HashMap<>();
    private static Map<String, List<String>> CMDDateAlias =new HashMap<>();
    public static Map<String, BotCommand> getCMDDates() {
        return CMDDate;
    }


    //可能返回空
    public static BotCommand getCMDDate(String command) {
        return CMDDate.get(command);
    }


    public static void registerCommand(BotCommand botCommand){
        CMDDate.put(botCommand.CMD,botCommand);
    }

    public static Map<String, List<String>> getCMDDateAlias() {
        return CMDDateAlias;
    }
    public static void  addCMDAlias(String CMD,String alias) {
        if(getCMDDate(CMD)!=null){
            List<String> list = new ArrayList<>();
            if(getCMDDateAlias().get(CMD)!=null){
                list=getCMDDateAlias().get(CMD);
            }
            list.add(alias);
            CMDDateAlias.put(CMD,list);
        }
    }
    public static BotCommand getCMDAlias(String alias) {
        if(getCMDDateAlias()!=null) {
            for (String CMD : getCMDDateAlias().keySet()) {
                if(getCMDDateAlias().get(CMD).contains(alias)){
                    return getCMDDate(CMD);
                }
            }
        }
        return null;
    }

    public static void  addCMDAlias(String CMD,List<String> alias) {
            List<String> list = new ArrayList<>();
            if(getCMDDateAlias().get(CMD)!=null){
                list=getCMDDateAlias().get(CMD);
            }
            list.addAll(alias);
            CMDDateAlias.put(CMD,list);
    }

    public static void runCMD(MsgEvent msgEvent) throws Exception {
        if(msgEvent instanceof MsgType){
            String msg = Plain.GetMsg(((MsgType) msgEvent).getChainJks());
            for(String prefix: CommandConfig.CMDPrefix){
            if(msg.startsWith(prefix)){
                msg= msg.replace(prefix,"");
                String[] CMDs =msg.split(" ");
                if(getCMDDate(CMDs[0])!=null){
                    BotCommand botCommand = getCMDDate(CMDs[0]);
                    if(CommandConfig.forbidden.contains(botCommand.CMD)){
                        return;
                    }
                    botCommand.getCMDManage(CMDs[0],getAgrs(CMDs),msgEvent).run();
                    return;
                }
                if(getCMDAlias(CMDs[0])!=null){
                    BotCommand botCommand = getCMDAlias(CMDs[0]);
                    if(CommandConfig.forbidden.contains(botCommand.CMD)){
                        return;
                    }
                    if(botCommand!=null) {
                        botCommand.getCMDManage(CMDs[0], getAgrs(CMDs), msgEvent).run();
                        return;
                    }
                }

            }
        }}
    }
    public static String[] getAgrs(String args[]){
        if(args.length>=2) {
            String[] args2 = new String[args.length - 1];
            int x=0;
            for (String arg : args) {
                if(arg.equals(args[0])){
                    continue;
                }
                args2[x]=arg;
                x=x+1;
            }
            return args2;
        }
        return null;
    }



}
