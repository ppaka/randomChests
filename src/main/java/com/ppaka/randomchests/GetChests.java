package com.ppaka.randomchests;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GetChests implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player){
            ArrayList<Location> locations;

            if (args.length == 1){
                try {
                    var radius = Integer.parseInt(args[0]);
                    locations = circle(player.getLocation(), radius);
                }
                catch (NumberFormatException e){
                    player.sendMessage("매개변수 형식이 정수가 아닙니다!");
                    return false;
                }
            } else{
                locations = circle(player.getLocation(), 10);
            }

            if (locations == null) {
                player.sendMessage("상자를 발견하지 못했습니다!");
                return false;
            }

            player.sendMessage("상자를 찾았습니다!");
            for (Location location : locations) {
                player.sendMessage(String.format("%s %s %s", location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            }
            return true;
        }
        return false;
    }

    private ArrayList<Location> circle(Location loc, int radius){
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        var list = new ArrayList<Location>();

        for(int x = cx - radius; x <= cx + radius; x++){
            for (int z = cz - radius; z <= cz + radius; z++){
                for(int y = (cy - radius); y < (cy + radius); y++){
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

                    if(dist < radius * radius){
                        Location l = new Location(loc.getWorld(), x, y + 2, z);
                        if (l.getBlock().getType() == Material.CHEST) {
                            list.add(l);
                        }
                    }
                }
            }
        }

        if (list.isEmpty()){
            return null;
        }
        return list;
    }
}
