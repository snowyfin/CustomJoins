package org.betamc.bjp;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHandler extends PlayerListener
{
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Message msg = BJoinsPlus.instance.loadMessage(event.getPlayer().getName());
        if (msg == null) msg = new Message();
        String message = msg.join;
        message = message.replace("%player%", event.getPlayer().getName());
        event.setJoinMessage(translateAlternateColorCodes('&', message));
    }
    
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Message msg = BJoinsPlus.instance.loadMessage(event.getPlayer().getName());
        if (msg == null) msg = new Message();
        String message = msg.quit;
        message = message.replace("%player%", event.getPlayer().getName());
        event.setQuitMessage(translateAlternateColorCodes('&', message));
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
