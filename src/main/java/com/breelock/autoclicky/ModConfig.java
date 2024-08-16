package com.breelock.autoclicky;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.MinecraftClient;

public class ModConfig {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path configFilePath = Paths.get(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "config", "com.breelock.autoclicky.config.json");

    public static PvP selectedPvp = PvP.Old;
    public static final List<String> pvpSystems = Arrays.asList("Old combat (1.8)", "New combat (1.9+)");

    public static class OldPvP {
        public static int leftMinDelay = Default.leftMinDelay;
        public static int leftMaxDelay = Default.leftMaxDelay;

        public static int rightMinDelay = Default.rightMinDelay;
        public static int rightMaxDelay = Default.rightMaxDelay;

        public static boolean firstClickIsInstant = Default.firstClickIsInstant;
        public static boolean showMessage = Default.showMessage;
        public static boolean autoJump = Default.autoJump;
        public static boolean onlyEntity = Default.onlyEntityOldPvP;
    }

    public static class NewPvP {
        public static int leftMinDelay = Default.leftMinDelay;
        public static int leftMaxDelay = Default.leftMaxDelay;

        public static int rightMinDelay = Default.rightMinDelay;
        public static int rightMaxDelay = Default.rightMaxDelay;

        public static boolean firstClickIsInstant = Default.firstClickIsInstant;
        public static boolean showMessage = Default.showMessage;
        public static boolean autoJump = Default.autoJump;
        public static boolean onlyEntity = Default.onlyEntityNewPvP;
    }

    public static class Default {
        public static final int selectedPvp = 0;

        public static final int leftMinDelay = 0;
        public static final int leftMaxDelay = 1;

        public static final int rightMinDelay = 0;
        public static final int rightMaxDelay = 1;

        public static final boolean firstClickIsInstant = true;
        public static final boolean showMessage = true;
        public static final boolean autoJump = false;

        public static boolean onlyEntityNewPvP = true;
        public static boolean onlyEntityOldPvP = false;
    }

    public enum PvP {
        Old,
        New
    }

    public static void save() {
        JsonObject config = new JsonObject();

        config.addProperty("selectedPvp", selectedPvp.ordinal());

        config.addProperty("OldPvP.leftMinDelay", OldPvP.leftMinDelay);
        config.addProperty("OldPvP.leftMaxDelay", OldPvP.leftMaxDelay);

        config.addProperty("OldPvP.rightMinDelay", OldPvP.rightMinDelay);
        config.addProperty("OldPvP.rightMaxDelay", OldPvP.rightMaxDelay);

        config.addProperty("OldPvP.firstClickIsInstant", OldPvP.firstClickIsInstant);
        config.addProperty("OldPvP.showMessage", OldPvP.showMessage);
        config.addProperty("OldPvP.autoJump", OldPvP.autoJump);
        config.addProperty("OldPvP.onlyEntity", OldPvP.onlyEntity);

        config.addProperty("NewPvP.leftMinDelay", NewPvP.leftMinDelay);
        config.addProperty("NewPvP.leftMaxDelay", NewPvP.leftMaxDelay);

        config.addProperty("NewPvP.rightMinDelay", NewPvP.rightMinDelay);
        config.addProperty("NewPvP.rightMaxDelay", NewPvP.rightMaxDelay);

        config.addProperty("NewPvP.firstClickIsInstant", NewPvP.firstClickIsInstant);
        config.addProperty("NewPvP.showMessage", NewPvP.showMessage);
        config.addProperty("NewPvP.autoJump", NewPvP.autoJump);
        config.addProperty("NewPvP.onlyEntity", NewPvP.onlyEntity);

        try (FileWriter writer = new FileWriter(configFilePath.toFile())) {
            gson.toJson(config, writer);
            AutoClicky.LOGGER.info("AutoClicky mod configuration saved");
        } catch (IOException e) {
            AutoClicky.LOGGER.error("AutoClicky mod failed to save configuration!", e);
        }
    }

