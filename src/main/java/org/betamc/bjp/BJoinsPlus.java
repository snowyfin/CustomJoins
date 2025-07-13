package org.betamc.bjp;

import java.io.File;

import org.betamc.bjp.Commands.CustomJoinMessage;
import org.betamc.bjp.Commands.CustomLeaveMessage;
import org.betamc.bjp.Commands.JoinsPlus;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BJoinsPlus extends JavaPlugin
{
    public static BJoinsPlus instance;
    
    public Config config;
    
    public void onEnable()
    {
        instance = this;
        
        PlayerHandler handler = new PlayerHandler();
        
        getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN, handler, Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Type.PLAYER_QUIT, handler, Priority.Normal, this);
        
        if (!getPluginDirectory().exists())
            getPluginDirectory().mkdirs();
        if (!getMessageDirectory().exists())
            getMessageDirectory().mkdirs();
        
        if (!getConfigFile().exists())
        {
            config = new Config("&e%player% joined the game.", "&e%player% left the game.");
            saveConfig();
        } else {
            loadConfig();
        }

        getCommand("jp").setExecutor(new JoinsPlus(this));
        getCommand("cjm").setExecutor(new CustomJoinMessage(this));
        getCommand("clm").setExecutor(new CustomLeaveMessage(this));

        System.out.println("JoinsPlus enabled.");
    }
    public void onDisable()
    {
        System.out.println("JoinsPlus disabled.");
    }



    //Loads and saves player files
    public void loadConfig()
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Config config = mapper.readValue(getConfigFile(), Config.class);
            this.config = config;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void saveConfig()
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(getConfigFile(), config);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    //Sets the player message
    public void setJoin(String username, String message)
    {
        Message msg = loadMessage(username);
        if (msg == null) msg = new Message();
        msg.join = message;
        saveMessage(username, msg);
    }
    public void setQuit(String username, String message)
    {
        Message msg = loadMessage(username);
        if (msg == null) msg = new Message();
        msg.quit = message;
        saveMessage(username, msg);
    }



    //Resets the player message
    public void resetJoin(String username)
    {
        Message msg = loadMessage(username);
        if (msg == null) msg = new Message();
        msg.join = "&e%player% joined the game.";
        saveMessage(username, msg);
    }
    public void resetQuit(String username)
    {
        Message msg = loadMessage(username);
        if (msg == null) msg = new Message();
        msg.quit = "&e%player% left the game.";
        saveMessage(username, msg);
    }



    //Message loading and saving from JSON
    public Message loadMessage(String username)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(getMessageDirectory(), username + ".json");
            if (!file.exists()) return null;
            Message message = mapper.readValue(file, Message.class);
            return message;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void saveMessage(String username, Message message)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(getMessageDirectory(), username + ".json");
            if (file.exists()) file.delete();
            mapper.writeValue(file, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    //File and directory creation
    public File getPluginDirectory()
    {
        return new File("plugins/JoinsPlus");
    }
    public File getMessageDirectory()
    {
        return new File(getPluginDirectory(), "messages");
    }
    public File getConfigFile()
    {
        return new File(getPluginDirectory(), "config.json");
    }
}
