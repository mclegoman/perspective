/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.render.entity.model.ModelTransformer;

import java.util.Map;

@Environment(EnvType.CLIENT)
public record PerspectiveModelTransformer(float scale, float yOffset) implements ModelTransformer {
	PerspectiveModelTransformer(float scale) {
		this(scale, 0.0F);
	}
	public ModelData apply(ModelData modelData) {
		ModelData returnValue = new ModelData();
		for (Map.Entry<String, ModelPartData> stringModelPartDataEntry : modelData.getRoot().getChildren()) {
			returnValue.getRoot().addChild(stringModelPartDataEntry.getKey(), stringModelPartDataEntry.getValue().applyTransformer((modelTransform) -> modelTransform.addPivot(0.0F, this.yOffset, 0.0F).scaled(this.scale)));
		}
		return returnValue;
	}
}

