package dev.tocraft.eomantle.client.book.action;

import net.minecraft.resources.ResourceLocation;
import dev.tocraft.eomantle.client.book.action.protocol.ActionProtocol;
import dev.tocraft.eomantle.client.screen.book.BookScreen;

import javax.annotation.Nullable;
import java.util.HashMap;

public class StringActionProcessor {

  public static final String PROTOCOL_SEPARATOR = " ";

  private static final HashMap<ResourceLocation, ActionProtocol> protocols = new HashMap<>();

  public static void registerProtocol(ResourceLocation id, ActionProtocol protocol) {
    if (protocols.containsKey(id)) {
      throw new IllegalArgumentException("Protocol " + id + " already registered.");
    }

    protocols.put(id, protocol);
  }

  //Format: modid:action param
  public static void process(@Nullable String action, BookScreen book) {
    if (action == null || !action.contains(PROTOCOL_SEPARATOR)) {
      return;
    }

    String id = action.substring(0, action.indexOf(PROTOCOL_SEPARATOR));
    if(!id.contains(":"))
      id = "eomantle:" + id;

    ResourceLocation protoId = new ResourceLocation(id);
    String protoParam = action.substring(action.indexOf(PROTOCOL_SEPARATOR) + PROTOCOL_SEPARATOR.length());

    if (protocols.containsKey(protoId)) {
      protocols.get(protoId).processCommand(book, protoParam);
    }
  }
}
