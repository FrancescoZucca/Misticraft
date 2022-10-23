package mod.francescozucca.misticraft;

import mod.francescozucca.misticraft.blocks.Mortar;
import mod.francescozucca.misticraft.blocks.entity.MortarBlockEntity;
import mod.francescozucca.misticraft.recipes.MortarRecipe;
import mod.francescozucca.misticraft.recipes.MortarRecipeSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Misticraft implements ModInitializer {

    public static String MODID = "misticraft";
    public static ItemGroup MISTICRAFT_IG = FabricItemGroupBuilder.build(
                id("general"),
                () -> new ItemStack(Items.BREWING_STAND)
        );

    public static Item ROSE_INCENSE = registerItem("rose_incense");
    public static Item ROSE_POWDER = registerItem("rose_powder");
    public static Item PESTLE = registerItem("pestle");


    public static Block MORTAR = registerBlock(new Mortar(FabricBlockSettings.of(Material.STONE)), "mortar");
    public static BlockEntityType<MortarBlockEntity> MORTAR_BET = BlockEntityType.Builder.create(MortarBlockEntity::new, MORTAR).build(null);

    @Override
    public void onInitialize() {
        Registry.register(Registry.RECIPE_SERIALIZER, MortarRecipeSerializer.ID, MortarRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, id(MortarRecipe.Type.ID), MortarRecipe.Type.INSTANCE);
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
