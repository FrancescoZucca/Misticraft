package mod.francescozucca.misticraft;

import mod.francescozucca.misticraft.block.Burner;
import mod.francescozucca.misticraft.block.Mortar;
import mod.francescozucca.misticraft.block.entity.BurnerBlockEntity;
import mod.francescozucca.misticraft.block.entity.MortarBlockEntity;
import mod.francescozucca.misticraft.effect.RoseIncenseStatusEffect;
import mod.francescozucca.misticraft.item.RoseIncense;
import mod.francescozucca.misticraft.recipe.MortarRecipe;
import mod.francescozucca.misticraft.recipe.MortarRecipeSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;

public class Misticraft implements ModInitializer {

    public static String MODID = "misticraft";
    public static ItemGroup MISTICRAFT_IG;

    public static Item ROSE_INCENSE;
    public static StatusEffect ROSE_INCENSE_SE;
    public static Item ROSE_POWDER;
    public static Item PESTLE;
    public static Item COAL_POWDER;
    public static Item CHARCOAL_POWDER;

    public static DefaultParticleType ROSE_INCENSE_PT;


    public static Block MORTAR;
    public static BlockEntityType<MortarBlockEntity> MORTAR_BET;

    public static Block BURNER;
    public static BlockEntityType<BurnerBlockEntity> BURNER_BET;

    public static Block SALT_ORE;
    public static Item SALT_CRYSTAL;
    public static Item COARSE_SALT;
    public static ConfiguredFeature<?, ?> SALT_ORE_CF;

    public static PlacedFeature SALT_ORE_PF;

    @Override
    public void onInitialize() {

        MISTICRAFT_IG =  FabricItemGroupBuilder.build(
                id("general"),
                () -> new ItemStack(Items.BREWING_STAND)
        );

        Registry.register(Registry.RECIPE_SERIALIZER, MortarRecipeSerializer.ID, MortarRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, id(MortarRecipe.Type.ID), MortarRecipe.Type.INSTANCE);
        ROSE_INCENSE = registerItem(new RoseIncense(new Item.Settings().group(MISTICRAFT_IG)),"rose_incense");
        ROSE_INCENSE_SE = Registry.register(Registry.STATUS_EFFECT, id("rose_incense"), new RoseIncenseStatusEffect());
        ROSE_INCENSE_PT = Registry.register(Registry.PARTICLE_TYPE, id("rose_incense"), FabricParticleTypes.simple());
        ROSE_POWDER = registerItem("rose_powder");
        PESTLE = registerItem("pestle");
        MORTAR = registerBlock(new Mortar(FabricBlockSettings.of(Material.STONE)), "mortar");
        CHARCOAL_POWDER = registerItem("charcoal_powder");
        COAL_POWDER = registerItem("coal_powder");
        MORTAR_BET = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("mortar"), FabricBlockEntityTypeBuilder.create(MortarBlockEntity::new, MORTAR).build(null));
        BURNER = registerBlock(new Burner(FabricBlockSettings.of(Material.METAL)), "burner");
        BURNER_BET = Registry.register(Registry.BLOCK_ENTITY_TYPE, id("burner"), FabricBlockEntityTypeBuilder.create(BurnerBlockEntity::new, BURNER).build(null));
        SALT_CRYSTAL = registerItem("salt_crystal");
        COARSE_SALT = registerItem("coarse_salt");
        SALT_ORE = registerBlock(new Block(AbstractBlock.Settings.of(Material.STONE)), "salt_ore");

        SALT_ORE_CF = new ConfiguredFeature<>(
                Feature.ORE,
                new OreFeatureConfig(
                        OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                        SALT_ORE.getDefaultState(),
                        5
                )
        );

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id("salt_ore"), SALT_ORE_CF);

        SALT_ORE_PF = new PlacedFeature(
                RegistryEntry.of(SALT_ORE_CF),
                Arrays.asList(
                        CountPlacementModifier.of(10),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))
                )
        );
        Registry.register(BuiltinRegistries.PLACED_FEATURE, id("salt_ore"), SALT_ORE_PF);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id("salt_ore")));
    }

    private static Block registerBlock(Block block, String id, Item item){
        if(item != null)
            registerItem(item, id);

        return Registry.register(Registry.BLOCK, id(id), block);
    }

    private static Block registerBlock(Block block, String id){
        return registerBlock(block, id, new BlockItem(block, new Item.Settings().group(MISTICRAFT_IG)));
    }

    private static Item registerItem(Item item, String id){
        return Registry.register(Registry.ITEM, id(id), item);
    }

    private static Item registerItem(String id){
        return registerItem(default_item(), id);
    }

    public static Identifier id(String name){
        return new Identifier(MODID, name);
    }

    private static Item default_item(){
        return new Item(new Item.Settings().group(MISTICRAFT_IG));
    }
}
