package com.oldschoolminecraft.jp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinsPlus extends JavaPlugin
{
    public static JoinsPlus instance;
    
    public void onEnable()
    {
        instance = this;
        
        getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN, new PlayerHandler(), Priority.Normal, this);
        
        System.out.println("JoinsPlus enabled.");
    }
    
    public void onDisable()
    {
        System.out.println("JoinsPlus disabled.");
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }
        
        Player player = (Player) sender;
        
        // cjm [sudo [player]] <message>
        if (label.equalsIgnoreCase("cjm") && player.hasPermission("joinsplus.use"))
        {
            if (args.length >= 1)
            {
                if (args[0].equalsIgnoreCase("sudo"))
                {
                    if (!player.hasPermission("joinsplus.sudo"))
                    {
                        player.sendMessage("&cYou don't have permission to use this command!");
                        return true;
                    }
                    
                    if (args.length < 3)
                    {
                        player.sendMessage("&cInsufficient parameters.");
                        player.sendMessage("&cUsage: /cjm sudo <player> <message>");
                        return true;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 3; i < args.length; i++)
                            sb.append(args[i] + " ");
                        
                        String username = args[2];
                        String message = sb.toString().trim();
                        
                        if (!message.contains("%player%"))
                        {
                            player.sendMessage("&cThe join message must contain the \"&b%player%&c\" variable.");
                            player.sendMessage("&bExample: &&e%player% joined the game.");
                            return true;
                        }
                        
                        Player ply = getServer().getPlayer(username);
                        
                        setMessage(username, message);
                        
                        if (ply == null)
                            player.sendMessage(String.format("&cCouldn't find an online player that matches '%s', but their join message was set anyway!", username));
                        else
                            player.sendMessage("&aYour join message was changed.");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("reset")) {
                    deleteMessage(player.getName());
                    player.sendMessage("&cYour join message was reset.");
                    return true;
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < args.length; i++)
                        sb.append(args[i] + " ");
                    String message = sb.toString().trim();
                    
                    if (!message.contains("%player%"))
                    {
                        player.sendMessage("&cYour join message must contain the \"%player%\" variable.");
                        player.sendMessage("&bExample: &&e%player% joined the game.");
                        return true;
                    }
                    
                    setMessage(player.getName(), message);
                    player.sendMessage("&aSuccessfully changed join message.");
                    return true;
                }
            } else {
                player.sendMessage("&cMissing parameters.");
                player.sendMessage("&cUsage: /cjm <message>");
                return true;
            }
        } else if (!player.hasPermission("joinsplus.use")) {
            player.sendMessage("&cYou don't have permission to use this command!");
            return true;
        }
        
        return true;
    }
    
    public String getMessage(String username)
    {
        return String.format("&e%s joined the game." + username);
    }
    
    public void setMessage(String username, String message)
    {
        //TODO
    }
    
    public void deleteMessage(String username)
    {
        //TODO
    }
}
