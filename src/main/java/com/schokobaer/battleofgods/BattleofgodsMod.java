package com.schokobaer.battleofgods;

import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.AbstractMap;

@Mod("battleofgods")
@Mod.EventBusSubscriber(modid = BattleofgodsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BattleofgodsMod {
	public static final Logger LOGGER = LogManager.getLogger(BattleofgodsMod.class);
	public static final String MODID = "battleofgods";

	public BattleofgodsMod() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
/*
		// Start of user code block mod init
		InitTier.TIERS.makeRegistry(()-> new RegistryBuilder<Tier>().setName(InitTier.TIER_KEY.location()));
		InitTier.TIERS.register(bus);
		InitRarity.RARITIES.makeRegistry(() -> new RegistryBuilder<Rarity>().setName(InitRarity.RARITY_KEY.location()));
		InitRarity.RARITIES.register(bus);
		InitItemClass.ITEM_CLASSES.register(bus);
		InitItemSubClass.ITEM_SUBCLASSES.register(bus);
		InitItem.ITEMS.register(bus);
		// End of user code block mod init
		BattleofgodsModBlocks.REGISTRY.register(bus);
		BattleofgodsModItems.REGISTRY.register(bus);
		BattleofgodsModTabs.REGISTRY.register(bus);
*/

	}

	// Start of user code block mod methods
	// End of user code block mod methods
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

	private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
	}

	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
			workQueue.forEach(work -> {
				work.setValue(work.getValue() - 1);
				if (work.getValue() == 0)
					actions.add(work);
			});
			actions.forEach(e -> e.getKey().run());
			workQueue.removeAll(actions);
		}
	}
	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		// Lade die Rezepte
		RecipeHandler.loadRecipes();
	}

	@SubscribeEvent
	public static void registerRecipeTypes(RegisterEvent event) {
		event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
			helper.register(new ResourceLocation("battleofgods:default_recipe"), new RecipeHandler.BattleRecipe.Type());
		});
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(RegisterEvent event) {
		event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> {
			helper.register(new ResourceLocation("battleofgods:default_recipe"), RecipeHandler.BattleRecipe.SERIALIZER);
		});
	}

}
