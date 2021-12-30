package de.amin.commands;

import de.amin.gamestates.GameState;
import de.amin.gamestates.InvincibilityState;
import de.amin.hardcoregames.SpeedHG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkipInvincibilityCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("hg.skipinvis"))return true;
        if(!(SpeedHG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof InvincibilityState)){
            sender.sendMessage(SpeedHG.INSTANCE.PREFIX + "§cInvincibility is not active at the moment!");
            return true;
        }
        Bukkit.broadcastMessage(SpeedHG.INSTANCE.PREFIX + "§7The invincibility has been skipped by an Operator.");
        SpeedHG.INSTANCE.getGameStateManager().setGameState(GameState.INGAME_STATE);
        return false;
    }
}
