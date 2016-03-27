package tehnut.graves;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    public static boolean placeOnGround;
    public static boolean forceGrave;

    public static boolean saveBaubles;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        String category;

        category = "General";
        placeOnGround = config.getBoolean("placeOnGround", category, true, "On death, lowers the gravestone to the ground.\nReplaces any block that is normally replaceable. (Air, tallgrass, water, etc)");
        forceGrave = config.getBoolean("forceGrave", category, false, "If the grave is not at a valid location, it will force place at the location. This will overwrite any block already there.\nThings likely to be replaced include any block that you can walk in, but cannot place blocks in. (Flowers, Pressure Plates, etc)");

        category = "Compatibility";
        saveBaubles = config.getBoolean("saveBaubles", category, true, "Saves Baubles to the gravestone on death.");

        config.save();
    }
}
