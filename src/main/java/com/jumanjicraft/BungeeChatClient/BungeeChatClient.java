package com.jumanjicraft.BungeeChatClient;

import org.bukkit.plugin.java.JavaPlugin;

public class BungeeChatClient extends JavaPlugin {

    private String prefixSymbol;
    private BungeeChatListener bungeeChatListener;

    @Override
    public void onEnable() {
        loadConfig();
        bungeeChatListener = new BungeeChatListener(this);
        getServer().getPluginManager().registerEvents(new BungeeListener(this), this);
    }

    public void loadConfig() {
        this.prefixSymbol = getConfig().getString("prefix-symbol", "");
    }
    
    public String getPrefixSymbol() {
        return prefixSymbol;
    }
    
    public BungeeChatListener getBungeeChatListener() {
        return bungeeChatListener;
    }
}
