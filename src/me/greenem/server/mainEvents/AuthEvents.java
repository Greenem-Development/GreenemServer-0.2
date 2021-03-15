package me.greenem.server.mainEvents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mchange.v1.jvm.TypeFormatException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import me.greenem.server.dataBase.MainDao;

public class AuthEvents implements Listener {
	public Plugin plugin = Bukkit.getPluginManager().getPlugin("GreenemServerMainPlugin");
	public Logger log = plugin.getLogger();
	private int maxAttempts = 5;
	//final Random random = new Random(System.currentTimeMillis());
	
	private static volatile AuthEvents INSTANCE;
	
	public static AuthEvents getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new AuthEvents();
		}
		return INSTANCE;
	}
	
	private Server getServer() {
		return plugin.getServer();
	}
	
	@EventHandler
	public void PlayerPreJoinE (AsyncPlayerPreLoginEvent e) {
		String pName = e.getName();
		if(getServer().getPlayer(e.getName()) != null) { //если такой игрок уже онлайн
			try {
				String dbLastLoginAdress = MainDao.getInstance().getAnything("players_authentication", "name", pName, "lastLoginAdress");
				if(dbLastLoginAdress != null) {
					String adress = e.getAddress().toString().split("/")[1].split(":")[0];
					if(adress.endsWith("\\")) adress.substring(0, adress.length()-1);
					if(!dbLastLoginAdress.equals(adress)) { //если уже играющий игрок под IP, который последний раз правильно ввёл пароль
						e.disallow(Result.KICK_OTHER, ChatColor.RED + "This player is already on the server");
					}
				}
				else {
					//он не загреган, но всё равно выкидывать уже играющего (в лобби регистрации) нельзя
					e.disallow(Result.KICK_OTHER, ChatColor.RED + "This player is already on the server");
				}
			} catch (SQLException e1) {
				//e1.printStackTrace(); //ignore
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void PlayerLoginEvent(PlayerJoinEvent e) throws IOException {
		int maxTimeDifference = 46400000;
		int maxPortDifference = 20000;
		
		Player p = e.getPlayer();
		e.setJoinMessage("");
		p.setOp(false);
		ArrayList<Object> dbValues = null;
		try {
			dbValues = MainDao.getInstance().getAnything("players_authentication", "name", p.getName(), "password", "lastLoginAdress", "lastLoginPort");
		} catch (SQLException e1) {
			//e1.printStackTrace();
		}
		String dbPassword = "";
		String dbLastLoginAdress = "";
		String dbLastLoginPort = "";
		if(dbValues!=null && !dbValues.isEmpty()) {
			dbPassword = dbValues.get(0).toString();
			dbLastLoginAdress = dbValues.get(1).toString();
			dbLastLoginPort = dbValues.get(2).toString();
		}
		if(dbPassword!="") {
			String adress = p.getAddress().toString().split("/")[1].split(":")[0];
			if(adress.endsWith("\\")) adress.substring(0, adress.length()-1);
			if(dbLastLoginAdress.equals(adress)) {
				if(Math.abs(Integer.parseInt(dbLastLoginPort) - p.getAddress().getPort()) < maxPortDifference) {
					ArrayList<Object> dbValuesProfile = null;
					try {
						dbValuesProfile = MainDao.getInstance().getAnything("players_profiles", "name", p.getName(), "lastRealLoginTime", "lastCustomLoginTime");
					} catch (SQLException e1) {
						//e1.printStackTrace();
					}
					String dbLastRealLoginTime = "";
					//String dbLastCustomLoginTime = "";
					if(dbValuesProfile!=null && !dbValuesProfile.isEmpty()) {
						dbLastRealLoginTime = dbValuesProfile.get(0).toString();
						//dbLastCustomLoginTime = dbValuesProfile.get(1).toString();
					}
					long timeNowInMiliseconds = Calendar.getInstance().getTimeInMillis();
					if(!dbLastRealLoginTime.equals("") && Math.abs(Long.parseLong(dbLastRealLoginTime)-timeNowInMiliseconds) < maxTimeDifference) {
						
						p.sendTitle("", "");
						p.sendMessage(ChatColor.GREEN + "You have already logged in before.");
						GiveOpIfNeeds(p);
						PrepareJoinedPlayer(p);
						//MixedEvents.getInstance().tpToSpawn(p);
						return;
					}
				}
			}
		}
		p.getInventory().clear();
		p.getInventory().setHeldItemSlot(0);
		setPlayerAuth(p, true);
		//evt.getPlayer().setCollidable(false);
		//evt.getPlayer().spigot().setCollidesWithEntities(false);
		for (Player p1 : getServer().getOnlinePlayers()) {
			p1.hidePlayer(p);
	        p.hidePlayer(p1);
		}
		MixedEvents.getInstance().RemoveMaterial(p, Material.NETHER_STAR);
		MixedEvents.getInstance().RemoveMaterial(p, Material.BLAZE_POWDER);
		MixedEvents.getInstance().RemoveMaterial(p, Material.CRYING_OBSIDIAN);
		Location loc = new Location(Bukkit.getWorld("auth"), 0.5, 37.5, 0.5);
		loc.setYaw(135);
		p.teleport(loc);
		p.getWorld().setSpawnLocation(loc);
		p.getWorld().setTime(1000);
		//if(props.getProperty(e.getPlayer().getName()+".lastLoginAdress")!=null) {//если он зареган
		if(dbPassword=="") {
			p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GOLD + "You should register", ChatColor.ITALIC + "" + ChatColor.GRAY + "/register <password> <password>", 5, 99999, 30);
		}
		else {
			p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GOLD + "You should login", ChatColor.ITALIC + "" + ChatColor.GRAY + "/login <password>", 5, 99999, 30);
		}
		p.setGameMode(GameMode.ADVENTURE);
		setPasswordAttempts(p, 0);
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
	
	public void GiveOpIfNeeds(Player p) {
		String isOp = "";
		try {
			isOp = MainDao.getInstance().getAnything("players_profiles", "name", p.getName(), "isOP").toString();
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		if(isOp.equalsIgnoreCase("0")) {
			p.setOp(false);
		}
		else if(isOp.equalsIgnoreCase("1")) {
			p.setOp(true);
		}
    }
	
	public void PrepareJoinedPlayer(Player p) {
		for (Player p1 : getServer().getOnlinePlayers()) {
          p1.showPlayer(p);
//          if(!AdminPlayersVanished.contains(p1)) p.showPlayer(p1);
		}
//		AuthAttempts.remove(p);
		p.setCanPickupItems(true);
		p.setCollidable(false);
		//p.spigot().setCollidesWithEntities(false);
		try {
			MixedEvents.getInstance().loadInventory(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MixedEvents.getInstance().tpToSpawn(p);
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
    	}.runTaskLater(plugin, 1);
	}
	
	public void setPlayerAuth(Player p, boolean auth){
		try {
			int bool = 0;
			if(auth == true) {
				bool = 1;
			}
			MainDao.getInstance().setAnything("players_log_off_data", "name", p.getName(), "isAuth", bool);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	public boolean isPlayerAuth(Player p){
		try {
			boolean result = true;
			String isAuth = MainDao.getInstance().getAnything("players_log_off_data", "name", p.getName(), "isAuth");
			if(isAuth.equalsIgnoreCase("0")) {
				result = false;
			}
			return result;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return true;
	}
	
	public int getPasswordAttempts(Player p) {
		String s = "";
		try {
			 s = (String) MainDao.getInstance().getAnything("players_log_off_data", "name", p.getName(), "PasswordAttempts").toString();
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		int result = 0; //-1
		try {
			result = Integer.valueOf(s).intValue();
		} catch (NumberFormatException e1) {
			//ignore
		}
		return result;
	}
	
	public void setPasswordAttempts(Player p, int attempts) {
		try {
			 MainDao.getInstance().setAnything("players_log_off_data", "name", p.getName(), "PasswordAttempts", attempts);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	public void AuthStartCountdown(Player p, int MaxSeconds) {
    	Long timeWhenJoined = Calendar.getInstance().getTimeInMillis();
    	new BukkitRunnable() {
    		@Override
    		public void run() {
    			if(p==null || !p.isOnline() || (!isPlayerAuth(p))) {
    				cancel();
    			}
    			else {
    				if(MaxSeconds - ((Calendar.getInstance().getTimeInMillis()-timeWhenJoined)/1000) > 0){
    					float persent;
    					//log.info((Calendar.getInstance().getTimeInMillis()-timeJoined)/1000 + "");
						persent = (float) (MaxSeconds - (Calendar.getInstance().getTimeInMillis()-timeWhenJoined)/1000) / MaxSeconds; 
						if(persent > 1 || persent < 0) persent = (float) 0.999;
						p.setLevel(Integer.parseInt(Long.toString(MaxSeconds - ((Calendar.getInstance().getTimeInMillis())-timeWhenJoined)/1000)));
						p.setExp(persent);
    				}
    				else {
    					p.kickPlayer("You were not in time while entering password");
    				}
    			}
    		}
    	}.runTaskTimer(plugin, 0, 20);
    }
	
	public void RegisterPlayer(Player p, String[] args)  {
		if(!isPlayerAuth(p)) {
			p.sendMessage(ChatColor.RED + "You have already signed in");
			return;
		}
		else if (args.length < 2) {
			p.sendMessage(ChatColor.RED + "Incorrect command using, Try " + ChatColor.GOLD + "/register <password> <password>" + ChatColor.RED + ".");
			return;
		} else if (args[0].equals(args[1])) {
			boolean alreadyRegistered = false;
			try {
				alreadyRegistered = MainDao.getInstance().ifRowExists("players_authentication", "name", p.getName(), "password");
			} catch (SQLException e1) {
				//e1.printStackTrace();
			}
			if(alreadyRegistered == false) {
				String adress = p.getAddress().toString().split("/")[1].split(":")[0];
				if (adress.endsWith("\\")) adress.substring(0, adress.length() - 1);
				String port = Integer.toString(p.getAddress().getPort());
				try {
					MainDao.getInstance().setAnything("players_authentication", "name", p.getName(), "password", args[0], "lastLoginAdress", adress, "lastLoginPort", port);
					MainDao.getInstance().setAnything("players_profiles", "name", p.getName(), "lastRealLoginTime", Calendar.getInstance().getTimeInMillis());
				} catch (SQLException e) {
					//e.printStackTrace();
				}
				p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GREEN + "Now you registered!", ChatColor.ITALIC + "" + ChatColor.DARK_GREEN + "Welcome! Don't forget the password ;)", 5, 40, 30);
				setPlayerAuth(p, false);
				GiveOpIfNeeds(p);
				PrepareJoinedPlayer(p);
				MixedEvents.getInstance().tpToSpawn(p);
			} else {
				p.sendMessage(ChatColor.RED + "This account is already registered. You should use " + ChatColor.GOLD
						+ "/login <password>" + ChatColor.RED + ".");
			}
		} else {
			p.sendMessage(ChatColor.RED + "The password which is first doesn't match the second one.");
		}
	}
	
	public void LoginPlayer(Player p, String[] args) {
		if(!isPlayerAuth(p)) {
			p.sendMessage(ChatColor.RED + "You have already signed in");
			return;
		}
		else if(args.length<1) {
			p.sendMessage(ChatColor.RED + "Incorrect command using, Try " + ChatColor.GOLD + "/login <password>"+ChatColor.RED+".");
			return;
		}
		String dbPassword = "";
		try {
			dbPassword = (String) MainDao.getInstance().getAnything("players_authentication", "name", p.getName(), "password").toString();
		} catch (SQLException e1) {
			//e1.printStackTrace();
		}
		if(!dbPassword.equals("")) {
			if(dbPassword.equals(args[0])) {
				String adress = getPlayerAdress(p);
				String port = getPlayerPort(p);
				try {
					 MainDao.getInstance().setAnything("players_authentication", "name", p.getName(), "lastLoginAdress", adress, "lastLoginPort", port);
					 MainDao.getInstance().setAnything("players_profiles", "name", p.getName(), "lastRealLoginTime", Calendar.getInstance().getTimeInMillis());
				} catch (SQLException e) {
					//e.printStackTrace();
				}
				p.sendTitle(ChatColor.ITALIC + "" + ChatColor.BOLD + "" + ChatColor.GREEN + "You logged in!", ChatColor.ITALIC + "" + ChatColor.GRAY + "Welcome back!", 5, 40, 30);
				setPlayerAuth(p, false);
				GiveOpIfNeeds(p);
				PrepareJoinedPlayer(p);
			}
			else {
				p.sendMessage(ChatColor.RED + "Incorrect password.");
				int dbAttempts = getPasswordAttempts(p);
				if(dbAttempts+1>=maxAttempts) {
					p.kickPlayer("To many attempts!");
				}
				setPasswordAttempts(p, dbAttempts+1);
			}
		}
		else {
			p.sendMessage(ChatColor.RED + "This account is NOT registered. You should use " + ChatColor.GOLD + "/register <password> <password>"+ChatColor.RED+".");
		}
	}
	
	public String getPlayerAdress(Player p) {
		String result = p.getAddress().toString().split("/")[1].split(":")[0];
		if (result.endsWith("\\")) result.substring(0, result.length() - 1);
		return result;
	}
	
	public String getPlayerPort(Player p) {
		String result = Integer.toString(p.getAddress().getPort());
		return result;
	}
	
	@EventHandler
    public void ChangeProfileOpFromPlayer(PlayerCommandPreprocessEvent e) {
		boolean OppingEachOtherAllowed = true;
    	if(e.getPlayer().isOp()) {
    		int opParameters = 0; //0 - default(nothing), 1 - to become op, 2 - to be deoped
    		if (e.getMessage().contains("/op") && e.getMessage().length() > 4) {
    			opParameters = 1;
    		}
    		if (e.getMessage().contains("/deop") && e.getMessage().length() > 6) {
    			opParameters = 2;
    		}
    		if(opParameters!=0) {
    			//log.info(e.getMessage().substring(4));
    			String p = "";
    			if(opParameters==1) p = e.getMessage().substring(4);
    			if(opParameters==2) p = e.getMessage().substring(6);
    			if(p != "") {
    				if(e.getPlayer().isOp() && OppingEachOtherAllowed) { //can player manage opps
    					boolean PlayerRegistered = false;
    					try {
							PlayerRegistered = MainDao.getInstance().ifRowExists("players_profiles", "name", p);
						} catch (SQLException e1) {
							//e1.printStackTrace();
						}
    					if(PlayerRegistered) {
    						if(opParameters==1) {
    							try {
    								MainDao.getInstance().setAnything("players_profiles", "name", p, "isOp", true);
    							} catch (SQLException e1) {
    								//e1.printStackTrace();
    							}
    						}
    						if(opParameters==2) {
    							try {
    								MainDao.getInstance().setAnything("players_profiles", "name", p, "isOp", false);
    							} catch (SQLException e1) {
    								//e1.printStackTrace();
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    }
	
	public void ChangeProfileOpFromConsole(CommandSender sender, Command cmd, String[] args) {
		log.info("ChangeProfileOpFromConsole");
		log.info((sender instanceof ConsoleCommandSender) + "");
	}
}
