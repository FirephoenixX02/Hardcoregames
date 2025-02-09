//Created by Duckulus on 11 Jul, 2021 

package de.amin.kit;

import de.amin.inventories.StartItemInventory;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.SpeedHG;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class StartItems implements Listener {

    public ArrayList<ItemStack> startItems;
    private final HashMap<String, ItemStack> players;

    public static final String DISPLAY_NAME = "§bStart Item Selecor";
    private final ArrayList<String> bucketLore;


    public StartItems(){
        startItems = new ArrayList<>();
        players = new HashMap<>();

        startItems.add(new ItemBuilder(new ItemStack(Material.PAPER)).setDisplayName("§7Nothing").getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.DIRT)).setAmount(64).getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.LOG)).setAmount(4).getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.WOOD_AXE)).setAmount(1).getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.ENDER_PEARL)).setAmount(1).getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.SPONGE)).setAmount(2).getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.EGG)).setAmount(16).setDisplayName("§fGrenade").getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.GOLD_INGOT)).setAmount(5).getItem());
        startItems.add(new ItemBuilder(new ItemStack(Material.CHAINMAIL_CHESTPLATE)).setAmount(1).getItem());

        bucketLore = new ArrayList<>();
        bucketLore.add("§9Can only be used for Water!");
        startItems.add(new ItemBuilder(new ItemStack(Material.BUCKET)).setAmount(1).setLore(bucketLore).getItem());
    }

    public void setStartItem(Player player, ItemStack item){
        for(ItemStack stack : startItems){
            if(stack.isSimilar(item)){
                players.put(player.getName(), stack);
            }
        }
        player.sendMessage("§aStart Item selected!");
    }

    public void giveItem(Player player){
        if(!players.containsKey(player.getName())){
            return;
        }
        if(players.get(player.getName()).getType().equals(Material.PAPER)){
            return;
        }
        player.getInventory().addItem(players.get(player.getName()));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!(SpeedHG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState))return;
        if(!e.hasItem() || !e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(DISPLAY_NAME))return;
        StartItemInventory.INVENTORY.open(e.getPlayer());
    }


}
