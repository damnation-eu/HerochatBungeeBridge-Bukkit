package com.jumanjicraft.BungeeChatClient;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.herochat.Herochat;
import com.herochat.api.Channel;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeChatListener implements PluginMessageListener {

    private final BungeeChatClient plugin;

    public BungeeChatListener(BungeeChatClient plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeChat", this);
        this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeChat");
    }

    public void TransmitChatMessage(String message, String chatchannel, String player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(chatchannel);
        out.writeUTF(message);
        out.writeUTF(player);
        this.plugin.getServer().getOnlinePlayers()[0].sendPluginMessage(plugin, "BungeeChat", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String tag, Player player, byte[] data) {
        if (!tag.equalsIgnoreCase("BungeeChat")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        String chatchannel = in.readUTF();
        String message = in.readUTF();
        Channel channel = Herochat.getChannelManager().getChannel(chatchannel);
        if (channel == null) {
            return;
        }
        if (plugin.getPrefixSymbol().isEmpty()) {
            channel.sendRawMessage(message);
        } else {
            channel.sendRawMessage(plugin.getPrefixSymbol() + message);
        }
    }
}
