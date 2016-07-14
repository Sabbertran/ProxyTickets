package de.sabbertran.proxytickets.handlers;

import de.sabbertran.proxytickets.ProxyTickets;
import de.sabbertran.proxytickets.commands.TicketCommand;
import de.sabbertran.proxytickets.commands.TicketsCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandHandler {

    private ProxyTickets main;

    public CommandHandler(ProxyTickets main) {
        this.main = main;
    }

    public void registerCommands() {
        main.getProxy().getPluginManager().registerCommand(main, new TicketCommand(main));
        main.getProxy().getPluginManager().registerCommand(main, new TicketsCommand(main));
    }

    public void sendUsage(CommandSender sender, Command cmd) {
        if (cmd instanceof TicketCommand)
            main.getMessageHandler().sendMessage(sender, main.getMessageHandler().getMessage("command.usage.ticket"));
        else if (cmd instanceof TicketsCommand)
            main.getMessageHandler().sendMessage(sender, main.getMessageHandler().getMessage("command.usage.tickets"));
    }
}
