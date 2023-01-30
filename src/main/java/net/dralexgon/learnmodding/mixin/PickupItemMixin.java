package net.dralexgon.learnmodding.mixin;

import net.dralexgon.learnmodding.ENCHANTED_BOOK_FINDER_STATE;
import net.dralexgon.learnmodding.EnchantedBookFinderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class PickupItemMixin {

    @Shadow public abstract ItemStack getStack();

    @Inject(at = @At("HEAD"), method = "onPlayerCollision")
    private void onPlayerCollisionMixin(PlayerEntity player, CallbackInfo ci) {
        /*
        MinecraftClient mc = MinecraftClient.getInstance();
        if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.MINING && this.getStack().getItem().equals(Items.LECTERN)) {
            mc.player.sendMessage(Text.of("§2 Lectern pickup !"), false);
            EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.WAITING_BARITONE_FINISIH;
        }
        mc.player.sendMessage(Text.of("§2 item pickup !"), false);*/
    }
}
