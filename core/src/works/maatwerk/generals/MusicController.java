package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;

public class MusicController extends InputAdapter {
    private Music bgm;

    public MusicController(Music bgm) {
        this.bgm = bgm;
    }

    @Override
    public boolean scrolled(int change) {
        super.scrolled(change);
            if (!Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) return false;

        adjustVolume(change);

        return true;
    }

    private void adjustVolume(int change) {
        float volumeChange = 0;
        if (change > 0) volumeChange = -0.1f;
        if (change < 0 && bgm.getVolume() < 1f) volumeChange = +0.1f;
        if (volumeChange < 0 && bgm.getVolume() < 0) {
            volumeChange = 0;
            bgm.pause();
            Gdx.app.debug("Music", "Pausing music");
        }
        if (volumeChange > 0 && bgm.getVolume() < 0) {
            bgm.play();
            Gdx.app.debug("Music", "Playing music");
        }

        bgm.setVolume(bgm.getVolume() + volumeChange);
        Gdx.app.debug("Music", "Set volume to: " + volumeChange);

    }
}
