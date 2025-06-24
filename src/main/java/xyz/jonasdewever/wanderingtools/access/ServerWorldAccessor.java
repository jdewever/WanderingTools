package xyz.jonasdewever.wanderingtools.access;

import net.minecraft.world.spawner.SpecialSpawner;

import java.util.List;

public interface ServerWorldAccessor {
  List<SpecialSpawner> spawners();
}
