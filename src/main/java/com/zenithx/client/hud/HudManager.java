package com.zenithx.client.hud;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HudManager {
    public static final List<HudElement> ELEMENTS = new ArrayList<>();
    private static final Queue<Long> leftClicks = new LinkedList<>();
    private static final Queue<Long> rightClicks = new LinkedList<>();

    public static void init() {
        ELEMENTS.clear();
        ELEMENTS.add(new HudElement("FPS", 8, 8) {
            @Override
            public String getDisplayText() {
                return "§cFPS §7» §f" + mc.getCurrentFps();
            }
        });

        ELEMENTS.add(new HudElement("CPS", 8, 26) {
            @Override
            public String getDisplayText() {
                return "§cCPS §7» §f" + getLeftCps() + " §8| §f" + getRightCps();
            }
        });

        ELEMENTS.add(new HudElement("Coords", 8, 44) {
            @Override
            public String getDisplayText() {
                if (mc.player == null) return "§cXYZ §7» §f0, 0, 0";
                return String.format("§cXYZ §7» §f%d, %d, %d",
                        (int) mc.player.getX(), (int) mc.player.getY(), (int) mc.player.getZ());
            }
        });
    }

    public static void registerLeftClick() { leftClicks.add(System.currentTimeMillis()); }
    public static void registerRightClick() { rightClicks.add(System.currentTimeMillis()); }

    public static int getLeftCps() {
        long now = System.currentTimeMillis();
        while (!leftClicks.isEmpty() && now - leftClicks.peek() > 1000L) leftClicks.poll();
        return leftClicks.size();
    }

    public static int getRightCps() {
        long now = System.currentTimeMillis();
        while (!rightClicks.isEmpty() && now - rightClicks.peek() > 1000L) rightClicks.poll();
        return rightClicks.size();
    }
}
