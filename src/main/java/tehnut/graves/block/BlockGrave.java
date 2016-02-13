package tehnut.graves.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tehnut.graves.SimpleGraves;
import tehnut.graves.block.base.BlockStringContainer;
import tehnut.graves.tile.TileGrave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BlockGrave extends BlockStringContainer {

    public BlockGrave() {
        super(Material.ground, GraveType.names());

        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName(SimpleGraves.MODID + ".grave");
        setRegistryName("BlockGrave");
        setHardness(0.5F);
        setResistance(Float.MAX_VALUE);
        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && tile instanceof TileGrave)
            ((TileGrave) tile).dropItems();

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && tile instanceof TileGrave) {
            if (!world.isRemote)
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chat.graves.herelies", ((TileGrave) tile).getPlayerName())));
        }

        return true;
    }

    public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return new ArrayList<ItemStack>();
    }

//    @Override
//    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
//        IBlockState state = world.getBlockState(pos);
//        GraveType graveType = GraveType.valueOf(state.getValue(getStringProp()));
//
//        switch (graveType) {
//            case UPRIGHT: {
////                setBlockBounds(0.1F, 0F, 0.1F, 0.1F, 0.9F, 0.1F);
//                setBlockBounds(0.05F, 0F, 0.05F, 0.95F, 0.1F, 0.95F);
//                return;
//            }
//            case FLAT: {
//                setBlockBounds(0.05F, 0F, 0.05F, 0.95F, 0.1F, 0.95F);
//                return;
//            }
//            default: {
//                setBlockBounds(0.025F, 0F, 0.025F, 0.975F, 0.025F, 0.975F);
//            }
//        }
//    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileGrave();
    }

    public enum GraveType implements IStringSerializable {
        FLAT;
//        UPRIGHT;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }

        public static String[] names() {
            return Arrays.toString(values()).replaceAll("^.|.$", "").split(", ");
        }
    }
}
