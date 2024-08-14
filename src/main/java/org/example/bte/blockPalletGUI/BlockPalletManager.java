package org.example.bte.blockPalletGUI;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class BlockPalletManager {

    private static final int PAGE_SIZE = 45;

    public void openBlockPallet(Player player, int page, String type) {
        ItemStack[] items;
        switch (type.toLowerCase()) {
            case "slabs":
                items = MenuItems.getSlabs();
                break;
            case "stairs":
                items = MenuItems.getStairs();
                break;
            case "walls":
                items = MenuItems.getWalls();
                break;
            default:
                items = MenuItems.getBlocksByColor();
        }

        int totalPages = (int) Math.ceil((double) items.length / PAGE_SIZE);

        String title = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase() + " Pallet - Page " + (page + 1) + "/" + totalPages;
        Inventory gui = Bukkit.createInventory(null, 54, title);

        int startIndex = page * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, items.length);
        for (int i = startIndex; i < endIndex; i++) {
            gui.setItem(i - startIndex, items[i]);
        }

        if (page > 0) {
            gui.setItem(45, createNavigationItem("Previous Page", "Click to go to the previous page"));
        }
        if (page < totalPages - 1) {
            gui.setItem(53, createNavigationItem("Next Page", "Click to go to the next page"));
        }

        player.openInventory(gui);
    }

    public ItemStack createNavigationItem(String name, String lore) {
        ItemStack item = XMaterial.PAPER.parseItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Collections.singletonList(lore));
            item.setItemMeta(meta);
        }
        return item;
    }
}
