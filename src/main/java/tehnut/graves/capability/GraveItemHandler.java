package tehnut.graves.capability;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class GraveItemHandler extends ItemStackHandler {

    public GraveItemHandler(int amount) {
        super(amount);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return null;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return null;
    }
}
