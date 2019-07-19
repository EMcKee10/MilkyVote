package io.github.emckee10.MilkyVote;


import org.bukkit.plugin.java.JavaPlugin;

public class MVMain extends JavaPlugin
{
  public static MVMain plugin;
  
  public static MVMain getInstance()
  {
    return plugin;
  }
  
  @Override
  public void onDisable()
  {
  
  }

  public void onEnable()
  {
    plugin = this;
    new MVUtil();
    this.getCommand("SetVotingSite").setExecutor(new MVExecutor(this));
    this.getCommand("MilkyVote").setExecutor(new MVExecutor(this));
    
  }
}

