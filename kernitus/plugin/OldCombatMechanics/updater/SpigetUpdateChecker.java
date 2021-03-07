package kernitus.plugin.OldCombatMechanics.updater;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import kernitus.plugin.OldCombatMechanics.updater.SpigetUpdateChecker.1;
import kernitus.plugin.OldCombatMechanics.updater.SpigetUpdateChecker.VersionPojo;

public class SpigetUpdateChecker {
	private static final String USER_AGENT = "OldCombatMechanics";
	private static final String VERSIONS_URL = "https://api.spiget.org/v2/resources/19510/versions?size=15000";
	private static final String UPDATES_URL = "https://api.spiget.org/v2/resources/19510/updates?size=15000";
	private String latestVersion = "";

	public boolean isUpdateAvailable() {
		try {
			List<VersionPojo> versions = this
					.getVersions("https://api.spiget.org/v2/resources/19510/versions?size=15000");
			if (versions.isEmpty()) {
				return false;
			} else {
				VersionPojo currentVersion = (VersionPojo) versions.get(versions.size() - 1);
				this.latestVersion = currentVersion.getName();
				return VersionChecker.shouldUpdate(this.latestVersion);
			}
		} catch (Exception var3) {
			return false;
		}
	}

	public String getUpdateURL() {
		try {
			List<VersionPojo> versions = this
					.getVersions("https://api.spiget.org/v2/resources/19510/updates?size=15000");
			if (versions.isEmpty()) {
				return "Error getting update URL";
			} else {
				VersionPojo currentVersion = (VersionPojo) versions.get(versions.size() - 1);
				String updateId = currentVersion.getId();
				return "https://www.spigotmc.org/resources/oldcombatmechanics.19510/update?update=" + updateId;
			}
		} catch (Exception var4) {
			return "Error getting update URL";
		}
	}

	public String getLatestVersion() {
		return this.latestVersion;
	}

	private List<VersionPojo> getVersions(String urlString) {
      try {
         InputStreamReader reader = this.fetchPage(urlString);
         Type pojoType = (new 1(this)).getType();
         List<VersionPojo> parsedVersions = (List)(new Gson()).fromJson(reader, pojoType);
         if (parsedVersions == null) {
            System.err.println("JSON was at EOF when checking for spiget updates!");
            return Collections.emptyList();
         } else {
            return parsedVersions;
         }
      } catch (IOException | JsonSyntaxException var5) {
         var5.printStackTrace();
         return Collections.emptyList();
      }
   }

	private InputStreamReader fetchPage(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("User-Agent", "OldCombatMechanics");
		InputStream inputStream = connection.getInputStream();
		return new InputStreamReader(inputStream);
	}
}