package io.github.emckee10.MilkyVote;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
   return (String)this.getSettingsConfiguration().get("settings.URL");
  }
  
  public void sendLink(CommandSender sender)
  {
    TextComponent link = buildMessage();
    sender.spigot().sendMessage(link);
  }
  
  public TextComponent buildMessage()
  {
    TextComponent message= new TextComponent("Vote by clicking on this link -> ");
    message.setColor(ChatColor.LIGHT_PURPLE);
    String url = (String) this.getURLSettings();
    TextComponent urlMessage = new TextComponent(url);
    urlMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click Me to Vote").color(ChatColor.GREEN).create()));
    urlMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
    message.addExtra(urlMessage);
    return message;
  }
  
}
