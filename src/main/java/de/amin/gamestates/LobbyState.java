package de.amin.gamestates;

import de.amin.countdowns.LobbyCountdown;
import de.amin.hardcoregames.SpeedHG;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LobbyState extends GameState{

    public static final int MIN_PLAYERS = (int) SpeedHG.INSTANCE.getConfig().get("game.min_players");

    private final LobbyCountdown countdown;

    public LobbyState(GameStateManager gameStateManager){
        countdown = new LobbyCountdown(gameStateManager);
    }

    @Override
    public void start() {
        countdown.startIdle();
    }

    @Override
    public void stop() {
        Bukkit.broadcastMessage(SpeedHG.INSTANCE.PREFIX + "§cThe tournament started. Good Luck!");
        countdown.stop();
        for(Player p : SpeedHG.INSTANCE.getPlayers()){
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
        }
    }

    public LobbyCountdown getCountdown() {
        return countdown;
    }
}
