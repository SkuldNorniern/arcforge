package com.nornity.arcforge.client.screen;

import com.nornity.arcforge.Arcforge;
import com.nornity.arcforge.machine.menu.ArcaneFurnaceMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class ArcaneFurnaceScreen extends AbstractContainerScreen<ArcaneFurnaceMenu> {
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath(Arcforge.MODID, "textures/gui/arcane_furnace.png");

    public ArcaneFurnaceScreen(ArcaneFurnaceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 166);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int xo = (this.width - this.imageWidth) / 2;
        int yo = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, xo, yo, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }
}
