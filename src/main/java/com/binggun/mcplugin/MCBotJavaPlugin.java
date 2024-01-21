package com.binggun.mcplugin;


import com.FBinggun.MySQl.Table;
import com.binggun.botcommand.CommandManage;
import com.binggun.botcommand.YaoQing;
import com.binggun.botwebsocket.BotCommandSend;
import com.binggun.botwebsocket.Client;
import com.binggun.botwebsocket.sendmsg.GroupMessage;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.customcmd.CustomCMD;
import com.binggun.mailbox.MailGui;
import com.binggun.mcplugin.command.bukkit.*;
import com.binggun.mcplugin.config.*;
import com.binggun.mcplugin.listener.BindListener;
import com.binggun.mcplugin.listener.GroupAtListener;
import com.binggun.mcplugin.listener.Ls;
import com.binggun.mcplugin.listener.MemberNameListener;
import com.binggun.mcplugin.runnablebukkit.InitialBotBukkit;
import com.binggun.util.ConfigUtil;
import com.binggun.util.Lang;
import com.binggun.util.TiemUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//插件主类
public final class MCBotJavaPlugin extends JavaPlugin implements McBot {
    private final String plugin = "JavaPlugin";
    public static McBot mcBot;

    public com.FBinggun.MySQl.SQLite getSQL() {
        return Config.sql;
    }

    public Long getBotQQ() {
        return BotConfig.botQQ;
    }

    public Long getVerifyKey() {
        return BotConfig.verifyKey;
    }

    @Override
    public String getIP() {
        return BotConfig.webSocketIP;
    }

    public static MCBotJavaPlugin mcBotJavaPlugin;

    @Override
    public void onEnable() {
        mcBotJavaPlugin = this;
        //设置连接
        Client.setMcBot(this);
        mcBot = this;
        //加载配置
        try {
            configLoad();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //注册MC指令
        registerCommand();
        //注册机器人指令
        registerBotCommand();
        //启动机器人
        if (BotConfig.enableBot) {
            InitialBotBukkit.init();
        }



        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (getSQL().IsConnection()) {
                        getSQL().connection();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimerAsynchronously(this, 10, 60);




        //注册监听
        getServer().getPluginManager().registerEvents(new Ls(), this);
        getServer().getPluginManager().registerEvents(new MailGui(), this);
        getServer().getPluginManager().registerEvents(new GroupAtListener(), this);
        getServer().getPluginManager().registerEvents(new MemberNameListener(), this);
        getServer().getPluginManager().registerEvents(new BindListener(), this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPI(this).register();
        }
    }

    @Override
    public Boolean getEnable() {
        return BotConfig.enableBot;
    }


    @Override
    public String getPlugin() {
        return plugin;
    }


    //配置文件加载
    public void configLoad() throws IOException, InvalidConfigurationException, SQLException, ClassNotFoundException {
//        if (!this.getDataFolder().exists()) {
//            this.getDataFolder().mkdir();
//        }
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.saveResource("config.yml", true);
        }
        File fileLang = new File(this.getDataFolder(), "Lang.yml");
        if (!fileLang.exists()) {
            saveResource("Lang.yml", true);
        }
        File mailConfig = new File(this.getDataFolder(), "MailTemplate.yml");
        if (!mailConfig.exists()) {
            saveResource("MailTemplate.yml", true);
        }
        MailConfig.init(ConfigUtil.load(mailConfig));
        Config.init(ConfigUtil.load(file));
        LangConfig.init(ConfigUtil.load(fileLang));
    }


    private Table QQDataTable;

    public Table getQQDataTable() {
        return QQDataTable;
    }

    @Override
    public YaoQing getYq() {
        return null;
    }

    @Override
    public Lang getLang() {
        return LangConfig.lang;
    }

    public void setEnable(Boolean enable) {
        BotConfig.enableBot = enable;
    }

    public static McBot getMcBot() {
        return mcBot;
    }


    private void registerCommand() {
        this.getCommand("Bind").setExecutor(new Bind());
        this.getCommand("UnBind").setExecutor(new UnBind());
        this.getCommand("Bot").setExecutor(new Bot());
        this.getCommand("Mail").setExecutor(new Mail());
        List<String> maillist = new ArrayList<>();
        maillist.add("yx");
        maillist.add("邮箱");
        maillist.add("yj");
        this.getCommand("Mail").setAliases(maillist);
        List<String> signList = new ArrayList<>();
        signList.add("qd");
        signList.add("签到");
        this.getCommand("Sign").setExecutor(new Sign());
        this.getCommand("Sign").setAliases(signList);
    }

    private void registerBotCommand() {
        CommandManage.registerCommand(new com.binggun.botcommand.Bind("Bind"));
        CommandManage.registerCommand(new com.binggun.botcommand.CommandExecution("CMD"));
        CommandManage.registerCommand(new com.binggun.botcommand.PlayerList("List"));
        CommandManage.registerCommand(new com.binggun.botcommand.SendMail("Mail"));
        CommandManage.registerCommand(new com.binggun.botcommand.Sign("Sign"));
        CommandManage.registerCommand(new com.binggun.botcommand.GiveItem("Give"));
        CommandManage.registerCommand(new com.binggun.botcommand.MoneyInfo("Money"));
        CommandManage.registerCommand(new com.binggun.botcommand.NuBind("NuBind"));
        CommandManage.registerCommand(new com.binggun.botcommand.BindBlack("BindBlack"));
        if (CustomCMDConfig.customCMDList != null) {
            if (CustomCMDConfig.customCMDList.size() != 0) {
                for (CustomCMD customCMD : CustomCMDConfig.customCMDList) {
                    CommandManage.registerCommand(new com.binggun.botcommand.CustomCommand(customCMD.getName(), customCMD));
                }
            }
        }

    }


    @Override
    public void onDisable() {
        try {
            BotConfig.enableBot = false;
            Client.getClient().close();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isTiem() throws Exception {
        Long data = TiemUtil.dateToStamp("2023年4月25日");
        if (data <= TiemUtil.date()) {
            return true;
        } else {
            return false;
        }
    }

    private String getIp() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            return ip;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
