package com.fastlittleboys.lifesteal.item;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ModItems {
    public static final Item TEAR_BOTTLE = register("tear_bottle", Item::new, new Item.Properties());
    public static final HeartItem HEART = register("heart", HeartItem::new, new Item.Properties()
        .rarity(Rarity.RARE));
    public static final ReviveItem REVIVE = register("revive", ReviveItem::new, new Item.Properties()
        .rarity(Rarity.EPIC)
        .stacksTo(1)
        .component(DataComponents.LORE, Lifesteal.createLore(Component.translatable("item.lifesteal.revive.lore"))));

    /**
     * JVM only initializes static fields on the first usage of a class.
     * By calling this dummy method, the class is static initialized, registering all items.
     */
    public static void initialize() {
        Lifesteal.LOGGER.info("Items registered.");
    }

    public static <GenericItem extends Item> GenericItem register(
            String name, Function<Item.Properties, GenericItem> itemFactory, Item.Properties settings) {
        var itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Lifesteal.MOD_ID, name));
        var item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
}