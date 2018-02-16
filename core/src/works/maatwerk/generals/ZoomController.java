package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class ZoomController extends InputAdapter {
    Vector3 tp = new Vector3();

    OrthographicCamera camera;

    ZoomController(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean scrolled (int change) {
        if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) return false;
        camera.unproject(tp.set(Gdx.input.getX(), Gdx.input.getY(), 0 ));
        float px = tp.x;
        float py = tp.y;
        camera.zoom += change * camera.zoom * 0.1f;
        camera.update();

        camera.unproject(tp.set(Gdx.input.getX(), Gdx.input.getY(), 0 ));
        camera.position.add(px - tp.x, py- tp.y, 0);
        camera.update();
        return true;
    }
}
