package com.jumanjicraft.BungeeChatClient;

import com.dthielke.Herochat;
import com.dthielke.api.Channel;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeChatListener implements PluginMessageListener {

    private final BungeeChatClient plugin;
    private String lastMessage;

    /**
     *
     * @param plugin
     */
    public BungeeChatListener(BungeeChatClient plugin) {
        this.plugin = plugin;
        register();
    }
    
    private void register() {
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeChat", this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeChat");
    }    
    
    /**
     *
     * @param cm
     */
    public void TransmitChatMessage(ChatMessage cm) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        
        /* Herochat tokens */
        out.writeUTF(cm.getChannel());
        out.writeUTF(cm.getMessage());
        out.writeUTF(cm.getSender());
        out.writeUTF(cm.getHeroColor());
        out.writeUTF(cm.getHeroNick());
        
        /* Vault tokens */
        out.writeUTF(cm.getPlayerPrefix());
        out.writeUTF(cm.getPlayerSuffix());
        out.writeUTF(cm.getGroupPrefix());
        out.writeUTF(cm.getGroupSuffix());
        out.writeUTF(cm.getPlayerGroup());
        
        plugin.getServer().sendPluginMessage(plugin, "BungeeChat", out.toByteArray());
    }

    /**
     *
     * @param tag
     * @param player
     * @param data
     */
    @Override
    public void onPluginMessageReceived(String tag, Player player, byte[] data) {
        if (!tag.equalsIgnoreCase("BungeeChat")) {
            return;
        }

        /* Herochat tokens */
        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        String chatchannel = in.readUTF();
        String message = in.readUTF();
        String sender = in.readUTF();
        String heroColor = in.readUTF();
        String heroNick = in.readUTF();
	
        /* Vault tokens */
        String prefix = in.readUTF();
        String suffix = in.readUTF();
        String groupPrefix = in.readUTF();
        String groupSuffix = in.readUTF();
        String group = in.readUTF();


        Channel channel = Herochat.getChannelManager().getChannel(chatchannel);
        if (channel == null) {
            return;
        }
        String format = channel.getFormatSupplier().getStandardFormat();
        format = format.replace("{name}", channel.getName());
        format = format.replace("{nick}", channel.getNick());
        format = format.replace("{color}", channel.getColor().toString());
        format = format.replace("{msg}", "%2$s");
        format = format.replace("{sender}", "%1$s");
	format = format.replaceAll("(?i)&([a-fklmnor0-9])", "§$1");

        format = format.replace("{plainsender}", sender);
	format = format.replace("{prefix}", prefix == null ? "" : prefix.replace("%", "%%"));
        format = format.replace("{suffix}", suffix == null ? "" : suffix.replace("%", "%%"));
        format = format.replace("{group}", group == null ? "" : group.replace("%", "%%"));
        format = format.replace("{groupprefix}", groupPrefix == null ? "" : groupPrefix.replace("%", "%%"));
        format = format.replace("{groupsuffix}", groupSuffix == null ? "" : groupSuffix.replace("%", "%%"));
        format = format.replace("{world}", "EXT");
	format = format.replace("%1$s", sender);
        String formattedMessage = format.replace("%2$s", message);
        if(!formattedMessage .equals(lastMessage)){
	    channel.sendRawMessage(formattedMessage);
            lastMessage = formattedMessage ;
        }
    }
}
