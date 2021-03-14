package info.sdust.flightmeter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class SimplePlayerManager {

    private static SimplePlayerManager simplePlayerManager;
    private static Map loadPlayerList = new HashMap<String, SimplePlayer>();

    private SimplePlayerManager() {
        Timer time = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                SimplePlayerManager simplePlayerManager = SimplePlayerManager.getInstance();
                simplePlayerManager.updatePlayers();
                try{
                    //Minecraft.getInstance().player.sendMessage(new StringTextComponent(simplePlayerManager.toString()));
                }catch (Exception e){

                }

            }
        };
        time.scheduleAtFixedRate(timerTask, 1000, 10000);

    }

    public static SimplePlayerManager getInstance() {
        if (simplePlayerManager == null) {
            simplePlayerManager = new SimplePlayerManager();
        }
        return simplePlayerManager;
    }

    public SimplePlayer getPlayer(String name) {
        SimplePlayer simplePlayer = null;

        if (getOnlinePlayers().containsKey(name)) {
            simplePlayer = getOnlinePlayers().get(name);
        }

        for (PlayerEntity it : Minecraft.getInstance().world.getPlayers()) {
            if (it.getName().getString().equals(name)) {
                if (simplePlayer != null) {
                    simplePlayer.Update(it);
                } else {
                    simplePlayer = new SimplePlayer(it);
                    loadPlayerList.put(it.getName(), new SimplePlayer(it));
                }
            }
        }
        return simplePlayer;
    }

    public void updatePlayers() {
        String urlString = "http://setuden.minecraftuser.jp/up/world/home01/9999999999999";
        JsonNode result = getResult(urlString);
        if (result != null) {
            Date timestamp = new Date(result.get("timestamp").asLong());
            result.get("players").forEach(it -> {
                String name = it.get("name").asText();
                if (!loadPlayerList.containsKey(name)) {
                    loadPlayerList.put(name, new SimplePlayer(it, timestamp));
                } else {
                    ((SimplePlayer) loadPlayerList.get(name)).Update(it, timestamp);
                }
            });
        }
    }

    public Map<String, SimplePlayer> getOnlinePlayers() {
        Map<String, SimplePlayer> onlinePlayerList = new HashMap();

        loadPlayerList.forEach((name, it) -> {
            SimplePlayer player = (SimplePlayer) it;
            LocalDateTime playerDateTime = LocalDateTime.ofInstant(player.getTimeStamp().toInstant(), ZoneId.systemDefault());
            //1分前以上更新されていない場合はオフラインと判断する
            if (playerDateTime.isAfter(LocalDateTime.now().minusMinutes(1))) {
                onlinePlayerList.put(player.getName(), player);
            }
        });
        return onlinePlayerList;
    }

    private JsonNode getResult(String urlString) {
        String result = "";
        JsonNode root = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String tmp = "";

            while ((tmp = in.readLine()) != null) {
                result += tmp;
            }

            ObjectMapper mapper = new ObjectMapper();
            root = mapper.readTree(result);
            in.close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        loadPlayerList.forEach((name, player) -> sb.append(player.toString()).append("\n"));
        return sb.toString();
    }
}

