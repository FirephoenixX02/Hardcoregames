package de.amin.countdowns;

import de.amin.gamestates.GameState;
import de.amin.gamestates.GameStateManager;
import de.amin.hardcoregames.SpeedHG;
import de.amin.mechanics.Scoreboards;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class InvincibilityCountdown extends Countdown{

    private final FileConfiguration config = SpeedHG.INSTANCE.getConfig();
    private final int INVINCIBILITY_TIME = config.getInt("timers.invincibility");
    private final GameStateManager gameStateManager;
    private int seconds;
    private int taskID;


    public InvincibilityCountdown(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        seconds = INVINCIBILITY_TIME;

    }

    @Override
    public void start() {
        String prefix = SpeedHG.INSTANCE.PREFIX;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SpeedHG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 120:
                        Bukkit.broadcastMessage(prefix + "§cInvincibility will end in 02:00");
                        break;
                    case 90:
                        Bukkit.broadcastMessage(prefix + "§cInvincibility will end in 01:30");
                        break;
                    case 60:
                        Bukkit.broadcastMessage(prefix + "§cInvincibility will end in 01:00");
                        break;
                    case 30:
                        Bukkit.broadcastMessage(prefix + "§cInvincibility will end in 00:30");
                        break;
                    case 15:
                        Bukkit.broadcastMessage(prefix + "§cInvincibility will end in 00:15");
                        break;
                    case 5: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(prefix + "§cInvincibility will end in " + seconds + " seconds");
                        for(Player p : SpeedHG.INSTANCE.getPlayers()){
                            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        }
                        break;
                    case 1:
                        Bukkit.broadcastMessage(prefix + "§cInvincibility will end in one second");
                        for(Player p : SpeedHG.INSTANCE.getPlayers()){
                            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        }
                        break;
                    case 0:
                        gameStateManager.setGameState(GameState.INGAME_STATE);
                        break;
                    default:
                        break;
                }
                for(Player p : Bukkit.getOnlinePlayers()){
                    Scoreboards.invincibilityScoreboard(p);
                }
                seconds--;
            }
        },0, 20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public int getSeconds() {
        return seconds;
    }


}
