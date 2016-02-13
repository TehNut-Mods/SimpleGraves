package tehnut.graves.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockGrave extends ItemBlock {

    public ItemBlockGrave(Block block) {
        super(block);

        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
