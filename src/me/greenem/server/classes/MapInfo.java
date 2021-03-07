package me.greenem.server.classes;

import java.util.ArrayList;
import org.bukkit.Location;

public class MapInfo {
	public int Number;
	public String Name;
	//public String Descripction;
	//public String[] Descripction = new String[20];
	public ArrayList<String> Descripction = new ArrayList<>();
	public Location Location;
	public String MinigameName;
	public ArrayList<String> Voted = new ArrayList<>();
	public boolean UnderConstruction = false;
	public int WorldTime = 1000;
	public Location SpawnForDeadPlayers;
	public ArrayList<Location> PlayersSpawnLocs;
	public ArrayList<Location> GoldSpawnLocs;
	
	
	@Override
	public String toString() {
		return "MapInfo [Number=" + Number + ", Name=" + Name + ", Descripction=" + Descripction + ", Location="
				+ Location + ", MinigameName=" + MinigameName + ", Voted=" + Voted + ", UnderConstruction="
				+ UnderConstruction + "]";
	}
}
