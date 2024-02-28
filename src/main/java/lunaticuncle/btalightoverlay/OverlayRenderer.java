package lunaticuncle.btalightoverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.culling.CameraFrustum;
import net.minecraft.core.block.*;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;


public class OverlayRenderer {
	private boolean canRender;
	private List<PosInfo> surroundingPos;
	private long ticks;
	private boolean isWorldInit;
	private final Color colorNumberBlockDark;
	private final Color colorNumberBlockLit;
	private final Color colorNumberSkyDark;
	private final Color colorNumberSkyLit;
	private final Color colorMarkerDark;
	private final Color colorMarkerBlockLit;
	private final Color colorMarkerSkyLit;

	public OverlayRenderer(){
		this.canRender = false;
		this.surroundingPos = new ArrayList<>();
		this.isWorldInit = false;

		colorNumberBlockDark = decode(Configs.Colors.NUMBER_BLOCK_DARK.value);
		colorNumberBlockLit = decode(Configs.Colors.NUMBER_BLOCK_LIT.value);
		colorNumberSkyDark = decode(Configs.Colors.NUMBER_SKY_DARK.value);
		colorNumberSkyLit = decode(Configs.Colors.NUMBER_SKY_LIT.value);
		colorMarkerDark = decode(Configs.Colors.MARKER_DARK.value);
		colorMarkerBlockLit = decode(Configs.Colors.MARKER_BLOCK_LIT.value);
		colorMarkerSkyLit = decode(Configs.Colors.MARKER_SKY_LIT.value);
	}

	public void update(Minecraft mc) {
		if(!canRender) {
			isWorldInit = false;
			return;
		}

		EntityPlayerSP thePlayer = mc.thePlayer;
		// Clamp to 1-20
		@SuppressWarnings("ManualMinMaxCalculation")
		int interval = Configs.General.UPDATE_INTERVAL.value > 20 ? 20 : (Configs.General.UPDATE_INTERVAL.value < 1 ? 1 : Configs.General.UPDATE_INTERVAL.value);

		if(ticks > interval || !isWorldInit) {
			ticks = 0;
			updatePos(thePlayer);
			isWorldInit = true;
		}
		updateLightLevels(mc);

		++ticks;
	}

	public void draw(Minecraft mc, float partialTick) {
		if(!canRender) {
			isWorldInit = false;
			return;
		}

		ICamera cam = mc.activeCamera;
		CameraFrustum frustum = new CameraFrustum(cam);
		World world = mc.theWorld;
		EntityPlayerSP thePlayer = mc.thePlayer;
		Tessellator tessellator = Tessellator.instance;

		GL11.glPushMatrix(); // World Render start

		GL11.glTranslated(-cam.getX(partialTick), -cam.getY(partialTick), -cam.getZ(partialTick));

        for (PosInfo queryPos : this.surroundingPos) {
            if (canSkipDraw(frustum, world, queryPos, partialTick)) {
                continue;
            }

            Block block = Block.blocksList[world.getBlockId(queryPos.x, queryPos.y, queryPos.z)];
            double offsetY = 0;

            if (block instanceof BlockLayerBase) {
                int meta = world.getBlockMetadata(queryPos.x, queryPos.y, queryPos.z);
                if (block instanceof BlockLayerLeaves) {
                    meta -= 128;
                }
                offsetY = (meta + 1) * 0.125;
            }

			String numbersMode = Configs.General.NUMBERS_MODE.value;
			String markersCondition = Configs.General.MARKERS_CONDITION.value;
			if(!numbersMode.equalsIgnoreCase("none")) {
				drawNumber(mc.fontRenderer, queryPos, Direction.getHorizontalDirection(thePlayer.yRot), offsetY);
			}
			if(!markersCondition.equalsIgnoreCase("never")) {
				drawMarker(tessellator, queryPos, offsetY);
			}

        }
		GL11.glPopMatrix(); // World Render end
	}

	private PosInfo getPlayerCoordinate(EntityPlayerSP thePlayer) {
		double x = MathHelper.floor_double(thePlayer.x);
		double y = MathHelper.floor_double(thePlayer.y);
		double z = MathHelper.floor_double(thePlayer.z);

		return new PosInfo((int) x, (int) y, (int) z);
	}

