package de.sabbertran.proxytickets.objects;

import de.sabbertran.proxytickets.handlers.CachedPlayerHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Date;
import java.util.UUID;

public class CachedPlayer {
    private CachedPlayerHandler handler;
    private UUID uuid;
    private String name;
    private Date blockedUntil;

    public CachedPlayer(CachedPlayerHandler handler, UUID uuid, String name) {
        this.handler = handler;
        this.uuid = uuid;
        this.name = name;
    }

    public CachedPlayer(CachedPlayerHandler handler, UUID uuid, String name, Date blockedUntil) {
        this(handler, uuid, name);
        this.blockedUntil = blockedUntil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CachedPlayer that = (CachedPlayer) o;

        return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;
    }

    public ProxiedPlayer getBungeePlayer() {
        return handler.getMain().getProxy().getPlayer(uuid);
    }

    public String getUUID() {
        return uuid != null ? uuid.toString() : name;
    }

    public String getName() {
        return name;
    }

    public Date getBlockedUntil() {
        return blockedUntil;
    }
}
