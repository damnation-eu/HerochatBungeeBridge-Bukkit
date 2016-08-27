package com.jumanjicraft.BungeeChatClient;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeChatClient extends JavaPlugin {

    private BungeeChatListener bungeeChatListener;
    public VaultHook vaultHelpers;

    @Override
    public void onEnable() {
        setupVault();
        bungeeChatListener = new BungeeChatListener(this);
        getServer().getPluginManager().registerEvents(new BungeeHeroListener(this), this);
    }

    public BungeeChatListener getBungeeChatListener() {
        return bungeeChatListener;
    }
    
    /**
     *
     */
    public void setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            vaultHelpers = new VaultHook(this);            
        } 
    }
    
    /**
     *
     * @param player
     * @return
     */
    public String getPlayerPrefix(Player player) {
        String prefix = "";
        if (vaultHelpers != null) {
            if (vaultHelpers.chat != null) {
                prefix = vaultHelpers.chat.getPlayerPrefix(player);
            }
        }
        if (prefix == null) {
            prefix = "";
        }
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }

    /**
     *
     * @param player
     * @return
     */
    public String getPlayerSuffix(Player player) {
        String suffix = "";
        if (vaultHelpers != null) {
            if (vaultHelpers.chat != null) {
                suffix = vaultHelpers.chat.getPlayerSuffix(player);
            }
        }
        if (suffix == null) {
            suffix = "";
        }
        return ChatColor.translateAlternateColorCodes('&', suffix);
    }
    
    /**
     *
     * @param player
     * @return
     */
    public String getGroupPrefix(Player player) {
        String prefix = "";
        if (vaultHelpers != null) {
            if (vaultHelpers.chat != null) {
                String group = "";
                try {
                    group = vaultHelpers.permission.getPrimaryGroup(player);
                } catch (Exception ex) {
                }
                if (group == null) {
                    group = "";
                }
                prefix = vaultHelpers.chat.getGroupPrefix(player.getLocation().getWorld(), group);
            }
        }
        if (prefix == null) {
            prefix = "";
        }
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }
    
    /**
     *
     * @param player
     * @return
     */
    public String getGroupSuffix(Player player) {
        String suffix = "";
        if (vaultHelpers != null) {
            if (vaultHelpers.chat != null) {
                String group = "";
                try {
                    group = vaultHelpers.permission.getPrimaryGroup(player);
                } catch (Exception ex) {
                }
                if (group == null) {
                    group = "";
                }
                suffix = vaultHelpers.chat.getGroupSuffix(player.getLocation().getWorld(), group);
            }
        }
        if (suffix == null) {
            suffix = "";
        }
        return ChatColor.translateAlternateColorCodes('&', suffix);
    }
    
    /**
     *
     * @param player
     * @return
     */
    public String getPlayerGroup(Player player) {
        String groupName = "";
        if (vaultHelpers != null) {
            if (vaultHelpers.permission != null) {
                try {
                    groupName = vaultHelpers.permission.getPrimaryGroup(player);
                } catch (Exception ex) {
                }
            }
        }
        if (groupName == null) {
            groupName = "";
        }
        return ChatColor.translateAlternateColorCodes('&', groupName);
    }

}
