package com.pahimar.ee3.api;

import com.pahimar.ee3.emc.EmcValuesDefault;
import com.pahimar.ee3.lib.Compare;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class EnergyStack implements Comparable<EnergyStack>
{

    public static final String VANILLA_SMELTING_ENERGY_NAME = "vanillaFuelValueUnits";
    public static final String IC2_ENERGY_NAME = "EnergyUnits";

    public static final int VANILLA_SMELTING_ENERGY_THRESHOLD = 200;

    public static final int SMELTING_ENERGY_PER_COAL = 1600;
    public static final int MINECRAFT_JOULES_PER_COAL = 1600;
    public static final int ENERGY_UNITS_PER_COAL = 4000;
    public static final int REDSTONE_FLUX_PER_COAL = 16000;
    public static final int JOULES_PER_COAL = 100000;

    public String energyName;
    public int stackSize;

    public EnergyStack(String energyName, int stackSize)
    {
        this.energyName = energyName;
        this.stackSize = stackSize;
    }

    public EnergyStack(String energyName)
    {
        this(energyName, 1);
    }

    public static float getCoalValue()
    {
        return EmcValuesDefault.getDefaultValueMap().get(new WrappedStack(new ItemStack(Item.coal, 1, 0))).getValue();
    }

    @Override
    public String toString()
    {
        return String.format("%dxenergyStack.%s", stackSize, energyName);
    }

    @Override
    public boolean equals(Object object)
    {
        return object instanceof EnergyStack && (this.compareTo((EnergyStack) object) == Compare.EQUALS);
    }

    public static boolean compareEnergyNames(EnergyStack energyStack1, EnergyStack energyStack2)
    {
        if (energyStack1 != null && energyStack2 != null)
        {
            if ((energyStack1.energyName != null) && (energyStack2.energyName != null))
            {
                return energyStack1.energyName.equalsIgnoreCase(energyStack2.energyName);
            }
        }

        return false;
    }

    @Override
    public int compareTo(EnergyStack energyStack)
    {
        return comparator.compare(this, energyStack);
    }

    public static int compare(EnergyStack energyStack1, EnergyStack energyStack2)
    {
        return comparator.compare(energyStack1, energyStack2);
    }

    public static Comparator<EnergyStack> comparator = new Comparator<EnergyStack>()
    {
        @Override
        public int compare(EnergyStack energyStack1, EnergyStack energyStack2)
        {
            if (energyStack1 != null)
            {
                if (energyStack2 != null)
                {
                    if (energyStack1.energyName.equalsIgnoreCase(energyStack2.energyName))
                    {
                        return energyStack1.stackSize - energyStack2.stackSize;
                    }
                    else
                    {
                        return energyStack1.energyName.compareToIgnoreCase(energyStack2.energyName);
                    }
                }
                else
                {
                    return Compare.LESSER_THAN;
                }
            }
            else
            {
                if (energyStack2 != null)
                {
                    return Compare.GREATER_THAN;
                }
                else
                {
                    return Compare.EQUALS;
                }
            }
        }
    };
}
