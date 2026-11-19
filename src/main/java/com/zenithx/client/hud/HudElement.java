package com.zenithx.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public abstract class HudElement {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private final String name;
    public int x, y, width, height;
    public boolean enabled = true;

    public HudElement(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public abstract String getDisplayText();

    public void render(DrawContext context) {
        if (!enabled) return;
        String text = getDisplayText();
        this.width = mc.textRenderer.getWidth(text) + 8;
        this.height = mc.textRenderer.fontHeight + 6;

        context.fill(x, y, x + width, y + height, 0x900B0B0F);
        context.fill(x, y, x + 2, y + height, 0xFFE11D48);
        context.drawTextWithShadow(mc.textRenderer, text, x + 5, y + 3, 0xFFFFFFFF);
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public String getName() { return name; }
}
