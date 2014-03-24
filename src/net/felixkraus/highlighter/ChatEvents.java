package net.felixkraus.highlighter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by felix on 23.03.14.
 */
public class ChatEvents implements Listener {
    private Plugin plugin;
    private String[] blacklist;
    private String color;
    private int minL;
    ChatEvents(Plugin plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        reloadConfig();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        String FinalMessage = message;
        String cc = getColor(message);
        message = message.replaceAll("!", "").replaceAll("\\?", "").replaceAll("\\.", "")
                .replaceAll(",", "").replaceAll(":", "").replaceAll("\\@", "").replaceAll("-", "");
        String[] MessageParts = message.split(" ");
        Player[] players = plugin.getServer().getOnlinePlayers();
        String[] playerNames = new String[players.length];
        for(int i = 0; i<players.length; i++){
            playerNames[i] = players[i].getName();
        }
        for (int i2 = 0; i2 < MessageParts.length; i2++) {
            for (int i = 0; i < players.length; i++) {
                if ((players[i].getDisplayName().toLowerCase().contains(MessageParts[i2].toLowerCase())) &&
                        (MessageParts[i2].split("").length > minL)) {
                    boolean blackl = false;
                    for(int b = 0; b<blacklist.length;b++){
                        if(blacklist[b].equals(MessageParts[i2])) blackl = false;
                    }
                    if(!blackl) FinalMessage = FinalMessage.replaceAll(MessageParts[i2], ChatColor.getByChar(color) + MessageParts[i2] + cc);
                }
            }
            e.setMessage(FinalMessage);
        }
    }

    private String getColor(String message) {
        if(message.contains("§")){
            return "§"+String.valueOf(message.charAt(message.lastIndexOf('§')+1));
        }
        else
        return ChatColor.RESET.toString();
    }

    public void reloadConfig(){
        minL = plugin.getConfig().getInt("min_length");
        blacklist = plugin.getConfig().getString("Blacklist").split(" ");
        color = plugin.getConfig().getString("Color");
    }

}
