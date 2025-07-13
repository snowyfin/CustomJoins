package org.betamc.bjp.Commands;

import org.betamc.bjp.BJoinsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomJoinMessage implements CommandExecutor {
    private final BJoinsPlus plugin;

    public CustomJoinMessage(BJoinsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
    if (player.hasPermission("joinsplus.use")){
        if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("sudo")){
                    if (!player.hasPermission("joinsplus.sudo")){
                        player.sendMessage(String.format("%sYou don't have permission to use this command!", ChatColor.RED));
                        return true;
                    }

                    if (args.length < 3){
                        player.sendMessage(String.format("%sInsufficient parameters.", ChatColor.RED));
                        player.sendMessage(String.format("%sUsage: /cjm sudo <player> <message>", ChatColor.RED));
                        return true;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 2; i < args.length; i++)
                            sb.append(args[i] + " ");

                        String username = args[1];
                        String regex = "player";
                        String message = sb.toString().trim().replaceFirst(regex, "%player%");

                        if (!message.contains("%player%")){
                            player.sendMessage(String.format("%sYour join message must contain the \"player\" variable.", ChatColor.RED));
                            player.sendMessage(String.format("%sExample: &eplayer joined the game.", ChatColor.AQUA));
                            return true;
                        }

                        Player ply = plugin.getServer().getPlayer(username);

                        plugin.setJoin(username, message);

                        if (ply == null)
                            player.sendMessage(String.format("%sCouldn't find an online player that matches '%s', but their join message was set anyway!", ChatColor.RED, username));
                        else
                            player.sendMessage(String.format("%sSuccessfully changed join message.", ChatColor.GREEN));
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("reset")) {
                    plugin.resetJoin(player.getName());
                    player.sendMessage(String.format("%sYour join message was reset.", ChatColor.GREEN));
                    return true;
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < args.length; i++)
                        sb.append(args[i] + " ");
                    String regex = "player";
                    String message = sb.toString().trim().replaceFirst(regex, "%player%");

                    if (!message.contains("%player%"))
                    {
                        player.sendMessage(String.format("%sYour join message must contain the \"player\" variable.", ChatColor.RED));
                        player.sendMessage(String.format("%sExample: &eplayer joined the game.", ChatColor.AQUA));
                        return true;
                    }

                    plugin.setJoin(player.getName(), message);
                    player.sendMessage(String.format("%sYour join message was changed.", ChatColor.GREEN));
                    return true;
                }
            } else {
                player.sendMessage(String.format("%sMissing parameters.", ChatColor.RED));
                player.sendMessage(String.format("%sUsage: /cjm <message>", ChatColor.RED));
                return true;
            }
        } else if (!player.hasPermission("joinsplus.use")) {
            player.sendMessage(String.format("%sYou don't have permission to use this command!", ChatColor.RED));
            return true;
        }
        return true;
    }
}



