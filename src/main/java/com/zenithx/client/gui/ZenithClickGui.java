package com.zenithx.client.gui;

import com.zenithx.client.ZenithXClient;
import com.zenithx.client.hud.HudElement;
import com.zenithx.client.hud.HudManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ZenithClickGui extends Screen {
    private HudElement draggingElement = null;
    private int dragOffsetX, dragOffsetY;

    public ZenithClickGui() {
        super(Text.literal("ZenithX ClickGUI"));
    }

    @Override
    protected void init() {
        int panelX = this.width / 2 - 110;
        int panelY = this.height / 2 - 70;

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("ToggleSprint: " + (ZenithXClient.toggleSprintEnabled ? "§aON" : "§cOFF")),
                btn -> {
                    ZenithXClient.toggleSprintEnabled = !ZenithXClient.toggleSprintEnabled;
                    btn.setMessage(Text.literal("ToggleSprint: " + (ZenithXClient.toggleSprintEnabled ? "§aON" : "§cOFF")));
                }).dimensions(panelX + 10, panelY + 30, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD Overlay: " + (ZenithXClient.hudEnabled ? "§aON" : "§cOFF")),
                btn -> {
                    ZenithXClient.hudEnabled = !ZenithXClient.hudEnabled;
                    btn.setMessage(Text.literal("HUD Overlay: " + (ZenithXClient.hudEnabled ? "§aON" : "§cOFF")));
                }).dimensions(panelX + 10, panelY + 55, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        for (HudElement el : HudManager.ELEMENTS) {
            el.render(context);
        }

        int panelX = this.width / 2 - 110;
        int panelY = this.height / 2 - 70;
        context.fill(panelX, panelY, panelX + 220, panelY + 115, 0xE60B0B0F);
        context.fill(panelX, panelY, panelX + 220, panelY + 2, 0xFFE11D48);
        context.drawCenteredTextWithShadow(this.textRenderer, "§c§lZENITHX §fCLIENT", this.width / 2, panelY + 10, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "§7Drag any HUD box to move it", this.width / 2, panelY + 95, 0xFFAAAAAA);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (HudElement el : HudManager.ELEMENTS) {
                if (el.isHovered(mouseX, mouseY)) {
                    draggingElement = el;
                    dragOffsetX = (int) mouseX - el.x;
                    dragOffsetY = (int) mouseY - el.y;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingElement != null && button == 0) {
            draggingElement.x = (int) mouseX - dragOffsetX;
            draggingElement.y = (int) mouseY - dragOffsetY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingElement = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
