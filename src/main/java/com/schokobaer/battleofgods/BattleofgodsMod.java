package com.schokobaer.battleofgods;

import com.schokobaer.battleofgods.init.*;
import com.schokobaer.battleofgods.mechanics.recipe.CraftPacket;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import com.schokobaer.battleofgods.mechanics.tag.MainClassTagProvider;
import com.schokobaer.battleofgods.mechanics.tag.RarityTagProvider;
import com.schokobaer.battleofgods.mechanics.tag.SubClassTagProvider;
import com.schokobaer.battleofgods.mechanics.tag.TierTagProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.registries.RegistryBuilder;
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

import com.schokobaer.battleofgods.mechanics.tier.Tier;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import com.schokobaer.battleofgods.mechanics.item.MainClass;

@Mod("battleofgods")
public class BattleofgodsMod {
	public static final Logger LOGGER = LogManager.getLogger(BattleofgodsMod.class);
	public static final String MODID = "battleofgods";

	public BattleofgodsMod() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		MinecraftForge.EVENT_BUS.register(this);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		// Start of user code block mod init
		InitTier.TIERS.makeRegistry(() -> new RegistryBuilder<Tier>().setName(InitTier.TIER_KEY.location()));
		InitRarity.RARITIES.makeRegistry(() -> new RegistryBuilder<Rarity>().setName(InitRarity.RARITY_KEY.location()));
		InitMainClass.MAIN_CLASSES.makeRegistry(() -> new RegistryBuilder<MainClass>().setName(InitMainClass.MAIN_CLASS_KEY.location()));
		InitTier.TIERS.register(bus);
		InitRarity.RARITIES.register(bus);
		InitMainClass.MAIN_CLASSES.register(bus);
		InitSubClass.SUBCLASSES.register(bus);
		InitItem.ITEMS.register(bus);
		// End of user code block mod init
		BattleofgodsModBlocks.REGISTRY.register(bus);
		BattleofgodsModItems.REGISTRY.register(bus);
		BattleofgodsModTabs.REGISTRY.register(bus);
		BattleofgodsModMenus.REGISTRY.register(bus);

		InitMenu.MENUS.register(bus);
		//bus.addGenericListener(FMLCommonSetupEvent.class,(GenericEvent<? extends FMLCommonSetupEvent> event) -> onCommonSetup((FMLCommonSetupEvent) event.getGenericType()));;
		//bus.addGenericListener(RegisterEvent.class,(GenericEvent<? extends RegisterEvent> event) -> registerRecipeTypes((RegisterEvent) event.getGenericType()));;
		//bus.addGenericListener(RegisterEvent.class,(GenericEvent<? extends RegisterEvent> event) -> registerRecipeSerializers((RegisterEvent) event.getGenericType()));;
		bus.addListener(BattleofgodsMod::registerRecipeTypes);
		bus.addListener(BattleofgodsMod::registerRecipeSerializers);
		//bus.addListener(BattleofgodsMod::onServerStarting);
		bus.addListener(BattleofgodsMod::onClientSetup);
		bus.addListener(this::gatherData);

		addNetworkMessage(CraftPacket.class, CraftPacket::encode, CraftPacket::decode, CraftPacket::handle);

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
	@OnlyIn(Dist.DEDICATED_SERVER)
	public static void onServerStarting(ServerStartingEvent event) {
		LOGGER.debug("Server loading recipes");
		RecipeHandler.loadRecipes(event.getServer().getResourceManager());

	}
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLCommonSetupEvent event) {
		LOGGER.debug("Client loading recipes");
		RecipeHandler.loadRecipes(Minecraft.getInstance().getResourceManager());
	}

	@SubscribeEvent
	public static void registerRecipeTypes(RegisterEvent event) {
		LOGGER.debug("Registering recipe types");
		event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
			helper.register(new ResourceLocation(MODID + 	":default_recipe"), RecipeHandler.BattleRecipe.Type.INSTANCE);
		});
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(RegisterEvent event) {
		LOGGER.debug("Registering recipe serializers");
		event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> {
			helper.register(new ResourceLocation(MODID + ":default_recipe"), RecipeHandler.BattleRecipe.SERIALIZER);
		});
	}

	@SubscribeEvent
	public void gatherData(GatherDataEvent event) {
		LOGGER.info("Gathering data");
		LOGGER.info("Generating subClass tags");
		event.getGenerator().addProvider(
				event.includeServer(),
				(DataProvider.Factory<SubClassTagProvider>) output -> new SubClassTagProvider(
						output,
						InitSubClass.ITEM_OVERRIDE,
						event.getLookupProvider(),
						MODID,
						event.getExistingFileHelper()
				)
		);
		LOGGER.info("Generating mainClass tags");
		event.getGenerator().addProvider(
				event.includeServer(),
				(DataProvider.Factory<MainClassTagProvider>) output -> new MainClassTagProvider(
						output,
						InitMainClass.MAIN_CLASS_KEY,
						event.getLookupProvider(),
						MODID,
						event.getExistingFileHelper()
				)
		);
		LOGGER.info("Generating Tier tags");
		event.getGenerator().addProvider(
				event.includeServer(),
				(DataProvider.Factory<TierTagProvider>) output -> new TierTagProvider(
						output,
						InitTier.TIER_KEY,
						event.getLookupProvider(),
						MODID,
						event.getExistingFileHelper()
				)
		);
		LOGGER.info("Generating Rarity tags");
		event.getGenerator().addProvider(
				event.includeServer(),
				(DataProvider.Factory<RarityTagProvider>) output -> new RarityTagProvider(
						output,
						InitRarity.RARITY_KEY,
						event.getLookupProvider(),
						MODID,
						event.getExistingFileHelper()
				)
		);
	}
}
