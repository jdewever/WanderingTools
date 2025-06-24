package xyz.jonasdewever.wanderingtools.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record JoinWithModPayload() implements CustomPayload {

  public static final Id<JoinWithModPayload> ID = new Id<>(Messages.JOIN_WITH_MOD);
  public static final PacketCodec<RegistryByteBuf, JoinWithModPayload> CODEC = PacketCodec.unit(new JoinWithModPayload());

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }
}
