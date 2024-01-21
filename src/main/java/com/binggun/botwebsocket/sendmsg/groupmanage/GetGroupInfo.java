package com.binggun.botwebsocket.sendmsg.groupmanage;

import com.binggun.botwebsocket.Content;

public class GetGroupInfo extends Content {


    Long group;
    Long user;

    public GetGroupInfo(Long group, Long user) {
        this.group = group;
        this.user = user;
    }

    @Override
    public String toString() {
        String s ="{\n    \"target\":"+group+",\n" +
                "    \"memberId\":" +
                user+"}";
        return s;
    }

    @Override
    public String getCommand() {
        return "memberInfo";
    }

    @Override
    public String getSubCommand(){
        return  "get";
    }
}
