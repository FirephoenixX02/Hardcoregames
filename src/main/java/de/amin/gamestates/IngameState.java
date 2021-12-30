package de.amin.gamestates;

import de.amin.countdowns.IngameTimer;
import de.amin.hardcoregames.SpeedHG;
import de.amin.kit.impl.ScoutKit;
import de.amin.mechanics.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class IngameState extends GameState{

    private final String prefix;
    private final IngameTimer timer;
    private final ItemManager itemManager;

    public IngameState(ItemManager itemManager){
        prefix = SpeedHG.INSTANCE.PREFIX;
        timer = new IngameTimer();
        this.itemManager = itemManager;
    }


    @Override
    public void start() {
        Bukkit.broadcastMessage(prefix + "§aInvincibility is over. May the fighting begin!");
        timer.start();
        ScoutKit.speedPotionRunnable(itemManager);
        for(Player p : SpeedHG.INSTANCE.getPlayers()) {
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        }
    }

    @Override
    public void stop() {

    }

    public IngameTimer getTimer() {
        return timer;
    }
}
