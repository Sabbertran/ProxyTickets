package de.sabbertran.proxytickets.handlers;

import de.sabbertran.proxytickets.ProxyTickets;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

import java.io.*;
import java.util.HashMap;

public class MessageHandler {
    private ProxyTickets main;
    private HashMap<String, String> messages;

    public MessageHandler(ProxyTickets main) {
        this.main = main;
    }

    public void readMessagesFromFile() {
        messages = new HashMap<String, String>();
        File f = new File(main.getDataFolder(), "messages.yml");
        try {
            BufferedReader read = new BufferedReader(new FileReader(f));
            String line;
            while ((line = read.readLine()) != null) {
                line = line.trim();
                if (!line.equals("") && !line.startsWith("#")) {
                    String[] split = line.split(": ");
                    String msg = "";
                    for (int i = 1; i < split.length; i++)
                        msg += split[i] + ": ";
                    msg = msg.substring(1, msg.length() - 3);
                    messages.put(split[0], msg);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(CommandSender receiver, String message) {
        if (receiver != null && !message.trim().equals(""))
            if (message.contains("%newMessage%")) {
                for (String s : message.split("%newMessage%"))
                    sendMessage(receiver, s);
            } else {
                if (receiver instanceof ProxiedPlayer && message.startsWith("[") && message.endsWith("]"))
                    ((ProxiedPlayer) receiver).unsafe().sendPacket(new Chat(message));
                else
                    receiver.sendMessage(translateColorCodes(message));
            }
    }

    public void broadcast(String message) {
        if (!message.trim().equals(""))
            for (ProxiedPlayer p : main.getProxy().getPlayers())
                sendMessage(p, message.trim());
    }

    public void sendMessageWithPermission(String message, String permission) {
        sendMessageWithPermission(message, permission, null);
    }

    public void sendMessageWithPermission(String message, String permission, ProxiedPlayer exclude) {
        for (ProxiedPlayer p : main.getProxy().getPlayers()) {
            if (!p.equals(exclude) && (permission == null || main.getPermissionHandler().hasPermission(p, permission))) {
                sendMessage(p, message);
            }
        }
    }

    public String getMessage(String identifier) {
        if (messages.containsKey(identifier))
            return messages.get(identifier);
        main.getLogger().info("Message '" + identifier + "' could not be found. Please update your messages.yml");
        return "";
    }

    public TextComponent translateColorCodes(String text) {
        TextComponent t = new TextComponent("");
        for (BaseComponent b : TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text)))
            t.addExtra(b);
        return t;
    }
}
