package dev.dannytaylor.perspective.api.gui;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.logo.LogoHelper;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.DateHelper;
import dev.dannytaylor.perspective.api.CoreClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

// TODO: This is temporary, it will be updated in a future version (soon!)
public class PerspectiveLogo {
    public static PerspectiveLogo.Logo getLogo() {
        return getLogo(DateHelper.isPride());
    }

    public static PerspectiveLogo.Logo getLogo(boolean isPride) {
        // This will have a event system for replacing, which will also allow for custom types.
        // I just need this functional for now.
        // Likely will be moved into PerspectiveMod as a getLogo();.
        return new PerspectiveLogo.Logo(CoreClient.getMod().idOf(CoreClient.getMod().getPerspectiveId(), true), "default");//isPride ? "pride" : "default");
    }

    public static void renderLogo(GuiGraphics context, int x, int y, int width, int height, boolean isPride) {
        context.blit(RenderPipelines.GUI_TEXTURED, getLogo(isPride).getTexture(), x, y, 0.0F, 0.0F, width, (int)((double)height * (double)0.6875F), width, height);
        LogoHelper.renderDevelopmentOverlay(context, (int)((float)x + (float)width / 2.0F - (float)width * 0.75F / 2.0F), (int)((float)y + ((float)height - (float)height * 0.54F)), width, height, Data.getVersion().isDevelopmentBuild(), 0, 0);
    }

    public static void renderLogo(GuiGraphics context, int x, int y, int width, int height) {
        renderLogo(context, x, y, width, height, DateHelper.isPride());
    }

    public PerspectiveLogo.Logo Logo(Identifier id) {
        return new PerspectiveLogo.Logo(id, "");
    }

    public static record Logo(Identifier id, String type) {
        public String getNamespace() {
            return this.id.getNamespace();
        }

        public String getName() {
            return this.id.getPath();
        }

        public String getType() {
            return this.type;
        }

        public Identifier getTexture() {
            return Identifier.fromNamespaceAndPath(this.getNamespace(), "textures/gui/logos/" + this.type + (!this.type.endsWith("/") && !this.type.isEmpty() ? "/" : "") + this.getName() + ".png");
        }
    }

    public static class Widget extends AbstractWidget {
        private final boolean shouldRenderSplashText;
        private final Translation.Data splashText;
        private final boolean isPride;

        public Widget(boolean shouldRenderSplashText, Translation.Data splashText, boolean isPride) {
            super(0, 0, 256, 64, Component.empty());
            this.shouldRenderSplashText = shouldRenderSplashText;
            this.splashText = splashText;
            this.isPride = isPride;
        }

        public Widget(boolean shouldRenderSplashText, Translation.Data splashText) {
            this(shouldRenderSplashText, splashText, DateHelper.isPride());
        }

        public void renderWidget(@NonNull GuiGraphics context, int mouseX, int mouseY, float delta) {
            PerspectiveLogo.renderLogo(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.isPride);
            if (this.shouldRenderSplashText) {
                LogoHelper.createSplashText(context, this.getWidth(), this.getX(), this.getY() + 32, ClientData.minecraft.font, this.splashText, -20.0F);
            }

        }

        protected void updateWidgetNarration(@NonNull NarrationElementOutput builder) {
        }

        protected boolean isValidClickButton(@NonNull MouseButtonInfo input) {
            return false;
        }
    }
}