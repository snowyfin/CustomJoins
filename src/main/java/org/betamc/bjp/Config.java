package org.betamc.bjp;

public class Config
{
    public String default_join;
    public String default_quit;
    
    public Config() {}
    
    public Config(String default_join, String default_quit)
    {
        this.default_join = default_join;
        this.default_quit = default_quit;
    }
}
