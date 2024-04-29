package fr.speccy.nickname;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Objects;

import static fr.speccy.nickname.Nickname.players;

public class NickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (players.containsKey(player.getName())) {
            CustomPlayer customPlayer = players.get(player.getName());
            NickManager.setName(player, customPlayer.getNewName());
            if (!Objects.equals(customPlayer.getNewSkin(), player.getDisplayName())) {
                NickManager.setSkin(player, customPlayer.getNewSkin());
            }
            NickManager.refresh(player);
        }
    }
}
