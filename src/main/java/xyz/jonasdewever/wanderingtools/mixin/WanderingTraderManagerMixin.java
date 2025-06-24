package xyz.jonasdewever.wanderingtools.mixin;

import net.minecraft.world.WanderingTraderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.jonasdewever.wanderingtools.access.WanderingTraderManagerAccess;

@Mixin(WanderingTraderManager.class)
abstract class WanderingTraderManagerMixin implements WanderingTraderManagerAccess {
  @Shadow
  private int spawnChance;
  @Shadow
  private int spawnTimer;
  @Shadow
  private int spawnDelay;

  public int spawnChance() {
    return spawnChance;
  }

  public int spawnTimer() {
    return spawnTimer;
  }

  public int spawnDelay() {
    return spawnDelay;
  }
}
