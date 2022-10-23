package mod.francescozucca.misticraft.recipes;

import com.google.gson.JsonObject;
import mod.francescozucca.misticraft.blocks.entity.MortarBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MortarRecipe implements Recipe<MortarBlockEntity> {

    private final Ingredient input;
    private final ItemStack output;
    private final Identifier id;

    public MortarRecipe(Ingredient input, ItemStack output, Identifier id){
        this.input = input;
        this.output = output;
        this.id = id;
    }

    public Ingredient getInput(){
        return input;
    }

    @Override
    public boolean matches(MortarBlockEntity inventory, World world) {
        if(inventory.getItems().size() != 1) return false;
        return input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(MortarBlockEntity inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MortarRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<MortarRecipe>{
        private Type(){}
        public static final Type INSTANCE = new Type();

        public static final String ID = "one_slot_recipe";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    class MortarRecipeJsonFormat{
        JsonObject input;
        String output;
        int amount;
    }
}
