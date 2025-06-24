package xyz.jonasdewever.wanderingtools.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record InfoIDPayload(int timer, int delay, int chance) implements CustomPayload {

  public static final Id<InfoIDPayload> ID = new Id<>(Messages.INFO_ID);
  public static final PacketCodec<RegistryByteBuf, InfoIDPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, InfoIDPayload::timer, PacketCodecs.INTEGER, InfoIDPayload::delay, PacketCodecs.INTEGER, InfoIDPayload::chance, InfoIDPayload::new);

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }
}
