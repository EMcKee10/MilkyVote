package io.github.emckee10.MilkyVote;

import org.apache.commons.validator.routines.UrlValidator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MVExecutor implements CommandExecutor
{
  private MVUtil util;
  private String displayMessage = "";
  
  MVExecutor()
  {
    util = MVUtil.getInstance();
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (command.getName().equalsIgnoreCase("setvotingsite")) {
      boolean result = setURL(sender, args);
      if (result) {
        sender.sendMessage(ChatColor.GREEN + "Success! Players can now use the /mvote command to vote!");
        return true;
      }
      else {
        return false;
      }
    }
    else if (command.getName().equalsIgnoreCase("setvotingdisplaymessage")) {
      {
        boolean result = setDisplayMessage(sender, args);
        if (result) {
          sender.sendMessage(ChatColor.GREEN + "Success! Players will now see the message \"" + ChatColor.RESET + displayMessage + ChatColor.GREEN + "\" instead of the URL!");
          return true;
        }
        else {
          return false;
        }
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
  
  private boolean setDisplayMessage(CommandSender sender, String[] args)
  {
    if (args.length > 0) {
      displayMessage = MVUtil.setDisplayMessageSettings(String.join(" ", args));
      return !displayMessage.equals("");
    }
    else {
      sender.sendMessage(ChatColor.RED + "You must enter in a display message to hid your link.");
      return false;
    }
  }
  
  private boolean setURL(CommandSender sender, String[] args)
  {
    String displayMessage = (String) MVUtil.getDisplayMessage();
    if (displayMessage.equals("")) {
      sender.sendMessage(ChatColor.RED + "You must set a display message first. Do so with the /setDisplayMessage command");
      return true;
    }
    if (args.length == 1) {
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
    if (!displayMessage.equals("") || !URL.equals(""))
      ((Player) sender).spigot().sendMessage(util.buildMessage(sender, URL, displayMessage));
  }
  
  
}
