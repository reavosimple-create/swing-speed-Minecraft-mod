package com.example.swingspeed.mixin;

import com.example.swingspeed.SwingSpeedClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    // Vanilla swing lasts ~6 ticks (modified by Haste / Mining Fatigue).
    @Inject(method = "getHandSwingDuration", at = @At("RETURN"), cancellable = true)
    private void swingspeed$scaleSwing(CallbackInfoReturnable<Integer> cir) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if ((Object) this == mc.player) {
            int scaled = Math.max(1, Math.round(cir.getReturnValue() / SwingSpeedClient.multiplier));
            cir.setReturnValue(scaled);
        }
    }
}
