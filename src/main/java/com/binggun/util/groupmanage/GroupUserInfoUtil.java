package com.binggun.util.groupmanage;

import com.alibaba.fastjson.JSONObject;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.Content;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.sendmsg.groupmanage.GetGroupUserInfo;
import com.binggun.util.Callback;
import com.binggun.util.CallbackUtil;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class GroupUserInfoUtil {
    public static Map<String, GroupUserInfoUtil> groupUserInfoUtil = new HashMap<>();
    public UUID uuid;
    Long group;
    Long qq;

    JSONObject jsonObject;
    GroupInfo groupInfo;
    private Callback<JSONObject, String> callbackUtil;
    public GroupUserInfoUtil(Long group, Long qq) throws URISyntaxException, InterruptedException, ExecutionException {
        this.group = group;
        this.qq = qq;
        uuid = UUID.randomUUID();
        groupUserInfoUtil.put(uuid.toString(), this);
        jsonObject = getGroupUserInfo();
        groupInfo = new GroupInfo(jsonObject);
        this.callbackUtil = new CallbackUtil<>();
    }

    private JSONObject getGroupUserInfo() throws URISyntaxException, InterruptedException, ExecutionException {
        Content c = new GetGroupUserInfo(group, qq);
        BotCommandSend cmd = new BotCommandSend(uuid.toString(), "memberProfile", null, c);
        Client.getClient().send(cmd.toString());
       return callbackUtil.callback(uuid.toString()).get();
    }

    //判断群员是否存在
    public boolean isGroupUserExist() throws URISyntaxException, InterruptedException {
        if (jsonObject != null) {
            if (jsonObject.getString("msg") != null) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public GroupInfo getGroupInfo() {
        return new GroupInfo(jsonObject);
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }



    public int getLevel() {
        return getGroupInfo().getLevel();
    }

    public int getAge() {
        return getGroupInfo().getAge();
    }

    public String getSign() {
        return  getGroupInfo().getSign();
    }

    public String getSex() {
        return getGroupInfo().getSex();
    }
    public String getNickname() {
        return getGroupInfo().getNickname();
    }
    class GroupInfo {
        private String nickname;
        private int level;
        private int age;
        private String sign;
        private String sex;

        public GroupInfo(JSONObject jsonObject) {
            this.nickname = jsonObject.getString("nickname");
            this.sign = jsonObject.getString("sign");
            this.sex = jsonObject.getString("sex");
            this.level = jsonObject.getInteger("level");
            this.age = jsonObject.getInteger("age");
        }

        public String getNickname() {
            return nickname;
        }

        public int getLevel() {
            return level;
        }

        public int getAge() {
            return age;
        }

        public String getSign() {
            return sign;
        }

        public String getSex() {
            return sex;
        }
    }
}


