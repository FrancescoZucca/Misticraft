package mod.francescozucca.misticraft.datagen;

import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.block.Burner;
import mod.francescozucca.misticraft.block.Mortar;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class Datagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(RecipeGenerator::new);
        fabricDataGenerator.addProvider(ModelGenerator::new);
    }

    private static class RecipeGenerator extends FabricRecipeProvider {

        public RecipeGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
            ShapelessRecipeJsonBuilder.create(Misticraft.ROSE_INCENSE).input(Items.GUNPOWDER).input(Misticraft.COAL_POWDER).input(Misticraft.ROSE_POWDER);
            ShapelessRecipeJsonBuilder.create(Misticraft.ROSE_INCENSE).input(Items.GUNPOWDER).input(Misticraft.CHARCOAL_POWDER).input(Misticraft.ROSE_POWDER);
        }
    }

    private static class ModelGenerator extends FabricModelProvider{

        public ModelGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(Misticraft.MORTAR)
                    .coordinate(BlockStateVariantMap.create(Mortar.EMPTY)
                            .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/mortar")))
                            .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/mortar")))
                    )
            );
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(Misticraft.BURNER)
                    .coordinate(BlockStateVariantMap.create(Burner.LIT)
                            .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/burner_lit")))
                            .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/burner")))
                    )
            );
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(Misticraft.ROSE_POWDER, Models.GENERATED);
            itemModelGenerator.register(Misticraft.ROSE_INCENSE, Models.GENERATED);
            itemModelGenerator.register(Misticraft.PESTLE, Models.GENERATED);
            itemModelGenerator.register(Misticraft.CHARCOAL_POWDER, Models.GENERATED);
            itemModelGenerator.register(Misticraft.COAL_POWDER, Models.GENERATED);
        }
    }
}
