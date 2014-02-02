package com.pahimar.ee3.recipe;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pahimar.ee3.api.EnergyStack;
import com.pahimar.ee3.api.WrappedStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;

import java.util.*;

public class RecipesSmelting
{
    private static Multimap<WrappedStack, List<WrappedStack>> smeltingRecipes = null;

    public static Multimap<WrappedStack, List<WrappedStack>> getSmeltingRecipes()
    {
        if (smeltingRecipes == null)
        {
            init();
        }

        return smeltingRecipes;
    }

    @SuppressWarnings("unchecked")
    private static void init()
    {
        smeltingRecipes = HashMultimap.create();

        for (Map.Entry<Object, Object> entry : ((Map<Object, Object>)FurnaceRecipes.smelting().getSmeltingList()).entrySet())
        {
            ItemStack inputStack = null;
            ItemStack outputStack = null;

            if (entry.getKey() instanceof Integer)
            {
                inputStack = new ItemStack((Integer)entry.getKey(), 1, 0);
            }
            else if (entry.getKey() instanceof ArrayList)
            {
                ArrayList<Integer> values = (ArrayList)entry.getKey();
                if (values.size() >= 2)
                {
                    inputStack = new ItemStack(values.get(0), 1, values.get(1));
                }
            }
            if (entry.getValue() instanceof ItemStack)
            {
                outputStack = (ItemStack) entry.getValue();
            }

            if (inputStack != null && outputStack != null)
            {
                smeltingRecipes.put(new WrappedStack(outputStack), Arrays.asList(new WrappedStack(inputStack), new WrappedStack(new EnergyStack(EnergyStack.VANILLA_SMELTING_ENERGY_NAME, EnergyStack.VANILLA_SMELTING_ENERGY_THRESHOLD))));
            }
        }
    }
}
