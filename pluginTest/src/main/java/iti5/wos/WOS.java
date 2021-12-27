package iti5.wos;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.URISyntaxException;

public final class WOS extends JavaPlugin {
    private Game game;

    @Override
    public void onEnable() {
        System.out.println("----------------------------------------");
        System.out.println("             Plugin enable");
        System.out.println("----------------------------------------");

        try {
            this.game = Game.getGame();
            this.getCommand("wos").setExecutor(new CommandManager(this.game));

            System.out.println("[Puissance 4] socket created");
        } catch (URISyntaxException e) {
            System.out.println("[Puissance 4] error creation socket");
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        this.game.disable();
        System.out.println("----------------------------------------");
        System.out.println("             Plugin disable");
        System.out.println("----------------------------------------");
    }
}
