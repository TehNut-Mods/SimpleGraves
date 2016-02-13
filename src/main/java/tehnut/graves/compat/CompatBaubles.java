package tehnut.graves.compat;

import baubles.api.BaublesApi;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import tehnut.graves.tile.TileGrave;

public class CompatBaubles {

    public static int handleBaubles(TileGrave grave, EntityPlayer player, int offset) {
        IInventory baubles = BaublesApi.getBaubles(player);

        for (int slot = 0; slot < baubles.getSizeInventory(); slot++) {
            grave.getHandler().setStackInSlot(slot + offset, baubles.getStackInSlot(slot));
            baubles.setInventorySlotContents(slot, null);
        }

        return offset + baubles.getSizeInventory();
    }

    public static void dropBaubles(EntityPlayer player) {
        IInventory baubles = BaublesApi.getBaubles(player);
        InventoryHelper.dropInventoryItems(player.getEntityWorld(), player.getPosition(), baubles);
        baubles.clear();
    }
}
