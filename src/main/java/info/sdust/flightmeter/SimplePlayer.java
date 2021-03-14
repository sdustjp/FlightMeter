package info.sdust.flightmeter;

import com.fasterxml.jackson.databind.JsonNode;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Date;

public class SimplePlayer {

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    public String getWorld_old() {
        return world_old;
    }

    public double getX() {
        return x;
    }

    public double getX_old() {
        return x_old;
    }

    public double getY() {
        return y;
    }

    public double getY_old() {
        return y_old;
    }

    public double getZ() {
        return z;
    }

    public double getZ_old() {
        return z_old;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Date getTimeStamp_old() {
        return timeStamp_old;
    }

    private String name;
    private String world;
    private String world_old;
    private double x;
    private double x_old;
    private double y;
    private double y_old;
    private double z;
    private double z_old;
    private Date timeStamp;
    private Date timeStamp_old;

    public SimplePlayer(String name, String world, double x, double y, double z, Date timeStamp) {
        this.name = name;
        this.x = x;
        this.x_old = x;
        this.y = y;
        this.y_old = y;
        this.z = z;
        this.z_old = z;
        this.timeStamp = timeStamp;
        this.timeStamp_old = timeStamp;
        this.world = world;
        this.world_old = world;
    }

    public SimplePlayer(JsonNode root, Date timeStamp) {
        this(root.get("name").asText(),
                root.get("world").asText(),
                root.get("x").asDouble(),
                root.get("y").asDouble(),
                root.get("z").asDouble(),
                timeStamp);
    }

    public SimplePlayer(PlayerEntity player) {
        this(player.getName().toString(),
                player.getEntityWorld().getWorldInfo().getWorldName(),
                player.getPositionVec().x,
                player.getPositionVec().y,
                player.getPositionVec().z,
                new Date());
    }

    public void Update(String world, double x, double y, double z, Date timeStamp) {


        if (this.timeStamp.before(timeStamp)) {
            this.timeStamp_old = this.timeStamp;
            this.world_old = this.world;
            this.x_old = this.x;
            this.y_old = this.y;
            this.z_old = this.z;

            this.timeStamp = timeStamp;
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public void Update(JsonNode root, Date timeStamp) {
        Update(
                root.get("world").asText(),
                root.get("x").asDouble(),
                root.get("y").asDouble(),
                root.get("z").asDouble(),
                timeStamp);
    }

    public void Update(PlayerEntity player) {
        Update(
                player.getEntityWorld().getWorldInfo().getWorldName(),
                player.getPositionVec().x,
                player.getPositionVec().y,
                player.getPositionVec().z,
                new Date());
    }

    @Override
    public String toString() {
        return "SimplePlayer{" +
                "name='" + name + '\'' +
                ", world='" + world + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