    public static void load() {
        if (configFilePath.toFile().exists()) {
            try (FileReader reader = new FileReader(configFilePath.toFile())) {
                JsonObject config = gson.fromJson(reader, JsonObject.class);

                selectedPvp = PvP.values()[config.has("selectedPvp") ? config.get("selectedPvp").getAsInt() : Default.selectedPvp];

                OldPvP.leftMinDelay = config.has("OldPvP.leftMinDelay") ? config.get("OldPvP.leftMinDelay").getAsInt() : Default.leftMinDelay;
                OldPvP.leftMaxDelay = config.has("OldPvP.leftMaxDelay") ? config.get("OldPvP.leftMaxDelay").getAsInt() : Default.leftMaxDelay;

                OldPvP.rightMinDelay = config.has("OldPvP.rightMinDelay") ? config.get("OldPvP.rightMinDelay").getAsInt() : Default.rightMinDelay;
                OldPvP.rightMaxDelay = config.has("OldPvP.rightMaxDelay") ? config.get("OldPvP.rightMaxDelay").getAsInt() : Default.rightMaxDelay;

                OldPvP.firstClickIsInstant = config.has("OldPvP.firstClickIsInstant") ? config.get("OldPvP.firstClickIsInstant").getAsBoolean() : Default.firstClickIsInstant;
                OldPvP.showMessage = config.has("OldPvP.showMessage") ? config.get("OldPvP.showMessage").getAsBoolean() : Default.showMessage;
                OldPvP.autoJump = config.has("OldPvP.autoJump") ? config.get("OldPvP.autoJump").getAsBoolean() : Default.autoJump;
                OldPvP.onlyEntity = config.has("OldPvP.onlyEntity") ? config.get("OldPvP.onlyEntity").getAsBoolean() : Default.onlyEntityOldPvP;

                NewPvP.leftMinDelay = config.has("NewPvP.leftMinDelay") ? config.get("NewPvP.leftMinDelay").getAsInt() : Default.leftMinDelay;
                NewPvP.leftMaxDelay = config.has("NewPvP.leftMaxDelay") ? config.get("NewPvP.leftMaxDelay").getAsInt() : Default.leftMaxDelay;

                NewPvP.rightMinDelay = config.has("NewPvP.rightMinDelay") ? config.get("NewPvP.rightMinDelay").getAsInt() : Default.rightMinDelay;
                NewPvP.rightMaxDelay = config.has("NewPvP.rightMaxDelay") ? config.get("NewPvP.rightMaxDelay").getAsInt() : Default.rightMaxDelay;

                NewPvP.firstClickIsInstant = config.has("NewPvP.firstClickIsInstant") ? config.get("NewPvP.firstClickIsInstant").getAsBoolean() : Default.firstClickIsInstant;
                NewPvP.showMessage = config.has("NewPvP.showMessage") ? config.get("NewPvP.showMessage").getAsBoolean() : Default.showMessage;
                NewPvP.autoJump = config.has("NewPvP.autoJump") ? config.get("NewPvP.autoJump").getAsBoolean() : Default.autoJump;
                NewPvP.onlyEntity = config.has("NewPvP.onlyEntity") ? config.get("NewPvP.onlyEntity").getAsBoolean() : Default.onlyEntityNewPvP;

                AutoClicky.LOGGER.info("AutoClicky mod loaded user configuration");
            } catch (IOException e) {
                AutoClicky.LOGGER.error("AutoClicky mod failed to load configuration!", e);
            }
        }
        else {
            loadDefault();
            AutoClicky.LOGGER.info("AutoClicky mod loaded default configuration");
        }
    }

    public static void loadDefault() {
        selectedPvp = PvP.values()[Default.selectedPvp];

        OldPvP.leftMinDelay = Default.leftMinDelay;
        OldPvP.leftMaxDelay = Default.leftMaxDelay;

        OldPvP.rightMinDelay = Default.rightMinDelay;
        OldPvP.rightMaxDelay = Default.rightMaxDelay;

        OldPvP.firstClickIsInstant = Default.firstClickIsInstant;
        OldPvP.showMessage = Default.showMessage;
        OldPvP.autoJump = Default.autoJump;
        OldPvP.onlyEntity = Default.onlyEntityOldPvP;

        NewPvP.leftMinDelay = Default.leftMinDelay;
        NewPvP.leftMaxDelay = Default.leftMaxDelay;

        NewPvP.rightMinDelay = Default.rightMinDelay;
        NewPvP.rightMaxDelay = Default.rightMaxDelay;

        NewPvP.firstClickIsInstant = Default.firstClickIsInstant;
        NewPvP.showMessage = Default.showMessage;
        NewPvP.autoJump = Default.autoJump;
        NewPvP.onlyEntity = Default.onlyEntityNewPvP;
    }
}
