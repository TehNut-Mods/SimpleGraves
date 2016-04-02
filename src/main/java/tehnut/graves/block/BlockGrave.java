package tehnut.graves.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tehnut.graves.SimpleGraves;
import tehnut.graves.block.base.BlockStringContainer;
import tehnut.graves.tile.TileGrave;

import java.util.ArrayList;
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
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && tile instanceof TileGrave)
            ((TileGrave) tile).dropItems();

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile != null && tile instanceof TileGrave) {
            if (!world.isRemote)
                player.addChatComponentMessage(new TextComponentString(I18n.translateToLocalFormatted("chat.graves.herelies", ((TileGrave) tile).getPlayerName())));
        }

        return true;
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
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

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
    }

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
            String[] ret = new String[values().length];
            for (int i = 0; i < values().length; i++)
                ret[i] = values()[i].getName();

            return ret;
        }
    }
}
