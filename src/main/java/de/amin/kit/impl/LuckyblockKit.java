package de.amin.kit.impl;


import de.amin.hardcoregames.SpeedHG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class LuckyblockKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final HashMap<String, Long> cooldown;
    private final HashMap<String, Integer> distanceCooldown;

    public LuckyblockKit() {
        kitManager = SpeedHG.INSTANCE.getKitManager();
        cooldown = SpeedHG.INSTANCE.getCooldown();
        distanceCooldown = new HashMap();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.AIR)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.AIR);
    }

    @Override
    public String getName() {
        return "LuckyBlockKit";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Not working!");
        description.add("§7---");
        description.add("§7---");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{null, null};
    }

}