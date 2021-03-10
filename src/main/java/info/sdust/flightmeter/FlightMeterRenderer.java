package info.sdust.flightmeter;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FlightMeterRenderer {

	public static final Logger LOGGER = FlightMeter.LOGGER;

	public FlightMeterRenderer() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post ev) {
		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;
		boolean keyitemEquip;
		boolean keyitemOnHand;

		if(!FlightMeter.isHUDMode()) return;

		if(ev.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {

			keyitemEquip = (player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == Items.ELYTRA);

			if(keyitemEquip) {

				keyitemOnHand = (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() == Items.FIREWORK_ROCKET);

				if(keyitemOnHand || FlightMeter.isAlwaysViewMode()) {
					if(FlightMeter.isHUDMode()) {
						renderHUD(ev.getPartialTicks());
					}
				}
			}

		}

	}


	public void renderTestMessage(float partialTicks) {

		Minecraft mc = Minecraft.getInstance();
		Matrix4f matrix = new Matrix4f();

		String str = "test abc";
		String str2;
		String str3;
		List<String> strLines = new ArrayList<String>();

		ClientPlayerEntity player = mc.player;

		int tx, ty;

		double px = player.getPosX();
		double cx = player.chasingPosX;
		double prevX = player.prevPosX;

		double dx = player.getPosX() - player.prevPosX;
		double dy = player.getPosY() - player.prevPosY;
		double dz = player.getPosZ() - player.prevPosZ;

		double airSpeed = Math.sqrt(dx*dx + dy*dy + dz*dz) * 20.0;
		double groundSpeed = Math.sqrt(dx*dx + dz*dz) * 20.0;

		float pitch = player.prevRotationPitch;
		float yaw = player.prevRotationYaw;

		double alt = player.getPosY();

		int winWidth = mc.getMainWindow().getWidth();
		int winHeight = mc.getMainWindow().getHeight();
		int winScaledWidth = mc.getMainWindow().getScaledWidth();
		int winScaledHeight = mc.getMainWindow().getScaledHeight();

		int width = winScaledWidth;
		int height = winScaledHeight;

		int strWidth;
		int strHeight;

		double fov;

		fov = mc.gameSettings.fov;

		strWidth = mc.fontRenderer.getStringWidth(str);
		strHeight = mc.fontRenderer.FONT_HEIGHT;
//		strHeight = mc.fontRenderer.getWordWrappedHeight(str, 10);


		strLines.add("debug info:");
//		strLines.add(String.format("px=%f, cx=%f", px, cx));
//		strLines.add(String.format("px=%f", prevX));
		strLines.add(String.format("fov=%f", fov));
		strLines.add(String.format("airSpeed=%f", airSpeed));
		strLines.add(String.format("groundSpeed=%f", groundSpeed));
		strLines.add(String.format("pitch=%f", pitch));
		strLines.add(String.format("yaw=%f", yaw));
		strLines.add(String.format("alt=%f", alt));
//		strLines.add(String.format("winWidth=%d", winWidth));
//		strLines.add(String.format("winHeight=%d", winHeight));
//		strLines.add(String.format("winScaledWidth=%d", winScaledWidth));
//		strLines.add(String.format("winScaledHeight=%d", winScaledHeight));
//		strLines.add(String.format("strWidth=%d", strWidth));
		strLines.add(String.format("strHeight=%d", strHeight));


//		mc.fontRenderer.drawString(str, 20, 20, 0xffffff);
//		mc.fontRenderer.drawString(str2, 20, 30, 0xffffff);
//		mc.fontRenderer.drawString(str3, 20, 40, 0xffffff);
//		mc.fontRenderer.renderString(str, 20, 20, 0xffffff, p_228079_5_, p_228079_6_, p_228079_7_, p_228079_8_, p_228079_9_, p_228079_10_)

//		tx = 5;
//		ty = 5;

//		Matrix4f.
//		AbstractGui.

		// debug text
//		drawStringLines(strLines, 5, 5, 0xffffff);

//		drawHorizonLine(pitch, width/4, height/4, width/2, height/2, 0xff00ff00);
//
//		drawPitchMeter(pitch, 10, height/4, width/10, height/2, 0xff00ff00);
//
//		drawSpeedMeter(airSpeed, width/5, height/4, 40, height/2, 0xff00ff00);
//		drawAltitudeMeter(alt, width*4/5 - 40, height/4, 40, height/2, 0xff00ff00);
//
//		drawAzimuthMeter(yaw, (width - 200) / 2, height*3/4, 200, 30, 0xff00ff00);
//
		drawFlightHUD(0, 0, winScaledWidth, winScaledHeight, 0xff00ff00);

//		drawSpeed(groundSpeed, width/4, height/2, 20, 100);


//		RenderType LINE_1 = RenderType.makeType("flightmeter_line_1", DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, 128, glstate);


//		LOGGER.info("[FlightMeter] renderTestMessage!");

//		AbstractGui.fill(300, 10, 310, 20, 0xffffff00);
//		AbstractGui.fill(310, 20, 320, 30, 0xffffff00);
//
//		AbstractGui.fill(350, 10, 360, 20, 0xffffff00);
//		AbstractGui.fill(360, 20, 370, 30, 0xffffff00);


//		drawTestHUD(partialTicks);














	}

	public void renderHUD(float partialTicks) {
		Minecraft mc = Minecraft.getInstance();
		int width = mc.getMainWindow().getScaledWidth();
		int height = mc.getMainWindow().getScaledHeight();

		drawFlightHUD(0, -height/10, width, height, 0xff00ff00);

	}

	public void drawTestHUD(float partialTicks) {
		Minecraft mc = Minecraft.getInstance();
		Matrix4f matrix;
		Vector4f vec;
		int width = mc.getMainWindow().getScaledWidth();
		int height = mc.getMainWindow().getScaledHeight();

		Quaternion rotation;

		ActiveRenderInfo renderInfo;

		List<String> strs = new ArrayList<String>();

		String[] strArray;

		renderInfo = mc.gameRenderer.getActiveRenderInfo();

		ClientPlayerEntity player = mc.player;

//		strs.add(String.format("maxAir=%d", player.getMaxAir()));
//		strs.add(String.format("air=%d", player.getAir()));
//		strs.add(String.format("maxFallHeight=%d", player.getMaxFallHeight()));

//		mc.worldRenderer.
//		activeRenderInfo.
//		mc.gameRenderer.
		matrix = mc.gameRenderer.getProjectionMatrix(renderInfo, partialTicks, true);

		strArray = matrix.toString().split("\n");

		strs.add("matrix=");
		for(String str : strArray) {
			strs.add(str);
		}

		//Quaternion qua = Quaternion.ONE;
		//qua.

		//matrix.mul(matrix.mul);

		Vec3d vec3 = new Vec3d(0.0, 0.0, 0.0);

//		vec3 = vec3.subtract(player.getPositionVector());
		vec3 = player.getPositionVector().subtract(vec3);

//		renderInfo.getProjectedView();

		rotation = renderInfo.getRotation().copy();

		strs.add("vec3=" + vec3.toString());

//		vec3 = vec3.rotatePitch((float)(player.rotationPitch * Math.PI / 180.0));
//		vec3 = vec3.rotateYaw((float)(player.rotationYaw * Math.PI / 180.0));

		strs.add("vec3=" + vec3.toString());

		vec = new Vector4f((float)vec3.x, (float)vec3.y, (float)vec3.z, 1.0f);

		rotation.multiply(-1.0f);
		vec.transform(rotation);

		strs.add("vec=" + vec.toString());

		vec.transform(matrix);

		strs.add("vec=" + vec.toString());


		drawStringLines(strs, 5, 5, 0xffffff);


		AbstractGui.fill((int)(vec.getX()*width/2 + width/2 - 10), (int)(vec.getY()*height/2 + height/2 - 10), (int)(vec.getX()*width/2 + width/2), (int)(vec.getY()*height/2 + height/2), 0xffffff00);
		AbstractGui.fill((int)(vec.getX()*width/2 + width/2), (int)(vec.getY()*height/2 + height/2), (int)(vec.getX()*width/2 + width/2 + 10), (int)(vec.getY()*height/2 + height/2 + 10), 0xffffff00);


	}


	public void drawFlightHUD(int x, int y, int width, int height, int color) {

		Minecraft mc = Minecraft.getInstance();
		ClientPlayerEntity player = mc.player;

		double dx = player.getPosX() - player.prevPosX;
		double dy = player.getPosY() - player.prevPosY;
		double dz = player.getPosZ() - player.prevPosZ;

		double airSpeed = Math.sqrt(dx*dx + dy*dy + dz*dz) * 20.0;
		double groundSpeed = Math.sqrt(dx*dx + dz*dz) * 20.0;

		float pitch = player.rotationPitch;
		float yaw = player.rotationYaw;

		double alt = player.getPosY();

		drawHorizonLine(pitch, x + width/3, y + height/4, width/3, height/2, color);

//		drawPitchMeter(pitch, x + 10, height/4, y + width/10, height/2, color);

		drawSpeedMeter(airSpeed, x + width/5, y + height/4, 40, height/2, color);

		drawGroundSpeed(groundSpeed, x + width/5, y + height/4 + height/2 + 20, color);

		drawAltitudeMeter(alt, x + width*4/5 - 40, y + height/4, 40, height/2, color);

		drawCoodinate(player, x + width*4/5 - 40, y + height/4 + height/2 + 20, color);

		drawAzimuthMeter(yaw, x + width/3, y + height*3/4, width/3, 30, color);

	}

	public void drawHorizonLine(float pitch, int x, int y, int width, int height, int color) {
		Minecraft mc = Minecraft.getInstance();
		int winWidth = mc.getMainWindow().getWidth();
		int winHeight = mc.getMainWindow().getHeight();
		int winScaledWidth = mc.getMainWindow().getScaledWidth();
		int winScaledHeight = mc.getMainWindow().getScaledHeight();

		double th;

		String str;

		int sx, sy = mc.fontRenderer.FONT_HEIGHT;

		double fov;
		double fd;

		int lx, ly;

		int num = 18;
		int intv = 20;

		int px, py;

		double dp;

		int add_y;



//		AbstractGui.fill(x, (int)(y + height/2), x + width, (int)(y + height/2 + 1), color);

//		th = pitch * Math.PI / 180.0;
//		hy = -(float) Math.tan(th) * (height / 2);
//		drawHLine(x, (int)(y + height/2 + hy), width/2, color);

		fov = mc.gameSettings.fov * Math.PI / 180.0;
//		fov = mc.gameRenderer.getActiveRenderInfo().getRenderViewEntity().getDistanceSq(new Vec3d());
//		fd = (winScaledWidth / 2.0) / Math.tan(fov / 2.0);
		fd = (winScaledHeight / 2.0) / Math.tan(fov / 2.0);

		th = pitch * Math.PI / 180.0;
		ly = winScaledHeight/2 - (int)(Math.tan(th) * fd);
//		hy = -(int)(Math.tan(th) * fd * winScaledWidth / winWidth);

		if((y <= ly) && (ly < (y + height))) {
			drawHLine(x, ly, width, color);
		}

		for(int i = -num/2; i <= num/2; i ++) {

			if(i != 0) {

				dp = pitch + i * intv;
				if((-90.0) < dp && (dp < 90.0)) {

					th = dp * Math.PI / 180.0;
					ly = winScaledHeight/2 - (int)(Math.tan(th) * fd);


					if(i < 0) {
						add_y = -sy;
					}
					else {
						add_y = 0;
					}

					if((y <= ly) && (ly < (y + height))) {
						drawHLine(x + width/5, ly, width/5, color);
						drawVLine(x + width/5, ly + add_y, sy, color);
						drawHLine(x + width*3/5 + 1, ly, width/5, color);
						drawVLine(x + width*3/5 + width/5, ly + add_y, sy, color);
					}

					str = String.format("%d", (i * intv));
					sx = mc.fontRenderer.getStringWidth(str);
//					px = x + width/3;
					py = ly;

					if((y <= py) && ((py + sy) < (y + height))) {
						mc.fontRenderer.drawString(str, x + width/5 - sx, py + add_y, color);
						mc.fontRenderer.drawString(str, x + width*3/5 + width/5 + 2, py + add_y, color);
					}

				}
			}
		}

	}

	public void drawPitchMeter(float pitch, int x, int y, int width, int height, int color) {

		Minecraft mc = Minecraft.getInstance();

		int sx, sy;
		int px, py;

		int num = 10;
		int intv = 10;

		int offset;
		int dn;

		String str;

		for(int i = 0; i <= num; i ++) {

			offset = (i * height / num) + (((int)pitch - ((int)pitch / intv * intv)) * height / num / intv);
			dn = ((int)pitch / intv - i + num/2) * intv;

			str = String.format("%d", dn);
			sx = mc.fontRenderer.getStringWidth(str);
			sy = 10;

			if ((offset > 0) && (offset < height)) {

				drawHLine(x + width/4, y + height - offset, width/2, color);
//				line x + width*3/4, y + offset, x + width, y + offset

			}

			if (((offset - sy/2) > 0) && ((offset + sy/2) < (height))) {

				py = y + height - offset;

				px = x;
				mc.fontRenderer.drawString(str, px, py, color);

				px = x + width - sx;
				mc.fontRenderer.drawString(str, px, py, color);

			}

		}


	}

	public void drawSpeedMeter(double value, int x, int y, int width, int height, int color) {

		Minecraft mc = Minecraft.getInstance();

		int sx, sy = mc.fontRenderer.FONT_HEIGHT;
		int px, py;

		int num = 10;
		int intv = 10;

		int offset;
		int dn;

		int midSSY;
		int midSEY;

		String str;

		str = "SPD";
		sx = mc.fontRenderer.getStringWidth(str);
		mc.fontRenderer.drawString(str, x, y, color);

		y += sy;
		height -= sy;

		str = String.format("%.1f", value);
		sx = mc.fontRenderer.getStringWidth(str);

		midSSY = y + height/2 - sy/2 - 2;
		midSEY = y + height/2 + sy/2 + 2;

		drawStringInBox(str, x, midSSY, width*3/4, midSEY - midSSY, color, -1, 2);

		offset = height/2;
		drawHLine(x + width*6/8, y + offset, width/8, color);

		drawHLine(x, y, width, color);
		drawHLine(x, y + height, width, color);
		drawVLine(x + width, y, height, color);

		for(int i = 0; i < num; i ++) {

			offset = (i * height / num) + (((int)value - ((int)value / intv * intv)) * height / num / intv);
			dn = ((int)value / intv - i + num/2) * intv;

			str = String.format("%d", dn);
			sx = mc.fontRenderer.getStringWidth(str);

			if ((offset > 0) && (offset < height)) {

				drawHLine(x + width - width/10, y + offset, width/10, color);

			}

			if((dn % 20) == 0) {

				px = x;
				py = y + offset - sy/2;

				if (((y < py) && ((py + sy) < midSSY))
				|| ((midSEY < py) && ((py + sy) < (y + height)))) {

					mc.fontRenderer.drawString(str, px, py, color);

				}
			}

		}
//
//
//
//
//		Minecraft mc = Minecraft.getInstance();
//
//		int sx, sy;
//		int px, py;
//
//		int num = 10;
//		int intv = 10;
//
//		int offset;
//		int dn;
//
//		String str;
//
//		str = String.format("%.1f", value);
//		sx = mc.fontRenderer.getStringWidth(str);
//		sy = mc.fontRenderer.FONT_HEIGHT;
//
//		px = x;
//		py = y + height/2 - sy/2;
//		mc.fontRenderer.drawString(str, px, py, color);
//
//		drawHLine(x, y + height/2 - sy/2 - 2, width/2 - 10, color);
//		drawHLine(x, y + height/2 + sy/2 + 2, width/2 - 10, color);
//		drawHLine(x + width/2 - 10, y + height/2, 10, color);
//
//		drawHLine(x, y, width, color);
//		drawHLine(x, y + height, width, color);
//		drawVLine(x + width, y, height, color);
//
//		for(int i = 0; i <= num; i ++) {
//
//			offset = (i * height / num) + (((int)value - ((int)value / intv * intv)) * height / num / intv);
//			dn = ((int)value / intv - i + num/2) * intv;
//
//			if((offset > 0) && (offset < height)) {
//
//				drawHLine(x + width*3/4, y + offset, width/4, color);
//
//			}
//
//			str = String.format("%d", dn);
//			sx = mc.fontRenderer.getStringWidth(str);
//			sy = mc.fontRenderer.FONT_HEIGHT;
//			px = x + width/2;
//			py = y + offset - sy/2;
//
//			if((0 < py) && (py < (y + height))) {
//
//				if((dn % 20) == 0) {
//
//					mc.fontRenderer.drawString(str, px, py, color);
//
//				}
//			}
//
//		}
//

	}

	public void drawAltitudeMeter(double value, int x, int y, int width, int height, int color) {

		Minecraft mc = Minecraft.getInstance();

		int sx, sy = mc.fontRenderer.FONT_HEIGHT;
		int px, py;

		int num = 10;
		int intv = 10;

		int offset;
		int dn;

		int midSSY;
		int midSEY;

		String str;

		str = "ALT";
		sx = mc.fontRenderer.getStringWidth(str);
		mc.fontRenderer.drawString(str, x + width - sx, y, color);

		y += sy;
		height -= sy;

		str = String.format("%.1f", value);
		sx = mc.fontRenderer.getStringWidth(str);

		midSSY = y + height/2 - sy/2 - 2;
		midSEY = y + height/2 + sy/2 + 2;

		drawStringInBox(str, x + width/4, midSSY, width*3/4, midSEY - midSSY, color, 1, 2);

		offset = height/2;
		drawHLine(x + width/8, y + offset, width/8, color);

		drawHLine(x, y, width, color);
		drawHLine(x, y + height, width, color);
		drawVLine(x, y, height, color);

		for(int i = 0; i < num; i ++) {

			offset = (i * height / num) + (((int)value - ((int)value / intv * intv)) * height / num / intv);
			dn = ((int)value / intv - i + num/2) * intv;

			str = String.format("%d", dn);
			sx = mc.fontRenderer.getStringWidth(str);

			if ((offset > 0) && (offset < height)) {

				drawHLine(x, y + offset, width/10, color);

			}

			if((dn % 20) == 0) {

				px = x + width - sx;
				py = y + offset - sy/2;

				if (((y < py) && ((py + sy) < midSSY))
				|| ((midSEY < py) && ((py + sy) < (y + height)))) {

					mc.fontRenderer.drawString(str, px, py, color);

				}
			}

		}

	}

	public void drawAzimuthMeter(float yaw, int x, int y, int width, int height, int color) {

		Minecraft mc = Minecraft.getInstance();

		int sx, sy;
		int px, py;

		int num = 10;
		int intv = 10;

		float azimuth;
		int offset;
		int dn;

		String str;

		azimuth = yaw - 180.0f;
		while(azimuth >= 360.0f) azimuth -= 360.0f;
		while(azimuth < 0.0f) azimuth += 360.0f;

		str = String.format("%d", (int)azimuth);





		sx = mc.fontRenderer.getStringWidth(str);
		sy = mc.fontRenderer.FONT_HEIGHT;


		px = x + width/2 - sx/2;
		py = y + height*3/4;
//		mc.fontRenderer.drawString(str, px, py, color);

		drawVLine(x + width/2, y + height/2, height/4, color);
		drawStringInBox(str, x + width/2 - sy*3/2, y + height*3/4, sy*3, sy+3, color, 0, 0);
//		drawRectangle(x + width/2 - sy*3/2, y + height*3/4, sy*3, height/4, color);

		for(int i = 0; i < num; i ++) {

			offset = (i * width / num) + (((int)azimuth - ((int)azimuth / intv * intv)) * width / num / intv);
			dn = ((int)(azimuth) / intv - i + num/2) * intv;

			if (dn >= 360) dn = dn - 360;
			if (dn < 0) dn = dn + 360;


			if ((offset > 0) && (offset < width)) {

				drawVLine(x + width - offset, y, height/4, color);

			}

			if (dn == 0) {
				str = "N";
			}
			else if (dn == 90) {
				str = "E";
			}
			else if (dn == 180) {
				str = "S";
			}
			else if (dn == 270) {
				str = "W";
			}
			else {
				str = "" + (dn/10);
			}
			sx = mc.fontRenderer.getStringWidth(str);
			sy = mc.fontRenderer.FONT_HEIGHT;

			if(((offset - sx/2) > 0) && ((offset + sx/2) < width)) {

				px = x + width - offset - sx/2 + 1;
				py = y + height/4;

				if((dn == 0) || (dn == 90) || (dn == 180) || (dn == 270)) {

					mc.fontRenderer.drawString(str, px, py, color);

				}
				else if((dn / 10 % 3) == 0) {

					mc.fontRenderer.drawString(str, px, py, color);

				}

			}

		}

	}

	public void drawGroundSpeed(double value, int x, int y, int color) {

		Minecraft mc = Minecraft.getInstance();

		int sy = mc.fontRenderer.FONT_HEIGHT;

		mc.fontRenderer.drawString("GS", x, y, color);
		mc.fontRenderer.drawString(String.format("%.1f", value), x, y + sy, color);

	}

	public void drawCoodinate(Entity target, int x, int y, int color) {

		Minecraft mc = Minecraft.getInstance();

		int sy = mc.fontRenderer.FONT_HEIGHT;

		mc.fontRenderer.drawString("X Z", x, y, color);
		mc.fontRenderer.drawString(String.format("%4.1f %4.1f", target.getPosX(), target.getPosZ()), x, y + sy, color);

	}

	protected void drawStringInBox(String str, int x, int y, int width, int height, int color, int align, int padding) {

		Minecraft mc = Minecraft.getInstance();

		int sx, sy;
		int px, py;

		sx = mc.fontRenderer.getStringWidth(str);
		sy = mc.fontRenderer.FONT_HEIGHT;

		switch(align) {
		case 0:
			px = x + (width - sx)/2;
			break;

		case 1:
			px = x + width - sx - padding;
			break;

		case -1:
		default:
			px = x + padding;
			break;
		}
		py = y + (height - sy)/2 + 1;

		mc.fontRenderer.drawString(str, px, py, color);
		drawRectangle(x, y, width, height, color);

	}

	protected void drawVLine(int x, int y, int height, int color) {

		AbstractGui.fill(x, y, x + 1, y + height, color);

	}

	protected void drawHLine(int x, int y, int width, int color) {

		AbstractGui.fill(x, y, x + width, y + 1, color);

	}

	protected void drawRectangle(int x, int y, int width, int height, int color) {

		AbstractGui.fill(x, y, x + width, y + 1, color);
		AbstractGui.fill(x, y + height - 1, x + width, y + height, color);

		AbstractGui.fill(x, y, x + 1, y + height, color);
		AbstractGui.fill(x + width - 1, y, x + width, y + height, color);

	}

	protected void drawFillRectangle(int x, int y, int width, int height, int color) {

		AbstractGui.fill(x, y, x + width, y + height, color);

	}


	public void drawStringLines(List<String> strLines, int x, int y, int color) {
		Minecraft mc = Minecraft.getInstance();
//		int strWidth;
		int strHeight = 10;

		for(String strLn : strLines) {
//			AbstractGui.fill(x, y, tx+100, ty+10, 0x60000000);
			mc.fontRenderer.drawString(strLn, x, y, color);
			y += strHeight;
		}

	}

	public void drawStringLinesCenter(List<String> strLines, int x, int y, int color) {

		Minecraft mc = Minecraft.getInstance();
		int strWidth;
		int strHeight = 10;

		for(String strLn : strLines) {
			strWidth = mc.fontRenderer.getStringWidth(strLn);
//			AbstractGui.fill(x, y, tx+100, ty+10, 0x60000000);
			mc.fontRenderer.drawString(strLn, x - (strWidth / 2), y, color);
			y += strHeight;
		}

	}


}
