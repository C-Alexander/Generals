package works.maatwerk.generals.responselisteners;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketAdapter;
import com.github.czyzby.websocket.data.WebSocketCloseCode;

public class TestSocketListener extends WebSocketAdapter {

    @Override
    public boolean onOpen(WebSocket webSocket) {
        Gdx.app.log("Networking", "Websocket opened");

        return super.onOpen(webSocket);
    }

    @Override
    public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
        Gdx.app.error("Networking", "Websocket closed, reason: " + reason);
        return super.onClose(webSocket, code, reason);
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        Gdx.app.debug("Networking", "Got packet: " + packet);
        return super.onMessage(webSocket, packet);
    }
}
