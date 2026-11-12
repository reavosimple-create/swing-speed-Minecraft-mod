package com.example.swingspeed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class SwingSpeedClient implements ClientModInitializer {
    /** 1.0 = vanilla. Higher = faster swing. */
    public static float multiplier = 1.0f;

    private static final float STEP = 0.25f;
    private static final float MIN = 0.25f;
    private static final float MAX = 5.0f;

    private static KeyBinding faster, slower, reset, menu;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category cat = KeyBinding.Category.create(Identifier.of("swingspeed", "main"));
        faster = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.swingspeed.faster", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_BRACKET, cat));
        slower = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.swingspeed.slower", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_BRACKET, cat));
        reset = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.swingspeed.reset", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, cat));

        menu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.swingspeed.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, cat));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menu.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new SwingSpeedScreen());
            }

            boolean changed = false;
            while (faster.wasPressed()) { multiplier = Math.min(MAX, multiplier + STEP); changed = true; }
            while (slower.wasPressed()) { multiplier = Math.max(MIN, multiplier - STEP); changed = true; }
            while (reset.wasPressed())  { multiplier = 1.0f; changed = true; }

            if (changed && client.player != null) {
                client.player.sendMessage(
                        Text.literal(String.format("Swing speed: %.2fx", multiplier)), true);
            }
        });
    }
}
