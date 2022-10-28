package mod.francescozucca.misticraft.client.model;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static mod.francescozucca.misticraft.Misticraft.id;

public class ModelProvider implements ModelResourceProvider {
    public static final Identifier MORTAR_MODEL = id("block/mortar");
    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException {
        if(resourceId.equals(MORTAR_MODEL)){
            return new MortarModel();
        }
        return null;
    }
}
