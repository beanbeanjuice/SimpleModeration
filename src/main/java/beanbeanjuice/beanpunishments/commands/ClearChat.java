package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClearChat implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        GeneralHelper helper = BeanPunishments.getHelper();

        if (!(sender instanceof Player)) {
            clearChat();
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission(getPermissions().get(0)) || player.isOp()) {
            clearChat();
            return true;
        } else {
            player.sendMessage(helper.getPrefix() + helper.getConfigString("no-permission"));
            return false;
        }
    }

    @Override
    public String getName() {
        return "clearchat";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("beanpunishments.clearchat"); // #0
        return arrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        return new CommandUsage();
    }

    @Override
    public boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, sender, arguments);
    }

    private void clearChat() {
        for (int i = 0; i < 300; i++) {
            Bukkit.broadcastMessage("");
        }
        Bukkit.broadcastMessage(BeanPunishments.getHelper().getPrefix() + BeanPunishments.getHelper().getConfigString("chat-cleared"));
    }
}
