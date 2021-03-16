package info.sdust.flightmeter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("flightmeter")
public class FlightMeter
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

//    private static int mode = 0;
    private static int mode = 1;	// TODO TEST

    public FlightMeter() {



        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        // Register the enqueueIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        // Register the processIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

//        // Register ourselves for server and other game events we are interested in
//        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    	new FlightMeterRenderer();

    	new ToggleModeKeybind();

    	// do something that can only be done on the client
//        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);


    }

//    private void enqueueIMC(final InterModEnqueueEvent event)
//    {
//        // some example code to dispatch IMC to another mod
////        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
//    }
//
//    private void processIMC(final InterModProcessEvent event)
//    {
//        // some example code to receive and process InterModComms from other mods
////        LOGGER.info("Got IMC {}", event.getIMCStream().
////                map(m->m.getMessageSupplier().get()).
////                collect(Collectors.toList()));
//    }
//    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(FMLServerStartingEvent event) {
//        // do something when the server starts
////        LOGGER.info("HELLO from server starting");
//    }
//
//    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
//    // Event bus for receiving Registry Events)
//    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
//    public static class RegistryEvents {
//        @SubscribeEvent
//        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
//            // register a new block here
//            LOGGER.info("HELLO from Register Block");
//        }
//    }
//
//	@OnlyIn(Dist.CLIENT)
//	@SubscribeEvent
//    public void onCommand(CommandEvent ev) {
//
//		ParseResults<CommandSource> pr;
//		pr = ev.getParseResults();
//
//		if(pr.getContext().getCommand().)
//
//
//
//	}
//

    public static int toggleMode() {
    	Minecraft mc = Minecraft.getInstance();

    	switch(mode) {
    	case 0:
    		mode = 1;
    		mc.player.sendMessage(new StringTextComponent("[FlightMeter] FlightMeter is enabled."));
    		break;
    	case 1:
    		mode = 2;
    		mc.player.sendMessage(new StringTextComponent("[FlightMeter] FlightMeter is always draw mode."));
    		break;
    	default:
    		mode = 0;
    		mc.player.sendMessage(new StringTextComponent("[FlightMeter] FlightMeter is disabled."));
    		break;
    	}

    	return mode;
    }

    public static boolean isHUDMode() {
    	return (mode == 1) || (mode == 2);
    }

    public static boolean isAlwaysViewMode() {
    	return (mode == 2);
    }
}
