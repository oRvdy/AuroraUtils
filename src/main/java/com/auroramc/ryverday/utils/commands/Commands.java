package com.auroramc.ryverday.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class Commands extends Command {

    private final boolean isOnlyPlayer;

    protected Commands(String name, boolean onlyPlayer, String... aliases) {
        super(name);
        this.setAliases(Arrays.asList(aliases));
        this.isOnlyPlayer = onlyPlayer;

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getMethod("getCommandMap").invoke(Bukkit.getServer());
            simpleCommandMap.register("aurorautils", this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (isOnlyPlayer) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("§cEste comando é exclusivo para jogadores");
                return false;
            }
        }

        perfomaceExecute(commandSender, s, strings);
        return true;
    }

    abstract void perfomaceExecute(CommandSender sender, String s, String[] args);

    public static void setupCommands() {
        new VanishCommand();
        new GmCommand();
        new CCCommand();
        new AddOffenciveWordsCommand();
        new ChatCommand();
        new SlimeJumperCommand();
        //new InveseeCommand();
    }
}
