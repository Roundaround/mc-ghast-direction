package me.roundaround.ghastdirection.mixin;

import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.entity.mob.GhastEntity")
public abstract class GhastEntityMixin {
  @Inject(
      method = "updateYaw", at = @At(
      value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getVelocity()Lnet/minecraft/util/math/Vec3d;"
  ), cancellable = true
  )
  private static void beforeUpdatingYaw(MobEntity ghast, CallbackInfo ci) {
    if (ghast.getVelocity().lengthSquared() < 0.01) {
      ci.cancel();
    }
  }
}
