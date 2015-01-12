package com.jumanjicraft.BungeeChatClient;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.herochat.Herochat;
import com.herochat.api.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeChatListener implements PluginMessageListener {

    private final BungeeChatClient plugin;

    public BungeeChatListener(BungeeChatClient plugin) {
        this.plugin = plugin;
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "BungeeChat", this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeChat");
    }

    public void TransmitChatMessage(String message, String chatchannel, String player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(chatchannel);
        out.writeUTF(message);
        out.writeUTF(player);
        Bukkit.getOnlinePlayers()[0].sendPluginMessage(plugin, "BungeeChat", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equalsIgnoreCase("BungeeChat")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String chatchannel = in.readUTF();
        String message = in.readUTF();
        Channel channel = Herochat.getChannelManager().getChannel(chatchannel);
        if (channel == null) {
            return;
        }
        if (plugin.symbol.equals("")) {
            channel.sendRawMessage(message);
        } else {
            channel.sendRawMessage(plugin.symbol + message);
        }
    }
}
