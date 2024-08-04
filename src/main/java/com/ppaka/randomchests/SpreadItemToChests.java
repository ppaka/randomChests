package com.ppaka.randomchests;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpreadItemToChests implements TabExecutor {
    CoolInventory inv;

    public SpreadItemToChests(CoolInventory inv) {
        this.inv = inv;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            ArrayList<Location> locations;
            int randomCount;

            if (args.length == 2) {
                try {
                    var radius = Integer.parseInt(args[0]);
                    randomCount = Integer.parseInt(args[1]);
                    locations = circle(player.getLocation(), radius);
                } catch (NumberFormatException e) {
                    player.sendMessage("매개변수 형식이 정수가 아닙니다!");
                    return false;
                }
            } else {
                player.sendMessage("잘못된 명령어입니다!");
                return false;
            }

            if (locations == null) {
                player.sendMessage("상자를 발견하지 못했습니다!");
                return false;
            }

            player.sendMessage("아이템을 뿌리는 중입니다...");

            if (inv.getInventory().isEmpty()) {
                player.sendMessage("경고! 가상 인벤토리가 비어있습니다! /setitemstospread 으로 설정해주세요");
                for (Location location : locations) {
                    Container container = (Container) location.getBlock().getState();
                    container.getInventory().clear();
                }
            }
            else{
                for (Location location : locations) {
                    Container container = (Container) location.getBlock().getState();
                    container.getInventory().clear();
                    var virtualInv = inv.getInventory().getStorageContents();

                    for (int i = 0; i < randomCount; i++) {
                        var randomValue = (int) (Math.random() * virtualInv.length);
                        var item = virtualInv[randomValue];
                        if (item == null) {
                            i--;
                            continue;
                        }
                        container.getInventory().addItem(item);
                    }
                }
            }
            player.sendMessage("작업이 완료되었습니다!");
            return true;
        }
        return false;
    }

    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    private ArrayList<Location> circle(Location loc, int radius) {
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        var list = new ArrayList<Location>();

        int maxY = clamp((cy + radius), 10, 200);
        int minY = clamp((cy - radius), 10, 200);

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                for (int y = minY; y < maxY; y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

                    if (dist < radius * radius) {
                        Location l = new Location(loc.getWorld(), x, y, z);
                        if (l.getBlock().getType() == Material.CHEST || l.getBlock().getType() == Material.BARREL) {
                            list.add(l);
                        }
                    }
                }
            }
        }

        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equals("SpreadItemToChests")) {
            if (args.length == 1) {
                return List.of("<radius>");
            } else if (args.length == 2) {

                try {
                    Integer.parseInt(args[0]);
                    return List.of("<randomCount>");
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
