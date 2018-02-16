package works.maatwerk.generals;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class PlayingScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture img;
    OrthographicCamera camera;
    InputMultiplexer multiplexer;
    Animation anim;
    ParticleEffect pEffect;


    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        batch = new SpriteBatch();
        multiplexer = new InputMultiplexer();
        CameraInputController cic = new CameraInputController(camera);
       cic.translateUnits = 900;
        cic.scrollFactor = 0;
        cic.forwardButton = -1000;
        cic.rotateButton = -1000;
        cic.rotateAngle = 0;
        cic.translateButton = Buttons.LEFT;
        TextureAtlas atlas = new TextureAtlas("character.atlas");
        anim = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        Music bgm = Gdx.audio.newMusic(Gdx.files.internal("data/music/megalovania.mp3"));
        bgm.play();
        multiplexer.addProcessor(new ZoomController(camera));
        multiplexer.addProcessor(new MusicController(bgm));
        multiplexer.addProcessor(cic);
        img = new Texture("badlogic.jpg");
        Gdx.input.setInputProcessor(multiplexer);

       // TextureAtlas pAtlas = new TextureAtlas("fire");
        pEffect = new ParticleEffect();

        pEffect.load(Gdx.files.internal("fire"), Gdx.files.internal(""));
        pEffect.getEmitters().first().setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        pEffect.start();
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
        batch.draw((TextureRegion)anim.getKeyFrame(stateTime, false), 0, 0);
        pEffect.draw(batch, delta);

        batch.end();
        if (pEffect.isComplete()) pEffect.reset();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }

}
