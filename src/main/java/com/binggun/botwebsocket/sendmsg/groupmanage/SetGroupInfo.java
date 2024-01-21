package com.binggun.botwebsocket.sendmsg.groupmanage;

import com.binggun.botwebsocket.Content;

//修改群员信息
public class SetGroupInfo extends Content {
    Long group;
    Long qq;
    String name;
    String specialTitle;
    //group 群
    // qq 用户QQ


    public SetGroupInfo(Long group, Long qq,String name,String specialTitle) {
        this.group = group;
        this.qq = qq;
        this.name=name;
        this.specialTitle=specialTitle;
    }
    public Info getInfo(){
        return new Info(name,specialTitle);
    }


    public String toString() {
        String s ="{\n    \"target\":"+group+",\n" +
                "    \"memberId\":" +
                qq+",\n" +
                "info: "+getInfo().toString()+"}";
        return s;
    }




    @Override
    public String getCommand() {
        return "memberInfo";
    }
    @Override
    public String getSubCommand(){
       return  "update";
    }
    public  class Info{
        String name;
        String specialTitle;

        public Info(String name, String specialTitle) {
            this.name = name;
            this.specialTitle = specialTitle;
        }
        public String toString() {
            String s="{}";
            if(name==null&&specialTitle!=null){
                s = "{\nspecialTitle:" + specialTitle + "}";
            }else
            if(name!=null&&specialTitle==null){
                s = "{\nname:" + name + "}";
            }else if(name!=null&&specialTitle!=null){
                s = "{\n    \"name\":" + name + ",\n" +
                        "    \"specialTitle\":" +
                        specialTitle + "}";
            }else {
                return s;
            }
            return s;
        }
    }
}
