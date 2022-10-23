package mod.francescozucca.misticraft.datagen;

import mod.francescozucca.misticraft.Misticraft;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class Datagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(RecipeGenerator::new);
    }

    private static class RecipeGenerator extends FabricRecipeProvider {

        public RecipeGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
            ShapelessRecipeJsonBuilder.create(Misticraft.ROSE_INCENSE).input(Items.GUNPOWDER).input(Items.CHARCOAL).input(Misticraft.ROSE_POWDER);
        }
    }
}
