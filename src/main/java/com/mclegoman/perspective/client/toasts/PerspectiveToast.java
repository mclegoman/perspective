/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.toasts;

import com.google.common.collect.ImmutableList;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.keybindings.KeybindingHelper;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PerspectiveToast implements Toast {
    private static final Identifier TEXTURE = Identifier.of(Data.getVersion().getID(), "toast/info");
    private static final int MIN_WIDTH = 200;
    private static final int WRAP_WIDTH = 320;
    private static final int LINE_HEIGHT = 12;

    private final Type type;
    private Text title;
    private List<OrderedText> lines;
    private long startTime;
    private boolean justUpdated;
    private final int width;
    private boolean hidden;
    private Visibility visibility;

    private PerspectiveToast(Type type, Text title, List<OrderedText> lines, int width) {
        this.visibility = Visibility.HIDE;
        this.type = type;
        this.title = title;
        this.lines = lines;
        this.width = width;
    }

    public static PerspectiveToast create(MinecraftClient client, Type type, Text title, @Nullable Text description) {
        TextRenderer textRenderer = client.textRenderer;

        List<OrderedText> wrappedLines = description != null
                ? textRenderer.wrapLines(description, WRAP_WIDTH)
                : ImmutableList.of();

        int maxLineWidth = wrappedLines.stream()
                .mapToInt(textRenderer::getWidth)
                .max()
                .orElse(0);

        int width = Math.max(MIN_WIDTH, 30 + Math.max(textRenderer.getWidth(title), maxLineWidth));

        return new PerspectiveToast(type, title, wrappedLines, width);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return 20 + Math.max(this.lines.size(), 1) * LINE_HEIGHT;
    }

    public void hide() {
        this.hidden = true;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public void update(ToastManager manager, long time) {
        if (this.justUpdated) {
            this.startTime = time;
            this.justUpdated = false;
        }

        double duration = (double)this.type.displayDuration * manager.getNotificationDisplayTimeMultiplier();
        long elapsed = time - this.startTime;
        this.visibility = !this.hidden && (double)elapsed < duration ? Visibility.SHOW : Visibility.HIDE;
    }

    public void draw(DrawContext context, TextRenderer textRenderer, long startTime) {
        context.drawGuiTexture(RenderLayer::getGuiTextured, TEXTURE, 0, 0, this.getWidth(), this.getHeight());
        context.drawText(textRenderer, this.title, 26, 7, 0xFFAA00, false);

        for (int i = 0; i < this.lines.size(); ++i) {
            context.drawText(textRenderer, this.lines.get(i), 26, 18 + i * LINE_HEIGHT, 0xFFFFFF, false);
        }
    }

    public void setContent(Text title, @Nullable Text description) {
        this.title = title;
        this.lines = description == null
                ? ImmutableList.of()
                : MinecraftClient.getInstance().textRenderer.wrapLines(description, MIN_WIDTH);
        this.justUpdated = true;
    }

    public Type getType() {
        return this.type;
    }

    public static void show(MinecraftClient client, Type type, Text title, @Nullable Text description) {
        client.getToastManager().add(PerspectiveToast.create(client, type, title, description));
    }

    public static class Type {
        public static final Type TUTORIAL = new Type();
        public static final Type WARNING = new Type();
        public static final Type UPDATE = new Type();

        final long displayDuration;

        public Type(long displayDuration) {
            this.displayDuration = displayDuration;
        }
        public Type() {
            this(5000L);
        }
    }

    public static class Helper {
        public static final boolean experimentsAvailable = false;
        private static boolean seenDevelopmentWarning = false;
        private static boolean showDowngradeWarning = false;
        private static boolean seenDowngradeWarning = false;
        public static boolean seenConflictingKeybindingToasts = false;
        public static void showDowngradeWarning() {
            showDowngradeWarning = true;
        }
        public static void tick() {
            if (!seenDevelopmentWarning && Data.getVersion().isDevelopmentBuild()) {
                Data.getVersion().sendToLog(LogType.INFO, "Development Build: Please submit a bug report if you encounter any issues.");
                show(ClientData.minecraft, Type.WARNING, Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.development_warning.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.development_warning.description"));
                seenDevelopmentWarning = true;
            }
            if (!seenDowngradeWarning && showDowngradeWarning) {
                Data.getVersion().sendToLog(LogType.INFO, "Downgrading is not supported: You may experience configuration related issues.");
                show(ClientData.minecraft, Type.WARNING, Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.downgrade_warning.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.downgrade_warning.description"));
                seenDowngradeWarning = true;
            }
            if (!seenConflictingKeybindingToasts && KeybindingHelper.hasKeybindingConflicts(Keybindings.allKeybindings)) {
                Data.getVersion().sendToLog(LogType.INFO, Translation.getString("Conflicting Keybinding: Keybinding conflicts have been detected."));
                show(ClientData.minecraft, Type.WARNING, Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.keybinding_conflicts.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.keybinding_conflicts.description"));
                seenConflictingKeybindingToasts = true;
            }
        }
    }
}