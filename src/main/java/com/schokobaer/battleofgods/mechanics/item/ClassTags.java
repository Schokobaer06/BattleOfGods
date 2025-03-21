package com.schokobaer.battleofgods.mechanics.item;

import com.schokobaer.battleofgods.BattleofgodsMod;
import com.schokobaer.battleofgods.init.InitMainClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public class ClassTags{
    public static TagKey<MainClass> createMainClassTag(@NotBlank String name){
        return TagKey.create(InitMainClass.MAIN_CLASS_KEY,new ResourceLocation(BattleofgodsMod.MODID,name.toLowerCase()));
    }
}
