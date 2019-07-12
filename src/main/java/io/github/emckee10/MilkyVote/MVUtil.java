package io.github.emckee10.MilkyVote;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class MVUtil
{
  private MVMain plugin;
  private File settingsFile;
  private FileConfiguration settingsConfiguration;
  private Logger logger;
  private String message;
  
  MVUtil(MVMain plugin)
  {
    this.plugin = plugin;
    createConfig();
  }
  
  private void createConfig()
  {
    settingsFile = new File(plugin.getDataFolder(), "settings.yml");
    
    
    if (!settingsFile.exists()) {
      settingsFile.getParentFile().mkdirs();
      plugin.saveResource("settings.yml", false);
    }
    
    settingsConfiguration = new YamlConfiguration();
    try {
      settingsConfiguration.load(settingsFile);
    }
    catch (IOException | InvalidConfigurationException e) {
      System.out.println("There was a problem loading your yml files");
      e.printStackTrace();
    }
  }
  
  public static String color(String msg)
  {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }
  
  private FileConfiguration getSettingsConfiguration()
  {
    return settingsConfiguration;
  }
  
  private File getSettingsFile()
  {
    return settingsFile;
  }
  
  public boolean setURLSettings(String URL)
  {
    this.getSettingsConfiguration().set("settings.URL", URL);
    try {
      this.getSettingsConfiguration().save(this.getSettingsFile());
      return true;
    }
    catch (IOException e) {
      this.logger.severe("An error has occurred while trying to set the number of slots");
    return false;
    }
  }
  private Object getURLSettings()
  {
    return this.getSettingsConfiguration().get("settings.URL");
  }
  
  public boolean sendLink(CommandSender sender)
  {
    boolean success = buildMessage();
    if (success) {
      sender.sendMessage(message);
      return true;
    }
    else {
      sender.sendMessage("Not a valid URL");
      return false;
    }
  }
  
  public boolean buildMessage()
  {
    String url = (String) this.getURLSettings();
    message = color("&5Vote by clicking on this link -> ") + color("&e" + url);
    return true;
  }
  
}
