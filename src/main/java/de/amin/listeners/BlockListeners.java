package de.amin.listeners;

import de.amin.gamestates.*;
import de.amin.hardcoregames.SpeedHG;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListeners implements Listener {


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        GameState currentGameState = SpeedHG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof LobbyState || currentGameState instanceof EndingState){
            event.setCancelled(true);
        } else {
            switch (event.getBlock().getType()) {
                case RED_MUSHROOM:
                case BROWN_MUSHROOM:
                    event.setCancelled(true);
                    event.getBlock().getDrops().forEach( e -> player.getInventory().addItem(e));
                    event.getBlock().setType(Material.AIR);
                    break;

            }
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getItemInHand().getItemMeta().spigot().isUnbreakable()){
            event.setCancelled(true);
        }
        GameState currentGameState = SpeedHG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof LobbyState){
            event.setCancelled(true);
        }
    }


}
