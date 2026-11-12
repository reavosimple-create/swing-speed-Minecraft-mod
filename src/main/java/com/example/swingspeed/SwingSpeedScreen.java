package com.example.swingspeed;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SwingSpeedScreen extends Screen {
    private static final float MIN = 0.25f;
    private static final float MAX = 5.0f;
    private static final float[] PRESETS = {0.5f, 1.0f, 2.0f, 3.0f, 5.0f};

    public SwingSpeedScreen() {
        super(Text.literal("Swing Speed"));
    }

    @Override
    protected void init() {
        int cx = width / 2;
        int y = height / 2 - 30;

        addDrawableChild(new SpeedSlider(cx - 100, y, 200, 20));

        // Preset buttons
        int bw = 38, gap = 2;
        int totalW = PRESETS.length * bw + (PRESETS.length - 1) * gap;
        int bx = cx - totalW / 2;
        for (float p : PRESETS) {
            addDrawableChild(ButtonWidget.builder(Text.literal(trim(p) + "x"), b -> {
                SwingSpeedClient.multiplier = p;
                clearAndInit();
            }).dimensions(bx, y + 26, bw, 20).build());
            bx += bw + gap;
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(cx - 50, y + 56, 100, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 50, 0xFFFFFF);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) { // Right Shift toggles the menu closed
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private static String trim(float f) {
        return f == (int) f ? String.valueOf((int) f) : String.valueOf(f);
    }

    private static class SpeedSlider extends SliderWidget {
        SpeedSlider(int x, int y, int w, int h) {
            super(x, y, w, h, Text.empty(),
                    (SwingSpeedClient.multiplier - MIN) / (MAX - MIN));
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(Text.literal(String.format("Swing Speed: %.2fx", toSpeed(value))));
        }

        @Override
        protected void applyValue() {
            SwingSpeedClient.multiplier = toSpeed(value);
        }

        private static float toSpeed(double v) {
            float raw = MIN + (float) v * (MAX - MIN);
            return Math.round(raw * 20f) / 20f; // snap to 0.05
        }
    }
}
