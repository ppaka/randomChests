package com.ppaka.randomchests;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CoolInventory implements InventoryHolder {

    private Inventory inventory;

    public CoolInventory() {
        this.inventory = Bukkit.createInventory(this, 54, "상자에 뿌려줄 아이템 목록");
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}