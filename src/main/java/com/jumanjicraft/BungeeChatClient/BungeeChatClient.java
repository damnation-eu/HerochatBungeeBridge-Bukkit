package com.jumanjicraft.BungeeChatClient;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeChatClient extends JavaPlugin {

    protected String symbol;
    protected BungeeChatListener bungeeChatListener;

    @Override
    public void onEnable() {
        loadConfig();
        bungeeChatListener = new BungeeChatListener(this);
        Bukkit.getPluginManager().registerEvents(new BungeeListener(this), this);
    }

    public void loadConfig() {
        this.symbol = getConfig().getString("prefix-symbol", "");
    }
}
