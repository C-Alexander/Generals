package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

class LoadingScreen extends ScreenAdapter {

    private final Game game;

    private FitViewport viewport;
    private OrthographicCamera camera;
    private final SpriteBatch batch;
    private Texture logo;
    private Texture fullBar;
    private Texture emptyBar;
    private Vector2 logoPos;
    private Vector2 ebPos;
    private Vector2 fbPos;
    private final AssetManager assetManager;

    private void initializeCamera() {
        Gdx.app.debug("Camera", "Initializing");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }

    private void loadLoadingBar() {
        Gdx.app.debug("Loading", "Loading Loadingbar");
        this.assetManager.load("loader/logo.jpg", Texture.class);
        this.assetManager.load("loader/main_bar.png", Texture.class);
        this.assetManager.load("loader/bar_full.png", Texture.class);

        this.assetManager.finishLoading();
    }

    private void initializeLoadingBar() {
        Gdx.app.debug("Loading", "Initializing Loadingbar");

        logo = this.assetManager.get("loader/logo.jpg");
        emptyBar = this.assetManager.get("loader/main_bar.png");
        fullBar = this.assetManager.get("loader/bar_full.png");

        logoPos = new Vector2((Gdx.graphics.getWidth()-logo.getWidth())/2 , (Gdx.graphics.getHeight() / 2)-100);
        ebPos = new Vector2((Gdx.graphics.getWidth()-emptyBar.getWidth())/2 , logoPos.y - emptyBar.getHeight() + 40);
        fbPos = new Vector2((ebPos.x + (emptyBar.getWidth() / 2)) - (fullBar.getWidth()/2), (ebPos.y + (emptyBar.getHeight() / 2)) - (fullBar.getHeight()/2));
    }

    LoadingScreen(Game game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;

        batch = new SpriteBatch();

        initializeCamera();

        loadLoadingBar();
        initializeLoadingBar();
    }

    @Override
    public void show() {
        super.show();

        loadAssets();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Gdx.app.debug("Camera", "Resizing screen");

        viewport.update(width, height);
        camera.update();
    }

    private void loadAssets() {
        Gdx.app.debug("Loading", "Loading assets");


        assetManager.load("character.atlas", TextureAtlas.class);
        assetManager.load("data/music/megalovania.mp3", Music.class);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        assetManager.update();


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(logo, logoPos.x, logoPos.y);
        batch.draw(emptyBar, ebPos.x, ebPos.y);
        batch.draw(fullBar, fbPos.x, fbPos.y, fullBar.getWidth() * assetManager.getProgress(), fullBar.getHeight());

        batch.end();

        if (assetManager.getProgress() == 1.0f) {
            Gdx.app.log("Screens", "Starting NetworkingScreen");
            game.setScreen(new NetworkingScreen(game, assetManager));
        }
    }
}