	private void drawNumber(FontRenderer fontRenderer, PosInfo pos, Direction facing, double offsetY) {
		double x = pos.x;
		double y = pos.y - 1 + offsetY;
		double z = pos.z;

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix(); // Translating and Rotating

		double offset = 0.0625;

		switch (facing) // Translation
		{
			case NORTH:
				GL11.glTranslated(x+offset*8.5, y+1.01, z+offset*5.25);
				break;

			case EAST:
				GL11.glTranslated(x+offset*10.75, y+1.01, z+offset*8.5);
				break;

			case SOUTH:
				GL11.glTranslated(x+offset*7.5, y+1.01, z+offset*10.75);
				break;

			case WEST:
				GL11.glTranslated(x+offset*5.25, y+1.01, z+offset*7.5);
				break;

			default:
		}

		GL11.glRotated(90, 1, 0, 0); // Rotation

		switch (facing) // Facing Rotation
		{
			case NORTH:
				break;

			case EAST:
				GL11.glRotated(90, 0, 0, 1);
				break;

			case SOUTH:
				GL11.glRotated(180, 0, 0, 1);
				break;

			case WEST:
				GL11.glRotated(270, 0, 0, 1);
				break;

			default:
		}

		GL11.glPushMatrix(); // Scaling
		GL11.glScaled(0.05, 0.05, 0.05);

		String mode = Configs.General.NUMBERS_MODE.value;
		Color color;
		if(mode.equalsIgnoreCase("block") || mode.equalsIgnoreCase("both")) {
			String num = String.valueOf(pos.lightLevelBlock);
			int length = fontRenderer.getStringWidth(num);
			color = pos.lightLevelBlock == 0 ? colorNumberBlockDark : colorNumberBlockLit;
			fontRenderer.drawString(num, -(length/2), 0, color.getRGB());
		}

		if(mode.equalsIgnoreCase("sky") || mode.equalsIgnoreCase("both")) {
			GL11.glPushMatrix(); // Sky value translating

			offset = 1.25;
			GL11.glTranslated(offset*2.5, offset*4, -0.1);

			String num = String.valueOf(pos.lightLevelSky);
			int length = fontRenderer.getStringWidth(num);
			color = pos.lightLevelSky == 0 ? colorNumberSkyDark : colorNumberSkyLit;
			fontRenderer.drawString(num, -(length/2), 0, color.getRGB());

			GL11.glPopMatrix(); // Sky value end
		}

		GL11.glPopMatrix(); // Scaling end
		GL11.glPopMatrix(); // Translating and Rotating end
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	private void drawMarker(Tessellator tessellator, PosInfo pos, double offsetY) {
		if(pos.lightLevelBlock > 0 && !Configs.General.MARKERS_CONDITION.value.equalsIgnoreCase("always")) {
			return;
		}

		Color color = colorMarkerBlockLit;
		if(pos.lightLevelBlock == 0) {
			color = pos.lightLevelSky > 4 ? colorMarkerSkyLit : colorMarkerDark;
		}

		double x = pos.x;
		double y = pos.y + offsetY + 0.03;
		double z = pos.z;

		double offset = 0.0625;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glLineWidth(2.0f);
		GL11.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
		tessellator.startDrawing(GL11.GL_LINES);

		tessellator.addVertex(x+offset, y, z+offset);
		tessellator.addVertex(x+1-offset, y, z+1-offset);
		tessellator.addVertex(x+offset, y, z+1-offset);
		tessellator.addVertex(x+1-offset, y, z+offset);

		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	private boolean canSkipDraw(CameraFrustum frustum, World world, PosInfo pos, float partialTick) {
		int x = pos.x;
		int y = pos.y;
		int z = pos.z;
		return !canSpawnAt(world, x, y, z) || isTopSolid(world, x, y, z)
			|| !frustum.isVisible(new AABB(x + 1, y, z + 1, x - 1, y, z - 1), partialTick);
	}

	//Copied from SpawnerMobs
	private boolean canSpawnAt(World world, int x, int y, int z) {
		return world.isBlockNormalCube(x, y - 1, z) && !world.isBlockNormalCube(x, y, z) && !world.getBlockMaterial(x, y, z).isLiquid();
	}

	private boolean isTopSolid(World world, int x, int y, int z) {
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		return (block instanceof BlockGlass) || (block instanceof BlockGlassTinted)
			|| (block instanceof BlockIce) || (block instanceof BlockLeavesBase)
			|| (block instanceof BlockPressurePlate);
	}

	public void toggleRender() {
		this.canRender = !this.canRender;
	}

	private void updatePos(EntityPlayerSP thePlayer) {
		this.surroundingPos = new ArrayList<>();
		// Clamp to 1-32
		@SuppressWarnings("ManualMinMaxCalculation")
		int horizontalRange = Configs.General.HORIZONTAL_RANGE.value > 32 ? 32 : (Configs.General.HORIZONTAL_RANGE.value < 1 ? 1 : Configs.General.HORIZONTAL_RANGE.value);
		@SuppressWarnings("ManualMinMaxCalculation")
		int verticalRange = Configs.General.VERTICAL_RANGE.value > 32 ? 32 : (Configs.General.VERTICAL_RANGE.value < 1 ? 1 : Configs.General.VERTICAL_RANGE.value);

		PosInfo playerCoordinate = getPlayerCoordinate(thePlayer);
		for(int dx = -horizontalRange; dx <= horizontalRange; ++dx) {
			for (int dy = -verticalRange; dy <= verticalRange; ++dy) {
				for (int dz = -horizontalRange; dz <= horizontalRange; ++dz) {
					PosInfo queryPos = new PosInfo(playerCoordinate.x + dx, playerCoordinate.y + dy, playerCoordinate.z + dz);
					surroundingPos.add(queryPos);
				}
			}
		}
	}

	private void updateLightLevels(Minecraft mc) {
		World world = mc.theWorld;

		for(PosInfo queryPos : this.surroundingPos) {
            queryPos.lightLevelBlock = world.getSavedLightValue(LightLayer.Block, queryPos.x, queryPos.y, queryPos.z);
			queryPos.lightLevelSky = world.getSavedLightValue(LightLayer.Sky, queryPos.x, queryPos.y, queryPos.z);
		}
	}

	private Color decode(String nm) {
		try {
			return Color.decode(nm);
		} catch (NumberFormatException e) {
			return Color.white;
		}
	}
}
