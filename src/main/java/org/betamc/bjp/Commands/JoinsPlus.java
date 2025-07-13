package org.betamc.bjp.Commands;

import org.betamc.bjp.BJoinsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinsPlus implements CommandExecutor {

    private final BJoinsPlus plugin;

    public JoinsPlus(BJoinsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        if (args.length > 0 && player.hasPermission("joinsplus.sudo")) {
            if (args[0].equalsIgnoreCase("reload"))
            {
                plugin.loadConfig();
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.GRAY + "JoinsPlus by moderator_man");
            sender.sendMessage(ChatColor.GRAY + "You can reload the config with /jp reload");
            return true;
        }
        return false;
    }
}
