package com.binggun.mcplugin;

import com.binggun.botcommand.YaoQing;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.mcplugin.data.sql.table.QdTable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class PAPI extends PlaceholderExpansion {
    private MCBotJavaPlugin plugin;

    public PAPI(MCBotJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {

        return true;
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "Bot";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    //%Bot_Invitation% 邀请人数量
    //%Bot_Signs_Count% 签到次数
    //%Bot_Signs_Continuous% 连续签到次数
    //%Bot_Bind_QQ% 玩家绑定的QQ
    //%Bot_Bind_Name_[QQ]% 获得此[QQ]绑定的ID
    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        String[] split =identifier.split("_");
        if(split==null){
            return identifier;
        }
        if(split[0].equals("Invitation")) {
            try {
            Long QQ =new QQDDataTable().getPlayerQQ(p.getName());
            if(QQ!=null){
                    return String.valueOf(YaoQing.getInvitation(QQ));
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return String.valueOf(0);
        }
        if(split[0].equals("Signs")) {
            if(split[1]!=null&&split[1].equalsIgnoreCase("Count")) {
                String v;
                try {
                    QdTable qdTable = new QdTable();
                    Long count = qdTable.getSignNumber(p.getName());
                    v = count.toString();
                    return v;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(split[1]!=null&&split[1].equalsIgnoreCase("Continuous")) {
                String v;
                try {
                    QdTable qdTable =new QdTable();
                    Long count =  qdTable.getContinuous(p.getName());
                    v= count.toString();
                    return v;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if(split[0].equals("Bind")) {
            if (split[1] != null && split[1].equalsIgnoreCase("QQ")) {
                try {
                    Long QQ = new QQDDataTable().getPlayerQQ(p.getName());
                    if (QQ != null) {
                        return String.valueOf(QQ);
                    }
                    return "";
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (split[1] != null && split[1].equalsIgnoreCase("Name")) {
                if(split[2]!=null){
                    try {
                        String name = new QQDDataTable().getPlayerName(Long.valueOf(split[2]));
                        if (name != null) {
                            return name;
                        }
                        return "无";
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                return identifier;
            }

        }
        return identifier;
    }
}

