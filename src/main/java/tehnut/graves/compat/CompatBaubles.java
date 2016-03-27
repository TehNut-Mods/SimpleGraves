package tehnut.graves.compat;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import tehnut.graves.ConfigHandler;
import tehnut.graves.api.IGrave;
import tehnut.graves.api.IGraveSaveable;

public class CompatBaubles implements IGraveSaveable {

    @Override
    public boolean shouldHandle(EntityPlayer player) {
        return ConfigHandler.saveBaubles;
    }

    @Override
    public int handleInventory(IGrave grave, EntityPlayer player, int offset) {
        IInventory baubles = BaublesApi.getBaubles(player);

        for (int slot = 0; slot < baubles.getSizeInventory(); slot++) {
            grave.getHandler().setStackInSlot(slot + offset, baubles.getStackInSlot(slot));
            baubles.setInventorySlotContents(slot, null);
        }

        return baubles.getSizeInventory();
    }

    @Override
    public void dropItems(EntityPlayer player) {
        IInventory baubles = BaublesApi.getBaubles(player);
        InventoryHelper.dropInventoryItems(player.getEntityWorld(), player.getPosition(), baubles);
        baubles.clear();
    }
}
