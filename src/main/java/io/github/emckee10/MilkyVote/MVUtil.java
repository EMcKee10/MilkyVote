package io.github.emckee10.MilkyVote;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

class MVUtil
{
  private static MVUtil util;
  private static File settingsFile;
  private static FileConfiguration settingsConfiguration;
  
  MVUtil()
  {
    util = this;
    createSettingsConfig();
  }
  
  static MVUtil getInstance()
  {
    return util;
  }
  
  private static void createSettingsConfig()
  {
    settingsFile = new File(MVMain.getInstance().getDataFolder(), "settings.yml");
    if (!settingsFile.exists()) {
      settingsFile.getParentFile().mkdirs();
      MVMain.getInstance().saveResource("settings.yml", false);
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
  
  private static FileConfiguration getSettingsConfiguration()
  {
    return settingsConfiguration;
  }
  
  private static File getSettingsFile()
  {
    return settingsFile;
  }
  
  static Object getURLSettings()
  {
    return getSettingsConfiguration().get("settings.URL");
  }
  
  static Object getDisplayMessage()
  {
    return getSettingsConfiguration().get("settings.Display_Message");
  }
  
  static boolean setURLSettings(String URL)
  {
    getSettingsConfiguration().set("settings.URL", URL);
    try {
      getSettingsConfiguration().save(getSettingsFile());
      return true;
    }
    catch (IOException e) {
      System.out.println("An error has occurred while trying to set the URL into setting.yml. Check to see that this file is present within your plugin folder.");
    return false;
    }
  }
  
  static String setDisplayMessageSettings(String message)
  {
    System.out.println(message);
    getSettingsConfiguration().set("settings.Display_Message", message);
    try {
      getSettingsConfiguration().save(getSettingsFile());
      return message;
    }
    catch (IOException e) {
      System.out.println("An error has occurred while trying to set the display message into setting.yml. Check to see that this file is present within your plugin folder.");
      return "";
    }
  }
  
  
  TextComponent buildMessage(CommandSender sender, String url, String displayMessage)
  {
    TextComponent message = new TextComponent();
    message.setColor(ChatColor.LIGHT_PURPLE);
    BaseComponent[] link = new ComponentBuilder(displayMessage).underlined(true).event(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click Me to Vote").color(ChatColor.GREEN).create())).create();
    for (BaseComponent baseComponent : link) {
      message.addExtra(baseComponent);
    }
    return message;
  }
}
