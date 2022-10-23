package mod.francescozucca.misticraft.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.francescozucca.misticraft.Misticraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MortarRecipeSerializer implements RecipeSerializer<MortarRecipe> {

    public static final MortarRecipeSerializer INSTANCE = new MortarRecipeSerializer();
    public static final Identifier ID = Misticraft.id("mortar");

    @Override
    public MortarRecipe read(Identifier id, JsonObject json) {
        MortarRecipe.MortarRecipeJsonFormat recipeJson = new Gson().fromJson(json, MortarRecipe.MortarRecipeJsonFormat.class);

        if(recipeJson.input == null || recipeJson.output == null){
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        if(recipeJson.amount == 0) recipeJson.amount = 1;

        Ingredient input = Ingredient.fromJson(recipeJson.input);
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.output)).orElseThrow(() -> new JsonSyntaxException("No such item: "+recipeJson.output));
        ItemStack output = new ItemStack(outputItem, recipeJson.amount);

        return new MortarRecipe(input, output, id);
    }

    @Override
    public MortarRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient input = Ingredient.fromPacket(buf);
        ItemStack output = buf.readItemStack();
        return new MortarRecipe(input, output, id);
    }

    @Override
    public void write(PacketByteBuf buf, MortarRecipe recipe) {
        recipe.getInput().write(buf);
        buf.writeItemStack(recipe.getOutput());
    }
}
