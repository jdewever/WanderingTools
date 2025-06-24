package xyz.jonasdewever.wanderingtools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import xyz.jonasdewever.wanderingtools.network.JoinWithModPayload;
import xyz.jonasdewever.wanderingtools.network.LeaveWithModPayload;
import xyz.jonasdewever.wanderingtools.network.Messages;

public class ClientMain implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    Messages.registerPackets();

    ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
      ClientPlayNetworking.send(new JoinWithModPayload());
    });

    ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
      ClientPlayNetworking.send(new LeaveWithModPayload());
    });
  }
}
