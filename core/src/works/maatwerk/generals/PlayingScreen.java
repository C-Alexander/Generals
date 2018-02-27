package works.maatwerk.generals;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class PlayingScreen extends ScreenAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    InputMultiplexer multiplexer;
    Animation anim;
    ParticleEffect pEffect;

    AssetManager assetManager;

    public PlayingScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        TextureAtlas atlas = assetManager.get("character.atlas");
        Music bgm = assetManager.get("data/music/megalovania.mp3");
        bgm.play();

        initializeCamera();
        batch = new SpriteBatch();
        initializeInputMultiplexer(bgm);


        initializeCharacterAnimations(atlas);
        initializeParticleEffects();


    }

    private void initializeParticleEffects() {
        // TextureAtlas pAtlas = new TextureAtlas("fire");
        pEffect = new ParticleEffect();

        pEffect.load(Gdx.files.internal("fire"), Gdx.files.internal(""));
        pEffect.getEmitters().first().setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        pEffect.start();
    }

    private void initializeCharacterAnimations(TextureAtlas atlas) {
        anim = new Animation<TextureRegion>(0.1f, atlas.getRegions());
    }

    private void initializeInputMultiplexer(Music bgm) {
        multiplexer = new InputMultiplexer();

        CameraInputController cameraInputController = initializeCameraInputController();
        multiplexer.addProcessor(new ZoomController(camera));
        multiplexer.addProcessor(new MusicController(bgm));
        multiplexer.addProcessor(cameraInputController);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private CameraInputController initializeCameraInputController() {
        CameraInputController cameraInputController = new CameraInputController(camera);
        cameraInputController.translateUnits = 900;
        cameraInputController.scrollFactor = 0;
        cameraInputController.forwardButton = -1000;
        cameraInputController.rotateButton = -1000;
        cameraInputController.rotateAngle = 0;
        cameraInputController.translateButton = Buttons.LEFT;
        return cameraInputController;
    }

    private void initializeCamera() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    public boolean fly = false;
    float stateTime = 0f;

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.setProjectionMatrix(camera.combined);
        stateTime += Gdx.graphics.getDeltaTime();

        // System.out.println(camera.zoom);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw((TextureRegion)anim.getKeyFrame(stateTime, true), 0, 0);
        pEffect.draw(batch, delta);

        batch.end();
        if (pEffect.isComplete()) pEffect.reset();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

}
