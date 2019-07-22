package io.github.emckee10.MilkyVote;


import org.bukkit.plugin.java.JavaPlugin;

public class MVMain extends JavaPlugin
{
  private static MVMain plugin;
  
  static MVMain getInstance()
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
    getCommand("SetVotingSite").setExecutor(new MVExecutor());
    getCommand("SetVotingDisplayMessage").setExecutor(new MVExecutor());
    getCommand("MilkyVote").setExecutor(new MVExecutor());
    
  }
}

