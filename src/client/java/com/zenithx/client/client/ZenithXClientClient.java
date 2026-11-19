package com.zenithx.client.client;

import com.zenithx.client.gui.ZenithClickGui;
import com.zenithx.client.hud.HudElement;
import com.zenithx.client.hud.HudManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZenithXClientClient implements ClientModInitializer {
    public static boolean toggleSprintEnabled = true;
    public static boolean hudEnabled = true;
    private static KeyBinding clickGuiKey;
    private static boolean wasAttackDown = false;
    private static boolean wasUseDown = false;

    @Override
    public void onInitializeClient() {
        HudManager.init();

        clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.zenithx.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.zenithx.client"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.wasPressed()) {
                client.setScreen(new ZenithClickGui());
            }

            if (toggleSprintEnabled && client.player != null
                    && client.options.forwardKey.isPressed()
                    && !client.player.isSneaking()) {
                client.player.setSprinting(true);
            }

            boolean attackDown = client.options.attackKey.isPressed();
            if (attackDown && !wasAttackDown) HudManager.registerLeftClick();
            wasAttackDown = attackDown;

            boolean useDown = client.options.useKey.isPressed();
            if (useDown && !wasUseDown) HudManager.registerRightClick();
            wasUseDown = useDown;
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (!hudEnabled || mc.player == null || mc.getDebugHud().shouldShowDebugHud()) return;

            for (HudElement el : HudManager.ELEMENTS) {
                el.render(drawContext);
            }
        });
    }
}
