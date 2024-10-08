package dev.tocraft.eomantle.client.model.fluid;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import dev.tocraft.eomantle.client.model.util.ColoredBlockModel;
import dev.tocraft.eomantle.client.model.util.SimpleBlockModel;

import java.util.List;
import java.util.function.Function;

/**
 * This model contains a list of fluid cuboids for the sake of rendering multiple fluid regions in world. It is used by the faucet at this time
 */
@AllArgsConstructor
public class FluidsModel implements IUnbakedGeometry<FluidsModel> {
  public static final IGeometryLoader<FluidsModel> LOADER = FluidsModel::deserialize;

  private final SimpleBlockModel model;
  private final List<FluidCuboid> fluids;

  @Override
  public void resolveParents(Function<ResourceLocation,UnbakedModel> modelGetter, IGeometryBakingContext context) {
    model.resolveParents(modelGetter, context);
  }

  @Override
  public BakedModel bake(IGeometryBakingContext owner, ModelBaker baker, Function<Material,TextureAtlasSprite> spriteGetter, ModelState transform, ItemOverrides overrides, ResourceLocation location) {
    BakedModel baked = model.bake(owner, baker, spriteGetter, transform, overrides, location);
    return new Baked(baked, fluids);
  }

  /** Baked model, mostly a data wrapper around a normal model */
  @SuppressWarnings("WeakerAccess")
  public static class Baked extends BakedModelWrapper<BakedModel> {
    @Getter
    private final List<FluidCuboid> fluids;
    public Baked(BakedModel originalModel, List<FluidCuboid> fluids) {
      super(originalModel);
      this.fluids = fluids;
    }
  }

  /** Deserializes the model from JSON */
  public static FluidsModel deserialize(JsonObject json, JsonDeserializationContext context) {
    ColoredBlockModel model = ColoredBlockModel.deserialize(json, context);
    List<FluidCuboid> fluid = FluidCuboid.listFromJson(json, "fluids");
    return new FluidsModel(model, fluid);
  }
}
