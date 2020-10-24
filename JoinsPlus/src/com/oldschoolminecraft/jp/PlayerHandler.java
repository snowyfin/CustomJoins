package com.oldschoolminecraft.jp;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerHandler extends PlayerListener
{
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage(JoinsPlus.instance.getMessage(event.getPlayer().getName()));
    }
}
