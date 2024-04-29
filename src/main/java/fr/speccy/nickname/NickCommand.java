package fr.speccy.nickname;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static fr.speccy.nickname.Nickname.players;

public class NickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandUsage = "§cSyntaxe : /nick <joueur> <nom|reset> <skin>";

        if (args.length == 0) {
            sender.sendMessage(commandUsage);
            return false;
        } else if (args.length == 1) {
            sender.sendMessage(commandUsage);
            return false;
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("reset")) {
                if (players.containsKey(args[0])) {
                    players.remove(args[0]);
                    sender.sendMessage(Nickname.PREFIX + "Le nickname de §b" + args[0] + " §fa été réinitialisé.");
                    return true;
                }
                sender.sendMessage("§cErreur: Le joueur `§f" + args[0] + "§c` n'a pas de nom personnalisé.");
                return true;
            }
            CustomPlayer newPlayer = new CustomPlayer(args[0], args[1], args[0]);
            players.remove(args[0]);
            players.put(args[0], newPlayer);
            sender.sendMessage(Nickname.PREFIX + "Le nom de §b" + args[0] + " §fa été changé.");
            return true;
        } else {
            CustomPlayer newPlayer = new CustomPlayer(args[0], args[1], args[2]);
            players.remove(args[0]);
            players.put(args[0], newPlayer);
            sender.sendMessage(Nickname.PREFIX + "Le nom de §b" + args[0] + " §fa été changé.");
            return true;
        }
    }
}
