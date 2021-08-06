package me.jombi.florence.ui.clickgui;

import me.jombi.florence.Florence;
import me.jombi.florence.modules.Module;
import me.jombi.florence.utils.Logger;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class Scroll extends GuiScreen {
    public static Scroll instance = new Scroll();  // for displayGuiScreen() func

    private float currentScroll;
    private final int x = 10, y = 10, width = 100, height = 300;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (currentScroll > 0) currentScroll = 0.0f;
        Gui.drawRect(x, y, width, height, 0xff212121);
        int textHeight = 0;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glTranslatef(0, currentScroll, 0); // Scroll
        ScaledResolution r = new ScaledResolution(mc);
        int s = r.getScaleFactor();
        int translatedY = r.getScaledHeight() - y - height;
        GL11.glScissor(x * s, translatedY * s + 18, width * s, height * s - 18); // Scissor with scaleFactor
        for (Module m : Florence.instance.moduleMgr.getModules()) { // TODO:Import your modules
            fontRendererObj.drawStringWithShadow(m.getName(), x + 2, y + textHeight + 2, -1);
            textHeight += fontRendererObj.FONT_HEIGHT + 2;
        }
        if (currentScroll < -(textHeight / 2.67f)) currentScroll = -(textHeight / 2.67f);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int i = Mouse.getEventDWheel();

        i = Integer.compare(i, 0);

        currentScroll += i * 5f;
    }
}
