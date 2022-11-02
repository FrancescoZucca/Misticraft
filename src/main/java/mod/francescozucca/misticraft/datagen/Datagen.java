package mod.francescozucca.misticraft.datagen;

import com.google.common.collect.ImmutableList;
import mod.francescozucca.misticraft.Misticraft;
import mod.francescozucca.misticraft.block.Burner;
import mod.francescozucca.misticraft.block.Candle;
import mod.francescozucca.misticraft.block.Mortar;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
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
        fabricDataGenerator.addProvider(EnglishLangProvider::new);
        fabricDataGenerator.addProvider(ItalianLangProvider::new);
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
            blockStateModelGenerator.registerSimpleCubeAll(Misticraft.SALT_ORE);
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(Misticraft.CANDLE)
                    .coordinate(BlockStateVariantMap.create(Candle.CONSUMED)
                            .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle")))
                            .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle_1")))
                            .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle_2")))
                            .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle_3")))
                            .register(4, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle_4")))
                            .register(5, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle_5")))
                            .register(6, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle_6")))
                            .register(7, BlockStateVariant.create().put(VariantSettings.MODEL, Misticraft.id("block/candle_7")))
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
            itemModelGenerator.register(Misticraft.SALT_CRYSTAL, Models.GENERATED);
            itemModelGenerator.register(Misticraft.COARSE_SALT, Models.GENERATED);
        }
    }

    private static class EnglishLangProvider extends FabricLanguageProvider {
        protected EnglishLangProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add(Misticraft.MISTICRAFT_IG, "Misticraft");
            translationBuilder.add(Misticraft.COAL_POWDER, "Coal Powder");
            translationBuilder.add(Misticraft.CHARCOAL_POWDER, "Charcoal Powder");
            translationBuilder.add(Misticraft.ROSE_INCENSE, "Rose Incense");
            translationBuilder.add(Misticraft.ROSE_POWDER, "Rose Powder");
            translationBuilder.add(Misticraft.BURNER, "Incense Burner");
            translationBuilder.add(Misticraft.MORTAR, "Mortar");
            translationBuilder.add(Misticraft.PESTLE, "Pestle");
            translationBuilder.add(Misticraft.ROSE_INCENSE_SE, "Rose Incense");
        }
    }

    private static class ItalianLangProvider extends FabricLanguageProvider {
        protected ItalianLangProvider(FabricDataGenerator dataGenerator) {
            super(dataGenerator, "it_it");
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add(Misticraft.MISTICRAFT_IG, "Misticraft");
            translationBuilder.add(Misticraft.COAL_POWDER, "Polvere di Carbone");
            translationBuilder.add(Misticraft.CHARCOAL_POWDER, "Polvere di Carbonella");
            translationBuilder.add(Misticraft.ROSE_INCENSE, "Incenso alla Rosa");
            translationBuilder.add(Misticraft.ROSE_POWDER, "Polvere di Rosa");
            translationBuilder.add(Misticraft.BURNER, "Bruciatore d'Incenso");
            translationBuilder.add(Misticraft.MORTAR, "Mortaio");
            translationBuilder.add(Misticraft.PESTLE, "Pestello");
            translationBuilder.add(Misticraft.ROSE_INCENSE_SE, "Incenso alla Rosa");
        }
    }
}


