package xyz.jonasdewever.wanderingtools.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record LeaveWithModPayload() implements CustomPayload {

  public static final Id<LeaveWithModPayload> ID = new Id<>(Messages.LEAVE_WITH_MOD);
  public static final PacketCodec<RegistryByteBuf, LeaveWithModPayload> CODEC = PacketCodec.unit(new LeaveWithModPayload());

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }
}
