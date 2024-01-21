package com.binggun.mcplugin;

import com.FBinggun.MySQl.Table;
import com.binggun.botcommand.YaoQing;
import com.binggun.util.Lang;

public interface McBot {
    public String getPlugin();
    public com.FBinggun.MySQl.SQLite getSQL();

    public Table getQQDataTable();

    public YaoQing getYq();

    public Long getBotQQ() ;

    public Long getVerifyKey();
    public String getIP();
    public Lang getLang();
    public void setEnable(Boolean enable);
    public Boolean getEnable();
}
