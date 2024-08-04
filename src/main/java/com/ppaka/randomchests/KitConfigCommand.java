package com.ppaka.randomchests;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public class KitConfigCommand implements TabExecutor {
    final CoolInventory inv;

    public KitConfigCommand(CoolInventory inv) {
        this.inv = inv;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player player) {
            if (!player.isOp()) {
                player.sendMessage("관리자만 사용할 수 있습니다.");
                return true;
            }
            inv.open(player);
            return true;
        }
        else if (sender instanceof ConsoleCommandSender) {   //명령어 사용자가 콘솔인 경우
            sender.sendMessage("콘솔에서는 이 명령어를 실행할 수 없습니다.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equals("SetItemsToSpread")) {
            return null;
        }
        return null;
    }
}
