//package beanbeanjuice.beanpunishments.managers.freeze;
//
//import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
//import org.bukkit.BanList;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.entity.Player;
//
//public class FrozenPlayer implements Runnable {
//
//    private Player player;
//    private Location location;
//    private int id;
//
//
//    public FrozenPlayer(Location location, Player player) {
//        this.location = location;
//        this.player = player;
//    }
//
//    public int getID() {
//        return id;
//    }
//
//    public void setID(int id) {
//        this.id = id;
//    }
//
//    @Override
//    public void run() {
//        FreezeManager.frozenEffects(player, location);
//
//        if (Bukkit.getPlayer(player.getName()) == null) {
//            FreezeManager.unfreezePlayer(player);
//            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), GeneralHelper.translateColors("&cLogged out while frozen. Appeal on discord &bhttps://discord.gg/Qy6NU5x"), null, null);
//            Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors("&a" + player.getName() + " &c&lhas left the game while frozen and has been permanently banned."));
//        }
//    }
//}