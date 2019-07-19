package io.github.emckee10.MilkyVote;

import org.apache.commons.validator.routines.UrlValidator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MVExecutor implements CommandExecutor
{
  MVMain plugin;
  MVUtil util;
  
  public MVExecutor(MVMain plugin)
  {
    plugin = MVMain.getInstance();
    util = MVUtil.getInstance();
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (command.getName().equalsIgnoreCase("setvotingsite")) {
      boolean result = setURL(sender, args);
      if (result) {
        sender.sendMessage(ChatColor.RED + "Success! Players can now use the /mvote command to vote!");
        return true;
      }
      else {
        return false;
      }
    }
    else if (command.getName().equalsIgnoreCase("milkyvote")) {
      if (args.length > 0) {
        return false;
      }
      else {
        sendVoteLink(sender);
        return true;
      }
    }
    else {
      return false;
    }
  }
  
  private boolean setDisplayMember(CommandSender sender, String[] args)
  {
    if (args.length > 0) {
      return MVUtil.setDisplayMessageSettings(args);
    }
    else {
      sender.sendMessage(ChatColor.RED + "You must enter in a display message to hid your link.");
      return false;
    }
  }
  
  private boolean setURL(CommandSender sender, String[] args)
  {
    if (args.length == 1) {
      if (Objects.isNull(MVUtil.getDisplayMessage())) {
        sender.sendMessage(ChatColor.RED + "You must set DisplayMessage first. Do so with the /setDisplayMessage command");
        return true;
      }
      UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);
      if (urlValidator.isValid(args[0])) {
        return MVUtil.setURLSettings(args[0]);
      }
      else {
        sender.sendMessage(ChatColor.RED + "URL is invalid, try a different link. Recommendation: Copy URL from the address bar of a valid site and paste in");
      }
    }
    else {
      sender.sendMessage(ChatColor.RED + "URL can not contain spaces. Recommendation: Copy URL from the address bar of a valid site and paste in");
    }
      
      return false;
  }
  
  private void sendVoteLink(CommandSender sender)
  {
    String URL = (String) MVUtil.getURLSettings();
    String displayMessage = (String) MVUtil.getDisplayMessage();
    ((Player) sender).spigot().sendMessage(util.buildMessage(sender, URL, displayMessage));
  }
  
  
}
