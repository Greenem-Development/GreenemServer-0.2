package me.greenem.server.mainEvents;

import me.greenem.server.*;

import static me.greenem.server.classes.FeatherFallingData.*;
import static me.greenem.server.voids.SomeClass.*;

import org.bukkit.event.player.PlayerBedEnterEvent;
import java.beans.PropertyChangeSupport;
//import me.greenem.TestPlugin.InventoryGui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.event.inventory.InventoryCloseEvent;

import javax.print.DocFlavor.CHAR_ARRAY;
import javax.swing.border.TitledBorder;
import java.util.Calendar;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Rotation;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Wall;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPainting;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.google.common.escape.ArrayBasedEscaperMap;
import com.google.common.eventbus.DeadEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;

import me.greenem.server.classes.*;
import me.greenem.server.dataBase.AuthDao;
import me.greenem.server.mainEvents.*;
import me.greenem.server.otherStuff.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.CommandTitle;
import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.DataWatcherObject;
import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
import net.minecraft.server.v1_16_R3.EnchantmentProtection.DamageType;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityPose;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.ItemFireworks.EffectType;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.NextTickListEntry;
import net.minecraft.server.v1_16_R3.PacketCompressor;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutAbilities;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreak;
import net.minecraft.server.v1_16_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_16_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.WorldServer;
import net.minecraft.server.v1_16_R3.ArgumentBlockPredicate.b;

import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.FlowerPot;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

//import net.minecraft.server.v1_8_R3.PlayerSelector;

public class ServerPlugin extends JavaPlugin implements Listener {
	public Logger log = getLogger();
	final Random random = new Random(System.currentTimeMillis());
	
	public Inventory AdminSettingsInventoryInv = Bukkit.createInventory(null, 45, "Admin Settings");
	public Inventory AdminSettingsInventoryChangeGamemodeInv = Bukkit.createInventory(null, 27, "Change Gamemode");
	public Inventory ModeChooseInv = Bukkit.createInventory(null, 45, "Mode");
	public Inventory MinigameChooseInv = Bukkit.createInventory(null, 45, "Minigames");
	public Inventory DebugInv = Bukkit.createInventory(null, 54, "DEBUG");
	public ArrayList<String> PlayersBreakers = new ArrayList<>();
	public ArrayList<String> FrozenPlayers = new ArrayList<>();
	public Map <String, Boolean> FrozenPlayersFlyState = new HashMap<>();
	public Map <String, Location> FrozenPlayersFlyLocs = new HashMap<>();
	public ArrayList<String> PlayersFlyInSurvival = new ArrayList<>();
	public Material DebugMaterial = Material.REPEATING_COMMAND_BLOCK;
	public Material DebugMaterialOwnInv = Material.COMMAND_BLOCK_MINECART;
	public ArrayList<String> Alphabet = new ArrayList<>();
	public int FfWorldsAvaliable = 3;
	public int TrWorldsAvaliable = 3;
	public int DwWorldsAvaliable = 3;
	public int SwWorldsAvaliable = 3;
	public int TgWorldsAvaliable = 3;
	public int MmWorldsAvaliable = 3; //murdermystery
	public int JrWorldsAvaliable = 3;
	public int BwWorldsAvaliable = 3;
	public ArrayList<Material> PotsTypes = new ArrayList<>();
	public ArrayList<String> MapListWithBreakingProtection = new ArrayList<>();
	public ArrayList<String> MapListWithHittingProtection = new ArrayList<>();
	public ArrayList<String> MapListWithVoidDeathProtection = new ArrayList<>();
	public ArrayList<String> MapListWithHungerProtection = new ArrayList<>();
	public ArrayList<String> MapListWithPlayersDamageProtection = new ArrayList<>();
	public ArrayList<String> MapListWithFallDamageProtection = new ArrayList<>();
	public ArrayList<String> MapListWithArmorStandProtection = new ArrayList<>();
	public ArrayList<String> MinigamesList = new ArrayList<>();

	public ArrayList<String> AdminPlayersVanished = new ArrayList<>();
	public ArrayList<String> MinigamePlayersVanished = new ArrayList<>();
	//public ArrayList<Location> LobbyLocations = new ArrayList<>();
	public Map <String, Location> LobbyLocations = new HashMap<>();
	public ArrayList<HashMap<String, ArrayList<MapInfo>>> MapLocations = new ArrayList<>();
	public Location MurderMysteryArenaSpectatorSpawn = new Location(Bukkit.getWorld("murdermystery"), 0.5, 104.5, 0.5);
	public String RightClickLore = ChatColor.GOLD + "Right click for using";
	public Location FfWaitingLobbyLoc = new Location(Bukkit.getWorld("featherfalling1"), 0.5, 101.5, -70.5);	
	//public Map <String, Location> Arenas = new HashMap<>();
	//-------------------------ARENAS-----------------------------
	public Map <String, MinigameArena> FfArenas = new HashMap<>();
	public Map <String, MinigameArena> MmArenas = new HashMap<>();
	public Map <String, MinigameArena> JrArenas = new HashMap<>();
	public Map <String, MinigameArena> AuArenas = new HashMap<>();
	//-------------------------ARENAS-----------------------------
//	public int[][][] AllCobwebCombinations1 = new int[6][6][20];
//	public int[][][] AllCobwebCombinations2 = new int[6][6][20];
//	public int[][][] AllCobwebCombinations3 = new int[6][6][20];
	public ArrayList<Player> AuthPlayers = new ArrayList<>();
	public Map <Player, Integer> AuthAttempts = new HashMap<>();
	public Map <String, Inventory> MapAdminSettingsInvs = new HashMap<>();
	public Map <String, Inventory> MapAccountSettingsInvs = new HashMap<>();
    public Properties props = new Properties();
	public File authDirectory = new File(getServer().getWorldContainer(), "files\\authorization");
	public File inventoriesDirectory = new File(getServer().getWorldContainer(), "files\\inventories");
	public File profilesDirectory = new File(getServer().getWorldContainer(), "files\\profiles");
	public ArrayList<Player> VentPlayers = new ArrayList<>();
	
	public ArrayList<String> TrialStaffPlayers = new ArrayList<>();
    
    //public static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	
	private static volatile ServerPlugin INSTANCE;
	
	public static ServerPlugin getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ServerPlugin();
		}
		return INSTANCE;
	}
	
	@Override
	public void onDisable() {
		log.info("Greenem's server plugin has been disabled.");
	}

	@Override
	public void onEnable() {
		log.info("Greenem's server plugin has been enabled!");
		
		//InitialiseInventories();
		
		//LobbyLocations.add(new Location(Bukkit.getWorld("featherfalling"), 0.5, 101.5, -70.5));
		//LobbyLocations.add(new Location(Bukkit.getWorld("murdermystery"), 0.5, 101.5, 0.5));
		
		//MapLocations.add(new Map<"featherfalling", Map<"map3", new Location(Bukkit.getWorld("featherfalling"), 0.5, 101.5, -70.5)>>);
		//new HashMap<>()<"murdermystery", new HashMap<>()<"map1", new Location(
		
	    
		getServer().getPluginManager().registerEvents(this, this);
		
		InitializeAll();
		
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	//log.info("runabble");
                //UpdateFeatherFallingTimer();
                //UpdateMurderMysteryTimer();
            	UpdateMinigameTimer(FfArenas, "featherfalling");
            	UpdateMinigameTimer(MmArenas, "murdermystery");
            	UpdateMinigameTimer(JrArenas, "jumprun");
            	UpdateMinigameTimer(AuArenas, "amongus");
            }
        }, 0L, 20L);
        
