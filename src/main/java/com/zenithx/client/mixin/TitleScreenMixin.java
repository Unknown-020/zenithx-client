package com.zenithx.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void renderZenithBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        TitleScreen screen = (TitleScreen) (Object) this;
        context.fillGradient(0, 0, screen.width, screen.height, 0xFF09090D, 0xFF1A070D);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderZenithWatermark(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        TitleScreen screen = (TitleScreen) (Object) this;
        context.drawCenteredTextWithShadow(
                screen.getTextRenderer(),
                "§c§lZENITHX CLIENT §8| §7Fast Performance Edition",
                screen.width / 2,
                20,
                0xFFFFFFFF
        );
    }
}
