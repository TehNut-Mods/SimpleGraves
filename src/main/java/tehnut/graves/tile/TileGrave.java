package tehnut.graves.tile;

import com.google.common.base.Strings;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import tehnut.graves.api.GraveItemHandler;
import tehnut.graves.api.IGrave;

import java.util.Random;

public class TileGrave extends TileEntity implements IGrave {

    private static final String[] randomNames = { "Rick Astley", "David Bowie", "Alan Rickman", "Abe Vigoda", "Bird Person", "your hopes and dreams" };

    private GraveItemHandler handler = new GraveItemHandler(200);
    private String playerName;
    private boolean playerPlaced;

    public TileGrave() {
        Random random = new Random();
        playerName = randomNames[random.nextInt(randomNames.length)];
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) handler;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        NBTTagCompound handlerTag = handler.serializeNBT();
        tag.setTag("handler", handlerTag);
        tag.setString("name", Strings.isNullOrEmpty(getPlayerName()) ? "" : getPlayerName());
        tag.setBoolean("playerPlaced", isPlayerPlaced());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("handler"))
            handler.deserializeNBT((NBTTagCompound) tag.getTag("handler"));
        if (tag.hasKey("name"))
            setPlayerName(tag.getString("name"));
        setPlayerPlaced(tag.getBoolean("playerPlaced"));
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new SPacketUpdateTileEntity(getPos(), -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    public void dropItems() {
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            if (handler.getStackInSlot(slot) != null) {
                float baseOff = 0.5F;
                double xOff = (double)(getWorld().rand.nextFloat() * baseOff) + (double)(1.0F - baseOff) * 0.5D;
                double yOff = (double)(getWorld().rand.nextFloat() * baseOff) + (double)(1.0F - baseOff) * 0.5D;
                double zOff = (double)(getWorld().rand.nextFloat() * baseOff) + (double)(1.0F - baseOff) * 0.5D;

                EntityItem entityItem = new EntityItem(getWorld(), getPos().getX() + xOff, getPos().getY() + yOff, getPos().getZ() + zOff, handler.getStackInSlot(slot));
                getWorld().spawnEntityInWorld(entityItem);
            }
        }
    }

    @Override
    public GraveItemHandler getHandler() {
        return handler;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean isPlayerPlaced() {
        return playerPlaced;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerPlaced(boolean playerPlaced) {
        this.playerPlaced = playerPlaced;
    }
}
