package xyz.jonasdewever.wanderingtools;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.World;
import net.minecraft.world.spawner.SpecialSpawner;
import xyz.jonasdewever.wanderingtools.access.ServerWorldAccessor;
import xyz.jonasdewever.wanderingtools.access.WanderingTraderManagerAccess;
import xyz.jonasdewever.wanderingtools.network.InfoIDPayload;
import xyz.jonasdewever.wanderingtools.network.JoinWithModPayload;
import xyz.jonasdewever.wanderingtools.network.LeaveWithModPayload;

import java.util.ArrayList;
import java.util.List;

public class Main implements ModInitializer {
  public static final String MOD_ID = "wandering-tools";
  public static List<ServerPlayerEntity> moddedPlayers = new ArrayList<>();

  @Override
  public void onInitialize() {

    PayloadTypeRegistry.playS2C().register(InfoIDPayload.ID, InfoIDPayload.CODEC);
    PayloadTypeRegistry.playC2S().register(JoinWithModPayload.ID, JoinWithModPayload.CODEC);
    PayloadTypeRegistry.playC2S().register(LeaveWithModPayload.ID, LeaveWithModPayload.CODEC);

    ServerTickEvents.START_WORLD_TICK.register(serverWorld -> {
      List<SpecialSpawner> spawners = ((ServerWorldAccessor) serverWorld.getServer().getWorld(World.OVERWORLD)).spawners();
      WanderingTraderManager wanderingManager = (WanderingTraderManager) spawners.get(4);
      WanderingTraderManagerAccess wanderingAccess = (WanderingTraderManagerAccess) wanderingManager;
      
      int chance = wanderingAccess.spawnChance();
      int timer = wanderingAccess.spawnTimer();
      int delay = wanderingAccess.spawnDelay();

      if (!moddedPlayers.isEmpty()) {
        for (ServerPlayerEntity player : moddedPlayers) {
          ServerPlayNetworking.send(player, new InfoIDPayload(timer, delay, chance));
        }
      }
    });

    ServerPlayNetworking.registerGlobalReceiver(JoinWithModPayload.ID, (payload, context) -> {
      moddedPlayers.add(context.player());
    });

    ServerPlayNetworking.registerGlobalReceiver(LeaveWithModPayload.ID, (payload, context) -> {
      moddedPlayers.remove(context.player());
    });
  }
}
