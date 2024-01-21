package com.binggun.botwebsocket.sendmsg.groupmanage;

import com.binggun.botwebsocket.Content;

//获取群员信息
public class GetGroupUserInfo extends Content {
    Long group;
    Long user;

    public GetGroupUserInfo(Long group, Long user) {
        this.group = group;
        this.user = user;
    }

    public String toString() {
        String s ="{\n    \"target\":"+group+",\n" +
                "    \"memberId\":" +
                user+"}";
        return s;
    }

    @Override
    public String getCommand() {
        return "memberProfile";
    }



}