//        try {
////			AuthDao.getInstance().getPassword("GREEN3M");
//			AuthDao.getInstance().setPassword("GREEN3M", "1245112");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
        
        //Plugin plugin = this;
        
		//plugin.saveDefaultConfig();
		//FileConfiguration config = plugin.getConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player p = getServer().getPlayer(sender.getName());
		if(cmd.getName().equalsIgnoreCase("register") || (cmd.getName().equalsIgnoreCase("r") && p.getWorld().getName().equalsIgnoreCase("auth"))){
			if(AuthPlayers.contains(p)) {
				RegisterPlayer(p, args);
			}
			else p.sendMessage(ChatColor.RED + "You have already signed in");
		}
		else if(cmd.getName().equalsIgnoreCase("login") || cmd.getName().equalsIgnoreCase("l")){
			if(AuthPlayers.contains(p)) {
				LoginPlayer(p, args);
			}
			else p.sendMessage(ChatColor.RED + "You have already signed in");
		}
		if((cmd.getName().equalsIgnoreCase("hub")) || (cmd.getName().equalsIgnoreCase("spawn"))){
			if(!AuthPlayers.contains(p)) {
				tpToSpawn(getServer().getPlayer(sender.getName()));
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("mode")){
			if(!AuthPlayers.contains(p)) {
				ModeCommand(p, args);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("setcount")){
			if(!AuthPlayers.contains(p)) {
				SetCountCommand(p, args);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("setteam")){ // setplayer <team> <player> <remove>
			if(!AuthPlayers.contains(p)) {
				SetTeamCommand(sender);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("test")){
			if(!AuthPlayers.contains(p)) {
				TestCommand(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("test2")){
			if(!AuthPlayers.contains(p)) {
				Test2Command(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("gm")){
			if(!AuthPlayers.contains(p)) {
				GmCommand(sender, args);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("heal")){
			if(!AuthPlayers.contains(p)) {
				HealCommand(getServer().getPlayer(sender.getName()), args);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("feather")){
			if(!AuthPlayers.contains(p)) {
				FeatherCommand(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("v")){
			if(!AuthPlayers.contains(p)) {
				VanishCommand(p, args);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("chesttocode")){
			if(!AuthPlayers.contains(p)) {
				ChestToCodeCommand(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("createnps")){
			if(!AuthPlayers.contains(p)) {
				//p.sendMessage("Command is disabled");
				CreateNPC(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("destroynpc")){
			if(!AuthPlayers.contains(p)) {
				//p.sendMessage("Command is disabled");
				DeleteAllNPC(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("fly")){
			if(!AuthPlayers.contains(p)) {
				ChangeAllowFly(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("stringstocode")){
			if(!AuthPlayers.contains(p)) {
				StringsToCode(p, args);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("revive")){
			if(!AuthPlayers.contains(p)) {
				ReviveCommand(p, args);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("scaffolding")){
			if(!AuthPlayers.contains(p)) {
				SpawnScaffolding(p, p.getLocation());
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("door")){
			if(!AuthPlayers.contains(p)) {
				CloseOropenDoor(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("auth")){
			if(!AuthPlayers.contains(p)) {
				TpToAuth(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("backupworld")){
			if(!AuthPlayers.contains(p)) {
				BackupWorld(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("save-world")){
			if(!AuthPlayers.contains(p)) {
				SaveWorld(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("newspawn")){
			if(!AuthPlayers.contains(p)) {
				tpToNewSpawn(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if(cmd.getName().equalsIgnoreCase("packetstests")){
			if(!AuthPlayers.contains(p)) {
				//p.sendMessage("Command is disabled");
				PacketsTests(p);
			}
			else p.sendMessage(ChatColor.RED + "Please, sign in to use this command.");
		}
		else if (cmd.getName().equalsIgnoreCase("sqltest")) {
			if(!AuthPlayers.contains(p)) {
				try {
//					AuthDao.getInstance().getPassword("GREEN3M");
					AuthDao.getInstance().setPassword("GREEN3M", "1245112");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
		//--------------------------------------------------------------------------------------------------------------------------------------------------\\
		//---------------------------------------------------------V-O-I-D-S--------------------------------------------------------------------------------\\
		//--------------------------------------------------------------------------------------------------------------------------------------------------\\
	
	
	public void FfLeave(Player p) {
		if(FfArenas.size() == 0) return;
		if(!p.getWorld().getName().startsWith("featherfalling")) return;
		log.info(p.getWorld().getName() + "- 1 player");
		if(FfArenas.get(p.getWorld().getName()).PlayersCount != 0) FfArenas.get(p.getWorld().getName()).PlayersCount--;
		
		if(FfArenas.get(p.getWorld().getName()).PlayersCount < FfArenas.get(p.getWorld().getName()).PlayersMin) {
			p.setExp((float)0.999);
			p.setLevel(0);
			FfArenas.get(p.getWorld().getName()).TimeState = "waiting for min players";
			
		}
		if(FfArenas.get(p.getWorld().getName()).PlayersCount>=FfArenas.get(p.getWorld().getName()).PlayersMin && FfArenas.get(p.getWorld().getName()).PlayersCount<FfArenas.get(p.getWorld().getName()).PlayersMax) {
			FfArenas.get(p.getWorld().getName()).TimeState = "waiting for max players";
			if(FfArenas.get(p.getWorld().getName()).PlayersCount + 1 == FfArenas.get(p.getWorld().getName()).PlayersMax) {
				FfArenas.get(p.getWorld().getName()).TimeLeftToStart = 1 + FfArenas.get(p.getWorld().getName()).TimeToStartMinPlayers;
				
				float persent;
				log.info(FfArenas.get(p.getWorld().getName()).TimeLeftToStart + " - this is time left");
				log.info("" + (FfArenas.get(p.getWorld().getName()).TimeLeftToStart) / (float) (FfArenas.get(p.getWorld().getName()).TimeToStartMinPlayers));
				if(FfArenas.get(p.getWorld().getName()).TimeLeftToStart > FfArenas.get(p.getWorld().getName()).TimeToStartMaxPlayers) persent = (FfArenas.get(p.getWorld().getName()).TimeLeftToStart) / (float) (FfArenas.get(p.getWorld().getName()).TimeToStartMinPlayers);
				else persent = (FfArenas.get(p.getWorld().getName()).TimeLeftToStart) / (float) (FfArenas.get(p.getWorld().getName()).TimeToStartMaxPlayers);
				if(persent > 1) persent = (float) 0.999;
				p.setLevel(FfArenas.get(p.getWorld().getName()).TimeLeftToStart);
				p.setExp(persent);
				
			}
		}
		if(FfArenas.get(p.getWorld().getName()).PlayersCount < 1) {
			FfArenas.remove(p.getWorld().getName());
		}
	}
	
	public void MinigameLeave(Player p, String ArenaType) {
		Map <String, MinigameArena> Data = new HashMap<>();
		Data = null;
		Data = GetMinigameData(ArenaType);
		if(Data == null) return;
		if(Data.size() == 0) return;
		if(!p.getWorld().getName().startsWith(ArenaType)) return;
		if(Data.get(p.getWorld().getName()).PlayersCount <= 0) return;
		if(Data.get(p.getWorld().getName()).ArenaState == "ingame") return;
		log.info(p.getWorld().getName() + "- 1 player");
		if(Data.get(p.getWorld().getName()).PlayersCount != 0) Data.get(p.getWorld().getName()).PlayersCount--;
		
		if(Data.get(p.getWorld().getName()).PlayersCount < Data.get(p.getWorld().getName()).PlayersMin) {
			p.setExp((float)0.999);
			p.setLevel(0);
			Data.get(p.getWorld().getName()).TimeState = "waiting for min players";
			
		}
		if(Data.get(p.getWorld().getName()).PlayersCount>=Data.get(p.getWorld().getName()).PlayersMin && Data.get(p.getWorld().getName()).PlayersCount<Data.get(p.getWorld().getName()).PlayersMax) {
			Data.get(p.getWorld().getName()).TimeState = "waiting for max players";
			if(Data.get(p.getWorld().getName()).PlayersCount + 1 == Data.get(p.getWorld().getName()).PlayersMax) {
				Data.get(p.getWorld().getName()).TimeLeftToStart = 1 + Data.get(p.getWorld().getName()).TimeToStartMinPlayers;
				
				float persent;
				log.info(Data.get(p.getWorld().getName()).TimeLeftToStart + " - this is time left");
				log.info("" + (Data.get(p.getWorld().getName()).TimeLeftToStart) / (float) (Data.get(p.getWorld().getName()).TimeToStartMinPlayers));
				if(Data.get(p.getWorld().getName()).TimeLeftToStart > Data.get(p.getWorld().getName()).TimeToStartMaxPlayers) persent = (Data.get(p.getWorld().getName()).TimeLeftToStart) / (float) (Data.get(p.getWorld().getName()).TimeToStartMinPlayers);
				else persent = (Data.get(p.getWorld().getName()).TimeLeftToStart) / (float) (Data.get(p.getWorld().getName()).TimeToStartMaxPlayers);
				if(persent > 1) persent = (float) 0.999;
				p.setLevel(Data.get(p.getWorld().getName()).TimeLeftToStart);
				p.setExp(persent);
				
			}
		}
		if(Data.get(p.getWorld().getName()).PlayersCount < 1) {
			Data.remove(p.getWorld().getName());
		}
	}
	
	public void LeaveCurrentMinigame(Player p) {
		if(p.getWorld().getName().startsWith("featherfalling")) {
			MinigameLeave(p, "featherfalling");
		}
		if(p.getWorld().getName().startsWith("murdermystery")) {
			MinigameLeave(p, "murdermystery");
		}
	}
	
	public void clearPlayer(Player p) {
		if(p.isOp() == false && !TrialStaffPlayers.contains(p.getName())){
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().clear();
		}
		for (PotionEffect effect : p.getActivePotionEffects()) {
	        p.removePotionEffect(effect.getType());
		}
		if(p.getInventory().contains(Material.NETHER_STAR)) {
			p.getInventory().remove(Material.NETHER_STAR);
		}
		if(p.getInventory().contains(Material.BLAZE_POWDER)) {
			p.getInventory().remove(Material.BLAZE_POWDER);
		}
		if(p.getInventory().contains(Material.REDSTONE)) {
			p.getInventory().remove(Material.REDSTONE);
		}
		if(p.getInventory().contains(Material.PAPER)) {
			p.getInventory().remove(Material.PAPER);
		}
		if(p.getInventory().contains(Material.EMERALD)) {
			p.getInventory().remove(Material.EMERALD);
		}
		String[] str = {};
		HealCommand(p, str);
		MurderMysteryItemsClear(p);
		MinigameVanishOff(p);
//		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
//			@Override
//			public void run() {
				p.setExp(0);
				p.setLevel(0);
//			}
//		}, 1);
	}
	
	/*public void createScoreboard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("Stats", "var");
		Team admin = board.registerNewTeam("Admin");
		Team mainBuilder = board.registerNewTeam("MainBuilder");
		admin.setPrefix("[Admin]");
		//admin.setColor(ChatColor.RED);
		//mainBuilder.setColor(ChatColor.GOLD);
		admin.setPrefix(ChatColor.DARK_RED + "[Admin]" + ChatColor.WHITE + "");
		mainBuilder.setPrefix(ChatColor.GOLD + "[MainBuilder]" + ChatColor.WHITE + "");
		admin.setAllowFriendlyFire(false);
		admin.setNameTagVisibility(NameTagVisibility.ALWAYS);
		
	}*/
	
	/*public void InventoryGui(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "Choose");
		inv.addItem(createGuiItem(Material.FEATHER, "Example Sword", "§aFirst line of the lore", "§bSecond line of the lore"));
        inv.addItem(createGuiItem(Material.IRON_SWORD, "§bExample Helmet", "§aFirst line of the lore", "§bSecond line of the lore"));
	}*/

	
	@EventHandler(priority=EventPriority.NORMAL)
	
	public void PlayerInteractEvent(PlayerInteractEvent evt){
		Player p = evt.getPlayer();
        Block b = evt.getClickedBlock();
        if(p.getGameMode() == GameMode.SURVIVAL) {
        	if(evt.getClickedBlock() != null) {
        		if(evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        			if(IfStringStartsWithArrayListEllement(evt.getPlayer().getWorld().getName(), MapListWithBreakingProtection)) {
        				if(PotsTypes.contains(evt.getClickedBlock().getType())) {
        					evt.setCancelled(true);
        					log.info("pot detected");
        				}
        				if(evt.getClickedBlock().getType().toString().toLowerCase().contains("trapdoor")) {
        					evt.setCancelled(true);
        					log.info("trapdoor detected");
        				}
        			}
        		}
        		if(evt.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("spawn"), 22, 106, -24))) {
        			p.teleport(new Location(p.getWorld(), -1, 146, 13));
        			p.sendMessage(ChatColor.GOLD + "Try to get on the very top of the tree to get a reward!");
        		}
        		if(evt.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("spawn"), 21, 187, -15))) {
        			p.teleport(p.getWorld().getSpawnLocation());
        			int dMoney = 25;
        			int totalMoney = ChangeMoney(p, dMoney);
        			p.sendMessage(ChatColor.GREEN + "Good job! You got " + dMoney + " coins for this. Now you have " + totalMoney + " coins total!");
        		}
        	}
        }
        //InitializeAll();
        if(evt.getPlayer().getWorld().getName().startsWith("spawn")) {
        	SpawnEvent(p, evt, "PlayerInteractEvent");
        }
        if(evt.getPlayer().getWorld().getName().startsWith("featherfalling")) {
        	FfEvent(p, evt, "PlayerInteractEvent");
        }
        AllWorldsEvent(p, evt, "PlayerInteractEvent");
		//if(evt.getPlayer().getWorld().getName().startsWith("featherfalling")) {
	    //evt.getPlayer().sendMessage("" + evt.getAction());
	    if (evt.getHand() == EquipmentSlot.OFF_HAND) return;
	    if((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) && (evt.getPlayer().getItemInHand().getType() == Material.FEATHER)){
	    	if(evt.getPlayer().getPotionEffect(PotionEffectType.SLOW_FALLING) == null){
	    		Vector vec = new Vector (evt.getPlayer().getVelocity().getX(), evt.getPlayer().getVelocity().getY() * .1, evt.getPlayer().getVelocity().getZ());
	    		evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 300, 0));
	    		evt.getPlayer().setVelocity(vec);
	    	}
	    	else{
	    		//Vector vec = new Vector (evt.getPlayer().getVelocity().getX(), evt.getPlayer().getVelocity().getY() * 1, evt.getPlayer().getVelocity().getZ());
	    		//zevt.getPlayer().setVelocity(vec);
	    		evt.getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
	    	}
	    }
	    if((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) && (evt.getPlayer().getItemInHand().getType() == DebugMaterial)){
	    	p.openInventory(DebugInv);
	    }
	    if((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) && (evt.getPlayer().getItemInHand().getType() == DebugMaterialOwnInv)){
	    	p.openInventory(DebugInv);
	    }
	    if(evt.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
	    //log.info("" + (FlowerPot) b.getState().getData().getContents());
	    //log.info(evt.getPlayer().getTargetBlockExact(10).getType().toString() + "");// equals(Material.FLOWER_POT)
	    //log.info(evt.getAction() + "");
	  //With Material:
	    //Set<Material> NullSet = null;
	    //InitializeAll();
	    //log.info(evt.getPlayer().getTargetBlock(NullSet, 4).getBlockData().getMaterial() + "");
	    int maxDistance = 10;
	    /*for(int i = 0; i<maxDistance; i++) {
	    	if(evt.getPlayer().getTargetBlockExact(i) != null) {
	    		//log.info("" + evt.getPlayer().getTargetBlockExact(i).getType());
	    		if(evt.getPlayer().getTargetBlockExact(i).getType().toString()!="AIR") {
	    			if(PotsTypes.contains(evt.getPlayer().getTargetBlockExact(i).getType())) {
	    				evt.setCancelled(true);
	    			}
	    		}
	    	}
	    }*/
	    }
	    //if (b.getState().getData() instanceof FlowerPot) {
        /*if (((FlowerPot) b.getState().getData()).getContents() != null && evt.getClickedBlock() != null && evt.getClickedBlock().getType() == Material.FLOWER_POT && evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	if(evt.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
        		evt.setCancelled(true);
        	}
        }*/
        //}
	    /*if (evt.getClickedBlock() != null && evt.getClickedBlock().getType() == Material.FLOWER_POT && evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
            evt.setCancelled(true);
        }*/
	    /*if(p.getItemInHand() == new ItemStack(Material.FEATHER , 1)){
	    	if(evt.getPlayer().getPotionEffect(PotionEffectType.SLOW_FALLING) == null) evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 180, 5));
	    	else evt.getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
	    }*/
		//}
	}
	
	@EventHandler
	public void DamageRulesManager(EntityDamageEvent e) {
		//log.info(e.getEntity().getType() + "");
		if (e.getEntity().getType().toString().equals("PLAYER")){
			log.info(e.getCause().toString());
			if(!e.getCause().equals(DamageCause.VOID)) {
				if(IfStringStartsWithArrayListEllement(e.getEntity().getWorld().getName(), MapListWithHittingProtection) == true || IfStringStartsWithArrayListEllement(e.getEntity().getWorld().getName(), MapListWithPlayersDamageProtection)) {
					e.setCancelled(true);
					log.info("DamageRulesManager");
				}
				if(e.getCause().equals(DamageCause.FALL)) {
					if(IfStringStartsWithArrayListEllement(e.getEntity().getWorld().getName(), MapListWithFallDamageProtection) == true) {
						e.setCancelled(true);
						log.info("DamageRulesManager");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void PlayerPortalEvent(PlayerPortalEvent evt) {
		
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void PlayerLoginEvent(PlayerJoinEvent e) throws IOException {
		Player p = e.getPlayer();
		e.setJoinMessage("");
		p.setOp(false);
		FileReader reader = null;
		props.clear();
		File f = new File(authDirectory, p.getName() + ".properties");
		if (f.exists()) {
			try {
				reader = new FileReader(f);
				try {
					props.load(reader);
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if(AdminPlayersVanished.contains(p.getName())) {
			AdminPlayersVanished.remove(p.getName());
		}
		if(MinigamePlayersVanished.contains(p.getName())) {
			MinigamePlayersVanished.remove(p.getName());
		}
		if(f.exists() && props.getProperty("password")!=null) {
			String s = p.getAddress().toString().split("/")[1].split(":")[0];
			//log.info(s);
			if(s.endsWith("\\")) s.substring(0, s.length()-1);
			//log.info(s);
			//log.info("same adress: "+props.getProperty("lastLoginAdress").equals(s));
			if(props.getProperty("lastLoginAdress").equals(s)) {
				//log.info(Integer.parseInt(props.getProperty(p.getName()+".lastLoginPort"))+"");
				//log.info("same port: " + (Math.abs(Integer.parseInt(props.getProperty("lastLoginPort"))-p.getAddress().getPort()) < 40000)+"");
				if(Math.abs(Integer.parseInt(props.getProperty("lastLoginPort"))-p.getAddress().getPort()) < 20000) {
					//log.info(Calendar.getInstance().getTimeInMillis()+"");
					//log.info(""+Long.parseLong(props.getProperty(p.getName()+".lastLoginTime")));
					//log.info(""+(Calendar.getInstance().getTimeInMillis() - Long.parseLong(props.getProperty(p.getName()+".lastLoginTime")) < 86400000));
					//log.info("time from last logging in: " + Long.toString(Math.abs(Long.parseLong(props.getProperty("lastRealLoginTime"))-Calendar.getInstance().getTimeInMillis())));
					if(Math.abs(Long.parseLong(props.getProperty("lastRealLoginTime"))-Calendar.getInstance().getTimeInMillis()) < 46400000) {
						p.sendTitle("", "");
						p.sendMessage(ChatColor.GREEN + "You have already logged in before.");
						GiveOpIfNeeds(p);
						tpToSpawn(p);
						PrepareJoinedPlayer(p);
						MakePlayerProfileUpToDate(p);
						MakePlayerAuthUpToDate(p);
						return;
					}
				}
			}
		}
		p.getInventory().clear();
		p.getInventory().setHeldItemSlot(0);
		AuthPlayers.add(p);
		//evt.getPlayer().setCollidable(false);
		//evt.getPlayer().spigot().setCollidesWithEntities(false);
		for (Player p1 : getServer().getOnlinePlayers()) {
			p1.hidePlayer(p);
	        p.hidePlayer(p1);
		}
		RemoveMaterial(p, Material.NETHER_STAR);
		RemoveMaterial(p, Material.BLAZE_POWDER);
		RemoveMaterial(p, Material.CRYING_OBSIDIAN);
		Location loc = new Location(Bukkit.getWorld("auth"), 0.5, 37.5, 0.5);
		loc.setYaw(135);
		p.teleport(loc);
		p.getWorld().setSpawnLocation(loc);
		p.getWorld().setTime(1000);
		//if(props.getProperty(e.getPlayer().getName()+".lastLoginAdress")!=null) {//если он зареган
		if(props.getProperty("password")==null) {
			p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GOLD + "You should register", ChatColor.ITALIC + "" + ChatColor.GRAY + "/register <password> <password>", 5, 99999, 30);
		}
		else {
			p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GOLD + "You should login", ChatColor.ITALIC + "" + ChatColor.GRAY + "/login <password>", 5, 99999, 30);
		}
		p.setGameMode(GameMode.ADVENTURE);
		AuthStartCountdown(p, 30);
		//}
//		for (Player p1 : getServer().getOnlinePlayers()) {
//            p1.showPlayer(p);
//        }
//		p.setCanPickupItems(true);
//		//p.setCollidable(false);
//		//p.spigot().setCollidesWithEntities(false);
//		tpToSpawn(p);
//		for (Player player : p.getWorld().getPlayers()) {
//			player.sendMessage(ChatColor.WHITE + e.getPlayer().getName() + " joined the server!");
//		}
//		evt.getPlayer().setGameMode(GameMode.SURVIVAL);
//		//evt.getPlayer().getInventory().clear();
//		evt.getPlayer().teleport(new Location(Bukkit.getWorld("Spawn"), 0.5, 101, 0.5));
//		evt.getPlayer().setLevel(0);
//		evt.getPlayer().setExp(0);
//		//Bukkit.broadcastMessage(ChatColor.WHITE + evt.getPlayer().getName() + " joined the server!");
	}
	
//	@EventHandler
//	public void PlayerChatEvent(AsyncPlayerChatEvent e) {
//		Player p = e.getPlayer();
//		if(p.getWorld().getName().equalsIgnoreCase("auth")) {
//			
//			e.setCancelled(true);
//		}
//	}
	
	public void tpToSpawn(Player p) {
		clearPlayer(p);
		LeaveCurrentMinigame(p);
		p.teleport(new Location(Bukkit.getWorld("Spawn"), 0.5, 101.5, 0.5));
		//         T        U           T
		if(!PlayersFlyInSurvival.contains(p.getName()) && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) p.setAllowFlight(false);
		if(!p.isOp() && !TrialStaffPlayers.contains(p.getName())) p.setGameMode(GameMode.SURVIVAL);
		VanishOff(p);
		p.getWorld().setSpawnLocation(0, 101, 0);
		
		//log.info(p.getInventory().getSize() + "");
		//if(p.getInventory().getSize() < )
		
		if(p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) {
			if(p.getInventory().contains(Material.REDSTONE)) {
				p.getInventory().remove(Material.REDSTONE);
			}
			if(p.getInventory().contains(Material.PAPER)) {
				p.getInventory().remove(Material.PAPER);
			}
			if(p.getInventory().contains(Material.EMERALD)) {
				p.getInventory().remove(Material.EMERALD);
			}
		}
		
		
		ItemStack ChooseMinigameItem = createGuiItem(Material.NETHER_STAR, ChatColor.GREEN + "Choose a minigame", ChatColor.GRAY + "Right click for using");
		ItemStack AdminSettingsItem = createGuiItem(Material.BLAZE_POWDER, ChatColor.GREEN + "Admin settings", ChatColor.GRAY + "Right click for using");
		ItemStack AccountSettingsItem = createGuiItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Account settings", ChatColor.GRAY + "Right click for using");
		
		if(!p.getInventory().contains(ChooseMinigameItem)) {
			if(p.getInventory().getItem(0) == null) p.getInventory().setItem(0, ChooseMinigameItem);
			else p.getInventory().addItem(ChooseMinigameItem);
		}
		if(!p.getInventory().contains(AccountSettingsItem)) {
			if(p.getInventory().getItem(4) == null) p.getInventory().setItem(4, AccountSettingsItem);
			else p.getInventory().addItem(AccountSettingsItem);
		}
		
		if(p.isOp() == true || TrialStaffPlayers.contains(p.getName())) {
			if(!p.getInventory().contains(AdminSettingsItem)) {
				if(p.getInventory().getItem(1) == null) p.getInventory().setItem(1, AdminSettingsItem);
				else p.getInventory().addItem(AdminSettingsItem);
			}
		}
		p.getWorld().setTime(15000);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent evt) {
		Player p = evt.getPlayer();
		int x = p.getLocation().getBlockX();
		int y = p.getLocation().getBlockY();
		int z = p.getLocation().getBlockZ();
		if(evt.getPlayer().getWorld().getName().startsWith("featherfalling")) {
			//p.sendMessage("" + evt.getPlayer().getLocation().getBlock().getType());
			//p.sendMessage("--------------------");
			//if(evt.getPlayer().getLocation().getBlock().getType() == Material.TRIPWIRE || evt.getPlayer().getWorld().getBlockAt(x, y+1, z).getType() == Material.TRIPWIRE) {
			if(evt.getPlayer().getLocation().getBlock().getType() == Material.TRIPWIRE || evt.getPlayer().getWorld().getBlockAt(x, y+1, z).getType() == Material.TRIPWIRE || evt.getPlayer().getWorld().getBlockAt(x, y+2, z).getType() == Material.TRIPWIRE) {
				p.sendTitle(ChatColor.RED + "You have toched it", "", 0, 20, 20);
			}
			if(evt.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR){
				evt.getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
			}	
		}
		//evt.getPlayer().sendMessage(evt.getPlayer().getLocation().getY() + "");
		if(evt.getPlayer().getLocation().getY() < -40.0){
			if(IfStringStartsWithArrayListEllement(p.getWorld().getName(), MapListWithVoidDeathProtection) == true) {
				evt.getPlayer().teleport(evt.getPlayer().getWorld().getSpawnLocation());
			}
		}
	}
	
	@EventHandler
	public void BlockBreakEvent(BlockBreakEvent evt) {
		if(!PlayersBreakers.contains(evt.getPlayer().getName())) {
			if((evt.getPlayer().getGameMode() == GameMode.SURVIVAL)) {
				if(IfStringStartsWithArrayListEllement(evt.getPlayer().getWorld().getName(), MapListWithBreakingProtection)){
					evt.getPlayer().sendMessage(ChatColor.DARK_RED + "You can't break blocks here.");
					evt.setCancelled(true);
					log.info("BlockBreakEvent");
					//evt.getPlayer().sendMessage("" + evt.getBlock().getType());
					//evt.getBlock().setType(evt.getBlock().getType());
				}
			}
		}
	}
	
    @EventHandler
    public void onFrameBrake(HangingBreakByEntityEvent e) {
    	/*log.info("entity: " + e.getEntity().getType().getName());
    	log.info("world: " + getServer().getEntity(e.getEntity().getUniqueId()).getWorld().getName());
    	log.info("cause: " + e.getCause().toString().equals("ENTITY"));
    	log.info("" + e.getEntity().getName());
    	log.info("" + e.getRemover());*/
    	//log.info("gamemode: " + getServer().getPlayer(e.getEntity().getType().PLAYER.getName()).getGameMode());
    	if(e.getRemover().getType() == EntityType.PLAYER){
    		if(!PlayersBreakers.contains(e.getRemover().getName())) {
    			if(IfStringStartsWithArrayListEllement(e.getRemover().getWorld().getName(), MapListWithBreakingProtection)){
					if(getServer().getPlayer(e.getRemover().getName()).getGameMode() == GameMode.SURVIVAL) {
						e.setCancelled(true);
						log.info("onFrameBrake");
					}
				}
    		}
    	}
    }
	
	@EventHandler
	public void BlockPlaceEvent(BlockPlaceEvent evt) {
		if(evt.getBlock().getType() == DebugMaterial && evt.getPlayer().getInventory().getItemInMainHand().getType() == DebugMaterial) {
			evt.setCancelled(true);
			log.info("BlockPlaceEvent");
		}
		if(!PlayersBreakers.contains(evt.getPlayer().getName())) {
			if((evt.getPlayer().getGameMode() == GameMode.SURVIVAL)) {
				if(IfStringStartsWithArrayListEllement(evt.getPlayer().getWorld().getName(), MapListWithBreakingProtection)){
					evt.getPlayer().sendMessage(ChatColor.DARK_RED + "You can't place blocks here.");
					evt.setCancelled(true);
					log.info("BlockPlaceEvent");
					//evt.getPlayer().sendMessage("" + evt.getBlock().getType());
					//evt.getBlock().setType(evt.getBlock().getType());
				}
			}
		}
	}
	
	@EventHandler
    public void FrameBreaking(EntityDamageByEntityEvent e) {
		if(IfStringStartsWithArrayListEllement(e.getDamager().getWorld().getName(), MapListWithBreakingProtection)){
        if (e.getEntity() instanceof ItemFrame) {
            if (e.getDamager() instanceof Player) {
            	Player Damager = (Player) e.getDamager();
                if (Damager.getGameMode() == GameMode.SURVIVAL || Damager.getGameMode() == GameMode.ADVENTURE) {
                	if(!PlayersBreakers.contains(Damager.getName())) {
                		e.setCancelled(true);
                		log.info("FrameBreaking");
                    }
                }
            }
            if (e.getDamager() instanceof Projectile) {
                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                	Projectile Arrow = (Projectile) e.getDamager();
                	Player Damager = (Player) Arrow.getShooter();
                    if (Damager.getGameMode() == GameMode.SURVIVAL || Damager.getGameMode() == GameMode.ADVENTURE) {
                		if(!PlayersBreakers.contains(Damager.getName())) {
                			e.getDamager().remove();
                        	e.setCancelled(true);
                        	log.info("FrameBreaking");
                		}
                	}
                }
            }
        }
        if (e.getEntity() instanceof Painting) {
        	log.info(e.getDamager().toString());
            if (e.getDamager() instanceof Projectile || e.getDamager() instanceof Arrow) {
            	Projectile Damager = (Projectile) e.getDamager();
            	ProjectileSource Shooter = Damager.getShooter();
            	if(Shooter instanceof Player) {
            		Player p = (Player) Shooter;
            		if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
                		if(!PlayersBreakers.contains(p.getName())) {
                			e.setCancelled(true);
                			log.info("FrameBreaking");
                    	}
                	}
            	}
            	else {
            		e.setCancelled(true);
            		log.info("FrameBreaking");
            	}
            }
            if (e.getDamager() instanceof Projectile) {
                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                	if (getServer().getPlayer(e.getDamager().getName()).getGameMode() == GameMode.SURVIVAL || (getServer().getPlayer(e.getDamager().getName()).getGameMode() == GameMode.ADVENTURE)) {     
                		if(!PlayersBreakers.contains(e.getDamager().getName())) {
                			e.getDamager().remove();
                        	e.setCancelled(true);
                        	log.info("FrameBreaking");
                		}
                	}
                }
            }
        }
        if(e.getEntity().getType() == EntityType.PLAYER) {
        	if(e.getDamager().getType() == EntityType.PLAYER) {
        		PlayerHitsPlayerEvt((Player)e.getDamager(), (Player)e.getEntity(), e);
        	}
        }
		}
		if(e.getEntity() instanceof ArmorStand) {
			if(IfStringStartsWithArrayListEllement(e.getEntity().getWorld().getName(), MapListWithArmorStandProtection)) {
				if(e.getDamager() instanceof Player) {
					if(((Player)e.getDamager()).getGameMode().equals(GameMode.ADVENTURE) || ((Player)e.getDamager()).getGameMode().equals(GameMode.SURVIVAL)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

    @EventHandler
    public void FrameRotation(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
        	if(!PlayersBreakers.contains(e.getPlayer().getName())) {
        		if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {     
        			e.setCancelled(true);
        			log.info("FrameRotation");
            	}
        	}
        }
    }
	
	@EventHandler
	public void FoodLevelChangeEvent(FoodLevelChangeEvent evt) {
		if(IfStringStartsWithArrayListEllement(evt.getEntity().getWorld().getName(), MapListWithHungerProtection)){
			evt.setCancelled(true);
			//log.info("FoodLevelChangeEvent");
		}
	}
	
	public void ChangeServerPlayerData (Player p) {
		/*ServerPlayer CurrentServerPlayer = new ServerPlayer();
		CurrentServerPlayer.nickName = p.getName();
		CurrentServerPlayer.coins = 0;
		CurrentServerPlayer.Role = "Player";
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		log.info(gson.toJson(CurrentServerPlayer));*/
	}
	
	public void AdminSettingsInventory(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, "Admin Settings");
		inv = AdminSettingsInventoryInv;
		if(AdminPlayersVanished.contains(p.getName())) {
			p.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(99999, 1));
		}
		//p.closeInventory();
		if(PlayersBreakers.contains(p.getName())) {
			inv.setItem(12, createGuiItem(Material.IRON_AXE, ChatColor.WHITE + "Breaking blocks", ChatColor.GOLD + "Breaking blocks in survival mode", ChatColor.GREEN + "Enabled"));
		}
		else {
			inv.setItem(12, createGuiItem(Material.IRON_AXE, ChatColor.WHITE + "Breaking blocks", ChatColor.GOLD + "Breaking blocks in survival mode", ChatColor.RED + "Disabled"));
		}
		if(PlayersFlyInSurvival.contains(p.getName())) {
			inv.setItem(10, createGuiItem(Material.ELYTRA, ChatColor.WHITE + "Flight", ChatColor.GOLD + "Flying in survival mode", ChatColor.GREEN + "Enabled"));
		}
		else {
			inv.setItem(10, createGuiItem(Material.ELYTRA, ChatColor.WHITE + "Flight", ChatColor.GOLD + "Flying in survival mode", ChatColor.RED + "Disabled"));
		}
		if(p.getPotionEffect(PotionEffectType.NIGHT_VISION) != null) {
			inv.setItem(14, createGuiItem(Material.ENDER_EYE, ChatColor.WHITE + "Night vision", ChatColor.GOLD + "Gives the night vision effect", ChatColor.GREEN + "Enabled"));
		}
		else {
			inv.setItem(14, createGuiItem(Material.ENDER_EYE, ChatColor.WHITE + "Night vision", ChatColor.GOLD + "Gives the night vision effect", ChatColor.RED + "Disabled"));
		}
		//log.info(AdminPlayersVanished.contains(p.getName())+"");
		if(AdminPlayersVanished.contains(p.getName())) {
			inv.setItem(31, createGuiItem(Material.CRIMSON_HYPHAE, ChatColor.WHITE + "Vanish", ChatColor.GOLD + "Enables invisibility mode", ChatColor.GREEN + "Enabled"));
		}
		else {
			inv.setItem(31, createGuiItem(Material.WARPED_HYPHAE, ChatColor.WHITE + "Vanish", ChatColor.GOLD + "Enables invisibility mode", ChatColor.RED + "Disabled"));
		}
        p.openInventory(inv);
	}
	
	public void AdminSettingsInventoryChangeGamemode(Player p){
		Inventory inv = AdminSettingsInventoryChangeGamemodeInv;
		//p.closeInventory();
		p.openInventory(inv);
	}
	
	public void AccountSettingsInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "Account Settings");
		//p.closeInventory();
		CreateAccountSettingsInv(p, inv);
		p.openInventory(inv);
	}
	
	@EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
        if (e.getInventory() == AdminSettingsInventoryInv || e.getInventory() == AdminSettingsInventoryChangeGamemodeInv || e.getInventory() == ModeChooseInv || e.getInventory() == MinigameChooseInv || e.getInventory() == DebugInv) {
        	e.setCancelled(true);
        	log.info("onInventoryClick");
        	final ItemStack clickedItem = e.getCurrentItem();
        	if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.VINE) return;
        	if(e.getInventory() == AdminSettingsInventoryInv) {
        		if(clickedItem.getType() == Material.ELYTRA) {
        			if(PlayersFlyInSurvival.contains(p.getName())) {
        				PlayersFlyInSurvival.remove(p.getName());
        				if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) p.setAllowFlight(false);
        			}
        			else {
        				PlayersFlyInSurvival.add(p.getName());
        				p.setAllowFlight(true);
        			}
        			/*if(p.getAllowFlight() == true) {
        				p.setAllowFlight(false);
        			}
        			else {
        				p.setAllowFlight(true);
        			}*/
        			p.closeInventory();
        		}
        		if(clickedItem.getType() == Material.IRON_AXE) {
        			if(PlayersBreakers.contains(p.getName())) {
        				PlayersBreakers.remove(p.getName());
        			}
        			else {
        				PlayersBreakers.add(p.getName());
        			}
        			p.closeInventory();
        		}
        		if(clickedItem.getType() == Material.ENDER_EYE) {
        			if(p.getPotionEffect(PotionEffectType.NIGHT_VISION) != null) {
        				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
        			}
        			else {
        				p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(99999, 1));
        			}
        			p.closeInventory();
        		}
        		if(clickedItem.getType() == Material.END_CRYSTAL) {
        			AdminSettingsInventoryChangeGamemode(p);
        		}
        		if(clickedItem.getType() == Material.WARPED_HYPHAE || clickedItem.getType() == Material.CRIMSON_HYPHAE) {
        			/*if(AdminPlayersVanished.contains(p.getName())) {
        				VanishOff(p);
        			}
        			else {
        				VanishOn(p);
        			}*/
        			String[] args = {};
        			VanishCommand(p, args);
        			p.closeInventory();
        		}
        		//log.info(AdminPlayersVanished.contains(p.getName())+"");
        	}
        	if(e.getInventory() == AdminSettingsInventoryChangeGamemodeInv) {
        		if(p.isOp() == false && !TrialStaffPlayers.contains(p.getName())) {
        			p.sendMessage(ChatColor.DARK_RED + "You don't have permissions to do this.");
        			return;
        		}
        		if(clickedItem.getType() == Material.CRIMSON_FUNGUS) {
        			AdminSettingsInventory(p);
        		}
        		if(clickedItem.getType() == Material.STONE_PICKAXE) {
        			if(PlayersFlyInSurvival.contains(p.getName())) {
        				p.setGameMode(GameMode.SURVIVAL);
        				p.setAllowFlight(true);
        			}
        			else {
        				p.setGameMode(GameMode.SURVIVAL);
        			}
        			p.closeInventory();
        		}
        		if(clickedItem.getType() == Material.NETHERITE_BLOCK) {
        			p.setGameMode(GameMode.CREATIVE);
        			p.closeInventory();
        		}
        		if(clickedItem.getType() == Material.FILLED_MAP) {
        			if(PlayersFlyInSurvival.contains(p.getName())) {
        				p.setGameMode(GameMode.ADVENTURE);
        				p.setAllowFlight(true);
        			}
        			else {
        				p.setGameMode(GameMode.ADVENTURE);
        			}
        			p.closeInventory();
        		}
        		if(clickedItem.getType() == Material.WHITE_STAINED_GLASS) {
        			p.setGameMode(GameMode.SPECTATOR);
        			p.closeInventory();
        		}
        	}
        	if(e.getInventory() == ModeChooseInv) {
        		if(clickedItem.getType() == Material.CAKE) {
        			ChooseMinigame(p);
        		}
        	}
        	if(e.getInventory() == MinigameChooseInv) {
        		if(clickedItem.getType() == Material.FEATHER) {
        			//TpToFeatherFalling(p);
        			TpToMinigame(p, FfArenas, "featherfalling", 60, 10, 2, 4, 3, 60, 120);
        		}
        		if(clickedItem.getType() == Material.IRON_SWORD) {
        			//TpToFeatherFalling(p);
        			TpToMinigame(p, MmArenas, "murdermystery", 60, 10, 2, 4, 3, 60, 120);
        		}
        		if(clickedItem.getType() == Material.NETHERITE_HOE) {
        			//TpToFeatherFalling(p);
        			TpToMinigame(p, AuArenas, "amongus", 60, 10, 2, 4, 1, 60, 120);
        		}
        		if(clickedItem.getType() == Material.CRIMSON_FUNGUS) {
        			ChoosingMode(p);
        		}
        	}
        	if(e.getInventory() == DebugInv) {
        		p.sendMessage("" + e.getRawSlot());
        	}
        	//p.sendMessage("You clicked at slot " + e.getRawSlot());
        }
        if(MapAccountSettingsInvs.values().contains(e.getInventory())) {
        	final ItemStack clickedItem = e.getCurrentItem();
        	e.setCancelled(true);
        	log.info("onInventoryClick");
        	if(clickedItem.getType() == Material.NETHERITE_CHESTPLATE) {
        		
        	}
        }
        if((p.getInventory().getItemInMainHand().getType() == DebugMaterial || p.getInventory().getItemInOffHand().getType() == DebugMaterial) && e.getInventory() != DebugInv) { // && e.getClickedInventory() != p.getInventory()
        	e.setCancelled(true);
        	log.info("onInventoryClick");
        	p.sendMessage("" + e.getRawSlot());
    	}
        /*if((p.getInventory().getItemInMainHand().getType() == DebugMaterialOwnInv || p.getInventory().getItemInOffHand().getType() == DebugMaterialOwnInv) && e.getClickedInventory().equals(p.getInventory())) {
        	e.setCancelled(true);
        	p.sendMessage("" + e.getRawSlot());
    	}*/
        if(GetMinigameData(p.getWorld().getName()) != null) {
    		//log.info("1234");
    		for(int i = 0; i<GetMinigameData(p.getWorld().getName()).size(); i++) {
    			if(e.getInventory() == GetMinigameData(p.getWorld().getName()).get(GetMinigameName(p.getWorld().getName()) + (i+1)).MapVotingInventory) {
    				//log.info("5678");
    				MapVotingClick(p, e);
    			}
    			if(e.getInventory() == GetMinigameData(p.getWorld().getName()).get(GetMinigameName(p.getWorld().getName()) + (i+1)).ModeVotingInventory) {
    				
    			}
    		}
    	}
    }
	
	public void MapVotingClick(Player p, InventoryClickEvent e) {
		if(e.isCancelled() == true) return;
		e.setCancelled(true);
		log.info("MapVotingClick");
		String MinigameName = GetMinigameName(p.getWorld().getName());
		String WorldName = p.getWorld().getName();
		Map <String, MinigameArena> Data = new HashMap<>();
		Data = GetMinigameData(WorldName);
		if(Data != null) {
			log.info("Data: ||||| " + Data.toString());
			if(Data.get(WorldName) == null) {
				log.info("NULL AFTER RELOAD");
				return;
			}
			if(Data.get(WorldName).MapVotingInventory.equals(e.getClickedInventory())) {
				log.info("Clicked Inventory");
				//String mapName = e.getCurrentItem().getItemMeta().getLocalizedName();
				int WherePVotedBefore = -1;
				String NumberHeClicked = null;
				for(int j = 0; j < MapLocations.size(); j++) {
					if(MapLocations.get(j).containsKey(MinigameName)) {
					for(int k = 0; k<MapLocations.get(j).get(MinigameName).size(); k++) {
						//log.info("checking name");
						if(e.getCurrentItem().getItemMeta().getDisplayName().toString().startsWith(MapLocations.get(j).get(MinigameName).get(k).Name)) {
							NumberHeClicked = Integer.toString(MapLocations.get(j).get(GetMinigameName(p.getWorld().getName())).get(k).Number);
							log.info("NumberHeCliked = " + NumberHeClicked);
						}
					}
					}
				}
				if(NumberHeClicked != null) {
						//log.info("Map moment 1");
						//log.info("Display: " + e.getCurrentItem().getItemMeta().getDisplayName());
						//log.info("Localized: " + e.getCurrentItem().getItemMeta().getLocalizedName());
						if(Data.get(WorldName).MapVoting.containsKey(p.getName())) {
							WherePVotedBefore = Integer.parseInt(Data.get(WorldName).MapVoting.get(p.getName()));
							Data.get(WorldName).MapVoting.remove(p.getName());
							log.info("removed");
						}
						//if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith(MapLocations.get(j).get(GetMinigameName(p.getWorld().getName())).get(k).Name)) {
							//log.info("Map moment 2");
						if(!Data.get(WorldName).MapVoting.containsKey(p.getName())) {
							Data.get(WorldName).MapVoting.put(p.getName(), NumberHeClicked);
							log.info("added");
						}
						p.closeInventory();
						/*if(MapLocations.get(j).get(GetMinigameName(p.getWorld().getName())).get(k).Number == WherePVotedBefore) {
						//p.sendMessage(ChatColor.RED + "You have already voted for this map.");
						}*/
					//}
				}
			}
		}
	}
	
	public void ChoosingMode(Player p){
			Inventory inv = ModeChooseInv; 
	        p.openInventory(inv);
	}
	
	public void ChoosingMinigameMap(Player p) {
		String MinigameName = GetMinigameName(p.getWorld().getName());
		String WorldName = p.getWorld().getName();
		Map <String, MinigameArena> Data = new HashMap<>();
		Data = GetMinigameData(WorldName);
		if(Data != null) {
			log.info("Data: ||||| " + Data.toString());
			if(Data.get(WorldName) == null) {
				log.info("NULL AFTER RELOAD");
				return;
			}
			if(Data.get(WorldName).PlayersCount < Data.get(WorldName).PlayersMax && Data.get(WorldName).ArenaState != "ingame") {
				Inventory inv = Data.get(WorldName).MapVotingInventory;
				//if(p.getInventory() == inv) return;
				for(int j = 0; j < MapLocations.size(); j++) {
					//log.info("1: " + MapLocations.get(j));
					//log.info("2: " + MapLocations.get(j).get(MinigameName).get(0));
					/*log.info(ChatColor.MAGIC + MapLocations.toString() + "");
					log.info(ChatColor.AQUA + "" + MapLocations.get(j).get(MinigameName).size() + "\n");*/
					if(MapLocations.get(j).containsKey(MinigameName)) {
					log.info(ChatColor.BLUE + "MapLocations.size() = " + MapLocations.size());
					for(int k = 0; k<MapLocations.get(j).get(MinigameName).size(); k++) {
						if(MinigameName == MapLocations.get(j).get(MinigameName).get(k).MinigameName) {
							log.info(ChatColor.DARK_RED + ""+ k + "");
							log.info(ChatColor.DARK_GRAY + "" + MapLocations.get(j).get(MinigameName).toString());
							log.info(ChatColor.DARK_GRAY + "" + MapLocations.get(j).get(MinigameName).get(k).toString());
							log.info(ChatColor.AQUA + MapLocations.get(j).get(MinigameName).toString() + "");
							int PositionNumber = 0;
							int currentCount = 0;
							int[] List = new int[20];
							for(int i = 0; i < MapLocations.get(j).get(MinigameName).size(); i++) {
								//if(MapLocations.get(j).get(MinigameName).get(i).MinigameName == MinigameName) {
									//if(MapLocations.get(j).get(MinigameName).get(i).Voted.contains(p.getName())) return;
									Material ItemMaterial = Material.WHITE_STAINED_GLASS;
									List[0] = 0;
									final int iCopy = i;
									final int jCopy = j;
									if(Data.get(WorldName).MapVoting == null) List[0] = 0; 
									else {
										Data.get(WorldName).MapVoting.values().forEach(v -> {
											log.info(Integer.parseInt(v) + " - this is first");
											log.info(MapLocations.get(jCopy).get(MinigameName).get(iCopy).Number + " - this is second");
											if(Integer.parseInt(v) == MapLocations.get(jCopy).get(MinigameName).get(iCopy).Number) {
												List[0]++;
												//currentCount++;
											}
										});
									}
									if(List[0] < 1) ItemMaterial = Material.WHITE_STAINED_GLASS;
									/*if(Data.get(MinigameName).MapVoting.containsValue(MapLocations.get(j).get(MinigameName).get(i).Number)){
										currentCount++;
									}*/
									if(List[0] >= 1) ItemMaterial = Material.RED_STAINED_GLASS;
									if(List[0] >= 2) ItemMaterial = Material.ORANGE_STAINED_GLASS;
									if(List[0] >= 3) ItemMaterial = Material.YELLOW_STAINED_GLASS;
									if(List[0] >= 4) ItemMaterial = Material.GREEN_STAINED_GLASS;
									if(List[0] >= 5) ItemMaterial = Material.LIGHT_BLUE_STAINED_GLASS;
									if(List[0] >= 6) ItemMaterial = Material.BLUE_STAINED_GLASS;
									currentCount = 0;
									
									/*if(MapLocations.get(j).get(MinigameName).get(i).Voted.size() >= 1) ItemMaterial = Material.RED_STAINED_GLASS;
									if(MapLocations.get(j).get(MinigameName).get(i).Voted.size() >= 2) ItemMaterial = Material.ORANGE_STAINED_GLASS;
									if(MapLocations.get(j).get(MinigameName).get(i).Voted.size() >= 3) ItemMaterial = Material.YELLOW_STAINED_GLASS;
									if(MapLocations.get(j).get(MinigameName).get(i).Voted.size() >= 4) ItemMaterial = Material.GREEN_STAINED_GLASS;
									if(MapLocations.get(j).get(MinigameName).get(i).Voted.size() >= 5) ItemMaterial = Material.LIGHT_BLUE_STAINED_GLASS;
									if(MapLocations.get(j).get(MinigameName).get(i).Voted.size() >= 6) ItemMaterial = Material.BLUE_STAINED_GLASS;*/
									int ThisMapVotings = 0;
									for (Player player : p.getWorld().getPlayers()) {
										if(GetArena(p).MapVoting.get(p.getName())!=null) {
											if(Integer.parseInt(GetArena(p).MapVoting.get(p.getName()))==MapLocations.get(j).get(MinigameName).get(i).Number) ThisMapVotings++;
										}
									}
									inv.setItem(PositionNumber, createGuiItem(ItemMaterial, MapLocations.get(j).get(MinigameName).get(i).Name + " (" + ThisMapVotings + " players voted)", MapLocations.get(j).get(MinigameName).get(i).Descripction)); //MapLocations.get(j).get(MinigameName).get(i).Voted.size()
									PositionNumber++;
								//}
							}
							p.openInventory(inv);
							log.info("Inventory has been opened.");
							
						}
					}
				}
				}
			}
		}
	}
	
	public void ChooseMinigame(Player p) {
		Inventory inv = MinigameChooseInv; 
        p.openInventory(inv);
	}
	
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
	
	protected ItemStack createGuiItem(final Material material, final String name, final ArrayList<String> lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        
        String[] loreArray = new String[lore.size()];
        for(int i = 0; i < lore.size(); i++) {
        	loreArray[i] = lore.get(i);
        }
        
        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(loreArray));

        item.setItemMeta(meta);

        return item;
    }
	
	public void InitialiseInventories(){
		
		ItemStack LimeStainedPlane = createGuiItem(Material.LIME_STAINED_GLASS_PANE, " ", " ", "");
		ItemStack GreenStainedPlane = createGuiItem(Material.GREEN_STAINED_GLASS_PANE, " ", " ", "");
		ItemStack GrayStainedPlane = createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " ", " ", "");
		
		Inventory inv = AdminSettingsInventoryInv;
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, createGuiItem(Material.VINE, " ", "", ""));
		}
		inv.setItem(10, createGuiItem(Material.ELYTRA, ChatColor.WHITE + "Flight", ChatColor.GOLD + "Flying in survival mode", ""));
		inv.setItem(11, LimeStainedPlane);
		inv.setItem(12, createGuiItem(Material.IRON_AXE, ChatColor.WHITE + "Breaking blocks", ChatColor.GOLD + "Breaking blocks in survival mode", ""));
		inv.setItem(13, LimeStainedPlane);
		inv.setItem(14, createGuiItem(Material.ENDER_EYE, ChatColor.WHITE + "Night vision", ChatColor.GOLD + "", ""));
		inv.setItem(15, LimeStainedPlane);
		inv.setItem(16, createGuiItem(Material.END_CRYSTAL, ChatColor.WHITE + "Change gamemode", ChatColor.GOLD + "Click to change gamemode", ""));
		inv.setItem(30, LimeStainedPlane);
		inv.setItem(31, createGuiItem(Material.WHITE_STAINED_GLASS_PANE, ChatColor.WHITE + "Vanish", ChatColor.GOLD + "Enables invisibility mode", ""));
		inv.setItem(32, LimeStainedPlane);
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		
		inv = AdminSettingsInventoryChangeGamemodeInv;
		
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, createGuiItem(Material.VINE, " ", "", ""));
		}
		inv.setItem(10, createGuiItem(Material.STONE_PICKAXE, ChatColor.WHITE + "Survival", ChatColor.GOLD + "Survival mode", ""));
		inv.setItem(11, LimeStainedPlane);
		inv.setItem(12, createGuiItem(Material.NETHERITE_BLOCK, ChatColor.WHITE + "Creative", ChatColor.GOLD + "Creative mode", ""));
		inv.setItem(13, LimeStainedPlane);
		inv.setItem(14, createGuiItem(Material.FILLED_MAP, ChatColor.WHITE + "Adventure", ChatColor.GOLD + "Adventure mode", ""));
		inv.setItem(15, LimeStainedPlane);
		inv.setItem(16, createGuiItem(Material.WHITE_STAINED_GLASS, ChatColor.WHITE + "Spectator", ChatColor.GOLD + "Spectator mode", ""));
		inv.setItem(22, createGuiItem(Material.CRIMSON_FUNGUS, ChatColor.WHITE + "Back to settings", ChatColor.GOLD + "Click to go back ", ""));
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		
		inv = ModeChooseInv;
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, createGuiItem(Material.VINE, " ", "", ""));
		}
		inv.setItem(12, LimeStainedPlane);
		inv.setItem(13, LimeStainedPlane);
		inv.setItem(14, LimeStainedPlane);
		inv.setItem(20, LimeStainedPlane);
		inv.setItem(21, LimeStainedPlane);
		inv.setItem(23, LimeStainedPlane);
		inv.setItem(24, LimeStainedPlane);
		inv.setItem(29, LimeStainedPlane);
		inv.setItem(30, LimeStainedPlane);
		inv.setItem(31, LimeStainedPlane);
		inv.setItem(32, LimeStainedPlane);
		inv.setItem(33, LimeStainedPlane);
		inv.setItem(22, createGuiItem(Material.CAKE, "Minigames", ChatColor.GOLD + "Click to choose a minigame", ""));
        //inv.addItem(createGuiItem(Material.IRON_SWORD, "§b", "§a", "§b"));
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		
		inv = MinigameChooseInv;
		
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, createGuiItem(Material.VINE, " ", "", ""));
		}
		
		inv.setItem(0, createGuiItem(Material.VINE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(1, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(2, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(3, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(4, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(5, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(6, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(7, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(8, createGuiItem(Material.VINE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(9, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(10, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(11, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(12, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(13, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(14, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(15, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(16, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(17, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(18, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(19, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(20, createGuiItem(Material.FEATHER, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(21, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(22, createGuiItem(Material.IRON_SWORD, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(23, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(24, createGuiItem(Material.NETHERITE_HOE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(25, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(26, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(27, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(28, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(29, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(30, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(31, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(32, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(33, createGuiItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(34, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(35, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(36, createGuiItem(Material.CRIMSON_FUNGUS, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(37, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(38, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(39, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(40, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(41, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(42, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(43, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));
		inv.setItem(44, createGuiItem(Material.CRIMSON_FUNGUS, ChatColor.WHITE + " ", ChatColor.GOLD + "", ""));


		
		/*inv.setItem(3, GrayStainedPlane);
		inv.setItem(5, GrayStainedPlane);
		inv.setItem(11, GrayStainedPlane);
		inv.setItem(15, GrayStainedPlane);
		inv.setItem(29, GrayStainedPlane);
		inv.setItem(33, GrayStainedPlane);
		inv.setItem(39, GrayStainedPlane);
		inv.setItem(41, GrayStainedPlane);
		inv.setItem(4, GreenStainedPlane);
		inv.setItem(12, GreenStainedPlane);
		inv.setItem(14, GreenStainedPlane);
		inv.setItem(20, GreenStainedPlane);
		inv.setItem(24, GreenStainedPlane);
		inv.setItem(30, GreenStainedPlane);
		inv.setItem(32, GreenStainedPlane);
		inv.setItem(40, GreenStainedPlane);
		inv.setItem(13, LimeStainedPlane);
		inv.setItem(21, LimeStainedPlane);
		inv.setItem(23, LimeStainedPlane);
		inv.setItem(31, LimeStainedPlane);*/
		inv.setItem(20, createGuiItem(Material.FEATHER, ChatColor.WHITE + "Feather Falling", ChatColor.GOLD + "Fall and don't touch the lines", ""));
		inv.setItem(22, createGuiItem(Material.IRON_SWORD, ChatColor.WHITE + "Murder Mystery", ChatColor.GOLD + "Guess, who is the murderer...", ""));
		inv.setItem(24, createGuiItem(Material.NETHERITE_HOE, ChatColor.WHITE + "Around Us", ChatColor.GOLD + "Find the imposter...", ""));
		inv.setItem(36, createGuiItem(Material.CRIMSON_FUNGUS, ChatColor.WHITE + "Back to mode", ChatColor.GOLD + "Back to previous page", ""));
		inv.setItem(44, createGuiItem(Material.CRIMSON_FUNGUS, ChatColor.WHITE + "Back to mode", ChatColor.GOLD + "Back to previous page", ""));
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		
		inv = DebugInv;
				
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, createGuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", "", ""));
		}
	}
	
	public void ConvertChestToText(Player p, ArrayList<Material> notToCheck) {
		if(p.getLocation().getBlock().getType() == Material.CHEST){
			//Chest chest = (Chest) p.getLocation().getBlock().getState();
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			Chest chest = (Chest) new Location(p.getWorld(), x, y, z).getBlock().getState();
			Inventory chestinventory = chest.getInventory();
			String message = "";
			for(int i = 0; i < chestinventory.getSize(); i++) {
				if(notToCheck != null && !notToCheck.contains(chestinventory.getItem(i).getType())) {
					message = message + "inv.setItem(" + i + ", createGuiItem(Material." +  chestinventory.getItem(i).getType() + ", ChatColor.WHITE + \" \", ChatColor.GOLD + \"\", \"\"));\n";
				}
			}
			p.sendMessage(message);
			log.info(message);
		}
	}
	
	@EventHandler
	public void PlayerDropItemEvent (PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if(IfStringStartsWithArrayListEllement(e.getPlayer().getWorld().getName(), MapListWithBreakingProtection)){
			if(p.isOp() == false && !TrialStaffPlayers.contains(p.getName())) {
				e.setCancelled(true);
				log.info("PlayerDropItemEvent");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerDeathEvent(PlayerDeathEvent e) {
		Player p = e.getEntity().getPlayer();
		if(IfStringStartsWithArrayListEllement(p.getWorld().getName(), MapListWithBreakingProtection)){
			for(int i = 0; i<p.getInventory().getSize()-1; i++) {
				if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) e.getDrops().remove(i);
			}
		}
	}
	
	public void HealCommand(Player p, String[] args) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
			if(args.length == 0){
				p.setHealth(20);
				p.setFoodLevel(20);
			}
			else if(args.length == 1){
				if(getServer().getPlayer(args[0]) != null){
					getServer().getPlayer(args[0]).setHealth(20);
					getServer().getPlayer(args[0]).setFoodLevel(20);
				}
				else {
					p.sendMessage(ChatColor.RED + "Such player doesn't exist.");
				}
			}
			else {
				p.sendMessage(ChatColor.RED + "Please, type the command in such order: /heal <player>.");
			}
		}
		else {
			p.sendMessage(ChatColor.RED + "you cannot use this command.");
		}
	}
	
	public void GmCommand(CommandSender sender, String[] args) {
		Player p = getServer().getPlayer(sender.getName());
		if(p.isOp() == true || TrialStaffPlayers.contains(p.getName())){
			if(args.length == 0){
				sender.sendMessage(ChatColor.RED + "Please, insert gamemode number.");
			}
			else if(args.length == 1){
				if(args[0].equalsIgnoreCase("0")){
					p.setGameMode(GameMode.SURVIVAL);
				}
				else if(args[0].equalsIgnoreCase("1")){
					p.setGameMode(GameMode.CREATIVE);
				}
				else if(args[0].equalsIgnoreCase("2")){
					p.setGameMode(GameMode.ADVENTURE);
				}
				else if(args[0].equalsIgnoreCase("3")){
					p.setGameMode(GameMode.SPECTATOR);
				}
				else {
					sender.sendMessage(ChatColor.RED + "Please, insert correct gamemode number.");
				}
			}
			else if(args.length == 2){
				if(getServer().getPlayer(args[1]) != null){
					if(args[0].equalsIgnoreCase("0")){
						getServer().getPlayer(args[1]).setGameMode(GameMode.SURVIVAL);
					}
					else if(args[0].equalsIgnoreCase("1")){
						getServer().getPlayer(args[1]).setGameMode(GameMode.CREATIVE);
					}
					else if(args[0].equalsIgnoreCase("2")){
						getServer().getPlayer(args[1]).setGameMode(GameMode.ADVENTURE);
					}
					else if(args[0].equalsIgnoreCase("3")){
						getServer().getPlayer(args[1]).setGameMode(GameMode.SPECTATOR);
					}
					else {
						sender.sendMessage(ChatColor.RED + "Please, insert correct gamemode number.");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Such player doesn't exist.");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "Please, type the command in such order: /gm <number 0-3> <player>.");
			}
		}
		else {
			sender.sendMessage(ChatColor.RED + "You can't use this command.");
		}
	}

	public void FeatherCommand(Player p) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
			p.getInventory().setItem(4, new ItemStack(Material.FEATHER, 1));
			p.updateInventory();
		}
		//p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 180, 5));
	}
	
	public void ChestToCodeCommand(Player p) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
			ArrayList<Material> NotToCheck = new ArrayList<>();
			NotToCheck.add(Material.BARRIER);
			//NotToCheck.add(Material.VINE);
			ConvertChestToText((p), NotToCheck);
		}
	}
	
	public void Test2Command(Player p) {
		/*if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
		MinigameArena arena = GetArena(p);
		Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getBlockY(), p.getLocation().getZ());
		ArmorStand sword = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0, -1, 0), EntityType.ARMOR_STAND);
		sword.setBasePlate(false);
		sword.setCanPickupItems(false);
		sword.setCollidable(false);
		sword.setGravity(false);
		sword.setRemoveWhenFarAway(false);
		sword.setRightArmPose(new EulerAngle(0, 0, 4.7));
		sword.setItemInHand(new ItemStack(Material.IRON_SWORD));*/
		
		displayMatrix();
		
		/*try {
			loadInventory(p);
		}
		catch(IOException e) {
			e.printStackTrace();
		}*/
		//}
	}
	
	public void DeprecatedFunction1(Player p) { // MOVED TO DOWN
		
//		Location loc = IsBlockNearby(p, Material.SCAFFOLDING, 2);
//		if(loc!=null) {
//			PullToVent(p, loc);
//		}
//		else {
//			p.sendMessage("No vent found.");
//		}
		/*try {
			saveInventory(p);
		}
		catch(IOException e) {
			e.printStackTrace();
		}*/
		
		//long time = Calendar.getInstance().getTimeInMillis();
		//log.info(p.getAddress().toString().split("/")[1]); //.substring(1)
		//String s = "router.asus.com/192.168.0.0\\:4095".split("/")[1].split(":")[0];
		//p.sendMessage(s.substring(0, s.length()-1));
		//p.sendTitle("", "");
		//p.sendMessage(p.getAddress().getPort() + ""); //.substring(1)
		
//		Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getBlockY(), p.getLocation().getZ()); 
//		//loc = p.getLocation();
//		//loc.setYaw(0);
//		loc = loc.getBlock().getLocation();
//		loc.setX(loc.getX()+0.5);
//		loc.setZ(loc.getZ()+0.5);
//		p.setCustomNameVisible(false);
//		//ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0, -1, 0), EntityType.ARMOR_STAND);
//		ArmorStand stand = p.getLocation().getWorld().spawn(loc, ArmorStand.class);
//		stand.setVisible(false);
//		//stand.setHelmet(new ItemStack(Material.BOW, 1));
//		//stand.setItemInHand(new ItemStack(Material.BOW, 1));
//		stand.setBasePlate(false);
//		stand.setCanPickupItems(false);
//        stand.setCollidable(false);
//        stand.setGravity(false);
//        stand.setRemoveWhenFarAway(false);
//        MinigameArena arena = GetArena(p);
//        stand.setRightArmPose(new EulerAngle(4.69, 0, 0));
//        stand.setItemInHand(new ItemStack(Material.BOW));
//        //arena.DroppedBow = stand;
//        //arena.DroppedBowLoc = loc;
//        //p.getInventory().setHeldItemSlot(8); //change slot
//        final Location center = loc;
//        final float radius = 0.2f;
//        final float radPerSec = 3f;
//        final float radPerTick = radPerSec / 20f;
//        for (Integer i = 1; i <= 36; i++) {
//        	final int iCopy = i;
//        	new BukkitRunnable() {
//        		int tick = 0;
//        		@Override
//        		public void run() {
////        			loc.setYaw(loc.getYaw()+10);
////        			loc.setPitch(loc.getPitch()+10);
////        			stand.teleport(loc);
//        			++tick;
//        			Location loc = getLocationAroundCircle(center, radius, radPerTick * tick);
//                    //System.out.println(loc);
//                    stand.setVelocity(new Vector(0, 0, 0));
//                    stand.teleport(loc);
//        			
//        			//stand.setRotation(loc.getYaw() + 10*iCopy, 100);
////					stand.teleport(p.getLocation().setYaw(stand.getLocation().getYaw()+5));
//        			//stand.getLocation().setPitch(2);
//        			//stand.getLocation().setYaw(2);
//                    if(iCopy==36) cancel();
//        		}
//        	}.runTaskTimer(this, 0, 2);
//        }
//        
//        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
//			@Override
//			public void run() {
//				stand.remove();
//				//arena.DroppedBow = null;
//				//arena.DroppedBowLoc = null;
//				p.setCustomNameVisible(true);
//			}
//		}, 180);
//        p.sendMessage("this was after");
		
		//p.sendMessage(p.getLocation().getBlock().getType()+"");
		//p.sendMessage(isBlockTypeToZeroOne(p.getLocation().getBlock(), Material.TRIPWIRE)+"");
		
		//int smth = random.nextInt(2);
		//p.sendMessage(smth + "");
		
		//p.sendTitle(ChatColor.GREEN + "РЫБА РЫБА РЫБА", "Ты рыба.", 5, 30, 10);
		
		//p.sendTitle(ChatColor.GREEN + "3", "", 5, 30, 10);
		//if(args.length == 0) {
//		BukkitTask Runnable1 = new BukkitRunnable() {
//			
//			@Override
//			public void run() {
//				getServer().broadcastMessage("Delayed message");
//				
//			}
//		}.runTaskLater(this, 20*4);
			
		//FfWaitingTask Runnable1 = new FfWaitingTask();
		
		//BukkitTask testRunnabe = new Runnable();
		
		//RunTaskLater plugin;
		
		//BukkitTask testwait = new FfWaitingTask(p).runTaskLater(new RunTaskLater(), 20*4);
		//}
		//if(args.length == 1) {
			//Runnable1.cancel();
		//}
	}
	
	public Location getLocationAroundCircle(Location center, double radius, double angleInRadian) {
        double x = center.getX() - radius * Math.cos(angleInRadian);
        double y = center.getY() - radius * Math.sin(angleInRadian);
        double z = center.getZ() - radius * Math.sin(angleInRadian);

        Location loc = new Location(center.getWorld(), x, center.getY(), z);
        Vector difference = center.toVector().clone().subtract(loc.toVector()); // this sets the returned location's direction toward the center of the circle
        loc.setDirection(difference);

        return loc;
    }
	
	public Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
                getRelativeCoord(loc.getBlockX()),
                getRelativeCoord(loc.getBlockY()),
                getRelativeCoord(loc.getBlockZ()));
    }
	
	public double getRelativeCoord(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }
	
	public void SetTeamCommand(CommandSender sender) {
		Player p = getServer().getPlayer(sender.getName());
		if(sender.isOp() == true || TrialStaffPlayers.contains(p.getName())) {/*
			if(args.length == 1) {
				
				//Bukkit.getScoreboardManager().getMainScoreboard().getTeam(args[0]).addPlayer(p);
			}
			if(args.length == 2) {
				if(getServer().getPlayer(args[1]) != null){
					Bukkit.getScoreboardManager().getMainScoreboard().getTeam(args[0]).addPlayer(getServer().getPlayer(args[1]));
				}
				else {
					sender.sendMessage(ChatColor.RED + "Such player doesn't exist.");
				}
				if(args[1].equalsIgnoreCase("remove")) {
					Bukkit.getScoreboardManager().getMainScoreboard().getTeam(args[0]).removePlayer(p);
				}
			}
			if(args.length == 3) {
				if(args[2] == "remove") {
					if(getServer().getPlayer(args[1]) != null){
						Bukkit.getScoreboardManager().getMainScoreboard().getTeam(args[0]).removePlayer(getServer().getPlayer(args[1]));
					}
					else {
						sender.sendMessage(ChatColor.RED + "Such player doesn't exist.");
					}
				}
			}*/
			if(getServer().getPlayer("Greenem") != null) Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Admin").addPlayer(getServer().getPlayer("Greenem"));
			if(getServer().getPlayer("8east") != null) Bukkit.getScoreboardManager().getMainScoreboard().getTeam("MainBuilder").addPlayer(getServer().getPlayer("8east"));
		}
	}
	
	public void SetCountCommand(Player p, String[] args) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
		if(args.length<1) {
			p.sendMessage(ChatColor.DARK_RED + "Not enough args");
		}
		if(args.length>0) {
				Integer count = null;
				try {
					count = Integer.valueOf(args[0]);
				} catch(NumberFormatException e) {}
				
				if(count != null) {
					ChangeCount(p, p.getWorld().getName(), count);
				}
		}
		} else {
			p.sendMessage(ChatColor.RED + "You can't use this command.");
		}
	}
	
	public void ChangeCount(Player p, String mode, int number) {
		Map <String, MinigameArena> Data = new HashMap<>();
		Data = null;
		Data = GetMinigameData(mode);
		log.info(Data.toString());
		if(Data != null){
				int countBefore = Data.get(p.getWorld().getName()).PlayersCount;
				if(Data.get(p.getWorld().getName()) == null) return;
				Data.get(p.getWorld().getName()).PlayersCount = number;
				//log.info("" + Data.get(p.getWorld().getName()).PlayersCount);
				
				//p.setExp((float)0.999);
				if(number>countBefore) {
				if(Data.get(p.getWorld().getName()).PlayersCount>=Data.get(p.getWorld().getName()).PlayersMin && Data.get(p.getWorld().getName()).PlayersCount<Data.get(p.getWorld().getName()).PlayersMax) {
					if(countBefore < Data.get(p.getWorld().getName()).PlayersMin && Data.get(p.getWorld().getName()).PlayersCount>=Data.get(p.getWorld().getName()).PlayersMin) {
						Data.get(p.getWorld().getName()).TimeState = "waiting for max players";
						Data.get(p.getWorld().getName()).TimeLeftToStart = 1 + Data.get(p.getWorld().getName()).TimeToStartMinPlayers;
					}
				}
				else if(Data.get(p.getWorld().getName()).PlayersCount == Data.get(p.getWorld().getName()).PlayersMax) {
					if(Data.get(p.getWorld().getName()).TimeLeftToStart > Data.get(p.getWorld().getName()).TimeToStartMaxPlayers) Data.get(p.getWorld().getName()).TimeLeftToStart = 1 + Data.get(p.getWorld().getName()).TimeToStartMaxPlayers;
					Data.get(p.getWorld().getName()).TimeState = "full";
				}
				p.sendMessage(ChatColor.GREEN + "[Debug] " + ChatColor.WHITE + "Successfully changed the players numbers");
				}
				else {
					
				}
		}
	}
	
	public void ModeCommand(Player p, String[] args) {
		if(args.length == 0) p.sendMessage(ChatColor.RED + "Please, add the game mode to the end of your message.");	
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("FF")){
				FeatherFallingMinigame(p, args);
			}
			if(args[0].equalsIgnoreCase("MM")){
				MurderMysteryMinigame(p, args);
			}
			if(args[0].equalsIgnoreCase("JR")){
				JumpRunMinigame(p, args);
			}
		}
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("FF")){
				FeatherFallingMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("tr")){
				TntRunMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("dw")){
				DirtWarsMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("sw")){
				SkyWarsMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("tg")){
				TagMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("mm")){
				MurderMysteryMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("jr")){
				JumpRunMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("au")){
				AmongUsMinigameSpecifiedLobby(p, args);
			}
			if(args[0].equalsIgnoreCase("bw")){
				BedWarsMinigameSpecifiedLobby(p, args);
			}
		}
	}
	
	public void FeatherFallingMinigame(Player p, String[] args) {
			/*if(p.getWorld().getName().startsWith("featherfalling")) {
				p.sendMessage(ChatColor.RED + "You are already in FeatherFalling minigame lobby");
				return;
			}*/
		//TpToFeatherFalling(p);
		TpToMinigame(p, FfArenas, "featherfalling", 60, 10, 2, 4, 3, 60, 120);
			
	}
	
	public void MurderMysteryMinigame(Player p, String[] args) {
		TpToMinigame(p, MmArenas, "murdermystery", 60, 10, 2, 4, 3, 60, 300);
	}
	
	public void JumpRunMinigame(Player p, String[] args) {
		TpToMinigame(p, JrArenas, "jumprun", 60, 10, 2, 4, 1, 60, 300);
	}
	
	public void TpToFeatherFalling(Player p) {
		if(FfArenas.size() == 0) {
			log.info("First arena.");
			//       T     U    T
			clearPlayer(p);
			if(p.getWorld().getName().startsWith("featherfalling")) LeaveCurrentMinigame(p);
			p.teleport(new Location(Bukkit.getWorld("featherfalling1"), FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
			MinigameArena arena = new MinigameArena();
			arena.ArenaName = "featherfalling1";
			arena.ArenaType = "featherfalling";
			arena.PlayersCount++;
			FfArenas.put(arena.ArenaName, arena);
			log.info("Arenas:" + FfArenas);
		}
		else {
			boolean createNew = true;
			for(int i = 1; i<=FfArenas.size(); i++) {
				//log.info("featherfalling" + i);
				//log.info("ArenaName:" + FfArenas.get("featherfalling" + (i)));
				//log.info("FfArenas.get(i-1) " + FfArenas.get("featherfalling" + (i-1)));
				//log.info("FfArenas.get.PlayersCount " + FfArenas.get("featherfalling " + (i)).PlayersCount);
				log.info("ALL: " + FfArenas);
				//log.info("" + );
				log.info("" + (FfArenas.get("featherfalling" + i).PlayersCount < FfArenas.get("featherfalling" + i).PlayersMax));
				if(FfArenas.get("featherfalling" + i).PlayersCount < FfArenas.get("featherfalling" + i).PlayersMax){
					//        T   U   T
					clearPlayer(p);
					p.teleport(new Location(Bukkit.getWorld(FfArenas.get("featherfalling" + i).ArenaName), FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
					log.info("Count before: " + FfArenas.get("featherfalling" + i).PlayersCount);
					FfArenas.get("featherfalling" + i).PlayersCount++;
					log.info("Count after: " + FfArenas.get("featherfalling" + i).PlayersCount);
					createNew = false;
				}
			}
			log.info("creating new: " +	 createNew);
			if(createNew == true) {
				if(FfArenas.size() == 3) {
					p.sendMessage(ChatColor.RED + "All arenas are full");
					return;
				}
				MinigameArena arena = new MinigameArena();
				arena.ArenaName = "featherfalling" + (FfArenas.size()+1);
				arena.ArenaType = "featherfalling";
				p.teleport(new Location(Bukkit.getWorld(arena.ArenaName), FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
				arena.PlayersCount++;
				log.info("Arenas:" + FfArenas);
				FfArenas.put(arena.ArenaName, arena);
			}
		}
		p.getWorld().setTime(1000);
		//teleporting done
		p.setExp((float)0.999);
		if(FfArenas.get(p.getWorld().getName()).PlayersCount>=FfArenas.get(p.getWorld().getName()).PlayersMin && FfArenas.get(p.getWorld().getName()).PlayersCount<FfArenas.get(p.getWorld().getName()).PlayersMax) {
			if(FfArenas.get(p.getWorld().getName()).PlayersCount - 1 < FfArenas.get(p.getWorld().getName()).PlayersMin && FfArenas.get(p.getWorld().getName()).PlayersCount<FfArenas.get(p.getWorld().getName()).PlayersMax) {
				FfArenas.get(p.getWorld().getName()).TimeState = "waiting for max players";
				FfArenas.get(p.getWorld().getName()).TimeLeftToStart = 1 + FfArenas.get(p.getWorld().getName()).TimeToStartMinPlayers;
			}
		}
		else if(FfArenas.get(p.getWorld().getName()).PlayersCount == FfArenas.get(p.getWorld().getName()).PlayersMax) {
			if(FfArenas.get(p.getWorld().getName()).TimeLeftToStart > FfArenas.get(p.getWorld().getName()).TimeToStartMaxPlayers) FfArenas.get(p.getWorld().getName()).TimeLeftToStart = 1 + FfArenas.get(p.getWorld().getName()).TimeToStartMaxPlayers;
			FfArenas.get(p.getWorld().getName()).TimeState = "full";		
		}
	}
	
	public void FeatherFallingMinigameSpecifiedLobby(Player p, String[] args) {
		if(args[1].equalsIgnoreCase("0")) {
			World FFworld = getServer().createWorld(new WorldCreator("featherfalling"));
			p.teleport(new Location(FFworld, FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
			p.getWorld().setSpawnLocation(0, 102, -1);
			if(p.isOp() == false && !TrialStaffPlayers.contains(p.getName())) p.setGameMode(GameMode.SURVIVAL);
			p.getWorld().setTime(1000);
			return;
		}
		if(args[1].equalsIgnoreCase("1")) {
			World FFworld = getServer().createWorld(new WorldCreator("featherfalling1"));
			p.teleport(new Location(FFworld, FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
			p.getWorld().setSpawnLocation(0, 102, -1);
			if(p.isOp() == false && !TrialStaffPlayers.contains(p.getName())) p.setGameMode(GameMode.SURVIVAL);
			p.getWorld().setTime(1000);
			return;
		}
		if(args[1].equalsIgnoreCase("2")) {
			World FFworld = getServer().createWorld(new WorldCreator("featherfalling2"));
			p.teleport(new Location(FFworld, FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
			p.getWorld().setSpawnLocation(0, 102, -1);
			if(p.isOp() == false && !TrialStaffPlayers.contains(p.getName())) p.setGameMode(GameMode.SURVIVAL);
			p.getWorld().setTime(1000);
			return;
		}
		if(args[1].equalsIgnoreCase("3")) {
			World FFworld = getServer().createWorld(new WorldCreator("featherfalling3"));
			p.teleport(new Location(FFworld, FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
			p.getWorld().setSpawnLocation(0, 102, -1);
			if(p.isOp() == false && !TrialStaffPlayers.contains(p.getName())) p.setGameMode(GameMode.SURVIVAL);
			p.getWorld().setTime(1000);
			return;
		}
	}
	
	public void TntRunMinigameSpecifiedLobby(Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("tntrun1"), 0, 102, 0));
				p.getWorld().setTime(1000);
			}
		}
	}
	
	public void DirtWarsMinigameSpecifiedLobby(Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("DirtWars1"), -100, 152, 0));
				p.getWorld().setTime(1000);
			}
		}
	}
	
	public void SkyWarsMinigameSpecifiedLobby(Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("skywars1"), 0, 102, 0));
				p.getWorld().setTime(1000);
			}
		}
	}
	
	public void TagMinigameSpecifiedLobby(Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("tag1"), 0, 102, 0));
				p.getWorld().setTime(1000);
			}
		}
	}
	
	public void MurderMysteryMinigameSpecifiedLobby(Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("murdermystery1"), 0, 102, 0));
				p.getWorld().setTime(15000);
			}
		}
	}
	
	public void JumpRunMinigameSpecifiedLobby (Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("jumprun1"), 0, 102, 0));
				p.getWorld().setTime(1000);
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 2));
			}
		}
	}
	
	public void AmongUsMinigameSpecifiedLobby (Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("amongus1"), 0, 102, 0));
				p.getWorld().setTime(1000);
			}
		}
	}
	
	public void BedWarsMinigameSpecifiedLobby (Player p, String[] args) {
		if(args.length == 1) {
			p.sendMessage(ChatColor.DARK_RED + "[Debug] Use arena number.");
		}
		if(args.length>1) {
			if(args[1].equalsIgnoreCase("1")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("bedwars1"), 0, 102, 0));
				p.getWorld().setTime(1000);
			}
			if(args[1].equalsIgnoreCase("2")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("bedwars2"), 0, 102, 0));
				p.getWorld().setTime(1000);
			}
			if(args[1].equalsIgnoreCase("3")) {
				tpToSpawn(p);
				p.teleport(new Location(Bukkit.getWorld("bedwars3"), 0, 102, 0));
				p.getWorld().setTime(1000);
			}
		}
	}
	
	public void InitializeAlphabetArrayList() {
		Alphabet.add("A");
		Alphabet.add("B");
		Alphabet.add("C");
		Alphabet.add("D");
		Alphabet.add("E");
		Alphabet.add("F");
		Alphabet.add("G");
		Alphabet.add("H");
		Alphabet.add("I");
		Alphabet.add("J");
		Alphabet.add("K");
		Alphabet.add("L");
		Alphabet.add("M");
		Alphabet.add("N");
		Alphabet.add("O");
		Alphabet.add("P");
		Alphabet.add("Q");
		Alphabet.add("R");
		Alphabet.add("S");
		Alphabet.add("T");
		Alphabet.add("U");
		Alphabet.add("V");
		Alphabet.add("W");
		Alphabet.add("X");
		Alphabet.add("Y");
		Alphabet.add("Z");
		
	}
	
	public void TpToMinigame(Player p, Map<String, MinigameArena> MinigameArenas, String ArenaType, int TimeToStartMinPlayers, int TimeToStartMaxPlayers, int PlayersMin, int PlayersMax, int WorldsAvaliable, int TimeLeftToStart, int TimeLeftToEnd) {
		if(MinigameArenas.size() == 0) {
			log.info("First arena.");
			//       T     U    T
			clearPlayer(p);
			MinigameVanishOff(p);
			//if(p.getWorld().getName().startsWith(ArenaType))
			LeaveCurrentMinigame(p);
			Location LocationToTp = LobbyLocations.get(ArenaType);
			p.teleport(new Location(Bukkit.getWorld(ArenaType + "1"), LocationToTp.getX(), LocationToTp.getY(), LocationToTp.getZ()));
			//p.teleport(new Location(Bukkit.getWorld(ArenaType + "1"), FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
			MinigameArena arena = new MinigameArena();
			arena.ArenaName = ArenaType + "1";
			arena.ArenaType = ArenaType;
			arena.TimeToStartMinPlayers = TimeToStartMinPlayers;
			arena.TimeToStartMaxPlayers = TimeToStartMaxPlayers;
			arena.PlayersMin = PlayersMin;
			arena.PlayersMax = PlayersMax;
			arena.TimeLeftToStart = TimeLeftToStart;
			arena.TimeLeftToEnd = TimeLeftToEnd;
			arena.PlayersCount = 0;
			arena.PlayersCount++;
			Inventory MapChooseInv = Bukkit.createInventory(null, 9, "Choose a map");
			arena.MapVotingInventory = MapChooseInv;
			MinigameArenas.put(arena.ArenaName, arena);
			log.info("Arenas:" + MinigameArenas);
		}
		else {
			boolean createNew = true;
			for(int i = 1; i<=MinigameArenas.size(); i++) {
				//log.info("ALL: " + MinigameArenas);
				log.info("" + (MinigameArenas.get(ArenaType + i).PlayersCount < MinigameArenas.get(ArenaType + i).PlayersMax));
				if(MinigameArenas.get(ArenaType + i).PlayersCount < MinigameArenas.get(ArenaType + i).PlayersMax && (MinigameArenas.get(ArenaType + i).ArenaState != "ingame")){
					//        T   U   T
					clearPlayer(p);	
					Location LocationToTp = LobbyLocations.get(ArenaType);
					p.teleport(new Location(Bukkit.getWorld(MinigameArenas.get(ArenaType + i).ArenaName), LocationToTp.getX(), LocationToTp.getY(), LocationToTp.getZ()));
					log.info("Count before: " + MinigameArenas.get(ArenaType + i).PlayersCount);
					MinigameArenas.get(ArenaType + i).PlayersCount++;
					log.info("Count after: " + MinigameArenas.get(ArenaType + i).PlayersCount);
					createNew = false;
				}
			}
			log.info("creating new: " +	 createNew);
			if(createNew == true) {
				boolean AllAreFull = true;
				int FreeArena = -1;
				if(MinigameArenas.size() < WorldsAvaliable) {
					AllAreFull = false;
				}
				else if(MinigameArenas.size() >= WorldsAvaliable) {
					AllAreFull = true;
				}
				else {
					for(int i = 0; i < MinigameArenas.size(); i++) {
						if((MinigameArenas.get(ArenaType + (i+1)).PlayersCount < MinigameArenas.get(ArenaType + (i+1)).PlayersMax && MinigameArenas.get(ArenaType + (i+1)).ArenaState != "ingame")) {
							AllAreFull = false;
							FreeArena = i;
						}
					}
				}
				if(AllAreFull == true) {
					p.sendMessage(ChatColor.RED + "All arenas are full");
					log.info(AllAreFull + "");
					log.info(MinigameArenas + "");
					return;
				}
				else {
					MinigameArena arena = new MinigameArena();
					if(FreeArena != -1) {
						arena.ArenaName = ArenaType + FreeArena;
					}
					else {
						arena.ArenaName = ArenaType + (MinigameArenas.size()+1);
					}
					arena.ArenaType = ArenaType;
					arena.TimeToStartMinPlayers = TimeToStartMinPlayers;
					arena.TimeToStartMaxPlayers = TimeToStartMaxPlayers;
					arena.PlayersMin = PlayersMin;
					arena.PlayersMax = PlayersMax;
					arena.TimeLeftToStart = TimeLeftToStart;
					arena.TimeLeftToEnd = TimeLeftToEnd;
					arena.PlayersCount = 0;
					Inventory MapChooseInv = Bukkit.createInventory(null, 9, "Choose a map");
					arena.MapVotingInventory = MapChooseInv;
					//p.teleport(new Location(Bukkit.getWorld(arena.ArenaName), FfWaitingLobbyLoc.getX(), FfWaitingLobbyLoc.getY(), FfWaitingLobbyLoc.getZ()));
					Location LocationToTp = LobbyLocations.get(ArenaType);
					p.teleport(new Location(Bukkit.getWorld(arena.ArenaName), LocationToTp.getX(), LocationToTp.getY(), LocationToTp.getZ()));
					arena.PlayersCount++;
					log.info("Arenas:" + MinigameArenas);
					MinigameArenas.put(arena.ArenaName, arena);
				}
			}
		}
		//p.getWorld().setTime(1000);
		//teleporting done
		p.setExp((float)0.999);
		if(MinigameArenas.get(p.getWorld().getName()).PlayersCount>=MinigameArenas.get(p.getWorld().getName()).PlayersMin && MinigameArenas.get(p.getWorld().getName()).PlayersCount<MinigameArenas.get(p.getWorld().getName()).PlayersMax) {
			if(MinigameArenas.get(p.getWorld().getName()).PlayersCount - 1 < MinigameArenas.get(p.getWorld().getName()).PlayersMin && MinigameArenas.get(p.getWorld().getName()).PlayersCount<MinigameArenas.get(p.getWorld().getName()).PlayersMax) {
				MinigameArenas.get(p.getWorld().getName()).TimeState = "waiting for max players";
				MinigameArenas.get(p.getWorld().getName()).TimeLeftToStart = 1 + MinigameArenas.get(p.getWorld().getName()).TimeToStartMinPlayers;
			}
		}
		else if(MinigameArenas.get(p.getWorld().getName()).PlayersCount == MinigameArenas.get(p.getWorld().getName()).PlayersMax) {
			if(MinigameArenas.get(p.getWorld().getName()).TimeLeftToStart > MinigameArenas.get(p.getWorld().getName()).TimeToStartMaxPlayers) MinigameArenas.get(p.getWorld().getName()).TimeLeftToStart = 1 + MinigameArenas.get(p.getWorld().getName()).TimeToStartMaxPlayers;
			MinigameArenas.get(p.getWorld().getName()).TimeState = "full";		
		}
		
		if(p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) {
			RemoveItemStack(p, new ItemStack(Material.NETHER_STAR));
			RemoveItemStack(p, new ItemStack(Material.BLAZE_POWDER));
			RemoveMaterial(p, Material.CRYING_OBSIDIAN);
		}
		
		ItemStack LeaveMinigameItem = createGuiItem(Material.REDSTONE, ChatColor.RED + "Leave the lobby", ChatColor.GOLD + "Right click for using");
		ItemStack ChooseMapItem = createGuiItem(Material.PAPER, ChatColor.RED + "Map voting", ChatColor.GOLD + "Right click for using");
		ItemStack ChooseModeItem = createGuiItem(Material.EMERALD, ChatColor.RED + "Mode voting", ChatColor.GOLD + "Right click for using");
		
		AddItemStack(p, ChooseMapItem, 0);
		AddItemStack(p, ChooseModeItem, 1);
		AddItemStack(p, LeaveMinigameItem, 8);
		
		p.getWorld().setTime(1000);
	}
	
	public void AddItemStack(Player p, ItemStack ItemStack, int slot) {
		if(!p.getInventory().contains(ItemStack)) {
			if(p.getInventory().getItem(slot) == null) p.getInventory().setItem(slot, ItemStack);
			else p.getInventory().addItem(ItemStack);
		}
	}
	
	public void RemoveItemStack(Player p, ItemStack ItemStack) {
		if(p.getInventory().contains(ItemStack)) {
			p.getInventory().remove(ItemStack);
		}
	}
	
	public void SpawnEvent(Player p, Event event, String eventType){
		if(event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;
			if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR)){
	        	ChoosingMode(p);
	        }
	        if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.BLAZE_POWDER)){
	        	if(p.isOp() == true || TrialStaffPlayers.contains(p.getName())) AdminSettingsInventory(p);
	        }
		}
	}
	
	public void FfEvent(Player p, Event event, String eventType){ //DEPRECATED
		if(event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;
			if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.REDSTONE)){
		        //getServer().broadcastMessage("" + evt.getPlayer().getItemInHand().getItemMeta().getLore());
		        if(e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equals(RightClickLore)) tpToSpawn(p);
			}
		}
	}
	
	public void AllWorldsEvent(Player p, Event event, String eventType){
		if(event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;
			if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR)){
	        	ChoosingMode(p);
	        }
	        if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.BLAZE_POWDER)){
	        	if(p.isOp() == true || TrialStaffPlayers.contains(p.getName())) AdminSettingsInventory(p);
	        }
			if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.REDSTONE)){
				if(e.getPlayer().getItemInHand().getItemMeta().getLore()!=null && e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equals(RightClickLore)) tpToSpawn(p);
			}
			if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.PAPER)){
				if(e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equals(RightClickLore)) ChoosingMinigameMap(p);
			}
		}
	}
	
	public void PlayerHitsPlayerEvt(Player Damager, Player Victim, EntityDamageByEntityEvent e) {
		World w = Damager.getWorld();
		if(IfStringStartsWithArrayListEllement(Damager.getWorld().getName(), MapListWithHittingProtection) == true) {
			e.setCancelled(true);
			log.info("PlayerHitsPlayerEvt");
		}
		else {
			if(w.getName().startsWith("murdermystery")) {
				MurderMysterySwordDamage(Damager, Victim, e);
			}
		}
	}
	
	public void InitializeAll() {
		InitialiseInventories();
		InitializePotsTypes();
		InitializeAlphabetArrayList();
		InitializeDamageRules();
		InilializeFirstLoadedWords();
		InitializeLocations();
		InitializeCommands();
		InitializeCobwebArray();
		InitializeTrialStaffPlayers();
		
	}
	
	public void InitializeCommands() {
		//getCommand("mode").setTabCompleter(new TabCompleteClass());
	}
	
	public void InilializeFirstLoadedWords() {
		getServer().createWorld(new WorldCreator("featherfalling"));
		getServer().createWorld(new WorldCreator("murdermystery"));
		getServer().createWorld(new WorldCreator("jumprun"));
		
		log.info("loaded worlds for copying");
		
		getServer().createWorld(new WorldCreator("featherfalling1"));
		getServer().createWorld(new WorldCreator("featherfalling2"));
		getServer().createWorld(new WorldCreator("featherfalling3"));
		
		log.info("loaded ff");
		
		getServer().createWorld(new WorldCreator("murdermystery1"));
		getServer().createWorld(new WorldCreator("murdermystery2"));
		getServer().createWorld(new WorldCreator("murdermystery3"));
		
		log.info("loaded mm");
		
		getServer().createWorld(new WorldCreator("bedwars1"));
		getServer().createWorld(new WorldCreator("bedwars2"));
		getServer().createWorld(new WorldCreator("bedwars3"));
		
		log.info("loaded bw");
		
		getServer().createWorld(new WorldCreator("tntrun1"));
		getServer().createWorld(new WorldCreator("dirtwars1"));
		getServer().createWorld(new WorldCreator("skywars1"));
		getServer().createWorld(new WorldCreator("tag1"));
		getServer().createWorld(new WorldCreator("jumprun1"));
		getServer().createWorld(new WorldCreator("amongus1"));
		
		getServer().createWorld(new WorldCreator("auth"));
		getServer().createWorld(new WorldCreator("spawnnew"));
		
		log.info("loaded other");
	}
	
	public void InitializeLocations() {
		LobbyLocations.put("featherfalling", new Location(Bukkit.getWorld("featherfalling"), 0.5, 101.5, -70.5));
		LobbyLocations.put("murdermystery", new Location(Bukkit.getWorld("murdermystery"), 0.5, 103.5, 200.5));
		LobbyLocations.put("jumprun", new Location(Bukkit.getWorld("jumprun"), 0.5, 101.5, 0.5));
		LobbyLocations.put("amongus", new Location(Bukkit.getWorld("amongus"), 19.5, 113.5, 14.5));
		
		//HashMap <String, MapInfo>  = new HashMap<>();
		ArrayList <MapInfo> ArrayListOfMaps = new ArrayList<>();
		HashMap <String, ArrayList<MapInfo>> MapHashMap = new HashMap<>();
		
		MapInfo currentMap = new MapInfo();
		currentMap.Name = "Map 1";
		currentMap.Descripction.add("Description");
		currentMap.Number = 1;
		currentMap.MinigameName = "murdermystery";
		currentMap.Location = new Location(Bukkit.getWorld("murdermystery"), 0.5, 103.5, 0.5);
		currentMap.SpawnForDeadPlayers = new Location(Bukkit.getWorld("murdermystery"), 0.5, 104.5, 0.5);
		currentMap.WorldTime = 15000;
		World world = Bukkit.getWorld("murdermystery");
		//log.info(world.toString());
		//currentMap.PlayersSpawnLocs.add(new Location(world, 0.5, 102.5, 0.5));
		ArrayListOfMaps.add(currentMap);
		MapHashMap.put(currentMap.MinigameName, ArrayListOfMaps);
		MapLocations.add(MapHashMap);                                                               //actually
		
		
		ArrayListOfMaps = new ArrayList<>();
		MapHashMap = new HashMap<>();
		
		
		currentMap = new MapInfo();
		currentMap.Name = "Map 1";
		currentMap.Descripction.add("Description");
		currentMap.Descripction.add("Line 2");
		currentMap.Number = 1;
		currentMap.MinigameName = "featherfalling";
		currentMap.Location = new Location(Bukkit.getWorld("featherfalling"), 30.5, 101.5, 0.5);
		ArrayListOfMaps.add(currentMap);
		
		currentMap = new MapInfo();
		currentMap.Name = "Map 2";
		currentMap.Descripction.add("Description");
		currentMap.Number = 2;
		currentMap.MinigameName = "featherfalling";
		currentMap.Location = new Location(Bukkit.getWorld("featherfalling"), 0.5, 101.5, -0.5);
		ArrayListOfMaps.add(currentMap);

		currentMap = new MapInfo();
		currentMap.Name = "Map 3";
		currentMap.Descripction.add("Description");
		currentMap.Number = 3;
		currentMap.MinigameName = "featherfalling";
		currentMap.Location = new Location(Bukkit.getWorld("featherfalling"), -29.5, 101.5, -0.5);
		ArrayListOfMaps.add(currentMap);
		MapHashMap.put(currentMap.MinigameName, ArrayListOfMaps);
		MapLocations.add(MapHashMap);                                                               //actually
		
		
		ArrayListOfMaps = new ArrayList<>();
		MapHashMap = new HashMap<>();
		
		
		currentMap = new MapInfo();
		currentMap.Name = "Map 1";
		currentMap.Descripction.add("Description");
		currentMap.Number = 1;
		currentMap.MinigameName = "jumprun";
		currentMap.Location = new Location(Bukkit.getWorld("jumprun"), -29.5, 101.5, -0.5);
		ArrayListOfMaps.add(currentMap);
		MapHashMap.put(currentMap.MinigameName, ArrayListOfMaps);
		MapLocations.add(MapHashMap);                                                               //actually
		
		ArrayListOfMaps = new ArrayList<>();
		MapHashMap = new HashMap<>();
		
		
		currentMap = new MapInfo();
		currentMap.Name = "The Skeld";
		currentMap.Descripction.add("Main map");
		currentMap.Number = 1;
		currentMap.MinigameName = "amongus";
		currentMap.Location = new Location(Bukkit.getWorld("amongus"), 19.5, 113.5, 14.5);
		ArrayListOfMaps.add(currentMap);
		MapHashMap.put(currentMap.MinigameName, ArrayListOfMaps);
		MapLocations.add(MapHashMap);                                                               //actually
		
		/*FfHashMap.put("map1", new Location(Bukkit.getWorld("featherfalling"), 30.5, 101.5, 0.5));
		MapHashMap.put("featherfalling", FfHashMap);
		MapLocations.add(MapHashMap);*/
		
		log.info(LobbyLocations + "");
		log.info(MapLocations + "");
	}
	
	public void InitializePotsTypes() {
		PotsTypes.add(Material.POTTED_ACACIA_SAPLING);
	    PotsTypes.add(Material.POTTED_ALLIUM);
	    PotsTypes.add(Material.POTTED_AZURE_BLUET);
	    PotsTypes.add(Material.POTTED_BAMBOO);
	    PotsTypes.add(Material.POTTED_BIRCH_SAPLING);
	    PotsTypes.add(Material.POTTED_BLUE_ORCHID);
	    PotsTypes.add(Material.POTTED_BROWN_MUSHROOM);
	    PotsTypes.add(Material.POTTED_CACTUS);
	    PotsTypes.add(Material.POTTED_CORNFLOWER);
	    PotsTypes.add(Material.POTTED_CRIMSON_FUNGUS);
	    PotsTypes.add(Material.POTTED_CRIMSON_ROOTS);
	    PotsTypes.add(Material.POTTED_DANDELION);
	    PotsTypes.add(Material.POTTED_DARK_OAK_SAPLING);
	    PotsTypes.add(Material.POTTED_DEAD_BUSH);
	    PotsTypes.add(Material.POTTED_FERN);
	    PotsTypes.add(Material.POTTED_JUNGLE_SAPLING);
	    PotsTypes.add(Material.POTTED_LILY_OF_THE_VALLEY);
	    PotsTypes.add(Material.POTTED_OAK_SAPLING);
	    PotsTypes.add(Material.POTTED_ORANGE_TULIP);
	    PotsTypes.add(Material.POTTED_OXEYE_DAISY);
	    PotsTypes.add(Material.POTTED_PINK_TULIP);
	    PotsTypes.add(Material.POTTED_POPPY);
	    PotsTypes.add(Material.POTTED_RED_MUSHROOM);
	    PotsTypes.add(Material.POTTED_RED_TULIP);
	    PotsTypes.add(Material.POTTED_SPRUCE_SAPLING);
	    PotsTypes.add(Material.POTTED_WARPED_FUNGUS);
	    PotsTypes.add(Material.POTTED_WARPED_ROOTS);
	    PotsTypes.add(Material.POTTED_WHITE_TULIP);
	    PotsTypes.add(Material.POTTED_WITHER_ROSE);
	    PotsTypes.add(Material.FLOWER_POT);
	    PotsTypes.add(Material.LEGACY_FLOWER_POT);
	    PotsTypes.add(Material.LEGACY_FLOWER_POT_ITEM);
	}

	public void InitializeDamageRules() {
		MapListWithArmorStandProtection.add("auth");
		MapListWithArmorStandProtection.add("spawn");
		MapListWithArmorStandProtection.add("featherfalling");
		MapListWithArmorStandProtection.add("tag");
		MapListWithArmorStandProtection.add("murdermystery");
		MapListWithArmorStandProtection.add("jumprun");
		MapListWithArmorStandProtection.add("amongus");
		
		MapListWithBreakingProtection.add("auth");
		MapListWithBreakingProtection.add("spawn");
		MapListWithBreakingProtection.add("featherfalling");
		MapListWithBreakingProtection.add("tntrun");
		MapListWithBreakingProtection.add("tag");
		MapListWithBreakingProtection.add("murdermystery");
		MapListWithBreakingProtection.add("jumprun");
		MapListWithBreakingProtection.add("amongus");
		
		MapListWithHittingProtection.add("spawn");
		MapListWithHittingProtection.add("featherfalling");
		MapListWithHittingProtection.add("tntrun");
		MapListWithHittingProtection.add("auth");
		
		MapListWithVoidDeathProtection.add("spawn");
		MapListWithVoidDeathProtection.add("featherfalling");
		MapListWithVoidDeathProtection.add("tntrun");
		MapListWithVoidDeathProtection.add("tag");
		MapListWithVoidDeathProtection.add("murdermystery");
		MapListWithVoidDeathProtection.add("dirtwars");
		MapListWithVoidDeathProtection.add("jumprun");
		MapListWithVoidDeathProtection.add("amongus");
		MapListWithVoidDeathProtection.add("auth");
		
		MapListWithHungerProtection.add("spawn");
		MapListWithHungerProtection.add("featherfalling");
		MapListWithHungerProtection.add("tntrun");
		MapListWithHungerProtection.add("tag");
		MapListWithHungerProtection.add("murdermystery");
		MapListWithHungerProtection.add("dirtwars");
		MapListWithHungerProtection.add("jumprun");
		MapListWithHungerProtection.add("amongus");
		MapListWithHungerProtection.add("auth");
		
		MapListWithFallDamageProtection.add("spawn");
		MapListWithFallDamageProtection.add("featherfalling");
		MapListWithFallDamageProtection.add("tntrun");
		MapListWithFallDamageProtection.add("jumprun");
		MapListWithFallDamageProtection.add("murdermystery");
		MapListWithFallDamageProtection.add("amongus");
		MapListWithFallDamageProtection.add("auth");
		
		MinigamesList.add("spawn");
		MinigamesList.add("featherfalling");
		MinigamesList.add("tntrun");
		MinigamesList.add("tag");
		MinigamesList.add("murdermystery");
		MinigamesList.add("dirtwars");
		MinigamesList.add("jumprun");
		MinigamesList.add("amongus");
	}
	
	public boolean IfStringStartsWithArrayListEllement(String s, ArrayList<String> a) {
		for(int i = 0; i < a.size(); i++) {
			if(s.startsWith(a.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
    public void MurderMysteryBowShot (EntityDamageByEntityEvent e) {
		if(e.getDamager().getWorld().getName().startsWith("murdermystery")){
			if (e.getEntity() instanceof Player) {
        		Player Victim = (Player) e.getEntity(); 
            	if (e.getDamager() instanceof Projectile) {
            		Projectile Arrow = (Projectile) e.getDamager();
            		if(Arrow.getShooter() instanceof Player) {
            			Player Damager = (Player) Arrow.getShooter();
                		if ((Damager.getGameMode().equals(GameMode.SURVIVAL) || Damager.getGameMode().equals(GameMode.ADVENTURE)) && (Victim.getGameMode().equals(GameMode.SURVIVAL) || Victim.getGameMode().equals(GameMode.ADVENTURE))) {
                			AtomicBoolean shooterDied = new AtomicBoolean();
                			shooterDied.set(false);
                			log.info(Damager.getName() + " shot at " + Victim.getName() + " using" + Arrow);
                			MinigameArena arena = GetArena(Damager);
                			if(Damager.equals(Victim) && !arena.MurderersList.contains(Victim)) {
                				MurderMysteryKillPlayer(Victim);
                				shooterDied.set(true);
                				Victim.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You shot yourself with a bow.", 5, 40, 30);
                			}
                			else {
            				if(arena!= null && (arena.MurderersList.contains(Damager) || arena.DetectivesList.contains(Damager) || arena.InnocentsList.contains(Damager))) {
            					if(arena.InnocentsList.contains(Victim)) {
            						MurderMysteryKillPlayer(Victim);
            						Victim.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You were killed with a bow.", 5, 40, 30);
            						if(arena.InnocentsList.contains(Damager) || arena.DetectivesList.contains(Damager)) {
            							MurderMysteryKillPlayer(Damager);
            							shooterDied.set(true);
            							Damager.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "That player was not an murderer.", 5, 40, 30);
            						}
            					}
            					else if(arena.MurderersList.contains(Victim)) {
            						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            							@Override
            							public void run() {
            								log.info(ChatColor.GOLD + "" + (Victim.getHealth()-Victim.getMaxHealth()/2));
            								if(Victim.getHealth()-Victim.getMaxHealth()/2>0.1) {
            									Victim.setHealth(Victim.getMaxHealth()/2);
            								}
            								else {
            									MurderMysteryKillPlayer(Victim);
            									if(arena.MurderersList.contains(Damager)) {
            										Victim.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You were killed by another murderer. We don't know why.", 5, 40, 30);
            									}
            									else {
            										Victim.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You were killed by a player.", 5, 40, 30);
            									}
            								}
            								if(arena.MurderersList.contains(Damager) && !Damager.equals(Victim)) {
//            									if(Damager.getHealth()-Damager.getMaxHealth()/2>0.1) {
//            										Damager.setHealth(Damager.getHealth()-Damager.getMaxHealth()/2);
//                								}
//                								else {
                									MurderMysteryKillPlayer(Damager);
                									shooterDied.set(true);
                									Damager.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You killed another murderer, avoid doing this next time.", 5, 40, 30);
//                								}
            								}
            							}
            						}, 1);
            					}
            					else if(arena.DetectivesList.contains(Victim)) {
            						MurderMysteryKillPlayer(Victim);
            						Victim.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED ", ChatColor.ITALIC + "" + ChatColor.GRAY + "You were killed with a bow.", 5, 40, 30);
            						if(arena.InnocentsList.contains(Damager) || arena.DetectivesList.contains(Damager)) {
            							MurderMysteryKillPlayer(Damager);
    									shooterDied.set(true);
            							Damager.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED ", ChatColor.ITALIC + "" + ChatColor.GRAY + "That player was not an murderer.", 5, 40, 30);
            						}
            					}
            				}
                			}
                			if(shooterDied.get() == false) {
                				if(arena.DetectivesList.contains(Damager)) {
                					AddItemStack(Damager, new ItemStack(Material.ARROW, 1), 9);
                				}
                			}
                		}
            		}
            	}
        	}
		}
	}
	
	@EventHandler
	public void MurderMysteryArrowDidnnotTouchPlayers(ProjectileHitEvent e) {
		log.info("ProjectileHitEvent");
//		if(e.getHitEntity() instanceof Painting) {
//			log.info("painting");
//			Painting painting = (Painting) e.getHitEntity();
//			Location loc = new Location(painting.getLocation().getWorld(), painting.getLocation().getX(), painting.getLocation().getY(), painting.getLocation().getZ());
//			Location realLoc = new Location(painting.getLocation().getWorld(), painting.getLocation().getX(), painting.getLocation().getY(), painting.getLocation().getZ());
//			Pose pose = painting.getPose();
//			double height = painting.getHeight();
//			BlockFace facing = painting.getFacing();
//			BlockFace attachedFace = painting.getAttachedFace();
//			double width = painting.getWidth();
//			Art art = painting.getArt();
//			log.info(painting.getClass().getName());
//			log.info(""+art.getBlockHeight());
//			log.info(""+art.getBlockWidth());
//			log.info("Painting loc before: " + painting.getLocation());
//			if(IfStringStartsWithArrayListEllement(e.getEntity().getWorld().getName(), MapListWithBreakingProtection)) {
//				painting.remove();
//				getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
//					@Override
//					public void run() {
//						if(art.getBlockHeight()==1) {
//							loc.setY(loc.getY());
//						}
//						else if(art.getBlockHeight()==4 || art.getBlockWidth()==4) {
//							loc.setY(loc.getY()-1);
//							if(loc.getYaw()==90) loc.setZ(loc.getZ()-1);
//							if(loc.getYaw()==0) loc.setX(loc.getX()-1);
//							
//						}
//						else if(art.getBlockHeight()==2 || art.getBlockWidth()==4) {
//							loc.setY(loc.getY()-1);
//							if(loc.getYaw()==90) loc.setZ(loc.getZ()-1);
//							if(loc.getYaw()==0) loc.setX(loc.getX()-1);
//						}
//						else if(art.getBlockHeight()==3 || art.getBlockWidth()==4) {
//							if(loc.getYaw()==90) loc.setZ(loc.getZ()-1);
//							if(loc.getYaw()==0) loc.setX(loc.getX()-1);
//						}
//						else if(art.getBlockHeight()==2 || art.getBlockWidth()==2) {
//							loc.setY(loc.getY()-1);
//							if(loc.getYaw()==90) loc.setZ(loc.getZ()-1);
//							if(loc.getYaw()==0) loc.setX(loc.getX()-1);
//						}
//						else if(art.getBlockHeight()==1 || art.getBlockWidth()==2) {
//							if(loc.getYaw()==90) loc.setZ(loc.getZ()-1);
//							if(loc.getYaw()==0) loc.setX(loc.getX()-1);
//						}
//						else if(art.getBlockHeight()==2 || art.getBlockWidth()==1) {
//							loc.setY(loc.getY()-1);
//						}
//						CraftPainting newPainting = loc.getWorld().spawn(loc, CraftPainting.class);
//						//newPainting.setFacingDirection(facing);
//						newPainting.setFacingDirection(attachedFace);
//						newPainting.setArt(art);
//						newPainting.setGravity(false);
//						newPainting.setPersistent(true);
//						//newPainting.setGravity(false);
//						log.info("realLoc.equals(newPainting.getLocation()) = " + realLoc.equals(newPainting.getLocation()));
//						if(!realLoc.equals(newPainting.getLocation())) {
//							//newPainting.remove();
//							newPainting.teleport(realLoc);
//							//newPainting.teleport(realLoc);
//							log.info("set same loc");
//						}
//						//newPainting.setMetadata(pose, MetadataValue.class);
//						log.info("Painting loc after: " + newPainting.getLocation());
//					}
//				}, 2);
//			}
//		}
		if(e.getHitEntity() instanceof ItemFrame) {
			log.info("frame");
			ItemFrame frame = (ItemFrame) e.getHitEntity();
			Location loc = new Location(frame.getLocation().getWorld(), frame.getLocation().getX(), frame.getLocation().getY(), frame.getLocation().getZ());
			ItemStack item = frame.getItem();
			Rotation rotaion = frame.getRotation();
			if(IfStringStartsWithArrayListEllement(e.getEntity().getWorld().getName(), MapListWithBreakingProtection)) {
				frame.remove();
				getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						ItemFrame newFrame = loc.getWorld().spawn(loc, ItemFrame.class);
						newFrame.setItem(item);
						newFrame.setRotation(rotaion);
						//newPainting.setFacingDirection(facing);
						//newPainting.setMetadata(pose, MetadataValue.class);
					}
				}, 2);
			}
		}
		if(e.getEntity().getWorld().getName().startsWith("murdermystery")){
			if(e.getEntity().getShooter() instanceof Player) {
				if(e.getEntity() instanceof Projectile) {
					Player p = (Player) e.getEntity().getShooter();
					MinigameArena arena = GetArena(p);
					if (arena!=null) {
						log.info("Hit block with arrow");
						log.info(p.toString());
						log.info(arena.toString());
						if(arena.DetectivesList.contains(p)) {
							e.getEntity().remove();
							AddItemStack(p, new ItemStack(Material.ARROW, 1), 9);
						}
					}
				}
			}
		}
	}
	
	/*public void ArrowShot (EntityShootBowEvent e) {
		if(e.getEntity().getWorld().getName().startsWith("murdermystery")){
			log.info(e.getEntity().getType().toString());
			if(e.getProjectile().getShooter() instanceof Player) {
				if(e.getEntity() instanceof Projectile) {
					Player p = (Player) e.getEntity().getShooter();
					MinigameArena arena = GetArena(p);
					log.info("Hit block with arrow");
					if(arena.DetectivesList.contains(p)) {
						e.getEntity().remove();
						AddItemStack(p, new ItemStack(Material.ARROW, 1), 28);
					}
				}
			}
		}
	}*/
	
//	public void DoRunnableForPainting(Location realLoc, Art art) {
//		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
//			@Override
//			public void run() {
//				CraftPainting newPainting2 = realLoc.getWorld().spawn(realLoc, CraftPainting.class);
//				newPainting2.setArt(art);
//			}
//		}, 2);
//	}
	
	public void MurderMysterySwordDamage(Player Damager, Player Victim, EntityDamageByEntityEvent e) {
		Damager.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
		MinigameArena arena = GetArena(Damager);
		if(arena==null || !arena.ArenaType.startsWith("murdermystery")) {
			e.setCancelled(true);
			log.info("MurderMysterySwordDamage");
			return;
		}
		if(Victim.getGameMode() != GameMode.CREATIVE && Victim.getGameMode() != GameMode.SPECTATOR) {
			if(Damager.getItemInHand().getType() == Material.IRON_SWORD || Damager.getInventory().getItemInOffHand().getType() == Material.IRON_SWORD) {
				if(arena.MurderersList.contains(Damager)) {
					if(arena!= null && arena.MurderersList.contains(Damager) && (arena.InnocentsList.contains(Victim) || arena.DetectivesList.contains(Victim))) {
						MurderMysteryKillPlayer(Victim);
						Victim.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You were killed by a player.", 5, 40, 30);
					}
					if(arena.MurderersList.contains(Victim)) {
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							@Override
							public void run() {
								log.info(ChatColor.GOLD + "" + (Victim.getHealth()-Victim.getMaxHealth()/2));
								if(Victim.getHealth()-Victim.getMaxHealth()/2>0.1) {
									Victim.setHealth(Victim.getMaxHealth()/2);
								}
								else {
									MurderMysteryKillPlayer(Victim);
									Victim.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You were shot by another murderer. We don't know why.", 5, 40, 30);
								}
								if(arena.MurderersList.contains(Damager) && !Damager.equals(Victim)) {
//									if(Damager.getHealth()-Damager.getMaxHealth()/2>0.1) {
//										Damager.setHealth(Damager.getHealth()-Damager.getMaxHealth()/2);
//									}
//									else {
									MurderMysteryKillPlayer(Damager);
									Damager.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.DARK_RED + "YOU DIED", ChatColor.ITALIC + "" + ChatColor.GRAY + "You killed another murderer, avoid doing this next time.", 5, 40, 30);
//									}
							}
						}
					}, 1);
				}
			}
			}
			else {
				e.setCancelled(true);
				log.info("MurderMysterySwordDamage");
			}
		}
	}
	
	/*public void BlockDamageEvent(BlockDamageEvent e) {
		Player p = e.getPlayer();
		log.info("BlockDamageEvent0");
		if(IfStringStartsWithArrayListEllement(p.getWorld().getName(), MapListWithBreakingProtection) == true) {
			log.info("BlockDamageEvent1");
			if(p.getGameMode() == GameMode.SURVIVAL) {
				log.info("BlockDamageEvent2");
				if(!PlayersBreakers.contains(p.getName())) {
					log.info("BlockDamageEvent3");
					e.setCancelled(true);
				}
			}
		}
	}*/
	
	public void MurderMysteryKillPlayer(Player HittenPlayer) {
		Location DeathLoc = new Location(HittenPlayer.getWorld(), HittenPlayer.getLocation().getX(), HittenPlayer.getLocation().getY(), HittenPlayer.getLocation().getZ());
		//HittenPlayer.setCustomNameVisible(false);
		HittenPlayer.setAllowFlight(true);
		MinigameVanishOn(HittenPlayer);
		MurderMysteryItemsClear(HittenPlayer);
		Location loc = HittenPlayer.getLocation();
		//e.setCancelled(true);
		MinigameArena arena = GetArena(HittenPlayer);
		double x = MurderMysteryArenaSpectatorSpawn.getX();
		double y = MurderMysteryArenaSpectatorSpawn.getY();
		double z = MurderMysteryArenaSpectatorSpawn.getZ();
		//HittenPlayer.getWorld().playEffect(HittenPlayer.getLocation(), Effect., Material.BARRIER.getId());
		Material blood = Material.REDSTONE_BLOCK;
		getServer().getOnlinePlayers().forEach(p -> {
			if(p.getWorld().getName().equals(HittenPlayer.getWorld().getName())) {
				p.spawnParticle(Particle.BLOCK_CRACK, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()), 100, blood.createBlockData());
				p.spawnParticle(Particle.BLOCK_CRACK, new Location(loc.getWorld(), loc.getX(), loc.getY() + 0.5, loc.getZ()), 100, blood.createBlockData());
				p.spawnParticle(Particle.BLOCK_CRACK, new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ()), 100, blood.createBlockData());
				p.spawnParticle(Particle.BLOCK_CRACK, new Location(loc.getWorld(), loc.getX(), loc.getY() + 1.5, loc.getZ()), 100, blood.createBlockData());
				//p.spawnParticle(Particle.BLOCK_CRACK, new Location(loc.getWorld(), loc.getX(), loc.getY() + 2, loc.getZ()), 100, blood.createBlockData());
			}
		});
		HittenPlayer.teleport(new Location(HittenPlayer.getWorld(), x, y, z));
		HittenPlayer.setFlying(true);
		if(arena.MurderersList.contains(HittenPlayer)) {
			arena.MurderersList.remove(HittenPlayer);
			arena.DeadList.add(HittenPlayer);
		}
		if(arena.DetectivesList.contains(HittenPlayer)) {
			arena.DetectivesList.remove(HittenPlayer);
			arena.DeadList.add(HittenPlayer);
			MurderMysterySpawnBow(HittenPlayer, DeathLoc);
		}
		if(arena.InnocentsList.contains(HittenPlayer)) {
			arena.InnocentsList.remove(HittenPlayer);
			arena.DeadList.add(HittenPlayer);
		}
		//HittenPlayer.setHealthScale(20);
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				HittenPlayer.setHealth(20.0);
			}
		}, 1);
		//HittenPlayer.setFoodLevel(20);
	}
	
	public void MurderMysterySpawnBow(Player p, Location pLoc) {
		Location loc = new Location(p.getWorld(), pLoc.getX(), pLoc.getBlockY(), pLoc.getZ()); 
		loc = loc.getBlock().getLocation();
		loc.setX(loc.getX()+0.5);
		loc.setZ(loc.getZ()+0.5);
		ItemStack compass = new ItemStack(Material.COMPASS, 1);
		ArmorStand stand = pLoc.getWorld().spawn(loc, ArmorStand.class);
		stand.setVisible(false);
		stand.setBasePlate(false);
		stand.setCanPickupItems(false);
        stand.setCollidable(false);
        stand.setGravity(false);
        stand.setRemoveWhenFarAway(false);
        MinigameArena arena = GetArena(p);
        stand.setRightArmPose(new EulerAngle(4.69, 0, 0));
        stand.setItemInHand(new ItemStack(Material.BOW));
        arena.DroppedBow.add(stand);
        for (Player inn : arena.InnocentsList) {
        	AddItemStack(inn, compass, 4);
        	inn.setCompassTarget(pLoc);
        }
        //arena.DroppedBowLoc.add(loc);
        final Location center = loc;
        final float radius = 0.2f;
        final float radPerSec = 3f;
        final float radPerTick = radPerSec / 20f;
        log.info("Player's loc: " + pLoc.toString());
        log.info("Bow's loc: " + stand.getLocation().toString());
        	new BukkitRunnable() {
        		int tick = 0;
        		@Override
        		public void run() {
        			if(stand==null) {
        				log.info("stand got null value");
        				cancel();
        			}
//        			loc.setYaw(loc.getYaw()+10);
//        			loc.setPitch(loc.getPitch()+10);
//        			stand.teleport(loc);
        			++tick;
        			Location loc = getLocationAroundCircle(center, radius, radPerTick * tick);
                    //System.out.println(loc);
                    stand.setVelocity(new Vector(0, 0, 0));
                    stand.teleport(loc);
        			
        			//stand.setRotation(loc.getYaw() + 10*iCopy, 100);
//					stand.teleport(p.getLocation().setYaw(stand.getLocation().getYaw()+5));
        			//stand.getLocation().setPitch(2);
        			//stand.getLocation().setYaw(2);
        		}
        	}.runTaskTimer(this, 0, 2);
        }
	
	public void VanishOn(Player p) {
		for (Player p1 : getServer().getOnlinePlayers()) {
            p1.hidePlayer(p);
        }
		p.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(99999, 1));
        AdminPlayersVanished.add(p.getName());
        p.setCanPickupItems(false);
		log.info(""+AdminPlayersVanished.contains(p.getName()));
	}
	
	public void VanishOff(Player p) {
		for (Player p1 : getServer().getOnlinePlayers()) {
            p1.showPlayer(p);
    		if(p.getPotionEffect(PotionEffectType.INVISIBILITY) != null && !MinigamePlayersVanished.contains(p)) {
    			p.removePotionEffect(PotionEffectType.INVISIBILITY);
    		}
            AdminPlayersVanished.remove(p.getName());
            p.setCanPickupItems(true);
        }
	}
	
	public void MinigameVanishOn(Player p) {
		for (Player p1 : getServer().getOnlinePlayers()) {
            p1.hidePlayer(p);
            MinigamePlayersVanished.add(p.getName());
    		p.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(99999, 1));
            p.setCanPickupItems(false);
        }
	}
	
	public void MinigameVanishOff(Player p) {
		for (Player p1 : getServer().getOnlinePlayers()) {
            p1.showPlayer(p);
		}
    	if(p.getPotionEffect(PotionEffectType.INVISIBILITY) != null && !AdminPlayersVanished.contains(p.getName())) {
    		p.removePotionEffect(PotionEffectType.INVISIBILITY);
    	}
        MinigamePlayersVanished.remove(p.getName());
        if(p.getGameMode()!=GameMode.CREATIVE && p.getGameMode()!=GameMode.SPECTATOR) p.setAllowFlight(false);
        p.setCanPickupItems(true);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void EntityTargetEvent(EntityTargetEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
	        if (e.getTarget() instanceof LivingEntity){
	            if(AdminPlayersVanished.contains(p.getName())) {
	            	e.setCancelled(true);
	            	log.info("EntityTargetEvent");
	            }
	        }
		}
	}
	
	public void VanishCommand(Player p, String[] args) {
		if(p.isOp() == true || TrialStaffPlayers.contains(p.getName())){
			if(args.length == 0){
				/*boolean AllPlayersCantSeeHim = true;
				if(AdminPlayersVanished.size() == 0) {
					for (Player p1 : getServer().getOnlinePlayers()) {
						if(p1.canSee(p)) {
							AllPlayersCantSeeHim = false;
		            		break;
		            	}
		        	}
				}
				else AllPlayersCantSeeHim = false;*/
				
				if(!AdminPlayersVanished.contains(p.getName())) {
					VanishOn(p);
					AdminPlayersVanished.add(p.getName());
					p.sendMessage(ChatColor.DARK_GREEN + "Vanish mode has been enabled!");
					return;
				}
				else if(AdminPlayersVanished.contains(p.getName())) {
					VanishOff(p);
					AdminPlayersVanished.remove(p.getName());
					p.sendMessage(ChatColor.DARK_GREEN + "Vanish mode has been disabled.");
					return;
				}
			}
			else if(args.length == 1){
				if(getServer().getPlayer(args[0]) != null) {
					if(!AdminPlayersVanished.contains(args[0])) {
						VanishOn(getServer().getPlayer(args[0]));						
						p.sendMessage(ChatColor.DARK_GREEN + "Vanish mode has been enabled for " + args[0] + "!");
						return;
					}
					else {
						VanishOff(getServer().getPlayer(args[0]));
						p.sendMessage(ChatColor.DARK_GREEN + "Vanish mode has been disabled for " + args[0] + ".");
						return;
					}
				}
			}
		}
		else {
			p.sendMessage(ChatColor.RED + "You can't use this command.");
			return;
		}
	}
	
	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		//PlayersBreakers.remove(p.getName());
		
		//PlayersFlyInSurvival.remove(p.getName());
		AdminPlayersVanished.remove(p.getName());
		AuthPlayers.remove(p.getName());
		if(!p.getWorld().getName().equalsIgnoreCase("auth")) {
			try {
				saveInventory(p);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		//e.setQuitMessage(ChatColor.WHITE + p.getName() + " left the server.");
		for(Player player : p.getWorld().getPlayers()) {
			player.sendMessage(ChatColor.WHITE + p.getName() + " left the server.");
		}
	}
	
	/*public void PlayerPickupItemEvent(PlayerPickupItemEvent e) {
		log.info("333");
		if(AdminPlayersVanished.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			log.info("111");
		}
	}*/
	
	public void PlayerPickupItemEvent(PlayerPickupArrowEvent e) { //cancelling picking an arrow up
		log.info("444");
		if(AdminPlayersVanished.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			log.info("PlayerPickupItemEvent");
			log.info("PlayerPickupArrowEvent canceled");
		}
	}
	
	public void UpdateFeatherFallingTimer() { //deprecated
		for(int i = 0; i < FfArenas.size(); i++) {
        	if(FfArenas.get("featherfalling" + (i+1)).PlayersCount < 1) {
    			FfArenas.remove("featherfalling" + (i+1));
    		}
        	//log.info("in for");
        	log.info("TimeState = " + FfArenas.get("featherfalling" + (i+1)).TimeState);
        	if((FfArenas.get("featherfalling" + (i+1)).TimeState == "waiting for max players") || (FfArenas.get("featherfalling" + (i+1)).TimeState == "full")) {
        		log.info("1");
        		if(FfArenas.get("featherfalling" + (i+1)).TimeLeftToStart > 0){
        			FfArenas.get("featherfalling" + (i+1)).TimeLeftToStart--;
        			//getServer().broadcastMessage(FfArenas.get("featherfalling" + (i+1)).TimeLeftToStart + "");
        			int iCopy = i;
        			getServer().getWorld("featherfalling" + (i+1)).getPlayers().forEach(p -> {
        				//p.setExp(0);
        				/*if(((FfArenas.get("featherfalling" + (iCopy+1)).PlayersCount >= FfArenas.get("featherfalling" + (iCopy+1)).PlayersMin) && (FfArenas.get("featherfalling" + (iCopy+1)).PlayersCount < FfArenas.get("featherfalling" + (iCopy+1)).PlayersMax))) {
        					float persent = (FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart) / (float) (FfArenas.get("featherfalling" + (iCopy+1)).TimeToStartMinPlayers);
        					p.setLevel(FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart);
        					p.setExp(persent);
        					//getServer().broadcastMessage(persent + "");
    					}
    					else if(FfArenas.get("featherfalling" + (iCopy+1)).PlayersCount == FfArenas.get("featherfalling" + (iCopy+1)).PlayersMax) {
    						float persent = (FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart) / (float) (FfArenas.get("featherfalling" + (iCopy+1)).TimeToStartMaxPlayers);
    						p.setLevel(FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart);
    						p.setExp(persent);
    						//getServer().broadcastMessage(persent + "");
    					}*/
        				float persent;
        				if(FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart > FfArenas.get("featherfalling" + (iCopy+1)).TimeToStartMaxPlayers) persent = (FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart) / (float) (FfArenas.get("featherfalling" + (iCopy+1)).TimeToStartMinPlayers);
        				else persent = (FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart) / (float) (FfArenas.get("featherfalling" + (iCopy+1)).TimeToStartMaxPlayers);
    					p.setLevel(FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart);
    					p.setExp(persent);
    					if(FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart <= 3 && FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart > 0) {
    						p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "" + FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart, "", 5, 30, 10);
    					}
    					if(FfArenas.get("featherfalling" + (iCopy+1)).TimeLeftToStart == 0) {
    						p.teleport(new Location(p.getWorld(), -30.0, 101.0, -1));
    						p.getInventory().setItem(4, new ItemStack(Material.FEATHER, 1));
    						p.updateInventory();
    						p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Start!", "", 5, 30, 10);
    						FfArenas.get("featherfalling" + (iCopy+1)).ArenaState = "ingame";
    					}
        			});
        		}
        	}
        	if(FfArenas.get("featherfalling" + (i+1)).ArenaState == "ingame") {
        		log.info("2");
        		if(FfArenas.get("featherfalling" + (i+1)).TimeLeftToEnd > 0){
        			FfArenas.get("featherfalling" + (i+1)).TimeLeftToEnd--;
        			getServer().broadcastMessage(FfArenas.get("featherfalling" + (i+1)).TimeLeftToEnd + "");
        		}
        	}
        }
	}
	
	public void UpdateMinigameTimer(Map<String, MinigameArena> MinigameArenas, String ArenaType) { //for changing smth every second
		for(int i = 0; i < MinigameArenas.size(); i++) {
        	if(MinigameArenas.get(ArenaType + (i+1)).PlayersCount < 1) {
    			MinigameArenas.remove(ArenaType + (i+1));
    		}
        	//log.info("TimeState = " + MinigameArenas.get(ArenaType + (i+1)).TimeState);
        	if((MinigameArenas.get(ArenaType + (i+1)).TimeState == "waiting for max players") || (MinigameArenas.get(ArenaType + (i+1)).TimeState == "full")) {
        		//log.info("1");
        		if(MinigameArenas.get(ArenaType + (i+1)).TimeLeftToStart > 0){
        			MinigameArenas.get(ArenaType + (i+1)).TimeLeftToStart--;
        			int iCopy = i;
        			getServer().getWorld(ArenaType + (i+1)).getPlayers().forEach(p -> {
        				float persent;
        				if(MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart > MinigameArenas.get(ArenaType + (iCopy+1)).TimeToStartMaxPlayers) persent = (MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart) / (float) (MinigameArenas.get(ArenaType + (iCopy+1)).TimeToStartMinPlayers);
        				else persent = (MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart) / (float) (MinigameArenas.get(ArenaType + (iCopy+1)).TimeToStartMaxPlayers);
    					p.setLevel(MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart);
    					p.setExp(persent);
    					if(MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart <= 3 && MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart > 0) {
    						p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "" + MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart, "", 5, 30, 10);
    					}
    					if(MinigameArenas.get(ArenaType + (iCopy+1)).TimeLeftToStart == 0) {
    						//HashMap MinigameHashMap = null;
    						for(int j = 0; j < MapLocations.size(); j++) {
    							if(MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber == 0) { //надо ли пересчитывать
    								if(MapLocations.get(j).containsKey(ArenaType)) {
    								int jUnderPrevCopy = j;
    								log.info(ChatColor.AQUA + "MapLocations.get(j): " + MapLocations.get(j).toString());
    								log.info(ChatColor.AQUA + "MinigameArenas.get(ArenaType + (iCopy+1)): " + MinigameArenas.get(ArenaType + (iCopy+1)));
    								MapLocations.get(j).get(ArenaType).forEach(map -> {
    									if(p.getWorld().getName().startsWith(map.MinigameName)) {
    										log.info("2215");
    										Map <String, String> MapsVoting = new HashMap<>();
    										log.info(ChatColor.BLUE+MapLocations.get(jUnderPrevCopy).toString()+"");
    										log.info(ChatColor.DARK_BLUE+MapLocations.get(jUnderPrevCopy).get(ArenaType).toString()+"");
    										for(int n = 0; n < MapLocations.get(jUnderPrevCopy).get(ArenaType).size(); n++) {
    											log.info("not n =" + MapLocations.get(jUnderPrevCopy).get(ArenaType).get(n).Number);
    											MapsVoting.put(Integer.toString(MapLocations.get(jUnderPrevCopy).get(ArenaType).get(n).Number) , "0");
    											//for(int g = 0, g < MapsVoting., g++) {
    											log.info(MapsVoting.toString());
    											//}
    										}
    										final AtomicBoolean nobodyVoted = new AtomicBoolean(true);
    										final AtomicBoolean AlreadyCheckedArena = new AtomicBoolean(false);
    										int[] MapsVotingArray = new int[20];
    										int jPrevCopy = jUnderPrevCopy;
    										getServer().getOnlinePlayers().forEach(player -> { //проверка для каждого игрока на его голос
    											int FinalVotesCount = 0;
    											int MapNumber = 0;
    											int MinNumber = 500;
    											int MaxNumber = 0;
    											int jCopy = jPrevCopy;
    											log.info(player.getWorld().getName() + " == " + ArenaType + (iCopy+1) + " = ?");
    											if(player.getWorld().getName().equals((ArenaType + (iCopy+1)))) { //берём игрока с арены
    												//if(AlreadyCheckedArena.get() == false) {
    												//AlreadyCheckedArena.set(true);
    												//ArrayList<String> MapsVoting = new ArrayList<>();
    												log.info("MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()) = " + MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()));
    												if(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()) == null) { //игрок не проголосовал
    													log.info("no vote");
    												}
    												else {
    													//if(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName())) != null) {
    													log.info("adding " + MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()) + " and " + Integer.toString(Integer.parseInt(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()))) + 1) + " to MapsVoting.");
    													log.info(ChatColor.AQUA+MinigameArenas.toString()+"");
    													log.info(ChatColor.AQUA+ MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.toString() +"");
    													log.info(ChatColor.AQUA+MapsVoting.toString()+"");
    													log.info(ChatColor.AQUA+MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()))+"");
    													MapsVoting.put(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()), Integer.toString(Integer.parseInt(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()))) + 1));
    													FinalVotesCount = FinalVotesCount+1;
    													//кладём голос игрока
    													//}
    													/*else {
    													log.info("nothing second");
    													MapsVoting.put(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()), "1");
    													}*/
    												}
    												log.info("MapsVoting we got: " + MapsVoting.toString());
    												
    												//boolean MapsVotingIsEmpty = true;
    												//for (int f = 0; f < FinalVotesCount; f++) {
    												
    												//}
    												
    												log.info("FinalVotesCount = " + FinalVotesCount);
    												
    												if(FinalVotesCount == 0) nobodyVoted.set(false);
    											
    										
    												if(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()) != null) nobodyVoted.set(false);//проголосовал ли хотя бы один игрок
    												//log.info("part: " + MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()));
    												//log.info("current number is " + MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName())));
    												//int MinNumber2 = MinNumber.get();
    												// HEEEEREEEEE
    												//if(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName())) != null && MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName())).equals(MinNumber)) {
//    												boolean alreadyIs = false;
//    												log.info("MapsVotingArray: " + MapsVotingArray.toString());
//    												for(int g : MapsVotingArray) {
//    													if(g == Integer.parseInt(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName())))) {
//    														alreadyIs = true;
//    													}
//    												}
//    												log.info("alreadyIs: " + alreadyIs);
//    												if(alreadyIs == false) {
//    													MapsVotingArray[MapsVotingArray.length] = Integer.parseInt(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName())));
//    													log.info("MapsVotingArray[MapsVotingArray.length] = " + MapsVotingArray[MapsVotingArray.length]);
//    												}
    											
    												//}
    											
    												Map <String, String> VotesNoEmpty = new HashMap<>();
    												ArrayList<String> HighestVotes = new ArrayList<>();
    											
    												for(int f = 1; f <= MapsVoting.size(); f++) {
    													log.info("MapsVoting.get(Integer.toString(f)) = " + MapsVoting.get(Integer.toString(f)));
    													if(Integer.parseInt(MapsVoting.get(Integer.toString(f))) > MaxNumber) {
    														//VotesNoEmpty.put(Integer.toString(f), MapsVoting.get(f));
    														MaxNumber = Integer.parseInt(MapsVoting.get(Integer.toString(f)));
    													}
    												}
    												log.info("MaxNumber = " + MaxNumber);
    												for(int f = 1; f <= MapsVoting.size(); f++) {
    													if(Integer.parseInt(MapsVoting.get(Integer.toString(f))) == MaxNumber) {
    														//VotesNoEmpty.put(Integer.toString(f), MapsVoting.get(f));
    														HighestVotes.add(Integer.toString(f));
    													}
    												}
    												log.info("HighestVotes: " + HighestVotes);
    											
    												getServer().broadcastMessage(HighestVotes.size() + "");
    												if(HighestVotes.size() == 0) {
    													MapNumber = random.nextInt(MapLocations.get(jCopy).get(ArenaType).size());
    													log.info("VotesNoEmpty.size() = 0");
    													MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber = MapNumber;
    												}
    												else if(HighestVotes.size() == 1) {
    													MapNumber = Integer.parseInt(HighestVotes.get(0));
    													log.info("VotesNoEmpty.size() = 1");
    													MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber = MapNumber;
    												}
    												else {
//    													for(int f = 0; f<HighestVotes.size(); f++) {
//    														if(VotesNoEmpty.get(f) != null && Integer.parseInt(VotesNoEmpty.get(f)) > 0 && Integer.parseInt(VotesNoEmpty.get(f))<MinNumber) {
//    															MinNumber = Integer.parseInt(VotesNoEmpty.get(f));
//    															MapNumber = f+1;
//    															log.info("MinNumber is now " + MinNumber);
//    														}
//    													}
    													int smth = random.nextInt(HighestVotes.size());
    													MapNumber = Integer.parseInt(HighestVotes.get(smth));
    													log.info("the random int is " + smth);
    													log.info("Integer.parseInt(HighestVotes.get(smth)) = " + Integer.parseInt(HighestVotes.get(smth)));
    													MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber = MapNumber;
    												
    													/*for(int f = 0; f<MapsVoting.size(); f++) {
    	    												if(MapsVoting.get(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()))) != null && Integer.parseInt(MapsVoting.get(MinigameArenas.get(ArenaType + (iCopy+1)).MapVoting.get(player.getName()))) == MinNumber) {
    	    													boolean alreadyIs = false;
    	    													for(int g : MapsVotingArray) {
    	    														if(g == f) {
    	    															alreadyIs = true;
    	    														}
    	    													}
    	    													if(alreadyIs == false) {
    	    														MapsVotingArray[MapsVotingArray.length] = f;
    	    														log.info("MapsVotingArray[MapsVotingArray.length] = " + MapsVotingArray[MapsVotingArray.length]);
    	    													}
    	    												}
    	    											}*/
//    	    											for(int g : MapsVotingArray) {
//    	    												log.info(MapsVotingArray[g] + "");
//    	    											}
//    	    											if(MapsVotingArray.length == 1) {
//    	    												MapNumber = MapsVotingArray[0];
//    	    												log.info("1 length");
//    	    											}
//    	    											else {
//    	    												MapNumber = MapsVotingArray[random.nextInt(MapsVotingArray.length)];
//    	    												log.info("random");
//    	    											}
    												}
    												MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber = MapNumber;
    											//}
    										}
    									});
    								}
    							});
    							}
    						}
    									if(MinigameArenas.get(p.getWorld().getName()).MapWonNumber<1) {
    										//p.sendMessage(ChatColor.DARK_RED + "[ERROR] " + ChatColor.WHITE + "An error occured, MapWonNumber is " + MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber);
    										p.sendMessage(ChatColor.DARK_RED + "[ERROR] " + ChatColor.WHITE + "An error occured, MapWonNumber is " + MinigameArenas.get(p.getWorld().getName()).MapWonNumber);
    										log.info(ChatColor.DARK_RED + "[ERROR] " + ChatColor.WHITE + "An error occured, MapWonNumber is " + MinigameArenas.get(p.getWorld().getName()).MapWonNumber);
    									}
    								
    									if(MapLocations.get(j).containsKey(ArenaType)) {
    									log.info(ChatColor.AQUA + "MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber = " + MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber);
    									log.info(ChatColor.AQUA + "" + MapLocations.get(j).get(ArenaType));
    									 MapLocations.get(j).get(ArenaType).forEach(d -> {
    										 if(d.Number == MinigameArenas.get(ArenaType + (iCopy+1)).MapWonNumber) {
    											log.info(ChatColor.AQUA + "" + d);
    											MinigameArenas.get(ArenaType + (iCopy+1)).MapLink = d;
    											Location LocFromDataBase = d.Location;
    											Location LocToTeleport = new Location(p.getWorld(), LocFromDataBase.getX(), LocFromDataBase.getY(), LocFromDataBase.getZ());
    											if(ArenaType == "featherfalling") {
    												p.teleport(LocToTeleport);
    												p.getWorld().setTime(d.WorldTime);
    												p.getInventory().setItem(4, new ItemStack(Material.FEATHER, 1));
    												p.updateInventory();
    												p.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Start!", "", 5, 30, 10);
    											}
    											if(ArenaType == "murdermystery") {
    												MurderMysteryStartGame(p, MinigameArenas.get(ArenaType + (iCopy+1)), d);
    											}
    											MinigameArenas.get(ArenaType + (iCopy+1)).ArenaState = "ingame";
    										 	}
    									 	});
    									}
    								
    							}
    						//p.teleport(new Location(p.getWorld(), -30.0, 101.0, -1));
    					}
        			});
        		}
        	}
        	if(MinigameArenas.get(ArenaType + (i+1)).ArenaState == "ingame") {
        		//log.info("2");
        		if(MinigameArenas.get(ArenaType + (i+1)).TimeLeftToEnd > 0){
        			MinigameArenas.get(ArenaType + (i+1)).TimeLeftToEnd--;
        			getServer().broadcastMessage(MinigameArenas.get(ArenaType + (i+1)).TimeLeftToEnd + "");
        		}
        	}
        }
	}
	
	public Map<String, MinigameArena> GetMinigameData(String name) { //returns data of the minigame arenas
		if(name.startsWith("featherfalling")) return FfArenas;
		if(name.startsWith("murdermystery")) return MmArenas;
		if(name.startsWith("jumprun")) return JrArenas;
		if(name.startsWith("amongus")) return AuArenas;
		return null;
	}
	
	public String GetMinigameName(String name) { //returns string of minigame name
		if(name.startsWith("featherfalling")) return "featherfalling";
		if(name.startsWith("murdermystery")) return "murdermystery";
		if(name.startsWith("jumprun")) return "jumprun";
		if(name.startsWith("amongus")) return "amongus";
		return null;
	}
	
	/*public Map<String, MinigameArena> GetMinigamelocType(String name) {
		if(name.startsWith("featherfalling")) return Ff;
		if(name.startsWith("murdermystery")) return MmArenas;
		return null;
	}*/
	
	
	public void CreateNPC(Player p) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
		Location PlayerLocation = p.getLocation();
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld)p.getWorld()).getHandle();
		GameProfile gameProfile = new GameProfile(p.getUniqueId(), p.getName());
		
		//DataWatcherObject<EntityPose> poseDataWatcherObject = DataWatcher.a(Entity.class, DataWatcherRegistry.s);
		EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
		Player npcPlayer = npc.getBukkitEntity().getPlayer();
		npcPlayer.setCustomName(p.getName());
		npcPlayer.setPlayerListName("");
		//npcPlayer.setSleepingIgnored(true);
		//npcPlayer.sleep(PlayerLocation, false);
		//DataWatcher dataWatcher = new DataWatcher(npc);
		//npcPlayer.register(poseDataWatcherObject, EntityPose.SLEEPING);
		//npc.entitySleep(PlayerLocation.getBlock());
		//npc.sendPacket(Packet.SLEEP);
		
		npc.setLocation(PlayerLocation.getX(), PlayerLocation.getY(), PlayerLocation.getZ(), PlayerLocation.getYaw(), PlayerLocation.getPitch());
		PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		}
	}
	
	public void DeleteAllNPC(Player p) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld)p.getWorld()).getHandle();
		GameProfile gameProfile = new GameProfile(p.getUniqueId(), p.getName());
		//Plugin plugin;
		for(int i = 0; i<server.getPlayers().length; i++) {
			if(getServer().getPlayer(server.getPlayers()[i]).getPlayerListName() == null) {
				getServer().broadcastMessage("Found");
				getServer().getPlayer(server.getPlayers()[i]).remove();
			}
		}
		}
	}
	
    public boolean onGround(Player p) { // wheather player is on ground or not
        //Player p = e.getPlayer();
        if(p.getWorld().getName().startsWith("jumprun")) {
        	//log.info(p.getTargetBlock(arg0, arg1));
        	if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
        		//p.sendMessage("You are on the ground");
        		return true;
        	}
        	else {
            	//p.sendMessage("You are in the air");
            	//p.setVelocity(p.getLocation().getDirection().multiply(1));
                //p.setVelocity(new Vector(p.getVelocity().getX(), 0.75D, p.getVelocity().getZ()));
        		return false;
        	}
        }
        return true;
    }
    
    public boolean onWall(Player p, int maxDistance) {
    	//int maxDistance = 2;
    	for(int i = 0; i<maxDistance; i++) {
 	    	if(p.getTargetBlockExact(i) != null) {
 	    		//log.info("Wall: " + p.getTargetBlockExact(i).getType());
 	    		if(p.getTargetBlockExact(i).getType().toString()!="AIR") {
 	    			return true;
 	    		}
 	    	}
 	    }
    	return false;
    }
    
    /*public void JumpEvent(PlayerMoveEvent e) { //Double jump
    	
    }*/
    
    
    public void ChangeAllowFly(Player p) {
    	if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
    	if(p.getAllowFlight() == true) {
    		p.setAllowFlight(false);
    	}
    	else {
    		p.setAllowFlight(true);
    	}
    	}
    }
    
    @EventHandler
    public void DoubleJumpManager(PlayerMoveEvent e) {
    	Player p = e.getPlayer();
    	if(FrozenPlayers.contains(p.getName())) {
    		/*if(!FrozenPlayersFlyLocs.containsKey(p.getName())) {
    			FrozenPlayersFlyLocs.put(p.getName(), p.getLocation());
    		}*/
    		//if((FrozenPlayersFlyLocs.containsKey(p.getName()) && FrozenPlayersFlyLocs.get(p.getName()).getBlock().getLocation()!=p.getLocation().getBlock().getLocation()) || e.getFrom().getBlockX()!=e.getTo().getBlockX() || e.getFrom().getBlockY()!=e.getTo().getBlockY() || e.getFrom().getBlockY()!=e.getTo().getBlockZ()) {
    		if(!e.getFrom().equals(e.getTo())) {
    		//if(e.getFrom().getBlock().getLocation()!=e.getTo().getBlock().getLocation()) {
    			Location loc = e.getFrom();
    			loc.setDirection(e.getTo().getDirection());
    			p.teleport(loc);
    		}
    	}
    	if(p.getWorld().getName().startsWith("jumprun")) {
    		if(p.getGameMode()!=GameMode.CREATIVE && p.getGameMode()!=GameMode.SPECTATOR) {
    			if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType()==Material.EMERALD_BLOCK) {
    				JumpBoostPlayer(p, 2, 0.5, 1.5);
    			}
    			if(onGround(p) == true) {
    				p.removePotionEffect(PotionEffectType.SLOW_FALLING);
    				if(p.getAllowFlight()==false) {
    					p.setAllowFlight(true);
    					/*if(p.hasPotionEffect(PotionEffectType.SLOW_FALLING)) {
    						p.removePotionEffect(PotionEffectType.SPEED);
    					}*/
    					log.info("allowed flight");
    				}
    			}
    			if(onGround(p) == false && onWall(p, 2) == true) {
    				if(p.getAllowFlight()==false) {
    					p.setAllowFlight(true);
    					log.info("allowed flight");
    				}
    			}
    			//log.info((p.isSneaking()==false) + "");
    			//log.info((p.hasPotionEffect(PotionEffectType.SLOW_FALLING)) + "");
    			if(p.isSneaking()==true && p.hasPotionEffect(PotionEffectType.SLOW_FALLING)) {
    				log.info(IsThereMaterialUnder(p, Material.LAVA, 12) +"");
    				if(IsThereMaterialUnder(p, Material.LAVA, 12)) {
    					log.info("Vector for slow falling");
    					Location loc = p.getLocation();
        				loc.setPitch(0);
    	    			//Vector vec = new Vector (p.getVelocity().getX(), p.getVelocity().getY(), p.getVelocity().getZ());
        				Vector vec = loc.getDirection().multiply(0.25).setY(0.3);
        				p.setVelocity(vec);
    				}
    				else {
    					log.info("Vector for slow falling");
    					Location loc = p.getLocation();
        				loc.setPitch(0);
    	    			//Vector vec = new Vector (p.getVelocity().getX(), p.getVelocity().getY(), p.getVelocity().getZ());
        				Vector vec = loc.getDirection().multiply(0.25).setY(-0.1);
        				p.setVelocity(vec);
    				}
    			}
    			//e.setCancelled(true);
    		}
    	}
    }
    
    
    public boolean IsThereMaterialUnder(Player p, Material m, int distanse) {
    	Location loc = p.getLocation().add(0,2,0);
    	for(int i = 0; i<distanse; i++) {
    		log.info(loc.getBlock().getType().toString());
    		if(loc.getBlock().getType().equals(m)) {
    			return true;
    		}
    		loc.add(0, -1, 0);
    	}
    	return false;
    }
    
    //if(!p.getFacing().equals(BlockFace.DOWN)) {
    

    @EventHandler
    public void onFlightAttempt(PlayerToggleFlightEvent e) {
    	Player p = e.getPlayer();
    	if(p.getWorld().getName().startsWith("jumprun")) {
    		if(p.getAllowFlight()==true && e.isFlying() && !p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR) && !PlayersFlyInSurvival.contains(p.getName())) {
    			e.setCancelled(true);
    			log.info("onFlightAttempt");
    			if(onWall(p, 2)==true && onGround(p)==false) {
    				Location loc = p.getLocation();
    				loc.setPitch(110);
    				Vector v = loc.getDirection().multiply(0.5).setY(1);
        			p.setVelocity(v);
        			p.setAllowFlight(false);
        			p.playSound(p.getLocation(), Sound.ENTITY_SLIME_SQUISH_SMALL, 1, 1);
            		log.info("forbidded flight");
    			}
    			else {
    				if(onWall(p, 3)==false) {
    					JumpBoostPlayer(p, 1, 0.5, 1);
    				}
    			}
    		}
    	}
   
    }
    
    public void JumpBoostPlayer(Player p, int type, double power, double yPower) {
    	//type:
    	//1 - FlyghtAttempt Jump Boost
    	//2 - Jump Boost block
    	//p.sendMessage(ChatColor.GREEN + "**WOOSH**");
		//p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 10, -10);
		Vector NULL_VECTOR = new Vector(0, 0, 0);
		Location loc = p.getLocation();
		if(p.getVelocity().getBlockX()==0.0 && p.getVelocity().getBlockY()==0.0 || type==2) {
			loc.setPitch(90);
			//if(type==2)loc.setPitch(0);
			Vector v = loc.getDirection().multiply(1.6*power).setY(yPower);
			p.setVelocity(v);
			p.playSound(p.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
		}
		else {
			//loc.setPitch(30);
			Vector v = p.getLocation().getDirection().multiply(power).setY(yPower);
			p.setVelocity(v);
			p.playSound(p.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
		}
		/*if(NULL_VECTOR.getY() < 0 && p.getVelocity().equals(NULL_VECTOR)) {
			loc.setPitch(90);
		}*/
		log.info(p.getVelocity().toString());
		//Vector jump = p.getLocation().getDirection().multiply(0.2).setY(1.1);
		//p.setVelocity(p.getVelocity().add(jump));
		if(type==1) {
			p.setAllowFlight(false);
			log.info("forbidded flight");
		}
		else if(type==2) {
			p.setAllowFlight(true);
			log.info("allowed flight");
		}
    }
    
    /*@EventHandler
    public void JumpEvent(PlayerMoveEvent e) { //Detects Jump
    	Player p = e.getPlayer();
    	double jumpVelocity = (double) 0.42F;
    	log.info("j0");
    	//log.info(Double.compare(p.getVelocity().getY(), jumpVelocity) + "");
    	log.info(p.getVelocity().getY() + "");
    	if ((p.getVelocity().getY() > 0 || p.getFallDistance() > 2) && p.getVelocity().getY()<0) {
    		log.info("j1");
            if (e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && e.getPlayer().getLocation().getBlock().getType().toString().endsWith("ACACIA_STAIRS") == false) {
            	log.info("j2");
                if (onGround(p)==false) { //Double.compare(p.getVelocity().getY(), jumpVelocity) == 0
                	p.sendMessage(System.currentTimeMillis() + "Jumping");
                }
            }
    	}
    }*/
    
    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) { //shift sneaking
        Player p = e.getPlayer();
        //log.info("shift toggled");
        if(p.getWorld().getName().startsWith("jumprun")) {
        	if(p.getGameMode()!=GameMode.CREATIVE && p.getGameMode()!=GameMode.SPECTATOR) {
        	if(p.isSneaking()==false) {
        		if(onGround(p)==false && onWall(p, 2)==false) {
//        			Location loc = p.getLocation();
//        			loc.setPitch(0);
//    	    		//Vector vec = new Vector (p.getVelocity().getX(), p.getVelocity().getY(), p.getVelocity().getZ());
//        			Vector vec = loc.getDirection().multiply(1);
    	    		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 99999, 2));
    	    		//p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
//    	    		p.setVelocity(vec);
    	    		log.info("added slow falling");
        		}
        	}
        	else{
	    		//zevt.getPlayer().setVelocity(vec); 
	    		p.removePotionEffect(PotionEffectType.SLOW_FALLING);
	    		p.removePotionEffect(PotionEffectType.SPEED);
	    		log.info("removed slow falling");
	    	}
        	}
        }
    }
    
    @EventHandler
    public void LaternHitJunpRunAbility(PlayerInteractEvent e) {
    	Player p = e.getPlayer();
    	if(p.getWorld().getName().startsWith("jumprun")) {
    		if(p.getGameMode()!=GameMode.CREATIVE && p.getGameMode()!=GameMode.SPECTATOR) {
    		if((e.getHand()==EquipmentSlot.HAND || e.getHand()==EquipmentSlot.OFF_HAND || e.isBlockInHand()==false || e.getPlayer().getItemInHand().getType() == null) && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
    			//log.info("Da");
    			Location loc = IsBlockNearby(p, Material.SOUL_LANTERN, 3);
    			if(loc != null) {
    				p.spawnParticle(Particle.BLOCK_CRACK, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()), 100, Material.LIGHT_BLUE_STAINED_GLASS.createBlockData());
    				FrozenPlayers.add(p.getName());
    				log.info("added");
    				if(!FrozenPlayersFlyLocs.containsKey(p.getName())) FrozenPlayersFlyLocs.put(p.getName(), p.getLocation());
        			if(!FrozenPlayersFlyState.containsKey(p.getName())) FrozenPlayersFlyState.put(p.getName(), p.getAllowFlight());
        			if(onGround(p)==false && p.getAllowFlight()==true) {
        				p.setFlying(true);
        			}
    			
        			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
        				@Override
        				public void run() {
        					FrozenPlayers.remove(p.getName());
        					//if(FrozenPlayersFlyState.get(p.getName())!=null) {
        						p.setAllowFlight(FrozenPlayersFlyState.get(p.getName()));
        						log.info("removed");
        					//}
    					}
        			}, 2);
        			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
        				@Override
        				public void run() {
        					if(!FrozenPlayers.contains(p.getName())) {
        						p.setFlying(false);
        						log.info("set flying to false");
        						FrozenPlayersFlyLocs.remove(p.getName());
        						FrozenPlayersFlyState.remove(p.getName());
        						p.setAllowFlight(true);
                				Vector v = p.getLocation().getDirection().multiply(1.2);
                    			p.setVelocity(v);
                    			//p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1, 1);
                    			p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
                    			//p.playSound(p.getLocation(), Sound.ENTITY_EGG_THROW, 1, 1);
        					}
    					}
        			}, 5);
    			}
    			//p.setAllowFlight(true);
    		}
    	}
    	}
    }
    
    /*
    //--------------------------------------------------------------------------------------------------------------------------------------------------
    private HashMap<String, Long> clickPlayers = new HashMap<String, Long>();
	private Block toExplode;
	private int toExplosion = 40;
	private JavaPlugin plugin;
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		e.getPlayer().sendMessage("I heard you!");
		long currTime = System.currentTimeMillis();
		if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.TNT) {
			if(!clickPlayers.containsKey(e.getPlayer().getName())) {
				if(clickPlayers.isEmpty()) {
					clickPlayers.put(e.getPlayer().getName(), currTime);
					return;
				}
			}
			
			long lastClick = clickPlayers.get(e.getPlayer().getName());
			if(currTime - lastClick > 250) {
				clickPlayers.remove(e.getPlayer().getName());
				e.getPlayer().sendMessage("You were taken out from arming with time of " + (currTime - lastClick));
		    }
			else {
				e.getPlayer().sendMessage("Surviving with a time of" + (currTime - lastClick));
				clickPlayers.put(e.getPlayer().getName(), currTime);
			}
		}
	}
	//--------------------------------------------------------------------------------------------------------------------------------------------------
    */
	
	public Location IsBlockNearby(Player p, Material material, int distanse) {
        ArrayList<Location> temp = new ArrayList<Location>();
        ArrayList<Double> dis = new ArrayList<Double>();
 
        Location loc = new Location(p.getWorld(), p.getLocation().getBlock().getX()-distanse, p.getLocation().getBlock().getY()-distanse, p.getLocation().getBlock().getZ()-distanse);
        //p.sendMessage("Drop event work");//For debug
        //p.sendMessage(loc.getX() + " " + loc.getZ());
        for(int i=1; i <= 2*distanse+1 ; i++) {
            for(int j =1; j <= 2*distanse+1 ; j++ ) {
            	for(int k =1; k <= 2*distanse+1 ; k++ ) {
            		//getServer().broadcastMessage(loc.getX() + " " + loc.getY() + " " + loc.getZ());
            		//getServer().broadcastMessage(loc.getBlock().getType().toString());
                	if(loc.getBlock().getType() == material) {
                		//getServer().broadcastMessage("Latern: " + loc);
                    	temp.add(loc.getBlock().getLocation());
                	}
            		loc.setX(loc.getX()+1);
            	}
            	loc.setX(p.getLocation().getBlock().getX()-distanse);
            	loc.setY(loc.getY()+1);
            }
            loc.setY(p.getLocation().getBlock().getY()-distanse);
        	loc.setZ(loc.getZ()+1);
        }
        for(int i = 0; i<temp.size(); i++) {
        	dis.add(temp.get(i).distance(p.getLocation()));
        }
        Collections.sort(dis);
        for(int i = 0; i<temp.size(); i++) {
        	if(temp.get(i).distance(p.getLocation()) == dis.get(0).doubleValue()) {
        		//p.sendMessage(temp.get(i) + "");
        		temp.get(i).add(0.5, 0, 0.5);
        		return temp.get(i);
        	}
        }
        //p.sendMessage("Temp :" + temp);//For debug
        //p.sendMessage("Dis :" + dis);//For debug
        //p.spawnParticle(Particle.BLOCK_CRACK, new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()), 100, blood.createBlockData());
        return null;
	}

	
	public void GenerateCombinationsCodeFromeGame(Player p, Material material, int times, int combNum, int offsetOfoffset) {
		int x = p.getLocation().getBlockX();
		int y = p.getLocation().getBlockY()-1;
		int z = p.getLocation().getBlockZ();
		int offset = 0;
		int rangeX = 4; //half, rangeX*2+1 is real range
		int rangeY = 4;
		int[][][] AllCombinations = new int[8][8][20];
		String s = "\n";
		if(combNum == 0) {
			combNum = 1;
		}
		if(offsetOfoffset == 0) {
			offsetOfoffset = 4;
		}
		//int[] Layers = new int[times];
		ArrayList<Integer> Layers = new ArrayList<>();
		Integer alreadyEmpty = 0;
		int lastY = y;
		Integer delta = 0;
		Integer height = 0;
		boolean toBreak = false;
		for (int i = 0; i < times; i++) {
			for (int j = -4; j <= rangeX; j++) {
				for (int k = -4; k <= rangeY; k++) {
					//AllCombinations[j+2][k+2][times] = isBlockTypeToZeroOne(new Location(p.getWorld(), x+j, y-offset, z+k).getBlock(), Material.COBWEB);
					s += "AllCobwebCombinations"+combNum+"["+(j+4)+"]["+(k+4)+"]["+i+"] = " + isBlockTypeToZeroOne(new Location(p.getWorld(), (x+j), (y-offset), (z+k)).getBlock(), material) + ";\n";
				}
			}
			s+= "//\n";
			//AllCobwebCombinations[0][0][times] = isBlockTypeToZeroOne(new Location(p.getWorld(), x-2, y-offset, z-2).getBlock(), Material.COBWEB);
			//offset+=offsetOfoffset;
			offset+=offsetOfoffset;
		}
		p.sendMessage(s);
		log.info(s);
	}
	
	public int isBlockTypeToZeroOne(Block b, Material type) {
		if(b.getType().equals(type)) {
			return(1);
		}
		else {
			return(0);
		}
	}
	
	public void StringsToCode(Player p, String[] args) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
		if(args.length<1) {
			GenerateCombinationsCodeFromeGame(p, Material.TRIPWIRE, 10, 1, 4);
			p.sendMessage(ChatColor.YELLOW + "[WARNING] " + ChatColor.WHITE + "Used default layers count (10)");
		}
		if(args.length>=1) {
			try{
				int num = Integer.parseInt(args[0]);
				int num2 = 0;
				int num3 = 0;
				// is an integer!
				if(args.length>=2) {
					num2 = Integer.parseInt(args[1]);
				}
				if(args.length>=3) {
					num3 = Integer.parseInt(args[2]);
				}
				GenerateCombinationsCodeFromeGame(p, Material.TRIPWIRE, num, num2, num3);
				p.sendMessage(ChatColor.GREEN + "[SUCCESSFUL] " + ChatColor.WHITE + "Done.");
			} catch (NumberFormatException e) {
				// not an integer!
				p.sendMessage(ChatColor.RED + "[ISSUE] " + ChatColor.WHITE + "Unable to understand the number format");
			}
		}
		}
	}
	
	public void MurderMysteryStartGame(Player p, MinigameArena minigameArena, MapInfo Mapinfo) {
	    for(Entity entity : p.getWorld().getEntities()){
	        if(entity instanceof ArmorStand) {
	        	entity.remove();
	        	
	        }
	    }
		Location LocFromDataBase = Mapinfo.Location;
		Location LocToTeleport = new Location(p.getWorld(), LocFromDataBase.getX(), LocFromDataBase.getY(), LocFromDataBase.getZ());
		p.teleport(LocToTeleport);
		p.setCustomNameVisible(false);
		p.getWorld().setTime(Mapinfo.WorldTime);
		int NumberOfMurderers = minigameArena.NumberOfMurderers;
		int NumberOfDetectives = minigameArena.NumberOfDetectives;
		ArrayList<Player> AllPlayers = new ArrayList<>();
		int PlayersTotal = AllPlayers.size();
		ArrayList<Player> PlayersForRoles = AllPlayers;
		ArrayList<Player> Murderers = new ArrayList<>();
		ArrayList<Player> Detectives = new ArrayList<>();
		ArrayList<Player> Innocents = new ArrayList<>();
		if(minigameArena.DetectivesList.isEmpty()) {
		getServer().getOnlinePlayers().forEach(player -> {
			if(player.getWorld().equals(p.getWorld())) {
				AllPlayers.add(player);
			}
		});
		/*Murderers.add(getServer().getPlayer("Greenem")); // ДЛЯ ТЕСТОВ, НЕ ЗАБУДЬ ВЫРУБ�?ТЬ, ВАНЯ
		for(int i = 0; i < PlayersForRoles.size(); i++) {
			if(PlayersForRoles.get(i).equals("Greenem")) {
				PlayersForRoles.remove(i);
			}
		}*/
		//log.info("AllPlayers: " + AllPlayers);
		for(int i = 0; i < NumberOfMurderers; i++) {
			if(PlayersForRoles.size() > 0) {
				int num = random.nextInt(PlayersForRoles.size());
				Murderers.add(PlayersForRoles.get(num));
				PlayersForRoles.remove(num);
			}
		}
		for(int i = 0; i < NumberOfDetectives; i++) {
			if(PlayersForRoles.size() > 0) {
				int num = random.nextInt(PlayersForRoles.size());
				Detectives.add(PlayersForRoles.get(num));
				PlayersForRoles.remove(num);
			}
		}
		for(int i = 0; i < PlayersForRoles.size(); i++) {
			if(PlayersForRoles.size() > 0) {
				Innocents.add(PlayersForRoles.get(i));
				PlayersForRoles.remove(i);
			}
		}
		minigameArena.CopyedDetectivesList = (ArrayList<Player>) minigameArena.DetectivesList.clone();
		minigameArena.CopyedInnocentsList = (ArrayList<Player>) minigameArena.InnocentsList.clone();
		log.info("Murderers: " + Murderers);
		log.info("Detectives: " + Detectives);
		log.info("Innocents: " + Innocents);
		
		minigameArena.MurderersList = Murderers;
		minigameArena.DetectivesList = Detectives;
		minigameArena.InnocentsList = Innocents;
		minigameArena.PlayersTotal = PlayersTotal;
		minigameArena.AllPlayers = AllPlayers;
		}
		else {
			Murderers = minigameArena.MurderersList;
			Detectives = minigameArena.DetectivesList;
			Innocents = minigameArena.InnocentsList;
			PlayersTotal = minigameArena.PlayersTotal;
		}
		MurderMysteryItemsClear(p);
		if(Murderers.contains(p)) {
			p.sendTitle(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "You are a " + ChatColor.RED + "MURDERER", "", 5, 30, 10);
			p.getInventory().setHeldItemSlot(4);
			AddItemStack(p, new ItemStack(Material.IRON_SWORD, 1), 0);
		}
		else if(Detectives.contains(p)) {
			p.sendTitle(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "You are a " + ChatColor.YELLOW + "DETECTIVE", "", 5, 30, 10);
			p.getInventory().setHeldItemSlot(4);
			AddItemStack(p, new ItemStack(Material.BOW, 1), 0);
			AddItemStack(p, new ItemStack(Material.ARROW, 1), 9);
		}
		else if(Innocents.contains(p)) {
			p.sendTitle(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "You are a " + ChatColor.GREEN + "INNOCENT", "", 5, 30, 10);
		}
		/*for(int i = 0; i < Murderers.size(); i++) {
			Murderers.get(i).sendTitle(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "You are a " + ChatColor.RED + "MURDERER", "", 5, 30, 10);
		}
		for(int i = 0; i < Detectives.size(); i++) {
			Detectives.get(i).sendTitle(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "You are a " + ChatColor.YELLOW + "DETECTIVE", "", 5, 30, 10);
		}
		for(int i = 0; i < Innocents.size(); i++) {
			Innocents.get(i).sendTitle(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "You are a " + ChatColor.GREEN + "INNOCENT", "", 5, 30, 10);
		}*/
	}
	
	public MinigameArena GetArena (Player p) {
		String WorldName = p.getWorld().getName();
		String MinigameName = GetMinigameName(WorldName);
		Map <String, MinigameArena> Data = GetMinigameData(WorldName);
		for(int i = 0; i < Data.size(); i++) {
	    	if(MinigameName == Data.get(MinigameName + (i+1)).ArenaType) {
	    		return Data.get(MinigameName + (i+1));
	    	}
		}
		return null;
	}

	public void ReviveCommand(Player p, String[] args) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
		if(args.length==0) {
			RevivePlayer(p);
		}
		else if(args.length==1) {
			if(getServer().getPlayer(args[0])!=null) {
				RevivePlayer(getServer().getPlayer(args[0]));
			}
			else {
				p.sendMessage(ChatColor.RED + "[ISSUE] " + ChatColor.WHITE + "Such player was not found.");
			}
		}
		else {
			p.sendMessage(ChatColor.RED + "[ISSUE] " + ChatColor.WHITE + "Wrong arguments");
		}
		}
	}
	
	public void RevivePlayer(Player p) {
		MinigameVanishOff(p); //игрок уже потерял свою "роль", не хочу заморачиваться на сохранение этих списков мёрдеров, 
		if(p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) p.setFlying(false); //это просто экстренная команда на случай бага на время тестов
	}
	
