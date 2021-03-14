package info.sdust.flightmeter;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoPilot {
    private static AutoPilot autoPilot;

    PlayerEntity player;
    GroundProximityWarningSystem gpws = new GroundProximityWarningSystem();
    SimplePlayerManager simplePlayerManager = SimplePlayerManager.getInstance();


    private AutoPilot() {

    }

    public static AutoPilot getInstance() {
        if (autoPilot == null) {
            autoPilot = new AutoPilot();
        }
        return autoPilot;
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    //後でイベントにする
    public void run() {
        player = Minecraft.getInstance().player;

        SimplePlayer target = simplePlayerManager.getPlayer("mew_tan");

        if (target == null) {
            return;
        }

        Vec3d target_vec = new Vec3d(target.getX(), target.getY(), target.getZ());

        double dx = player.getPosX() - player.prevPosX;
        double dy = player.getPosY() - player.prevPosY;
        double dz = player.getPosZ() - player.prevPosZ;

        Vec3d player_speed = new Vec3d(dx, dy, dz);

        //10m以内に近づいたら処理を停止する(雑)
        if (target_vec.distanceTo(player.getPositionVec()) < 10) {
            player.sendMessage(new StringTextComponent("End"));
            return;
        }
        //進行方向(水平)にブロックがある場合45度上を向いて回避(雑)
        if (gpws.CheckHorizontal(player, player_speed, player.getEntityWorld())) {
            player.rotationPitch = -45;
            //20Tick後にブロックにぶつかりそうな場合は反対を向いて停止させる(雑)
        } else if (gpws.CheckGround(player, player_speed, player.getEntityWorld())) {
            player.rotationPitch = 0;
            player.rotationYaw = player.rotationYaw - 180;
            return;
        } else {
            //相手の方向を向く
            lookDirection(player, target_vec.subtract(player.getPositionVec()));
        }

    }

    private void lookDirection(PlayerEntity playerEntity, Vec3d vector) {
        double _2PI = 6.283185307179586D;
        double x = vector.getX();
        double z = vector.getZ();
        if (x == 0.0D && z == 0.0D) {
            playerEntity.rotationPitch = (float) (vector.getY() > 0.0D ? -90 : 90);
        } else {
            double theta = Math.atan2(-x, z);
            playerEntity.rotationYaw = (float) Math.toDegrees((theta + _2PI) % _2PI);
            double x2 = x * x;
            double z2 = z * z;
            double xz = Math.sqrt(x2 + z2);
            playerEntity.rotationPitch = (float) Math.toDegrees(Math.atan(-vector.getY() / xz));
        }
    }
}
