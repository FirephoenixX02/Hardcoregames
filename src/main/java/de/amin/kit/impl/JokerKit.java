//Created by Duckulus on 16 Aug, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.SpeedHG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class JokerKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final GameStateManager gameStateManager;
    private final SpeedHG plugin;
    private final KitSetting cooldown = new KitSetting(this, "Cooldown", 60, 0, 200);

    public JokerKit(SpeedHG plugin) {
        this.plugin = plugin;
        this.kitManager = plugin.getKitManager();
        this.gameStateManager = plugin.getGameStateManager();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(Material.GHAST_TEAR).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GHAST_TEAR);
    }

    @Override
    public String getName() {
        return "Joker";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Shuffle your opponents Inventory");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown};
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(!(kitManager.getKit(player) instanceof JokerKit))return;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        if(player.getItemInHand()==null ||
                player.getItemInHand().getType().equals(Material.AIR) ||
                !player.getItemInHand().getType().equals(Material.GHAST_TEAR)||
        !player.getItemInHand().getItemMeta().spigot().isUnbreakable())return;
        if(!(event.getRightClicked() instanceof Player))return;
        if(isCooldown(player, cooldown.getValue()))return;
        Player target = (Player) event.getRightClicked();

        ItemStack[] contents = target.getInventory().getContents();

        target.sendMessage("§cA Joker shuffled your Inventory");
        player.sendMessage("§aYou shuffled their inventory.");

        activateCooldown(player);

        for (int i = 0; i <10; i+=3) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                shuffle(contents);
                target.getInventory().setContents(contents);
            }, i);
        }
    }

    private void shuffle(ItemStack[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            ItemStack a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