//	public void InventoryCloseEvent(InventoryCloseEvent e) {
//		if(e.getPlayer() instanceof Player) {
//			Player p = (Player) e.getPlayer();
//			if(AdminPlayersVanished.contains(p.getName())) {
//				p.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(99999, 1));
//			}
//		}
//	}
	
	public void RemoveMaterial(Player p, Material material) {
		if(p.getInventory().contains(material)) {
			p.getInventory().remove(material);
		}
	}
	
	public void MurderMysteryItemsClear(Player p) {
		RemoveMaterial(p, Material.PAPER);
		RemoveMaterial(p, Material.EMERALD);
		RemoveMaterial(p, Material.REDSTONE);
		RemoveMaterial(p, Material.BOW);
		RemoveMaterial(p, Material.IRON_SWORD);
		RemoveMaterial(p, Material.ARROW);
		RemoveMaterial(p, Material.SPECTRAL_ARROW);
		RemoveMaterial(p, Material.COMPASS);
	}

	@EventHandler
	public void MurderMysteryPickingUpBow(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		//log.info("11");
		if(p.getWorld().getName().startsWith("murdermystery")) {
			MinigameArena arena = GetArena(p);
			//log.info(arena + "");
			if(arena != null) {
				if(arena.InnocentsList.contains(p) && !arena.DetectivesList.contains(p)) {
					for(int i = 0; i < arena.DroppedBow.size(); i++) {
						if(arena.DroppedBow.get(i).getLocation().distance(p.getLocation()) <= 1) {
							arena.DetectivesList.add(p);
							arena.HeroesList.add(p);
							arena.InnocentsList.remove(p);
							ArmorStand stand = arena.DroppedBow.get(i);
							arena.DroppedBow.remove(i);
							stand.remove();
							AddItemStack(p, new ItemStack(Material.BOW, 1), 0);
							AddItemStack(p, new ItemStack(Material.ARROW, 1), 9);
							for (Player inn : arena.InnocentsList) {
					        	RemoveMaterial(inn, Material.COMPASS);
					        }
							RemoveMaterial(p, Material.COMPASS);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void DisableArmorStandIntaract (PlayerInteractAtEntityEvent e) {
		if(e.getPlayer().getWorld().getName().startsWith("murdermystery")) {
			if(e.getRightClicked() instanceof ArmorStand) {
				e.setCancelled(true);
				log.info("DisableArmorStandIntaract");
			}
		}
		if(e.getRightClicked() instanceof ArmorStand) {
			if(IfStringStartsWithArrayListEllement(e.getPlayer().getWorld().getName(), MapListWithArmorStandProtection)) {
				if(e.getPlayer().getGameMode().equals(GameMode.ADVENTURE) || e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	public void SpawnScaffolding(Player p, Location loc) {
		if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
			loc.getBlock().setType(Material.SCAFFOLDING, false);
			//loc.getBlock().setBlockData(, false);
		}
	}
	
	/*@EventHandler
	public void PaintingRemoved(EntityDeathEvent e) {
		log.info(e.getEntity().toString());
	}*/
	
	public void LoadFiles() throws FileNotFoundException {
		
	}
	
	public void RegisterPlayer(Player p, String[] args)  {
		if (args.length < 2) {
			p.sendMessage(ChatColor.RED + "Incorrect command using, Try " + ChatColor.GOLD + "/register <password> <password>" + ChatColor.RED + ".");
		} else if (args[0].equals(args[1])) {
			FileOutputStream fos = null;
			props.clear();
			FileReader reader = null;
			try {
				File f = new File(authDirectory, p.getName() + ".properties");
				if (f.exists()) {
					try {
						reader = new FileReader(f);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					props.load(reader);
					reader.close();
				}
				if (props.getProperty("password") == null) {
					props.setProperty("password", args[0]);
					props.setProperty("lastRealLoginTime", Long.toString(Calendar.getInstance().getTimeInMillis()));
					String s = p.getAddress().toString().split("/")[1].split(":")[0];
					log.info(s);
					if (s.endsWith("\\")) s.substring(0, s.length() - 1);
					props.setProperty("lastLoginAdress", s);
					props.setProperty("lastLoginPort", Integer.toString(p.getAddress().getPort()));
					try {
						FileOutputStream out = new FileOutputStream(f);
						try {
							fos = new FileOutputStream(f);
							props.store(fos, "");
							fos.close();
							p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GREEN + "Now you registered!", ChatColor.ITALIC + "" + ChatColor.DARK_GREEN + "Welcome! Don't forget the password ;)", 5, 40, 30);
							if (AuthPlayers.contains(p)) AuthPlayers.remove(p);
							GiveOpIfNeeds(p);
							PrepareJoinedPlayer(p);
							MakePlayerProfileUpToDate(p);
							MakePlayerAuthUpToDate(p);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					p.sendMessage(ChatColor.RED + "This account is already registered. You should use " + ChatColor.GOLD
							+ "/login <password>" + ChatColor.RED + ".");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			p.sendMessage(ChatColor.RED + "The password which is first doesn't match the second one.");
		}
	}
	
	public void LoginPlayer(Player p, String[] args) {
		if(args.length<1) {
			p.sendMessage(ChatColor.RED + "Incorrect command using, Try " + ChatColor.GOLD + "/login <password>"+ChatColor.RED+".");
			return;
		}
		FileOutputStream fos = null;
    	props.clear();
    	FileReader reader = null;
		try {
			File f = new File(authDirectory, p.getName()+".properties");
			if (f.exists()) {
				try {
					reader = new FileReader(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				props.load(reader);
				reader.close();
			}
			if(props.getProperty("password")!=null) {
				if(props.getProperty("password").equals(args[0])) {
					//and here
					props.setProperty("lastRealLoginTime", Long.toString(Calendar.getInstance().getTimeInMillis()));
					String s = p.getAddress().toString().split("/")[1].split(":")[0];
					if(s.endsWith("\\")) s.substring(0, s.length()-1);
					props.setProperty("lastLoginAdress", s);
					props.setProperty("lastLoginPort", Integer.toString(p.getAddress().getPort()));
					try {
						fos = new FileOutputStream(f);
						props.store(fos, "");
						fos.close();
						p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GREEN + "You logged in!", ChatColor.ITALIC + "" + ChatColor.GRAY + "Welcome back!", 5, 40, 30);
						if(AuthPlayers.contains(p)) AuthPlayers.remove(p);
						GiveOpIfNeeds(p);
						PrepareJoinedPlayer(p);
						MakePlayerProfileUpToDate(p);
						MakePlayerAuthUpToDate(p);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				else {
					p.sendMessage(ChatColor.RED + "Incorrect password.");
					log.info(AuthAttempts.get(p)+"");
					if(AuthAttempts.get(p)!=null && AuthAttempts.get(p).intValue()+1>=5) {
						p.kickPlayer("To many attempts!");
					}
					if(AuthAttempts.containsKey(p)) {
						AuthAttempts.put(p, AuthAttempts.get(p).intValue()+1);
					}
					else {
						AuthAttempts.put(p, 1);
					}
				}
			}
			else {
				p.sendMessage(ChatColor.RED + "This account is NOT registered. You should use " + ChatColor.GOLD + "/register <password> <password>"+ChatColor.RED+".");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void PrepareJoinedPlayer(Player p) {
		for (Player p1 : getServer().getOnlinePlayers()) {
          p1.showPlayer(p);
          if(!AdminPlayersVanished.contains(p1)) p.showPlayer(p1);
		}
		AuthAttempts.remove(p);
		p.setCanPickupItems(true);
		//p.setCollidable(false);
		//p.spigot().setCollidesWithEntities(false);
		try {
			loadInventory(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tpToSpawn(p);
		for (Player player : p.getWorld().getPlayers()) {
			player.sendMessage(ChatColor.WHITE + p.getName() + " joined the server!");
		}
		//evt.getPlayer().getInventory().clear();
		p.setLevel(0);
		p.setExp(0);
		new BukkitRunnable() {
    		@Override
    		public void run() {
    			p.setGameMode(GameMode.SURVIVAL);
    		}
    	}.runTaskLater(this, 1);
	}
	
	@EventHandler
	public void PlayerPreJoinE (AsyncPlayerPreLoginEvent e) {
		FileReader reader = null;
		props.clear();
		File f = new File(authDirectory, e.getName() + ".properties");
		if (f.exists()) {
			try {
				reader = new FileReader(f);
				try {
					props.load(reader);
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if(getServer().getPlayer(e.getName()) != null) { //если такой игрок уже онлайн
			if(props.getProperty("lastLoginAdress")!=null) {//если он зареган
				String s = e.getAddress().toString().split("/")[1].split(":")[0];
				if(s.endsWith("\\")) s.substring(0, s.length()-1);
				log.info(ChatColor.AQUA + "s = " + s + " (AsyncPlayerPreLoginEvent)");
				log.info(ChatColor.AQUA + "lastLoginAdress = " + props.getProperty("lastLoginAdress") + " (AsyncPlayerPreLoginEvent)");
				log.info(ChatColor.AQUA + "" + props.getProperty("lastLoginAdress").equals(s));
				if(!props.getProperty("lastLoginAdress").equals(s)) {
					//если уже играющий игрок под IP, который последний раз правильно ввёл пароль
					e.disallow(Result.KICK_OTHER, ChatColor.RED + "This player is already on the server");
				}
			}
			else {
				//он не загреган, но всё равно выкидывать уже играющего (в лобби регистрации) нельзя
				e.disallow(Result.KICK_OTHER, ChatColor.RED + "This player is already on the server");
			}
		}
	}
	
    public void saveInventory (Player p) throws IOException {
    		YamlConfiguration c = new YamlConfiguration();
    		c.set("inventory.armor", p.getInventory().getArmorContents());
    		c.set("inventory.content", p.getInventory().getContents());
    		c.save(new File(inventoriesDirectory, p.getName()+".yml"));
    }
    
    public void loadInventory(Player p) throws IOException {
    	//File file = new File(inventoriesDirectory, p.getName()+".yml");
        YamlConfiguration c = YamlConfiguration.loadConfiguration(new File(inventoriesDirectory, p.getName()+".yml"));
        ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
        p.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
	}
       
    public void CreateAccountSettingsInv(Player p, Inventory inv) {
    	ItemStack LimeStainedPlane = createGuiItem(Material.LIME_STAINED_GLASS_PANE, " ", " ", "");
		ItemStack GreenStainedPlane = createGuiItem(Material.GREEN_STAINED_GLASS_PANE, " ", " ", "");
		ItemStack GrayStainedPlane = createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " ", " ", "");
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, createGuiItem(Material.VINE, " ", "", ""));
		}
		inv.setItem(10, GreenStainedPlane);
		inv.setItem(11, createGuiItem(Material.NETHERITE_CHESTPLATE, ChatColor.WHITE + "Ask for password every time", ChatColor.GRAY + "Whether to do it EVERY time", ""));
		inv.setItem(12, LimeStainedPlane);
		inv.setItem(13, createGuiItem(Material.LEGACY_BOOK_AND_QUILL, ChatColor.WHITE + "Add e-mail adress", ChatColor.GRAY + "", ""));
		inv.setItem(14, LimeStainedPlane);
		inv.setItem(15, createGuiItem(Material.NAME_TAG, ChatColor.WHITE + "Change password", ChatColor.GRAY + "Click to change password", ""));
		inv.setItem(16, GreenStainedPlane);
		MapAccountSettingsInvs.put(p.getName(), inv);
    }
    
    @EventHandler
    public void RmbEvent (PlayerInteractEvent e) {
    	Player p = e.getPlayer();
    	if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getType() == Material.CRYING_OBSIDIAN)){
        	AccountSettingsInventory(p);
        }
    }
    
    public void PullToVent (Player p, Location vent) {
    	boolean ToDown = true;
    	if(p.getLocation().getY()<vent.getY()) ToDown = false;
    	Location loc2 = vent;
    	loc2.setPitch(p.getLocation().getPitch());
    	loc2.setYaw(p.getLocation().getYaw());
    	if (ToDown==true) loc2.setY(loc2.getY()+1);
    	else loc2.setY(loc2.getY());
    	p.teleport(loc2);
    	String world = p.getWorld().getName();
    	for (Integer i = 1; i <= 15; i++) {
        	final int iCopy = i;
        	final boolean ToDownCopy = ToDown;
        	new BukkitRunnable() {
        		@Override
        		public void run() {
        			if(!p.getWorld().getName().equals(world) || iCopy>=15) {
        				cancel();
        			}
        			if (ToDownCopy==true) loc2.setY(loc2.getY()-0.02);
        			else loc2.setY(loc2.getY()+0.02);
                    p.setVelocity(new Vector(0, 0.2, 0));
                    p.teleport(loc2);
        		}
        	}.runTaskLater(this, 1);
        }
    }
    
    @EventHandler
    public void AmongUsLMB (PlayerInteractEvent e) {
    	//log.info(""+e.isCancelled());
    	if(e.getPlayer().getItemInHand().getType().equals(Material.GLOWSTONE_DUST) && (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))) {
    		Player p = e.getPlayer();
    		Location loc = IsBlockNearby(p, Material.SCAFFOLDING, 2);
			if(loc!=null) {
				PullToVent(p, loc);
			}
			else { //No vent found
				//p.sendMessage("No vent found.");
			}
    	}
    }
    
    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent e) {
    	Player p = e.getPlayer();
    	log.info("beggining of PlayerBedEnterEvent");
    	if(p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)) {
    		if(p.getWorld().getName().startsWith("murdermystery")) {
    			e.setCancelled(true);
    			log.info("PlayerBedEnterEvent");
    		}
    	}
    }

	public void CloseOropenDoor(Player p) {
		Location loc = new Location(p.getWorld(), 87, 111, 14);
		int width = 4;
		int height = 5;
		int face = 2; // синяя стрелка вперёд на стену - 1, иначе 2
		String world = p.getWorld().getName();
		for (int xorz = 1; xorz <= width+1; xorz++) {
			for (int y = 0; y < height; y++) {
				for (int realXorZ = 0; realXorZ < width + 1; realXorZ++) {
					final int i = xorz;
					final int j = realXorZ;
					final int yCopy = y;
					new BukkitRunnable() {
						@Override
						public void run() {
							if (!p.getWorld().getName().equals(world) || !(j <= width+1)) {
								cancel();
							}

							log.info(i + "");
							Material[][] blocks = new Material[6][6];
							blocks = AmongUsWall(i);
							Block b1 = loc.getBlock();
							Block b2 = loc.getBlock();
							if (face == 1) {
								b1 = new Location(p.getWorld(), loc.getX() - width + j, loc.getY() + yCopy, loc.getZ()).getBlock();
								b2 = new Location(p.getWorld(), loc.getX() + width - j, loc.getY() + yCopy, loc.getZ()).getBlock();
							}
							if (face == 2) {
								b1 = new Location(p.getWorld(), loc.getX(), loc.getY() + yCopy, loc.getZ() - width + j).getBlock();
								b2 = new Location(p.getWorld(), loc.getX(), loc.getY() + yCopy, loc.getZ() + width - j).getBlock();
							}
							//log.info(blocks[j + 1][yCopy + 1] + "");
							if (blocks[j + 1][yCopy + 1] != null) {
								b1.setType(blocks[j + 1][yCopy + 1]);
								b2.setType(blocks[j + 1][yCopy + 1]);
								//if(b1 instanceof Wall) {
									log.info("it is wall");
									Wall wall = (Wall) b1.getLocation().getBlock();
									wall.setUp(false);
									wall = (Wall) b2;
									wall.setUp(false);
								//}
							} else {
								b1.setType(Material.AIR);
								b2.setType(Material.AIR);
							}
						}
					}.runTaskLater(this, 1*i);
				}
			}
		}
	}
        
	public Material[][] AmongUsWall(int i){
    	Material[][] blocks = new Material[6][6];
    	//for(int i = 0; i < )
    	if (i==1) {
    		blocks[1][1] = Material.ANDESITE_WALL;
    		blocks[1][2] = Material.ANDESITE_WALL;
    		blocks[1][3] = Material.ANDESITE_WALL;
    		blocks[1][4] = Material.ANDESITE_WALL;
    		blocks[1][5] = Material.ANDESITE_WALL;
    	}
    	else if (i==2) {
    		blocks[1][1] = Material.ANDESITE_WALL;
    		blocks[1][2] = Material.DIORITE_WALL;
    		blocks[1][3] = Material.ANDESITE_WALL;
    		blocks[1][4] = Material.DIORITE_WALL;
    		blocks[1][5] = Material.ANDESITE_WALL;
    		
    		blocks[2][1] = Material.ANDESITE_WALL;
    		blocks[2][2] = Material.ANDESITE_WALL;
    		blocks[2][3] = Material.ANDESITE_WALL;
    		blocks[2][4] = Material.ANDESITE_WALL;
    		blocks[2][5] = Material.ANDESITE_WALL;
    	}
    	else if (i==3) {
    		blocks[1][1] = Material.ANDESITE_WALL;
    		blocks[1][2] = Material.DIORITE_WALL;
    		blocks[1][3] = Material.ANDESITE_WALL;
    		blocks[1][4] = Material.DIORITE_WALL;
    		blocks[1][5] = Material.ANDESITE_WALL;
    		
    		blocks[2][1] = Material.ANDESITE_WALL;
    		blocks[2][2] = Material.DIORITE_WALL;
    		blocks[2][3] = Material.ANDESITE_WALL;
    		blocks[2][4] = Material.DIORITE_WALL;
    		blocks[2][5] = Material.ANDESITE_WALL;
    		
    		blocks[3][1] = Material.ANDESITE_WALL;
    		blocks[3][2] = Material.ANDESITE_WALL;
    		blocks[3][3] = Material.ANDESITE_WALL;
    		blocks[3][4] = Material.ANDESITE_WALL;
    		blocks[3][5] = Material.ANDESITE_WALL;
    	}
    	else if (i==4) {
    		blocks[1][1] = Material.ANDESITE_WALL;
    		blocks[1][2] = Material.DIORITE_WALL;
    		blocks[1][3] = Material.ANDESITE_WALL;
    		blocks[1][4] = Material.DIORITE_WALL;
    		blocks[1][5] = Material.ANDESITE_WALL;
    		
    		blocks[2][1] = Material.ANDESITE_WALL;
    		blocks[2][2] = Material.DIORITE_WALL;
    		blocks[2][3] = Material.ANDESITE_WALL;
    		blocks[2][4] = Material.DIORITE_WALL;
    		blocks[2][5] = Material.ANDESITE_WALL;
    		
    		blocks[3][1] = Material.ANDESITE_WALL;
    		blocks[3][2] = Material.DIORITE_WALL;
    		blocks[3][3] = Material.ANDESITE_WALL;
    		blocks[3][4] = Material.DIORITE_WALL;
    		blocks[3][5] = Material.ANDESITE_WALL;
    		
    		blocks[4][1] = Material.ANDESITE_WALL;
    		blocks[4][2] = Material.ANDESITE_WALL;
    		blocks[4][3] = Material.ANDESITE_WALL;
    		blocks[4][4] = Material.ANDESITE_WALL;
    		blocks[4][5] = Material.ANDESITE_WALL;
    	}
    	else if (i==5) {
    		blocks[1][1] = Material.DIORITE_WALL;
    		blocks[1][2] = Material.ANDESITE_WALL;
    		blocks[1][3] = Material.ANDESITE_WALL;
    		blocks[1][4] = Material.ANDESITE_WALL;
    		blocks[1][5] = Material.DIORITE_WALL;
    		
    		blocks[2][1] = Material.ANDESITE_WALL;
    		blocks[2][2] = Material.DIORITE_WALL;
    		blocks[2][3] = Material.ANDESITE_WALL;
    		blocks[2][4] = Material.DIORITE_WALL;
    		blocks[2][5] = Material.ANDESITE_WALL;
    		
    		blocks[3][1] = Material.ANDESITE_WALL;
    		blocks[3][2] = Material.DIORITE_WALL;
    		blocks[3][3] = Material.ANDESITE_WALL;
    		blocks[3][4] = Material.DIORITE_WALL;
    		blocks[3][5] = Material.ANDESITE_WALL;
    		
    		blocks[4][1] = Material.ANDESITE_WALL;
    		blocks[4][2] = Material.DIORITE_WALL;
    		blocks[4][3] = Material.ANDESITE_WALL;
    		blocks[4][4] = Material.DIORITE_WALL;
    		blocks[4][5] = Material.ANDESITE_WALL;
    		
    		blocks[5][1] = Material.ANDESITE_WALL;
    		blocks[5][2] = Material.ANDESITE_WALL;
    		blocks[5][3] = Material.ANDESITE_WALL;
    		blocks[5][4] = Material.ANDESITE_WALL;
    		blocks[5][5] = Material.ANDESITE_WALL;
    	}
    	return blocks;
    }

    public void TestCommand(Player p) {
    	if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
    		GenerateStings(new Location(Bukkit.getWorld("featherfalling1"), -30, 96, 6), 1, 10, Material.TRIPWIRE, 4);
    	/*FileOutputStream fos = null;
    	props.clear();
    	FileReader reader = null;
		try {
			File f = new File(profilesDirectory, p.getName()+".properties");
			if (f.exists()) {
				try {
					reader = new FileReader(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				props.load(reader);
				reader.close();
			}
		    log.info(props.toString());
		    log.info(f.exists() + "");
		    if(props.getProperty("money")==null) props.setProperty("money", "0");
		    else props.setProperty("money", Integer.toString(Integer.parseInt(props.getProperty("money")) + 1));
		    fos = new FileOutputStream(f);
			props.store(fos, "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
			  try {
				fos.close();
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
			}
		}*/
    	}
    }
    
    public void MakePlayerProfileUpToDate(Player p) {
    	FileOutputStream fos = null;
    	props.clear();
    	FileReader reader = null;
		try {
			File f = new File(profilesDirectory, p.getName()+".properties");
			if (f.exists()) {
				try {
					reader = new FileReader(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				props.load(reader);
				reader.close();
			}
		    if(props.getProperty("money")==null) props.setProperty("money", "0");
		    if(props.getProperty("nickname")==null) props.setProperty("nickname", p.getName());
		    if(props.getProperty("registeringDate")==null) props.setProperty("registeringDate", Long.toString(Calendar.getInstance().getTimeInMillis()));
		    if(props.getProperty("lastLoginTime")==null) props.setProperty("lastLoginTime", Long.toString(Calendar.getInstance().getTimeInMillis()));
		    if(props.getProperty("mainTag")==null) props.setProperty("mainTag", "Player");
		    if(props.getProperty("Tags")==null) props.setProperty("Tags", "Player");
		    if(props.getProperty("isOP")==null) props.setProperty("isOP", "0");
		    //if(props.getProperty("askForPasswordEveryTime")==null) props.setProperty("ask for password every time", "0");
		    fos = new FileOutputStream(f);
			props.store(fos, "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
			  try {
				fos.close();
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
			}
		}
    }
    
    public void MakePlayerAuthUpToDate(Player p) {
    	if(p==null) return;
    	FileOutputStream fos = null;
    	props.clear();
    	FileReader reader = null;
		try {
			File f = new File(authDirectory, p.getName()+".properties");
			if (f.exists()) {
				try {
					reader = new FileReader(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				props.load(reader);
				reader.close();
			}
		    if(props.getProperty("password")==null) log.info(ChatColor.RED + "[ISSUE] " + p.getName() + "'s password does not exist");
		    if(props.getProperty("lastLoginAdress")==null) log.info(ChatColor.RED + "[ISSUE] " + p.getName() + "'s lastLoginAdress does not exist");
		    if(props.getProperty("lastLoginPort")==null) log.info(ChatColor.RED + "[ISSUE] " + p.getName() + "'s lastLoginPort does not exist");
		    if(props.getProperty("lastRealLoginTime")==null) log.info(ChatColor.RED + "[ISSUE] " + p.getName() + "'s lastRealLoginTime does not exist");
		    if(props.getProperty("askForPasswordEveryTime")==null) props.setProperty("askForPasswordEveryTime", "0");
		    fos = new FileOutputStream(f);
			props.store(fos, "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
			  try {
				fos.close();
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
			}
		}
    }
    
    @EventHandler
    public void ChangeProfileOp(PlayerCommandPreprocessEvent e) {
    	if(e.getPlayer().isOp()) {
    	int Op = 0;
    	if (e.getMessage().contains("/op")) {
    		Op = 1;
    	}
    	if (e.getMessage().contains("/deop")) {
    		Op = 2;
    	}
    	if(Op!=0) {
    		//log.info(e.getMessage().substring(4));
    		String p = "";
			if(Op==1) p = e.getMessage().substring(4);
			if(Op==2) p = e.getMessage().substring(6);
    		if(p != "") {
    			if(e.getPlayer().isOp()) {
    				//Player p = e.get
    				FileOutputStream fos = null;
    				props.clear();
    				FileReader reader = null;
    				//log.info(props.toString());
    				try {
    					File f = new File(profilesDirectory, p+".properties");
    					if (f.exists()) {
    						try {
    							reader = new FileReader(f);
    						} catch (FileNotFoundException e1) {
    							e1.printStackTrace();
    						}
    						props.load(reader);
    						reader.close();
    					}
    					if(props.size() == 1 && Op == 2)
    					{
    						f.delete();
    						return;
    					}
    					if(Op==1) props.setProperty("isOP", "1");
    					if(Op==2) props.setProperty("isOP", "0");
    					//log.info(props.toString());
    					//if(props.getProperty("askForPasswordEveryTime")==null) props.setProperty("ask for password every time", "0");
    					fos = new FileOutputStream(f);
    					props.store(fos, "");
    					//log.info("done");
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				} finally {
    					if(fos != null) {
    						try {
    							fos.close();
    						} catch (IOException e2) {
    							e2.printStackTrace();
    						}
    					}
    				}
    			}
    		}
    	}
    	}
    }

    @EventHandler
    public void DisableAuthMessages(PlayerChatEvent e) {
    	if(e.getPlayer().getWorld().getName().equals("auth") && AuthPlayers.contains(e.getPlayer())) {
    		if(!e.getMessage().startsWith("/")) {
    			e.setCancelled(true);
    		}
    	}
    }
    
    public void GiveOpIfNeeds(Player p) {
    	File f2 = new File(profilesDirectory, p.getName() + ".properties");
		FileReader reader = null;
		props.clear();
		if (f2.exists()) {
			try {
				reader = new FileReader(f2);
				try {
					props.load(reader);
					reader.close();
					if(props.getProperty("isOP")!=null && props.getProperty("isOP").equals("1")) {
						p.setOp(true);
					}
					props.setProperty("lastLoginTime", Long.toString(Calendar.getInstance().getTimeInMillis()));
					FileOutputStream fos = new FileOutputStream(f2);
					props.store(fos, "");
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    public int ChangeMoney (Player p, int dMoney) {
    	File f = new File(profilesDirectory, p.getName() + ".properties");
		FileReader reader = null;
		props.clear();
		int result = -1;
		if (f.exists()) {
			try {
				reader = new FileReader(f);
				try {
					props.load(reader);
					reader.close();
					props.setProperty("money", Integer.toString(Integer.parseInt(props.getProperty("money"))+dMoney));
					FileOutputStream fos = new FileOutputStream(f);
					result = Integer.parseInt(props.getProperty("money"));
					props.store(fos, "");
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return result;
    }
    
    public Long getLastRealLogin (Player p) {
    	File f = new File(authDirectory, p.getName() + ".properties");
		FileReader reader = null;
		props.clear();
		Long result = (long) -1;
		if (f.exists()) {
			try {
				reader = new FileReader(f);
				props.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			result = Long.parseLong(props.getProperty("lastRealLoginTime"));
		}
		return result;
    }
    
    public void InitializeTrialStaffPlayers () {
    	TrialStaffPlayers.add("TheSheerDarkness");
    }
    
    public void AuthStartCountdown(Player p, int MaxSeconds) {
    	Long timeJoined = Calendar.getInstance().getTimeInMillis();
    	new BukkitRunnable() {
    		@Override
    		public void run() {
    			if(p==null || !p.isOnline() || (!AuthPlayers.contains(p))) {
    				cancel();
    			}
    			else {
    				if(MaxSeconds - ((Calendar.getInstance().getTimeInMillis()-timeJoined)/1000) > 0){
    					float persent;
    					//log.info((Calendar.getInstance().getTimeInMillis()-timeJoined)/1000 + "");
						persent = (float) (MaxSeconds - (Calendar.getInstance().getTimeInMillis()-timeJoined)/1000) / MaxSeconds; 
						if(persent > 1 || persent < 0) persent = (float) 0.999;
						p.setLevel(Integer.parseInt(Long.toString(MaxSeconds - ((Calendar.getInstance().getTimeInMillis())-timeJoined)/1000)));
						p.setExp(persent);
    				}
    				else {
    					p.kickPlayer("You were not in time while entering password");
    				}
    			}
    		}
    	}.runTaskTimer(this, 0, 20);
    }
    
    public void TpToAuth (Player p) {
    	if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
    		Location loc = new Location(Bukkit.getWorld("auth"), 0.5, 37.5, 0.5);
    		loc.setYaw(135);
    		p.teleport(loc);
    		p.getWorld().setSpawnLocation(loc);
    		p.getWorld().setTime(1000);
    	}
    }
    
    public void BackupWorld(Player p) {
    	World world = p.getWorld();
    	String worldName = world.getName();
    	ArrayList<Player> wereThere = new ArrayList<>();
    	Long time = world.getTime();
    	if(p.getWorld().getName().startsWith("spawn")) {
    		for (Player player : world.getPlayers()) {
    			wereThere.add(player);
    			Location loc = new Location(Bukkit.getWorld("auth"), 0.5, 37.5, 0.5);
        		loc.setYaw(135);
        		player.teleport(loc);
        		p.getWorld().setTime(1000);
        		p.getWorld().setSpawnLocation(loc);
    		}
    	}
    	else {
    		for (Player player : world.getPlayers()) {
    			wereThere.add(player);
    			player.teleport(Bukkit.getWorld("spawn").getSpawnLocation());
    		}
    	}
    	Bukkit.unloadWorld(world.getName(), false);
    	getServer().createWorld(new WorldCreator(worldName));
    	for (Player player : wereThere) {
    		player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
    		Bukkit.getWorld(worldName).setTime(time);
    	}
    	/*getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				
			}
		}, 20);*/
    }
    
    public void SaveWorld(Player p) {
    	World world = p.getWorld();
    	world.save();
    }
    
    public void tpToNewSpawn (Player p) {
    	if(p.isOp() || TrialStaffPlayers.contains(p.getName())) {
    		Location loc = new Location(Bukkit.getWorld("spawnnew"), 0.5, 101.5, 0.5);
    		//loc.setYaw(180);
    		p.teleport(loc);
    		p.getWorld().setSpawnLocation(0, 101, 0);
    		p.getWorld().setTime(1000);
    	}
    }
    
    public void PacketsTests (Player p) {
    	
    	PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
    	
    	PacketPlayOutCamera packet = new PacketPlayOutCamera();
    	ArmorStand camera = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0, 3, 0), EntityType.ARMOR_STAND);
    	camera.setVisible(false);
    	ArmorStand before = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
    	before.setVisible(false);
    	Location loc = p.getLocation().clone();
    	packet.a = camera.getEntityId();
    	PlayerConnection connection1 = ((CraftPlayer) p).getHandle().playerConnection;
    	connection1.sendPacket(packet);
    	getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
    		@Override
			public void run() {
    			packet.a = before.getEntityId();
    			connection1.sendPacket(packet);
    			camera.remove();
    			before.remove();
    			
			}
    	}, 20);
    	PacketPlayOutBlockBreak packet1 = new PacketPlayOutBlockBreak();
    	Location loc1 = p.getLocation().clone();
    	loc1.add(0, -1, 0);
    	//packet
    }

    public org.apache.logging.log4j.core.Filter.Result filter(LogEventFactory e) {
		log.info("LogEvent");
		log.info(e.toString());
    	//return checkMessage(e.getMessage().getFormattedMessage());
		return checkMessage(e.toString());
	}
    
    public void publish(LogRecord record) {
    	log.info("LogRecord");
        String message = record.getMessage();
        if(checkMessage(message).equals(org.apache.logging.log4j.core.Filter.Result.DENY)) record.setLevel(Level.OFF);
    }
    
    public org.apache.logging.log4j.core.Filter.Result checkMessage(String message) {
    	log.info("Checking message");
		if (message.contains("issued server command: /")) {
			return org.apache.logging.log4j.core.Filter.Result.DENY;
			}
		return org.apache.logging.log4j.core.Filter.Result.NEUTRAL;
	}

    public void GenerateStings(Location startLoc, int level, int numOfLevels, Material material, int range){
    	int[][][] Combinations = GetCombinationsArray(level);
    	if(Combinations==null) return;
    	int x = startLoc.getBlockX();
		int y = startLoc.getBlockY()-1;
		int z = startLoc.getBlockZ();
		int offset = 0;
		int rangeX = range; //half, rangeX*2+1 is real range
		int rangeY = range;
		int[][][] AllCombinations = new int[5][5][20];
		int offsetOfoffset = 5;
		ArrayList<Integer> Layers = new ArrayList<>();
		Integer alreadyEmpty = 0;
		int lastY = y;
		Integer delta = 0;
		Integer height = 0;
		boolean toBreak = false;
		WorldTonnelRemoveMaterial(startLoc, 100, material, rangeY);
		for (int i = 0; i < numOfLevels; i++) {
			int[][] matrix = new int[rangeX*2+1][rangeY*2+1];
			for(int m1 = 0; m1<rangeX*2+1; m1++) {
				for(int m2 = 0; m2<rangeX*2+1; m2++) {
					matrix[m1][m2] = Combinations[m1][m2][i];
				}
			}
			int patrern = random.nextInt(4);
			if(patrern == 1) {
				matrix = rotateMatrixBy90DegreesRight(matrix);
			}
			if(patrern == 2) {
				matrix = rotateMatrixBy90DegreesLeft(matrix);
			}
			if(patrern == 3) {
				matrix = rotateMatrixBy180Degrees(matrix);
			}
			for (int j = 0; j < 2*rangeX+1; j++) {
				for (int k = 0; k < 2*rangeY+1; k++) {
					//if(Combinations[(j+4)][(k+4)][i] == 1) {
					Block b = new Location(startLoc.getWorld(), (x+j-4), (y-offset), (z+k+-4)).getBlock();
					if(matrix[j][k] == 1) {
						//log.info(new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().toString());
						//new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().setType(material, true);
						//new Location(startLoc.getWorld(), (x+j-4), (y-offset), (z+k+-4)).getBlock().setType(material, true);
						b.setType(material);
						b.getState().update(true, true);
						log.info(b.getState().getBlockData().toString());
					}
				}
			}
			offset+=offsetOfoffset;
		}
		WorldTonnelUpdateAir(startLoc, 50, rangeY);
		log.info("GenerateStings " + ChatColor.GREEN + "Done");
    }
    
    public void WorldTonnelRemoveMaterial(Location startLoc, int fullHeight, Material material, int range){
    	int x = startLoc.getBlockX();
		int y = startLoc.getBlockY()-1;
		int z = startLoc.getBlockZ();
		int offset = 0;
		int rangeX = range; //half, rangeX*2+1 is real range
		int rangeY = range;
		int offsetOfoffset = 1;
		int lastY = y;
		for (int j = 0; j < 2*rangeX+1; j++) {
			for (int k = 0; k < 2*rangeY+1; k++) {
				//if(Combinations[(j+4)][(k+4)][i] == 1) {
				if(new Location(startLoc.getWorld(), (x+j-4), (y-offset), (z+k+-4)).getBlock().getType().equals(material)) {	
					//log.info(new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().toString());
					//new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().setType(material, true);
					new Location(startLoc.getWorld(), (x+j-4), (y-offset), (z+k+-4)).getBlock().setType(Material.AIR, true);
				}
			}
		}
		offset+=offsetOfoffset;
		log.info("WorldTonnelRemoveMaterial " + ChatColor.GREEN + "Done");
    }
    
    public void WorldTonnelUpdateAir(Location startLoc, int fullHeight, int range){
    	int x = startLoc.getBlockX();
		int y = startLoc.getBlockY()-1;
		int z = startLoc.getBlockZ();
		int offset = 0;
		int rangeX = range; //half, rangeX*2+1 is real range
		int rangeY = range;
		int offsetOfoffset = 1;
		int lastY = y;
		for (int y2 = 0; y2 < fullHeight; y2++) {
		for (int j = 0; j < 2*rangeX+1; j++) {
			for (int k = 0; k < 2*rangeY+1; k++) {
				//if(Combinations[(j+4)][(k+4)][i] == 1) {
				Block b = new Location(startLoc.getWorld(), (x+j-4), (y-offset), (z+k+-4)).getBlock();
				if(b.getType().equals(Material.AIR)) {	
					//log.info(new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().toString());
					//new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().setType(material, true);
					//log.info("air: " + b.getLocation().toString());
					b.setType(Material.BARRIER);
				}
			}
		}
		
		for (int j = 0; j < 2*rangeX+1; j++) {
			for (int k = 0; k < 2*rangeY+1; k++) {
				//if(Combinations[(j+4)][(k+4)][i] == 1) {
				Block b = new Location(startLoc.getWorld(), (x+j-4), (y-offset), (z+k+-4)).getBlock();
				if(b.getType().equals(Material.TRIPWIRE)) {	
					//log.info(new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().toString());
					//new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().setType(material, true);
					//log.info("air: " + b.getLocation().toString());
					b.getState().update(true, true);
				}
			}
		}
		
		for (int j = 0; j < 2*rangeX+1; j++) {
			for (int k = 0; k < 2*rangeY+1; k++) {
				//if(Combinations[(j+4)][(k+4)][i] == 1) {
				Block b = new Location(startLoc.getWorld(), (x+j-4), (y-offset), (z+k+-4)).getBlock();
				if(b.getType().equals(Material.BARRIER)) {	
					//log.info(new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().toString());
					//new Location(startLoc.getWorld(), (x+j), (y-offset), (z+k)).getBlock().setType(material, true);
					//log.info("air: " + b.getLocation().toString());
					b.setType(Material.AIR);
				}
			}
		}
		offset+=offsetOfoffset;
		}
		log.info("WorldTonnelRemoveMaterial " + ChatColor.GREEN + "Done");
    }
    
    public int[][] rotateMatrixBy180Degrees (int[][] arr) {
    	int [][] newArray = new int[arr.length][arr.length];
    	for(int i=0; i<arr[0].length; i++){
            for(int j=arr.length-1; j>=0; j--){
                newArray[i][j] = arr[j][i];
            }
        }
    	return newArray;
    }
    
    public void displayMatrix () {
    	int [][] arr = new int[5][5];
    	int [][] newArray = new int[5][5];
    	for(int i = 0; i<5; i++) {
    		for(int j = 0; j<5; j++) {
        		arr[i][j]=0;
        	}
    	}
    	arr[0][4]=1;
    	newArray = rotateMatrixBy90DegreesRight(arr);
    	for(int i = 0; i<5; i++) {
    		for(int j = 0; j<5; j++) {
        		log.info("newArray[" + i + "][" +j + "] = " + newArray[i][j]);
        	}
    	}
    }
    
    //Rotate Matrix to 90 degree toward Right(clockwise)
	public int[][] rotateMatrixBy90DegreesRight(int[][] matrix) {

		int totalRowsOfRotatedMatrix = matrix[0].length; // Total columns of Original Matrix
		int totalColsOfRotatedMatrix = matrix.length; // Total rows of Original Matrix

		int[][] rotatedMatrix = new int[totalRowsOfRotatedMatrix][totalColsOfRotatedMatrix];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				rotatedMatrix[j][(totalColsOfRotatedMatrix - 1) - i] = matrix[i][j];
			}
		}
		return rotatedMatrix;
	}

	// Rotate Matrix to 90 degree toward Left(counter clockwise)
	public int[][] rotateMatrixBy90DegreesLeft(int[][] matrix) {

     int totalRowsOfRotatedMatrix = matrix[0].length; //Total columns of Original Matrix
     int totalColsOfRotatedMatrix = matrix.length; //Total rows of Original Matrix

     int[][] rotatedMatrix = new int[totalRowsOfRotatedMatrix][totalColsOfRotatedMatrix];

     for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
       rotatedMatrix[(totalRowsOfRotatedMatrix-1)-j][i] = matrix[i][j]; 
      }
     }
     return rotatedMatrix;
	}

	public void GenerateLootSkyWars(Location l1, Location l2) {
		int x1 = l1.getBlockX();
		int y1 = l1.getBlockY();
		int z1 = l1.getBlockZ();
		int x2 = l2.getBlockX();
		int y2 = l2.getBlockY();
		int z2 = l2.getBlockZ();
		int dX = +1;
		int dY = +1;
		int dZ = +1;
		if(x1<x2) {
			dX = -1;
		}
		if(y1<y2) {
			dY = -1;
		}
		if(z1<z2) {
			dZ = -1;
		}
		for (int x = x1; x!=x2; x+=dX) {
			for (int y = y1; y!=y2; y+=dY) {
				for (int z = z1; z!=z2; z+=dZ) {
					if(l1.getWorld().getBlockAt(x, y, z).getType().equals(Material.CHEST)) {
						
					}
				}
			}
		}
	}

	@EventHandler
	public void ExternalPlayerMoveEvent(PlayerMoveEvent e) { //disabled
		//AuthEvents.getInstance().MoveEventNew1(e);
		//log.info("new1");
	}
}