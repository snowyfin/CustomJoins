package com.oldschoolminecraft.jp;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerHandler extends PlayerListener
{
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        String message = JoinsPlus.instance.getMessage(event.getPlayer().getName());
        message = message.replace("%player%", event.getPlayer().getName());
        event.setJoinMessage(translateAlternateColorCodes('&', message));
    }
    
    private String translateAlternateColorCodes(char altColorChar, String textToTranslate)
    {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++)
        {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i+1]) > -1)
            {
                b[i] = '\u00A7';
                b[i+1] = Character.toLowerCase(b[i+1]);
            }
        }
        return new String(b);
    }
}
