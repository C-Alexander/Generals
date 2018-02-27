package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Logger;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.net.ExtendedNet;
import works.maatwerk.generals.models.FuckTeun;
import works.maatwerk.generals.responselisteners.AllGamesResponseListener;

import java.io.StringWriter;

public class Generals extends Game {

	AssetManager assetManager;

	@Override
	public void create() {
		assetManager = new AssetManager();

		Gdx.app.log("Screens", "Starting LoadingScreen");
		this.setScreen(new LoadingScreen(this, assetManager));
	}



}
