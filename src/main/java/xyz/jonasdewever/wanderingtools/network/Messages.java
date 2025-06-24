package xyz.jonasdewever.wanderingtools.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import xyz.jonasdewever.wanderingtools.Main;

public class Messages {
  public static final Identifier INFO_ID = Identifier.of(Main.MOD_ID, "info");
  public static final Identifier JOIN_WITH_MOD = Identifier.of(Main.MOD_ID, "join_with_mod");
  public static final Identifier LEAVE_WITH_MOD = Identifier.of(Main.MOD_ID, "leave_with_mod");
  public static int timer = 0;
  public static int delay = 0;
  public static int chance = 0;
  public static void registerPackets() {
    ClientPlayNetworking.registerGlobalReceiver(InfoIDPayload.ID, (payload, context) -> {
      context.client().execute(() -> {
        timer = payload.timer();
        chance = payload.chance();
        delay = payload.delay();
      });
    });
  }
}
