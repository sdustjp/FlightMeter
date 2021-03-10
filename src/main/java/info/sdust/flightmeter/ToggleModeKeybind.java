package info.sdust.flightmeter;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ToggleModeKeybind {

	KeyBinding key;

	public ToggleModeKeybind() {
		key = new KeyBinding("flightmeter.keybind.togglemode", GLFW.GLFW_KEY_V, "key.categories.misc");
		ClientRegistry.registerKeyBinding(key);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent ev) {
		Minecraft mc = Minecraft.getInstance();

		if(mc.isGameFocused() && key.isPressed()) {
			FlightMeter.toggleMode();
		}


	}


}
