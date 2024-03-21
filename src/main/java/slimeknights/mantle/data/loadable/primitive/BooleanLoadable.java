package slimeknights.mantle.data.loadable.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import slimeknights.mantle.data.loadable.Loadable;
import slimeknights.mantle.data.loadable.field.LoadableField;

import java.util.function.Function;

/** Loadable for a boolean */
public enum BooleanLoadable implements Loadable<Boolean> {
  INSTANCE;

  @Override
  public Boolean convert(JsonElement element, String key) {
    return GsonHelper.convertToBoolean(element, key);
  }

  @Override
  public JsonElement serialize(Boolean object) {
    return new JsonPrimitive(object);
  }

  @Override
  public Boolean fromNetwork(FriendlyByteBuf buffer) {
    return buffer.readBoolean();
  }

  @Override
  public void toNetwork(Boolean object, FriendlyByteBuf buffer) {
    buffer.writeBoolean(object);
  }

  @Override
  public <P> LoadableField<Boolean,P> defaultField(String key, Boolean defaultValue, Function<P,Boolean> getter) {
    // booleans are cleaner if they serialize by default
    return defaultField(key, defaultValue, true, getter);
  }
}
