package io.github.emckee10.MilkyVote;


import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MVMain extends JavaPlugin
{
  
  public MVUtil Util;
  
  public void onEnable()
  {
    Util = new MVUtil(this);
    //TODO create Toggle for individual users.
    Objects.requireNonNull(this.getCommand("SetVotingSite")).setExecutor(new MVExecutor(this));
    Objects.requireNonNull(this.getCommand("MilkyVote")).setExecutor(new MVExecutor(this));
    
  }
  @Override
  public void onDisable()
  {
  
  }
  
  public MVUtil getUtil()
  {
    return Util;
  }
}

