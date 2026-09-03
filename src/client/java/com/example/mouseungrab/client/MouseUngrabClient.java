package com.example.mouseungrab.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class MouseUngrabClient implements ClientModInitializer {

    public static final String MOD_ID = "mouseungrab";

    // Category shown in Options > Controls > Key Binds
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(MOD_ID, "main")
    );

    // The actual keybind, defaulting to "U"
    private static final KeyMapping UNGRAB_KEY = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key.mouseungrab.ungrab",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_U,
                    CATEGORY
            )
    );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (UNGRAB_KEY.consumeClick()) {
                // Only release if we're actually in-game (no menu open) and the mouse is grabbed.
                if (client.screen == null && client.mouseHandler.isMouseGrabbed()) {
                    client.mouseHandler.releaseMouse();
                }
            }
        });
    }
}
