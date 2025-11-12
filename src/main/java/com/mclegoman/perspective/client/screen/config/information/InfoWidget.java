package com.mclegoman.perspective.client.screen.config.information;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.StringIdentifiable;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoWidget extends EntryListWidget<InfoWidget.InfoEntry> {
    private final TextRenderer textRenderer;

    public InfoWidget(MinecraftClient client, Identifier id, int width, int height, int y, int lineHeight) {
        this(client, id, width, height, y, lineHeight, (double)0.0F);
    }

    public InfoWidget(MinecraftClient client, Identifier id, int width, int height, int y, int lineHeight, double scrollY) {
        super(client, width, height, y, lineHeight);
        this.textRenderer = client.textRenderer;

        for(OrderedText row : this.textRenderer.wrapLines(load(id, InfoWidget::read), this.getRowWidth())) {
            this.addEntry(new InfoWidget.InfoEntry(row));
        }

        this.setScrollY(scrollY);
    }

    protected void renderEntry(DrawContext context, int mouseX, int mouseY, float delta, int index, int x, int y, int entryWidth, int entryHeight) {
        InfoWidget.InfoEntry entry = this.getEntry(index);
        entry.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, Objects.equals(this.getHoveredEntry(), entry), delta);
    }

    public int getRowWidth() {
        return this.width - 24;
    }

    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    private static Text load(Identifier id, InfoWidget.InfoReader infoReader) {
        try (Reader reader = ClientData.minecraft.getResourceManager().openAsReader(id)) {
            return infoReader.read(reader);
        } catch (Exception exception) {
            Data.getVersion().sendToLog(LogType.ERROR, "Couldn't load info from file " + id + ": " + exception.getLocalizedMessage());
            return Text.empty();
        }
    }

    private static Text read(Reader reader) {
        JsonObject root = JsonHelper.deserialize(reader).getAsJsonObject();
        MutableText text = Text.empty();
        if (root.has("values")) {
            for(JsonElement element : root.getAsJsonArray("values")) {
                text.append(read(Text.empty(), element));
                if (root.has("line_breaks") && root.get("line_breaks").getAsBoolean()) {
                    text.append("\n");
                }
            }
        }

        return text;
    }

    private static Text read(MutableText text, JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        if (jsonObject.has("indent")) {
            int indents = jsonObject.get("indent").getAsInt();
            if (indents > 0) {
                text.append(Text.literal(" ".repeat(indents)));
            }
        }

        List<Text> args = new ArrayList();
        if (jsonObject.has("args")) {
            for(JsonElement argElement : jsonObject.getAsJsonArray("args")) {
                args.add(read(Text.empty(), argElement));
            }
        }

        if (jsonObject.has("value")) {
            MutableText var10001;
            switch ((jsonObject.has("type") ? InfoWidget.TextType.valueOf(jsonObject.get("type").getAsString()) : InfoWidget.TextType.literal).ordinal()) {
                case 0 -> var10001 = Text.literal(jsonObject.get("value").getAsString());
                case 1 -> var10001 = Text.translatable(jsonObject.get("value").getAsString(), args.toArray(new Object[0]));
                default -> throw new MatchException((String)null, (Throwable)null);
            }

            text.append(var10001);
        }

        return text;
    }

    public class InfoEntry extends EntryListWidget.Entry<InfoWidget.InfoEntry> {
        private final OrderedText text;

        public InfoEntry(OrderedText text) {
            this.text = text;
        }

        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
            context.drawTextWithShadow(InfoWidget.this.textRenderer, this.text, x, y, -5592406);
        }
    }

    private static enum TextType implements StringIdentifiable {
        literal("literal"),
        translatable("translatable");

        final String id;

        private TextType(String id) {
            this.id = id;
        }

        public String asString() {
            return this.id;
        }
    }

    interface InfoReader {
        Text read(Reader var1) throws IOException;
    }
}
