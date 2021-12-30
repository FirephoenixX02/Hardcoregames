package de.amin.listeners;

import de.amin.gamestates.GameState;
import de.amin.hardcoregames.SpeedHG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        SpeedHG.INSTANCE.getStats().increment(e.getEntity().getName(), "DEATHS");
        SpeedHG.INSTANCE.getPlayers().remove(e.getEntity());
        e.getDrops().removeIf(itemStack -> itemStack.getItemMeta().spigot().isUnbreakable());
        if(SpeedHG.INSTANCE.getPlayers().size()<=1){
            SpeedHG.INSTANCE.getGameStateManager().setGameState(GameState.ENDING_STATE);
        }
        if(e.getEntity().getKiller()!=null){
            Player killer = e.getEntity().getKiller();
            int kills = SpeedHG.INSTANCE.getKills().getOrDefault(killer.getName(), 0);
            SpeedHG.INSTANCE.getKills().put(killer.getName(), kills+1);
            SpeedHG.INSTANCE.getStats().increment(killer.getName(), "KILLS");
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        World world = Bukkit.getWorld("world");
        Bukkit.getScheduler().scheduleSyncDelayedTask(SpeedHG.INSTANCE, () -> e.getPlayer().teleport(new Location(world, 0, world.getHighestBlockYAt(0,0), 0)), 1);
        if(e.getPlayer().hasPermission("hg.adminmode")){
            SpeedHG.INSTANCE.getAdminMode().activate(e.getPlayer());
        }else {
            SpeedHG.INSTANCE.getSpecMode().activate(e.getPlayer());
        }
    }


}
