//Created by Duckulus on 02 Jul, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.hardcoregames.SpeedHG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KayaKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final GameStateManager gameStateManager;
    private final ArrayList<Block> blocks;

    public KayaKit(){
        kitManager = SpeedHG.INSTANCE.getKitManager();
        gameStateManager = SpeedHG.INSTANCE.getGameStateManager();
        blocks = new ArrayList<>();
        run();
    }

    @Override
    public void giveItems(Player p) {
        ItemStack grass = new ItemStack(Material.GRASS);
        grass.setAmount(20);
        p.getInventory().addItem(grass);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GRASS);
    }

    @Override
    public String getName() {
        return "Kaya";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Place down Grass Blocks that");
        description.add("§7disappear when other Players");
        description.add("§7step on them.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if(!(kitManager.getKitHashMap().get(player.getName()) instanceof KayaKit))return;
        if(!e.getBlock().getType().equals(Material.GRASS))return;

        blocks.add(e.getBlock());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(!e.getBlock().getType().equals(Material.GRASS))return;
        if(kitManager.getKitHashMap().get(e.getPlayer().getName()) instanceof KayaKit){
            e.setCancelled(true);
            e.getPlayer().getInventory().addItem(new ItemStack(Material.GRASS));
            e.getBlock().setType(Material.AIR);
        }
        blocks.remove(e.getBlock());
    }


    public void run(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SpeedHG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(SpeedHG.INSTANCE.getPlayers().contains(player) && !(kitManager.getKit(player) instanceof KayaKit)){

                            if(blocks.contains(player.getLocation().subtract(0,1,0).getBlock())){
                                blocks.remove(player.getLocation().subtract(0,1,0).getBlock());
                                player.getLocation().subtract(0,1,0).getBlock().setType(Material.AIR);
                            }

                    }
                }
            }
        }, 0, 1);
    }


}
