package de.amin.gamestates;

import de.amin.countdowns.InvincibilityCountdown;
import de.amin.hardcoregames.SpeedHG;
import de.amin.kit.impl.HermitKit;
import de.amin.kit.KitManager;
import de.amin.kit.impl.SurpriseKit;
import de.amin.mechanics.RandomTP;
import de.amin.mechanics.Scoreboards;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class InvincibilityState extends GameState{

    private final InvincibilityCountdown countdown;
    private final KitManager kitManager;

    public InvincibilityState(GameStateManager gameStateManager, KitManager kitManager){
        countdown = new InvincibilityCountdown(gameStateManager);
        this.kitManager = kitManager;
    }

    @Override
    public void start() {
        countdown.start();
        SpeedHG.INSTANCE.setPlayersAtStart(SpeedHG.INSTANCE.getPlayers().size());
        HermitKit.run();
        for(Player p : SpeedHG.INSTANCE.getPlayers()){

            //give Player random kit if surprise
            if(kitManager.getKit(p) instanceof SurpriseKit){
                int random = new Random().nextInt(kitManager.getKitArray().size());
                kitManager.setKit(p.getName(), kitManager.getKitArray().get(random));
            }else if(kitManager.getKit(p) instanceof HermitKit) {
                if(HermitKit.hermitPlayers.containsKey(p.getName())){
                    p.teleport(RandomTP.getHighestPoint(HermitKit.hermitPlayers.get(p.getName())));
                }else {
                    RandomTP.randomTeleport(p);
                }
            }

            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false ));
            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false ));

            SpeedHG.INSTANCE.getStats().increment(p.getName(), "GAMESPLAYED");
            p.getInventory().clear();
            SpeedHG.INSTANCE.getKitManager().getKitHashMap().get(p.getName()).giveItems(p);
            SpeedHG.INSTANCE.getStartItems().giveItem(p);
            p.getInventory().setItem(8, new ItemBuilder(new ItemStack(Material.COMPASS)).setDisplayName("§c§lPlayer Tracker").getItem());
            Scoreboards.invincibilityScoreboard(p);
        }
    }

    @Override
    public void stop() {
        for(Player p : SpeedHG.INSTANCE.getPlayers()){
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
        }
        countdown.stop();
    }

    public InvincibilityCountdown getCountdown() {
        return countdown;
    }
}
