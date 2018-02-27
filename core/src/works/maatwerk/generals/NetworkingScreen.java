package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;

public class NetworkingScreen extends ScreenAdapter {

    private final NetworkTestsRunnable networkTestsRunnable = new NetworkTestsRunnable();
    private Game game;
    private AssetManager assetManager;

    public NetworkingScreen(Game game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        super.show();

        Gdx.app.setLogLevel(Logger.INFO);

        Gdx.app.debug("Network", "Starting NetworkingTests");
        new Thread(new NetworkTestsRunnable()).start();
        Gdx.app.log("Screens", "Starting PlayingScreen");
        game.setScreen(new PlayingScreen(assetManager));
    }
}
