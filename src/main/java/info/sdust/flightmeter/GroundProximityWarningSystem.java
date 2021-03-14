package info.sdust.flightmeter;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class GroundProximityWarningSystem {

    //進行方向30mにブロックがあるかチェックする(雑)
    public boolean CheckHorizontal(PlayerEntity player, Vec3d player_Speed, World world) {
        Vec3d player_pos = player.getPositionVec();
        for (int i = 0; i < 30; i++) {
            player_pos = player_pos.add(player_Speed.x < 0 ? -1 : 1, 0, player_Speed.z < 0 ? -1 : 1);
            BlockState blockState = world.getBlockState(new BlockPos(player_pos));
            if (blockState.getMaterial() != Material.AIR) {
                return true;
            }
        }
        return false;
    }

    //20Tick後にブロックがあるかチェックする(雑)
    public boolean CheckGround(PlayerEntity player, Vec3d player_Speed, World world) {
        Vec3d player_Pos = player.getPositionVec();
        for (int i = 0; i <= 20; i++) {
            player_Pos = player_Pos.add(player_Speed);
            BlockState blockState = world.getBlockState(new BlockPos(player_Pos));
            if (blockState.getMaterial() != Material.AIR) {
                player.sendMessage(new StringTextComponent("Pull UP!"));
                player.sendMessage(new StringTextComponent(blockState.getBlock().toString() + ""));
                player.sendMessage(new StringTextComponent(
                        "X:" + (int) player_Pos.x
                                + "Y" + (int) player_Pos.y
                                + "Z" + (int) player_Pos.z));
                player.sendMessage(new StringTextComponent(0.05 * i + "sec"));
                player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_HARP, 1, 10);
                return true;
            }
        }
        return false;
    }
}
