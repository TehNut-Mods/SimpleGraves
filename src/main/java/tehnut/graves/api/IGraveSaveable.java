package tehnut.graves.api;

import net.minecraft.entity.player.EntityPlayer;

public interface IGraveSaveable {

    /**
     * Whether or not the inventory items should be stored in the grave.
     *
     * If true, {@link #handleInventory(IGrave, EntityPlayer, int)} will be called.
     * If false, {@link #dropItems(EntityPlayer)} will be called.
     *
     * @param player - The player who died.
     *
     * @return - If the items should be stored or dropped.
     */
    boolean shouldHandle(EntityPlayer player);

    /**
     * Handles the placement of the items in the grave as well as removing items from the inventory.
     *
     * @param grave  - The Grave tile entity to place items into.
     * @param player - The player who died.
     * @param offset - The current offset position from before your handling.
     *
     * @return - The size of your inventory. If you do not return this correctly, your items will be overwritten by the next IGraveSaveable in the list.
     */
    int handleInventory(IGrave grave, EntityPlayer player, int offset);

    /**
     * If {@link #shouldHandle(EntityPlayer)} is {@code false}, use this to drop the items in world and clear the inventory.
     *
     * @param player - The player who died.
     */
    void dropItems(EntityPlayer player);
}
