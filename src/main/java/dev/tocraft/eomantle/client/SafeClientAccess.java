package dev.tocraft.eomantle.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;

/** Class to add one level of static indirection to client only lookups */
public class SafeClientAccess {
  /** Gets the currently pressed key for tooltips, returns UNKNOWN on a server */
  public static TooltipKey getTooltipKey() {
    if (FMLEnvironment.dist == Dist.CLIENT) {
      return ClientOnly.getPressedKey();
    }
    return TooltipKey.UNKNOWN;
  }

  /** Gets the client player entity, or null on a server */
  @Nullable
  public static Player getPlayer() {
    if (FMLEnvironment.dist == Dist.CLIENT) {
      return ClientOnly.getClientPlayer();
    }
    return null;
  }

  /** Gets the client player entity, or null on a server */
  @Nullable
  public static Level getLevel() {
    if (FMLEnvironment.dist == Dist.CLIENT) {
      return ClientOnly.getClientLevel();
    }
    return null;
  }

  /** Gets the registry access client side */
  @Nullable
  public static RegistryAccess getRegistryAccess() {
    Level level = getLevel();
    if (level != null) {
      return level.registryAccess();
    }
    return null;
  }

  /** This class is only loaded on the client, so is safe to reference client only methods */
  private static class ClientOnly {
    /** Gets the currently pressed key modifier for tooltips */
    public static TooltipKey getPressedKey() {
      if (Screen.hasShiftDown()) {
        return TooltipKey.SHIFT;
      }
      if (Screen.hasControlDown()) {
        return TooltipKey.CONTROL;
      }
      if (Screen.hasAltDown()) {
        return TooltipKey.ALT;
      }
      return TooltipKey.NORMAL;
    }

    /** Gets the client player instance */
    @Nullable
    public static Player getClientPlayer() {
      return Minecraft.getInstance().player;
    }

    /** Gets the client level instance */
    @Nullable
    public static Level getClientLevel() {
      return Minecraft.getInstance().level;
    }
  }
}
