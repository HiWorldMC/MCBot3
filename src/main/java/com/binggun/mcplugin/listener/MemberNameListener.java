package com.binggun.mcplugin.listener;

import com.binggun.botwebsocket.BotEventInit;
import com.binggun.botwebsocket.receivemsg.bukkitevent.GroupMsgBukkitEvent;
import com.binggun.botwebsocket.sendmsg.chain.At;
import com.binggun.botwebsocket.sendmsg.chain.MessageChain;
import com.binggun.botwebsocket.sendmsg.chain.textChain;
import com.binggun.mcplugin.MCBotJavaPlugin;
import com.binggun.mcplugin.config.GroupChatConfig;
import com.binggun.mcplugin.data.sql.table.QQDDataTable;
import com.binggun.util.BotUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class MemberNameListener implements Listener {

    @EventHandler
    public void on(GroupMsgBukkitEvent event) throws SQLException, ClassNotFoundException, URISyntaxException, InterruptedException {
      new BukkitRunnable() {
          @Override
          public void run() {
              if (GroupChatConfig.groupList.contains(event.groupMsgEvent.groupId)) {
                if(GroupChatConfig.getMemberName().isCoerce()){
                  if (GroupChatConfig.getMemberName() != null && GroupChatConfig.getMemberName().getFormat() != null) {
                      String name = event.groupMsgEvent.useName;
                      String format = GroupChatConfig.getMemberName().getFormat();
                      boolean meet=true;
                      try {
                          if (getName(event.groupMsgEvent.useId) != null) {
                                  format = format.replaceAll("%name%", getName(event.groupMsgEvent.useId));
                                 meet = name.matches(format);
                          } else {
                              format = format.replace("%name%", name);
                              name.matches(format);
                          }
                      } catch (SQLException e) {
                          throw new RuntimeException(e);
                      } catch (ClassNotFoundException e) {
                          throw new RuntimeException(e);
                      }
                      if(!meet) {
                          if (GroupChatConfig.getMemberName().isCoerce()) {
                              try {
                                  if (getName(event.groupMsgEvent.useId) != null) {
                                      BotUtil.setMemberName(event.groupMsgEvent.groupId, event.groupMsgEvent.useId, getName(event.groupMsgEvent.useId));
                                  }
                              } catch (SQLException e) {
                                  throw new RuntimeException(e);
                              } catch (ClassNotFoundException e) {
                                  throw new RuntimeException(e);
                              } catch (URISyntaxException e) {
                                  throw new RuntimeException(e);
                              } catch (InterruptedException e) {
                                  throw new RuntimeException(e);
                              } catch (ExecutionException e) {
                                  throw new RuntimeException(e);
                              }
                          } else {
                              String warning = GroupChatConfig.getMemberName().getWarning();
                              try {
                                  if (getName(event.groupMsgEvent.useId)  != null) {
                                      if (warning != null) {
                                          warning = warning.replace("%name%", getName(event.groupMsgEvent.useId) );
                                          At at = new At(event.groupMsgEvent.useId);
                                          textChain txt = new textChain(warning);
                                          MessageChain me = new MessageChain(at, txt);
                                          try {
                                              event.getMsgEvent().sendMsg(me);
                                          } catch (URISyntaxException e) {
                                              throw new RuntimeException(e);
                                          }
                                      }
                                  }
                              } catch (SQLException e) {
                                  throw new RuntimeException(e);
                              } catch (ClassNotFoundException e) {
                                  throw new RuntimeException(e);
                              }
                          }
                      }
                  }
              }}
          }
      }.runTaskAsynchronously(MCBotJavaPlugin.mcBotJavaPlugin);


    }
    public static void setName(Long groupId,Long qq,String name) throws SQLException, ClassNotFoundException, URISyntaxException, InterruptedException, ExecutionException {
        if (GroupChatConfig.getMemberName() != null && GroupChatConfig.getMemberName().getFormat() != null) {
                if (GroupChatConfig.getMemberName().isCoerce()) {
                    BotUtil.setMemberName(groupId, qq, name);
                }
        }
    }

    public String getName(Long qq) throws SQLException, ClassNotFoundException {
        QQDDataTable qqdDataTable = new QQDDataTable();
        String name = qqdDataTable.getPlayerName(qq);
        if (name != null) {
            return name;
        }
        return null;
    }


}
