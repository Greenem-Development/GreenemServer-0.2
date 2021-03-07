package me.greenem.server.classes;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapView;

public class MinigameArena {
	public String ArenaName;
	public String ArenaType;
	public String ArenaState = "free";
	public int TimeToStartMinPlayers;
	public int TimeToStartMaxPlayers;
	public int PlayersCount;
	public int PlayersMax;
	public int PlayersMin;
	public String TimeState = "waiting";
	public int TimeLeftToStart;
	public int TimeLeftToEnd;
	public int PlayersFinishedNumber = 0;
	public ArrayList<String> WinnersList = new ArrayList<>();
	public Map <String, String> PlayersLevels = new HashMap();
	public Inventory MapVotingInventory;
	public Inventory ModeVotingInventory;
	public Map <String, String> MapVoting = new HashMap();
	public int MapWonNumber = 0;
	public Map <String, String> ModeVoting = new HashMap();
	public MapInfo MapLink;
	
	//==========EXCLUSIVE=DATA==========//
	
	//--Murder Mystery--
	public ArrayList<Player> MurderersList = new ArrayList<>();
	public ArrayList<Player> DetectivesList = new ArrayList<>();
	public ArrayList<Player> InnocentsList = new ArrayList<>();
	public ArrayList<Player> HeroesList = new ArrayList<>();
	public ArrayList<Player> DeadList = new ArrayList<>();
	public ArrayList<Player> AllPlayers = new ArrayList<>();
	public ArrayList<Player> CopyedDetectivesList = new ArrayList<>();
	public ArrayList<Player> CopyedInnocentsList = new ArrayList<>();
	public int PlayersTotal;
	//public ArmorStand DroppedBow;
	//public Location DroppedBowLoc;
	public ArrayList<ArmorStand> DroppedBow = new ArrayList<>();
	public ArrayList<Location> DroppedBowLoc = new ArrayList<>();
	
	//mode:
	
	public int NumberOfMurderers = 1;
	public int NumberOfDetectives = 1;
	
	//--Murder Mystery--
	
	
	//--Among Us--
	
	public ArrayList<Player> ImpostersList = new ArrayList<>();
	public ArrayList<Player> CrewmatesList = new ArrayList<>();
	//public ArrayList<Player> DeadList, AllPlayers
	//map
	
	
	//--Among Us--
	
	//==========EXCLUSIVE=DATA==========//
	
	@Override
	public String toString() {
		return "MinigameArena [ArenaName=" + ArenaName + ", ArenaType=" + ArenaType + ", ArenaState=" + ArenaState
				+ ", TimeToStartMinPlayers=" + TimeToStartMinPlayers + ", TimeToStartMaxPlayers="
				+ TimeToStartMaxPlayers + ", PlayersCount=" + PlayersCount + ", PlayersMax=" + PlayersMax
				+ ", PlayersMin=" + PlayersMin + ", TimeState=" + TimeState + ", TimeLeftToStart=" + TimeLeftToStart
				+ ", TimeLeftToEnd=" + TimeLeftToEnd + ", PlayersFinishedNumber=" + PlayersFinishedNumber
				+ ", WinnersList=" + WinnersList + ", PlayersLevels=" + PlayersLevels + ", MapVotingInventory="
				+ MapVotingInventory + ", ModeVotingInventory=" + ModeVotingInventory + ", MapVoting=" + MapVoting
				+ ", MapWonNumber=" + MapWonNumber + ", ModeVoting=" + ModeVoting + ", MurderersList=" + MurderersList
				+ ", DetectivesList=" + DetectivesList + ", InnocentsList=" + InnocentsList + ", NumberOfMurderers="
				+ NumberOfMurderers + ", NumberOfDetectives=" + NumberOfDetectives + "]";
	}
	
	
}

