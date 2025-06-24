package xyz.jonasdewever.wanderingtools.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(ServerWorld.class)
abstract class ServerWorldMessageMixin {
  @Inject(method = "spawnEntity", at = @At("TAIL"))
  private void onEntitySpawn(Entity entity, CallbackInfoReturnable<Boolean> cir) {
    if (entity instanceof WanderingTraderEntity wandy && cir.getReturnValue()) {

      ServerWorld world = (ServerWorld) (Object) this;
      BlockPos position = entity.getBlockPos();

      boolean useName = true;
      String wandyName = entity.getName().getString();
      if (wandyName.equals("Wandering Trader")) useName = false;

      StringBuilder message = new StringBuilder("🧉 A Trader ");
      if (useName) message.append(String.format("%s ", wandyName));
      message.append(String.format("has wandered into this realm at approx. X: %d, Y: %d, Z: %d\n",
              position.getX(), position.getY(), position.getZ()));

      List<TradeOffer> offers = wandy.getOffers();
      Set<Item> itemsWeWant = Set.of(
              Items.MOSS_BLOCK,
              Items.PALE_MOSS_BLOCK,
              Items.PACKED_ICE,
              Items.BLUE_ICE,
              Items.KELP,
              Items.SLIME_BALL,
              Items.POINTED_DRIPSTONE,
              Items.SAND,
              Items.RED_SAND,
              Items.ROOTED_DIRT,
              Items.PODZOL,
              Items.ACACIA_SAPLING,
              Items.BIRCH_SAPLING,
              Items.CHERRY_SAPLING,
              Items.SPRUCE_SAPLING,
              Items.JUNGLE_SAPLING,
              Items.DARK_OAK_SAPLING,
              Items.OAK_SAPLING,
              Items.PALE_OAK_SAPLING,
              Items.MANGROVE_PROPAGULE
      );
      Set<Item> legendaryItems = Set.of(
              Items.MOSS_BLOCK,
              Items.POINTED_DRIPSTONE
      );
      HashSet<Item> notableItems = new HashSet<>();

      for (TradeOffer offer : offers) {
        ItemStack output = offer.getSellItem();
        if (itemsWeWant.contains(output.getItem())) {
          notableItems.add(output.getItem());
        }
      }

      if (notableItems.isEmpty()) {
        message.append("\nThey are awaiting execution for their failure to present good trades.");
      } else {
        for (Item item : notableItems) {
          if (legendaryItems.contains(item)) {
            message.append("\nLegendary Item: ").append(item.getName().getString());
            world.playSound(
                    null,
                    position,
                    SoundEvents.BLOCK_END_PORTAL_SPAWN,
                    SoundCategory.MASTER,
                    5.0f,
                    1.0f
            );
          } else {
            message.append("\nBro has: ").append(item.getName().getString());
          }
        }
      }

      world.getServer().getPlayerManager().broadcast(
              Text.literal(message.toString()).formatted(Formatting.GOLD),
              false
      );
    }
  }
}
