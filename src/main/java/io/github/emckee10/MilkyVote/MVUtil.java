package io.github.emckee10.MilkyVote;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class MVUtil
{
  private MVMain plugin;
  private File settingsFile;
  private FileConfiguration settingsConfiguration;
  private Logger logger;
  
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
  
  public boolean buildMessage(CommandSender sender) throws UnsupportedEncodingException
  {
    TextComponent message;
    message = new TextComponent("Vote by clicking on this link ");
    message.setColor(ChatColor.LIGHT_PURPLE);
    String url = (String) this.getURLSettings();
    BaseComponent[] link = new ComponentBuilder("-> Click Me to vote <-").underlined(true).event(new ClickEvent(ClickEvent.Action.OPEN_URL, URLEncoder.encode(url, "utf-8"))).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click Me to Vote").color(ChatColor.GREEN).create())).create();
    
    for (BaseComponent baseComponent : link) {
      message.addExtra(baseComponent);
    }
    ((Player) sender).spigot().sendMessage(message);
    return true;
  }
  
  
}
