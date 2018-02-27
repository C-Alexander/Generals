package works.maatwerk.generals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class Generals extends Game {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		Gdx.app.log("Screens", "Starting LoadingScreen");
		this.setScreen(new LoadingScreen(this, new AssetManager()));
	}



}
