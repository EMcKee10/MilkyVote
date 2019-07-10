package io.github.emckee10.MilkyVote;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MVExecutor implements CommandExecutor
{
  MVMain plugin;
  MVUtil util;
  public MVExecutor(MVMain plugin)
  {
    this.plugin = plugin;
    this.util = plugin.getUtil();
    
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
  
    boolean result = false;
    switch (command.getName().toLowerCase()) {
      case "setvotingsite":
        if(sender.hasPermission("MilyVote.SetVotingSite"))
        result = setURL(sender, args);
        else
          result = false;
        
        break;
      case "MilkyVote":
        result = vote(sender);
        break;
    }
    
    return result;
  }
  
  private boolean setURL(CommandSender sender, String[] args)
  {
    if(args.length ==1) {
      util.setURLSettings(args[0]);
      return true;
    }
      else
        return false;
  }
  
  private boolean vote(CommandSender sender)
  {
    util.sendLink(sender);
    return true;
  }
  
}
