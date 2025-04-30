package com.schokobaer.battleofgods;

import com.schokobaer.battleofgods.init.*;
import com.schokobaer.battleofgods.mechanics.item.MainClass;
import com.schokobaer.battleofgods.mechanics.item.override.ItemOverride;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
import com.schokobaer.battleofgods.mechanics.recipe.CraftPacket;
import com.schokobaer.battleofgods.mechanics.recipe.RecipeHandler;
import com.schokobaer.battleofgods.mechanics.tag.MainClassTagProvider;
import com.schokobaer.battleofgods.mechanics.tag.RarityTagProvider;
import com.schokobaer.battleofgods.mechanics.tag.SubClassTagProvider;
import com.schokobaer.battleofgods.mechanics.tag.TierTagProvider;
import com.schokobaer.battleofgods.mechanics.tier.Tier;
import net.minecraft.core.Registry;
import net.minecraft.data.DataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod("battleofgods")
public class BattleofgodsMod {
    public static final Logger LOGGER = LogManager.getLogger(BattleofgodsMod.class);
    public static final String MODID = "battleofgods";
    // Start of user code block mod methods
    // End of user code block mod methods
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();
    private static int messageID = 0;


    //private static final Boolean debug = true;

    public static Boolean isDebug() {
        return true;
    }

    public BattleofgodsMod() {
        // Start of user code block mod constructor
        // End of user code block mod constructor
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Start of user code block mod init
        InitTier.TIERS.makeRegistry(() -> new RegistryBuilder<Tier>()
                .setName(InitTier.TIER_KEY.location())
                .hasTags());
        InitRarity.RARITIES.makeRegistry(() -> new RegistryBuilder<Rarity>()
                .setName(InitRarity.RARITY_KEY.location())
                .hasTags());
        InitMainClass.MAIN_CLASSES.makeRegistry(() -> new RegistryBuilder<MainClass>()
                .setName(InitMainClass.MAIN_CLASS_KEY.location())
                .hasTags());
        InitTier.TIERS.register(bus);
        InitRarity.RARITIES.register(bus);
        InitMainClass.MAIN_CLASSES.register(bus);
        InitSubClass.SUBCLASSES.register(bus);
        InitItem.ITEMS.register(bus);

        InitAttributes.ATTRIBUTES.register(bus);

        InitBlocks.BLOCKS.register(bus);

        InitTabs.CREATIVE_MODE_TABS.register(bus);

        // End of user code block mod init

        InitMenu.MENUS.register(bus);
        bus.addListener(BattleofgodsMod::registerRecipeTypes);
        bus.addListener(BattleofgodsMod::registerRecipeSerializers);
        bus.addListener(this::gatherData);

        addNetworkMessage(CraftPacket.class, CraftPacket::encode, CraftPacket::decode, CraftPacket::handle);
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        if (isDebug())
            LOGGER.debug("Registering network message: {}", messageType.getName());
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
    }

    @SubscribeEvent
    public static void registerRecipeTypes(RegisterEvent event) {
        if (isDebug())
            LOGGER.debug("Registering recipe types");
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
            helper.register(new ResourceLocation(MODID + ":default_recipe"), RecipeHandler.BattleRecipe.Type.INSTANCE);
        });
    }

    @SubscribeEvent
    public static void registerRecipeSerializers(RegisterEvent event) {
        if (isDebug())
            LOGGER.debug("Registering recipe serializers");
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> {
            helper.register(new ResourceLocation(MODID + ":default_recipe"), RecipeHandler.BattleRecipe.SERIALIZER);
        });
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
    public void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener((ResourceManagerReloadListener) manager -> {
            if (isDebug())
                LOGGER.debug("Reloading recipes");
            RecipeHandler.loadRecipes(manager);
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
                        (ResourceKey<? extends Registry<ItemOverride>>) (Object) ForgeRegistries.ITEMS.getRegistryKey(),
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
