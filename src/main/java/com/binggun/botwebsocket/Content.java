package com.binggun.botwebsocket;

//指令参数 父类
public abstract class Content {

    public abstract String toString();

    public abstract String getCommand();
    public String getSubCommand(){
        return null;
    }
}
