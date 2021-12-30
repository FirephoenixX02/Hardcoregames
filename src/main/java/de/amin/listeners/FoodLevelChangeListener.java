package de.amin.listeners;

import de.amin.gamestates.GameState;
import de.amin.gamestates.InvincibilityState;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.SpeedHG;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        GameState currentGameState = SpeedHG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof LobbyState || currentGameState instanceof InvincibilityState){
            e.setCancelled(true);
        }
        e.setFoodLevel(e.getFoodLevel()+2);
    }


}
