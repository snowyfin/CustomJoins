package com.oldschoolminecraft.jp;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JoinsPlus extends JavaPlugin
{
    public static JoinsPlus instance;
    
    public void onEnable()
    {
        instance = this;
        
        getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN, new PlayerHandler(), Priority.Normal, this);
        
        if (!getMessageDirectory().exists())
            getMessageDirectory().mkdirs();
        
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
                        player.sendMessage(String.format("%sYou don't have permission to use this command!", ChatColor.RED));
                        return true;
                    }
                    
                    if (args.length < 3)
                    {
                        player.sendMessage(String.format("%sInsufficient parameters.", ChatColor.RED));
                        player.sendMessage(String.format("%sUsage: /cjm sudo <player> <message>", ChatColor.RED));
                        return true;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 2; i < args.length; i++)
                            sb.append(args[i] + " ");
                        
                        String username = args[1];
                        String message = sb.toString().trim();
                        
                        if (!message.contains("%player%"))
                        {
                            player.sendMessage(String.format("%sYour join message must contain the \"%%player%%\" variable.", ChatColor.RED));
                            player.sendMessage(String.format("%sExample: &e%%player%% joined the game.", ChatColor.AQUA));
                            return true;
                        }
                        
                        Player ply = getServer().getPlayer(username);
                        
                        setMessage(username, message);
                        
                        if (ply == null)
                            player.sendMessage(String.format("%sCouldn't find an online player that matches '%s', but their join message was set anyway!", ChatColor.RED, username));
                        else
                            player.sendMessage(String.format("%sSuccessfully changed join message.", ChatColor.GREEN));
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("reset")) {
                    deleteMessage(player.getName());
                    player.sendMessage(String.format("%sYour join message was reset.", ChatColor.GREEN));
                    return true;
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < args.length; i++)
                        sb.append(args[i] + " ");
                    String message = sb.toString().trim();
                    
                    if (!message.contains("%player%"))
                    {
                        player.sendMessage(String.format("%sYour join message must contain the \"%%player%%\" variable.", ChatColor.RED));
                        player.sendMessage(String.format("%sExample: &e%%player%% joined the game.", ChatColor.AQUA));
                        return true;
                    }
                    
                    setMessage(player.getName(), message);
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
    
    public String getMessage(String username)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(getMessageDirectory(), username + ".json");
            if (!file.exists()) return "&e" + username + " joined the game.";
            Message message = mapper.readValue(file, Message.class);
            return message.message;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "&e" + username + " joined the game.";
        }
    }
    
    public void setMessage(String username, String message)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(getMessageDirectory(), username + ".json"), new Message(message));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteMessage(String username)
    {
        File file = new File(getMessageDirectory(), username + ".json");
        if (file.exists()) file.delete();
    }
    
    public File getMessageDirectory()
    {
        return new File("plugins/JoinsPlus/messages");
    }
}
