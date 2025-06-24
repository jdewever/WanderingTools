package xyz.jonasdewever.wanderingtools.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.SpecialSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.jonasdewever.wanderingtools.access.ServerWorldAccessor;

import java.util.List;

@Mixin(ServerWorld.class)
public abstract class ServerWorldInfoMixin implements ServerWorldAccessor {
  @Shadow
  @Final
  private List<SpecialSpawner> spawners;

  public List<SpecialSpawner> spawners() {
    return this.spawners;
  }
}
