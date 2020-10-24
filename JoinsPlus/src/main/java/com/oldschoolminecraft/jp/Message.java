package com.oldschoolminecraft.jp;

import java.io.Serializable;

public class Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public String join;
    public String quit;
    
    public Message()
    {
        this.join = "&e%player% has joined the game.";
        this.quit = "&e%player% has left the game.";
    }
    
    public Message(String join, String quit)
    {
        this.join = join;
        this.quit = quit;
    }
}
