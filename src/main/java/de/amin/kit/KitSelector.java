package de.amin.kit;

import de.amin.inventories.KitSelectorInventory;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.SpeedHG;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitSelector implements Listener {

    private final KitManager kitManager;
    public static final String DISPLAY_NAME = "§aKit Selector";

    public KitSelector(){
        kitManager = SpeedHG.INSTANCE.getKitManager();
    }



    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!(SpeedHG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState))return;
        if(SpeedHG.INSTANCE.getAdminMode().getAdminPlayers().containsKey(e.getPlayer().getName()))return;
        if(!e.getPlayer().getItemInHand().getType().equals(Material.CHEST))return;
        if(!e.hasItem() || !e.getItem().getItemMeta().getDisplayName().equals(DISPLAY_NAME))return;
        if(kitManager.getForcedKit()!=null){
            e.getPlayer().sendMessage("§cYou can't use this because a kit has been forced.");
            return;
        }
        KitSelectorInventory.INVENTORY.open(e.getPlayer());
    }


}
