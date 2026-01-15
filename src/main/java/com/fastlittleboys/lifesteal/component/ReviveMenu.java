package com.fastlittleboys.lifesteal.component;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import org.jspecify.annotations.NonNull;

public class ReviveMenu extends ChestMenu {


    public ReviveMenu(MenuType<?> menuType, int i, Inventory inventory, Container container, int j) {
        super(menuType, i, inventory, container, j);
    }

    public static ReviveMenu openMenu(int syncId, Inventory inventory) {
        return new ReviveMenu(
            MenuType.GENERIC_9x3,
            syncId,
            inventory,
            new SimpleContainer(27), 3);
    }

    @Override
    public void clicked(int i, int j, @NonNull ClickType clickType, @NonNull Player player) {
        if(getSlot(i).index <= 26){
            Lifesteal.LOGGER.info("cancelled");
            return;
        }
        super.clicked(i, j, clickType, player);

    }





}
