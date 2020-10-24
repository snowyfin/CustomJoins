package com.oldschoolminecraft.jp;

import java.io.Serializable;

public class Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public String message;
    
    public Message() {}
    
    public Message(String message)
    {
        this.message = message;
    }
}
