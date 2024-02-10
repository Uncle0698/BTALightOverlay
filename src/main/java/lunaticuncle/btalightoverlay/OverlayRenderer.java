package lunaticuncle.btalightoverlay;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.render.culling.CameraFrustum;
import net.minecraft.core.block.*;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;


public class OverlayRenderer {
	private boolean canRender;
	//TODO: Add colors to configs
	private final Color colorDark;
	private final Color colorLit;

	private List<BlockPos> surroundingPos;
	private long ticks;
	private boolean isWorldInit;

	public OverlayRenderer(){
		canRender = false;

		colorDark = new Color();
		colorDark.setRGB(255, 0, 0);
		colorLit = new Color();
		colorLit.setRGB(0, 255, 0);

		this.surroundingPos = new ArrayList<>();
		isWorldInit = false;
	}

	public void draw(Minecraft mc, float partialTick) {
		if(!canRender) {
			isWorldInit = false;
			return;
		}

		ICamera cam = mc.activeCamera;
		CameraFrustum frustum = new CameraFrustum(cam);
		World world = mc.theWorld;

		GL11.glPushMatrix(); // World Render start

		GL11.glTranslated(-cam.getX(partialTick), -cam.getY(partialTick), -cam.getZ(partialTick));

		EntityPlayerSP thePlayer = mc.thePlayer;
		if(ticks > 200 || !isWorldInit) { // One second interval, a partialTick = 1/10 tick
			ticks = 0;
			updatePos(thePlayer);
			isWorldInit = true;
		}

        //TODO: - Add radius to configs
        for (BlockPos queryPos : this.surroundingPos) {
            if (canSkipDraw(frustum, world, queryPos, partialTick)) {
                continue;
            }

            //TODO: Add option to toggle between Block/Sky/Both light information
            int blockLight = world.getSavedLightValue(LightLayer.Block, queryPos.x, queryPos.y, queryPos.z);

            Block block = Block.blocksList[world.getBlockId(queryPos.x, queryPos.y, queryPos.z)];
            double offsetY = 0;

            if (block instanceof BlockLayerBase) {
                int meta = world.getBlockMetadata(queryPos.x, queryPos.y, queryPos.z);
                if (block instanceof BlockLayerLeaves) {
                    meta -= 128;
                }
                offsetY = (meta + 1) * 0.125;

            }

            Color color = blockLight == 0 ? colorDark : colorLit;
            drawNumber(mc.fontRenderer, queryPos.x, queryPos.y - 1 + offsetY, queryPos.z, Direction.getHorizontalDirection(thePlayer.yRot), color, String.valueOf(blockLight));

        }
		GL11.glPopMatrix(); // World Render end
		++ticks;
	}

	private BlockPos getPlayerCoordinate(EntityPlayerSP thePlayer) {
		double x = MathHelper.floor_double(thePlayer.x);
		double y = MathHelper.floor_double(thePlayer.y);
		double z = MathHelper.floor_double(thePlayer.z);

		return new BlockPos((int) x, (int) y, (int) z);
	}

	private void drawNumber(FontRenderer fontRenderer, double x, double y, double z, Direction facing, Color color, String num) {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix(); // Translating and Rotating

		double offset1 = 0.53125;
		double offset2 = 0.25;

		switch (facing)
		{
			case NORTH:
				GL11.glTranslated(x+offset1, y+1.01, z+offset2);
				break;

			case EAST:
				GL11.glTranslated(x+offset2*3, y+1.01, z+offset1);
				break;

			case SOUTH:
				GL11.glTranslated(x+offset1-0.0625, y+1.01, z+offset2*3);
				break;

			case WEST:
				GL11.glTranslated(x+offset2, y+1.01, z+offset1-0.0625);
				break;

			default:
		}

		GL11.glRotated(90, 1, 0, 0);

		switch (facing)
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
		GL11.glScaled(0.07, 0.07, 0.07);

		int length = fontRenderer.getStringWidth(num);
		fontRenderer.drawString(num, -(length/2), 0, color.getARGB());

		GL11.glPopMatrix(); // Scaling end
		GL11.glPopMatrix(); // Translating and Rotating end
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	private boolean canSkipDraw(CameraFrustum frustum, World world, BlockPos pos, float partialTick) {
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

		BlockPos playerCoordinate = getPlayerCoordinate(thePlayer);
		for(int dx = -16; dx <= 16; ++dx) {
			for (int dy = -16; dy <= 16; ++dy) {
				for (int dz = -16; dz <= 16; ++dz) {
					BlockPos queryPos = new BlockPos(playerCoordinate.x + dx, playerCoordinate.y + dy, playerCoordinate.z + dz);
					surroundingPos.add(queryPos);
				}
			}
		}
	}

}
