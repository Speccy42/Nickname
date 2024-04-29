package fr.speccy.nickname;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_9_R2.PacketPlayOutNamedEntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;

public class NickManager {

    public static String getOfficialUUID(String playerName) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");
            String json = (new Scanner(uc.getInputStream(), "UTF-8")).useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            return obj.toString().split(",")[1].split(":")[1].replace("\"", "").replace("}", "");
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static void setName(Player player, String name) {
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        GameProfile gameProfile = entityPlayer.getProfile();

        try {
            Field gameProfileName = gameProfile.getClass().getDeclaredField("name");
            gameProfileName.setAccessible(true);
            gameProfileName.set(gameProfile, name);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(new int[]{entityPlayer.getId()});
        PacketPlayOutNamedEntitySpawn packetPlayOutNameEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);

        for (Player players : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)players).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
            ((CraftPlayer)players).getHandle().playerConnection.sendPacket(packetPlayOutNameEntitySpawn);
        }

    }

    public static void setSkin(Player player, String skin) {
        String uuid = getOfficialUUID(skin);
        String name = null;
        String value = null;
        String signature = null;

        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");
            String json = (new Scanner(uc.getInputStream(), "UTF-8")).useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray properties = (JSONArray)((JSONObject)obj).get("properties");

            for(int i = 0; i < properties.size(); ++i) {
                try {
                    JSONObject property = (JSONObject)properties.get(i);
                    name = (String)property.get("name");
                    value = (String)property.get("value");
                    signature = property.containsKey("signature") ? (String)property.get("signature") : null;
                } catch (Exception var14) {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", var14);
                }
            }

            EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            GameProfile gameProfile = entityPlayer.getProfile();
            gameProfile.getProperties().clear();
            gameProfile.getProperties().put(name, new Property(name, value, signature));
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }

    public static void refresh(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.hidePlayer(player);
            players.showPlayer(player);
        }
    }
}
