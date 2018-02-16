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
        System.out.println(change);
        System.out.println(bgm.getVolume());
        float volumeChange = 0;
        if (change > 0) volumeChange = -0.1f;
        if (change < 0 && bgm.getVolume() < 1f) volumeChange = +0.1f;
        if (volumeChange < 0 && bgm.getVolume() < 0) {
            volumeChange = 0;
            bgm.pause();
        }
        if (volumeChange > 0 && bgm.getVolume() < 0) {
            bgm.play();
        }

        bgm.setVolume(bgm.getVolume() + volumeChange);
        System.out.println(bgm.getVolume());

        return true;
    }
}
