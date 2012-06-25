package Lihad.Conflict;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import Lihad.Conflict.Command.CommandHandler;
import Lihad.Conflict.Information.BeyondInfo;
import Lihad.Conflict.Listeners.BeyondBlockListener;
import Lihad.Conflict.Listeners.BeyondEntityListener;
import Lihad.Conflict.Listeners.BeyondPlayerListener;
import Lihad.Conflict.Listeners.BeyondPluginListener;
import Lihad.Conflict.Listeners.BeyondSafeModeListener;
import Lihad.Conflict.Util.BeyondUtil;

public class Conflict extends JavaPlugin {
	/** Name of the plugin, used in output messages */
	protected static String PLUGIN_NAME = "Conflict";
	/** Header used for console and player output messages */
	protected static String header = "[" + PLUGIN_NAME + "] ";

	public static YamlConfiguration information;

	public static PermissionHandler handler;
	public static PermissionManager ex;
	private static Logger log = Logger.getLogger("Minecraft");

    public static City Abatton = new City("Abatton");
    public static City Oceian = new City("Oceian");
    public static City Savania = new City("Savania");
    public static City[] cities = {Abatton, Oceian, Savania};
    
    public static Perk Blacksmith = new LocationPerk("Blacksmith");
    public static Perk Potions = new LocationPerk("Potions");
    public static Perk Enchantments = new LocationPerk("Enchantments");
    public static Perk RichPortal = new PortalPerk("RichPortal");
    public static Perk MystPortal = new PortalPerk("MystPortal");
    
    //public static List<PerkNode> perkNodes = new LinkedList<PerkNode>();
    
    public static Location TRADE_BLACKSMITH;
    public static Location TRADE_POTIONS;
    public static Location TRADE_ENCHANTMENTS;
    public static Location TRADE_RICHPORTAL;
    public static Location TRADE_MYSTPORTAL;
    
    public static List<Node> nodes = new LinkedList<Node>();

	public static List<String> UNASSIGNED_PLAYERS = new LinkedList<String>(); 
	public static Map<String, String> PLAYER_SET_SELECT = new HashMap<String, String>();

	public static Map<String, Integer> TRADE_BLACKSMITH_PLAYER_USES = new HashMap<String, Integer>();
	public static Map<String, Integer> TRADE_POTIONS_PLAYER_USES = new HashMap<String, Integer>();
	public static Map<String, Integer> TRADE_ENCHANTMENTS_PLAYER_USES = new HashMap<String, Integer>();

    public static War war = null;

	public static int TASK_ID_EVENT;

	public static boolean IS_EVENT_RUNNING = false;

	public static CommandExecutor cmd;
    public static Random random = new Random();
    
    public static City getPlayerCity(String playerName) {
    	for (int i=0; i<cities.length; i++)
    		if (cities[i].hasPlayer(playerName))
    			return cities [i];
        return null;
    }
    
    public static boolean PlayerCanUseTrade(String playerName, String trade) {
        City c = getPlayerCity(playerName);
        if (c != null)
        	return (c.getTrades().contains(trade));
        return false;
    }

	private final BeyondPluginListener pluginListener = new BeyondPluginListener(this);
	private final BeyondBlockListener blockListener = new BeyondBlockListener(this);
	private final BeyondPlayerListener playerListener = new BeyondPlayerListener(this);
	private final BeyondEntityListener entityListener = new BeyondEntityListener(this);
	private final BeyondSafeModeListener safeListener = new BeyondSafeModeListener(this);

	public static File infoFile = new File("plugins/Conflict/information.yml");


