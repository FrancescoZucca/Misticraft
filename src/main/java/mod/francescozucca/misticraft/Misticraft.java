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
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
