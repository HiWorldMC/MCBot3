package com.binggun.util;

import com.FBinggun.MySQl.Util;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.Content;
import com.binggun.botwebsocket.sendmsg.*;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.Chain;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.config.BotConfig;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.util.groupmanage.GetGroupInfoUtil;
import com.binggun.util.groupmanage.GroupUserInfoUtil;
import com.binggun.util.groupmanage.SetGroupInfoUtil;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BotUtil{

    public static Long getAt(String msg) {
        if (msg.contains("At:")) {
            msg = msg.replace("At:", "");
            System.out.println(msg);
            return Long.valueOf(msg);
        }
        return null;
    }


    public static boolean isEnableGroup(Long group) {
        if (BotConfig.groupList.contains(group)) {
            return true;
        }
        return false;
    }


    public static void sendGroupMsg(String msg, Long group) throws URISyntaxException {
        textChain txt = new textChain(msg);
        MessageChain me = new MessageChain(txt);
        Content c = new GroupMessage(group, me);
        BotCommandSend cmd = new BotCommandSend("123", c);
        Client.getClient().send(cmd.toString());
    }


    //获用户昵称
    public static String getNickname(Long group, Long qq) throws URISyntaxException, InterruptedException, ExecutionException {
        GroupUserInfoUtil info = new GroupUserInfoUtil(group, qq);
        return info.getNickname();
    }
    //获取群昵称
    public static String getGroupNickname(Long group, Long qq) throws URISyntaxException, InterruptedException, ExecutionException {
        GetGroupInfoUtil getGroupInfoUtil = new GetGroupInfoUtil(group, qq);
        GetGroupInfoUtil.Info info= getGroupInfoUtil.send();
        if(info!=null){
            return info.getMemberName();
        }
        return getNickname(group,qq);
    }
    //获取群头衔
    public static String getGroupSpecialTitle(Long group, Long qq) throws URISyntaxException, InterruptedException, ExecutionException {
        GetGroupInfoUtil getGroupInfoUtil = new GetGroupInfoUtil(group, qq);
        GetGroupInfoUtil.Info info= getGroupInfoUtil.send();
        if(info!=null){
            return info.getSpecialTitle();
        }
        return null;
    }

    //获取用户群等级
    public static int getLevel(Long group, Long qq) throws URISyntaxException, InterruptedException, ExecutionException {
        GroupUserInfoUtil info = new GroupUserInfoUtil(group, qq);
        return info.getLevel();
    }

    //设置群昵称
    public static boolean setMemberName(Long group, Long qq,String name) throws URISyntaxException, InterruptedException, ExecutionException {
        SetGroupInfoUtil info = new SetGroupInfoUtil(group, qq,name,null);
        return info.send();
    }
    //设置群头衔
    public static boolean setSpecialTitle(Long group, Long qq,String name) throws URISyntaxException, InterruptedException, ExecutionException {
        SetGroupInfoUtil info = new SetGroupInfoUtil(group, qq,null,name);
        return info.send();
    }

    public static MessageChain clearAt(MessageChain me){
        Chain[] chains=    me.getMessageChain();
        List<Chain> chains1 = new ArrayList<>();
        for(Chain c:chains){
            if(c instanceof At){
                continue;
            }else {
                chains1.add(c);
            }
        }
        me.setMessageChain(chains1);
        return me;
    }


    //全部在线玩家
    public static List<String> getPlayerTiem(int ve,boolean te) throws Exception {
        List<String> list = new ArrayList<>();
        String t = "select * from "+new QQDDataTable().getQqData().getTablename();
        ResultSet s = new QQDDataTable().getQqData().getSql().getStatement().executeQuery(t);
        while (s.next()){
            String p = s.getString("ID");
            Long qq = s.getLong("QQ");
            if(qq!=null&&qq!=0){
                Long date = Util.date()-(86400000*ve);
                String r =s.getString("LOGIN");
                if(r!=null){
                    if(Util.ISTimeLimit(Util.getDateTiemString(r),date,te)){
                        list.add(p);
                    }
                }

            }
        }
        return list;
    }

}