	@Override
	public void onDisable() {
        saveInfoFile();
	}
	@Override
	public void onEnable() {
		//InfoManager
		information = new YamlConfiguration();
        loadInfoFile(information, infoFile);
        BeyondInfo.loader();
		//TimerManager
        boolean safeMode = false;
        for (int i=0; i<cities.length; i++) {
        	if (cities[i].getLocation() == null) {
        		safeMode = true;
        		severe("Unable to find capital location for " + cities[i].name);
        	}
        	else if (cities[i].getSpongeLocation() == null) {
        		safeMode = true;
        		severe("Unable to find drifter location for " + cities[i].name);
        	}
        }
		if(TRADE_BLACKSMITH == null || TRADE_POTIONS == null || TRADE_ENCHANTMENTS == null
				|| TRADE_RICHPORTAL == null || TRADE_MYSTPORTAL == null) {
			severe("Unable to find all trade locations.");
			safeMode = true;
		}
		if (!safeMode) {
			this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
				public void run() {
					TRADE_BLACKSMITH_PLAYER_USES.clear();
					TRADE_POTIONS_PLAYER_USES.clear();
					TRADE_ENCHANTMENTS_PLAYER_USES.clear();
				}
			},0L, 432000L);
			this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
				public void run() {
                    // Maintenance timer - runs every 5 mins
                    if(war != null){
                        war.postWarAutoList(null);
                    }
                    purgeInactivePlayers();
                    saveInfoFile();
				}
			},1200L, 5500L);
			this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
				public void run() {
                    // War timer - runs every second
                    if(war == null){
                        if(War.warShouldStart()){
							Abatton.clearTrades();
							Oceian.clearTrades();
							Savania.clearTrades();
							war = new War();
							getServer().broadcastMessage(ChatColor.RED+"Mount your Pigs! Strap on your Diamond Armor!  It Has BEGUN!!!");
						}
					}
                    else{
                        war.executeWarTick();

                        if(war.warShouldEnd()) {
                            war.endWar();  // This should make the peace protesters happy.
                            war = null;
                        }
					}
				}
			},200L, 20L);
		}
		//CommandManager
		cmd = new CommandHandler(this);
		getCommand("abatton").setExecutor(cmd);
		getCommand("oceian").setExecutor(cmd);
		getCommand("savania").setExecutor(cmd);
		getCommand("point").setExecutor(cmd);
		getCommand("purchase").setExecutor(cmd);
		getCommand("spawn").setExecutor(cmd);
		getCommand("setcityspawn").setExecutor(cmd);
		getCommand("rarity").setExecutor(cmd);
		getCommand("post").setExecutor(cmd);
		getCommand("look").setExecutor(cmd);
		getCommand("gear").setExecutor(cmd);
		getCommand("cc").setExecutor(cmd);
		getCommand("cca").setExecutor(cmd);
		getCommand("nulls").setExecutor(cmd);
		getCommand("protectcity").setExecutor(cmd);
		getCommand("myst").setExecutor(cmd);
		getCommand("cwho").setExecutor(cmd);
		getCommand("warstats").setExecutor(cmd);
		getCommand("perks").setExecutor(cmd);
		getCommand("bnn").setExecutor(cmd);


		//PermsManager
		setupPermissions();
		setupPermissionsEx();

		//PluginManager
		if(Abatton.getLocation() == null || Oceian.getLocation() == null || Savania.getLocation() == null){
			severe("Unable to find all Capital Locations.  Booted in SAFE MODE for Listeners");
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(safeListener, this);
		}else if(TRADE_BLACKSMITH == null || TRADE_POTIONS == null || TRADE_ENCHANTMENTS == null
				|| TRADE_RICHPORTAL == null || TRADE_MYSTPORTAL == null){
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(safeListener, this);
			severe("Unable to find all Trade Locations.  Booted in SAFE MODE for Listeners");
		}else if(Abatton.getSpongeLocation() == null || Oceian.getSpongeLocation() == null || Savania.getSpongeLocation() == null){
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(safeListener, this);
			severe("Unable to find all Drifter Locations.  Booted in SAFE MODE for Listeners");
		}else{
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(blockListener, this);
			pm.registerEvents(pluginListener, this);
			pm.registerEvents(playerListener, this);
			pm.registerEvents(entityListener, this);
			pm.registerEvents(safeListener, this);
		}
	}
	public void setupPermissions() {
		Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

		if (permissionsPlugin != null) {
			info("Succesfully connected to Permissions!");
			handler = ((Permissions) permissionsPlugin).getHandler();

		} else {
			handler = null;
			warning("Disconnected from Permissions...what could possibly go wrong?");
		}
	}
	public void setupPermissionsEx() {
		Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("PermissionsEx");

		if (permissionsPlugin != null) {
			info("Succesfully connected to PermissionsEx!");
			ex = PermissionsEx.getPermissionManager();

		} else {
			ex = null;
			warning("Disconnected from PermissionsEx...what could possibly go wrong?");
		}
	}
	public void notification(String message){
		getServer().broadcastMessage(ChatColor.LIGHT_PURPLE.toString() +"[Religion Notification] " + ChatColor.AQUA.toString() + message);
	}
	/**
	 * Logs an informative message to the console, prefaced with this plugin's header
	 * @param message: String
	 */
	public static void info(String message)
	{ 
		log.info(header + ChatColor.WHITE + message);
	}

	/**
	 * Logs a severe error message to the console, prefaced with this plugin's header
	 * Used to log severe problems that have prevented normal execution of the plugin
	 * @param message: String
	 */
	public static void severe(String message)
	{
		log.severe(header + ChatColor.RED + message);
	}

	/**
	 * Logs a warning message to the console, prefaced with this plugin's header
	 * Used to log problems that could interfere with the plugin's ability to meet admin expectations
	 * @param message: String
	 */
	public static void warning(String message)
	{
		log.warning(header + ChatColor.YELLOW + message);
	}

	/**
	 * Logs a message to the console, prefaced with this plugin's header
	 * @param level: Logging level under which to send the message
	 * @param message: String
	 */
	public static void log(java.util.logging.Level level, String message)
	{
		log.log(level, header + message);
	}
	public static void saveInfoFile() {
		try {
            BeyondInfo.writer();
            information.save(infoFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public static void loadInfoFile(YamlConfiguration config, File file) {
        try {
            config.load(file);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String theerror = "error not set";
    
    private static boolean purgeHasRunAlready = false;
    public static void purgeInactivePlayers() {
        // Only need to do this once per server restart.
        if (purgeHasRunAlready) return;

        Abatton.purgeInactivePlayers();
        Oceian.purgeInactivePlayers();
        Savania.purgeInactivePlayers();

        purgeHasRunAlready = true;
    }
    
    public static long getPlayerLastSeenTime(String playerName) {
        // Implementation using FirstLastSeen
        String filename = "plugins/FirstLastSeen/data/"+playerName.toLowerCase();

        java.io.BufferedReader reader = null;
        try {
            reader = new java.io.BufferedReader(new java.io.FileReader(filename));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                String[] s = text.split("\\=");
                if (s.length != 2) continue;
                if (s[0].equals("LastSeen")) {
                    return Long.parseLong(s[1]);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            severe("Can't find last seen file for " + filename);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (reader != null) { reader.close(); } } catch (IOException e) { e.printStackTrace(); }
        }
        return 0;
    }
}
