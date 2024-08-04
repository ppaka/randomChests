package com.ppaka.randomchests;

import org.bukkit.plugin.java.JavaPlugin;

public final class RandomChests extends JavaPlugin {
    CoolInventory kitInv;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onEnable() {
        getLogger().info("플러그인이 활성화되었습니다.");
        kitInv = new CoolInventory();
        var cmd1 = new SpreadItemToChests(kitInv);
        getCommand("SpreadItemToChests").setExecutor(cmd1);
        getCommand("SpreadItemToChests").setTabCompleter(cmd1);
        getCommand("SetItemsToSpread").setExecutor(new KitConfigCommand(kitInv));
    }

    @Override
    public void onDisable() {
        getLogger().info("플러그인이 비활성화되었습니다.");
    }
}
