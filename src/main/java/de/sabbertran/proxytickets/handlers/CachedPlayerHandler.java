package de.sabbertran.proxytickets.handlers;

import de.sabbertran.proxytickets.ProxyTickets;
import de.sabbertran.proxytickets.objects.CachedPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.UUID;

public class CachedPlayerHandler {
    private ProxyTickets main;
    private ArrayList<CachedPlayer> cachedPlayers;

    public CachedPlayerHandler(ProxyTickets main) {
        this.main = main;
        cachedPlayers = new ArrayList<CachedPlayer>();
    }

    public CachedPlayer getCachedPlayer(CommandSender sender) {
        return sender instanceof ProxiedPlayer ? getCachedPlayer(((ProxiedPlayer) sender).getUniqueId(), sender.getName()) : getConsolePlayer();
    }

    private CachedPlayer getConsolePlayer() {
        return new CachedPlayer(this, null, "CONSOLE");
    }

    public CachedPlayer getCachedPlayer(UUID uuid, String name) {
        for (CachedPlayer cp : cachedPlayers)
            if (cp.getUUID().equals(uuid) || cp.getName().equalsIgnoreCase(name))
                return cp;
        return addCachedPlayer(uuid, name);
    }

    private CachedPlayer addCachedPlayer(UUID uuid, String name) {
        CachedPlayer cp = new CachedPlayer(this, uuid, name);
        cachedPlayers.add(cp);
        return cp;
    }


    public ProxyTickets getMain() {
        return main;
    }
}
