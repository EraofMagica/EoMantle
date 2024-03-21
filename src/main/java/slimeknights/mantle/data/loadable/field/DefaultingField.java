package slimeknights.mantle.data.loadable.field;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import slimeknights.mantle.data.loadable.Loadable;

import java.util.function.Function;

/**
 * Optional field with a default value if missing
 * @param <P>  Parent object
 * @param <T>  Loadable type
 */
public record DefaultingField<T,P>(Loadable<T> loadable, String key, T defaultValue, boolean serializeDefault, Function<P,T> getter) implements AlwaysPresentLoadableField<T,P> {
  @Override
  public T get(JsonObject json) throws JsonSyntaxException {
    if (json.has(key)) {
      JsonElement element = json.get(key);
      if (!element.isJsonNull()) {
        return loadable.convert(element, key);
      }
    }
    return defaultValue;
  }

  @Override
  public void serialize(P parent, JsonObject json) {
    T object = getter.apply(parent);
    if (serializeDefault || defaultValue.equals(object)) {
      json.add(key, loadable.serialize(object));
    }
  }
}
