package net.felixkraus.highlighter;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by felix on 23.03.14.
 */
public class Highlighter extends JavaPlugin {
    @Override
    public void onEnable(){
        saveDefaultConfig();
        new ChatEvents(this);

    }

}
