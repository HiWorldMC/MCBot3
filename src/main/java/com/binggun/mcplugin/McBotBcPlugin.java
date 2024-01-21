package com.binggun.mcplugin;


import com.FBinggun.MySQl.SQLite;
import com.FBinggun.MySQl.Table;
import com.binggun.botcommand.YaoQing;
import com.binggun.util.Lang;
import net.md_5.bungee.api.plugin.Plugin;

public class McBotBcPlugin extends Plugin implements McBot {
    final String plugin="BcPlugin";
    public Lang lang;
    public static McBotBcPlugin mCBotBcPlugin;
    @Override
    public String getPlugin() {
        return plugin;
    }

    @Override
    public SQLite getSQL() {
        return null;
    }

    @Override
    public Table getQQDataTable() {
        return null;
    }

    @Override
    public YaoQing getYq() {
        return null;
    }

    @Override
    public Long getBotQQ() {
        return null;
    }

    @Override
    public Long getVerifyKey() {
        return null;
    }

    @Override
    public String getIP() {
        return null;
    }

    @Override
    public Lang getLang() {
        return lang;
    }

    @Override
    public void setEnable(Boolean enable) {

    }

    @Override
    public Boolean getEnable() {
        return null;
    }


}
