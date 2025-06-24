package xyz.jonasdewever.wanderingtools.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.jonasdewever.wanderingtools.network.Messages;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
  @Shadow
  @Final
  private MinecraftClient client;

  @Inject(at = @At("RETURN"), method = "getLeftText")
  protected void getLeftText(CallbackInfoReturnable<List<String>> cir) {
    int chance = Messages.chance;
    int timer = Messages.timer;
    int delay = Messages.delay;

    String prefix = "W.Info:// ";

    cir.getReturnValue().add(prefix + "Spawn Chance: " + chance + "%");
    cir.getReturnValue().add(prefix + "Ticks until attempt: " + (delay - (1200 - timer)));
  }
}
