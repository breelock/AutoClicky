package com.breelock.autoclicky;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.minecraft.client.MinecraftClient;

public class ModConfig {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path configFilePath = Paths.get(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "config", "com.breelock.autoclicky.config.json");

    private static final boolean leftEnabledDef = true;
    private static final int leftMinDelayDef = 0;
    private static final int leftMaxDelayDef = 1;

    private static final boolean rightEnabledDef = true;
    private static final int rightMinDelayDef = 0;
    private static final int rightMaxDelayDef = 1;

    private static final boolean showMessageDef = true;
    private static final boolean firstClickIsInstantDef = true;

    public static void saveConfig() {
        JsonObject config = new JsonObject();

        config.addProperty("leftEnabled", AutoClicky.leftEnabled);
        config.addProperty("leftMinDelay", AutoClicky.leftMinDelay);
        config.addProperty("leftMaxDelay", AutoClicky.leftMaxDelay);

        config.addProperty("rightEnabled", AutoClicky.rightEnabled);
        config.addProperty("rightMinDelay", AutoClicky.rightMinDelay);
        config.addProperty("rightMaxDelay", AutoClicky.rightMaxDelay);

        config.addProperty("showMessage", AutoClicky.showMessage);
        config.addProperty("firstClickIsInstant", AutoClicky.firstClickIsInstant);

        try (FileWriter writer = new FileWriter(configFilePath.toFile())) {
            gson.toJson(config, writer);
            AutoClicky.LOGGER.info("AutoClicky mod configuration saved");
        } catch (IOException e) {
            AutoClicky.LOGGER.error("AutoClicky mod failed to save configuration!", e);
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        if (configFilePath.toFile().exists()) {
            try (FileReader reader = new FileReader(configFilePath.toFile())) {
                JsonObject config = gson.fromJson(reader, JsonObject.class);

                AutoClicky.leftEnabled = config.has("leftEnabled") ? config.get("leftEnabled").getAsBoolean() : leftEnabledDef;
                AutoClicky.leftMinDelay = config.has("leftMinDelay") ? config.get("leftMinDelay").getAsInt() : leftMinDelayDef;
                AutoClicky.leftMaxDelay = config.has("leftMaxDelay") ? config.get("leftMaxDelay").getAsInt() : leftMaxDelayDef;

                AutoClicky.rightEnabled = config.has("rightEnabled") ? config.get("rightEnabled").getAsBoolean() : rightEnabledDef;
                AutoClicky.rightMinDelay = config.has("rightMinDelay") ? config.get("rightMinDelay").getAsInt() : rightMinDelayDef;
                AutoClicky.rightMaxDelay = config.has("rightMaxDelay") ? config.get("rightMaxDelay").getAsInt() : rightMaxDelayDef;

                AutoClicky.showMessage = config.has("showMessage") ? config.get("showMessage").getAsBoolean() : showMessageDef;
                AutoClicky.firstClickIsInstant = config.has("firstClickIsInstant") ? config.get("firstClickIsInstant").getAsBoolean() : firstClickIsInstantDef;

                AutoClicky.LOGGER.info("AutoClicky mod loaded user configuration");
            } catch (IOException e) {
                AutoClicky.LOGGER.error("AutoClicky mod failed to load configuration!", e);
                e.printStackTrace();
            }
        }
        else {
            AutoClicky.leftEnabled = leftEnabledDef;
            AutoClicky.leftMinDelay = leftMinDelayDef;
            AutoClicky.leftMaxDelay = leftMaxDelayDef;

            AutoClicky.rightEnabled = rightEnabledDef;
            AutoClicky.rightMinDelay = rightMinDelayDef;
            AutoClicky.rightMaxDelay = rightMaxDelayDef;

            AutoClicky.showMessage = showMessageDef;
            AutoClicky.firstClickIsInstant = firstClickIsInstantDef;

            AutoClicky.LOGGER.info("AutoClicky mod loaded default configuration");
        }
    }
}
