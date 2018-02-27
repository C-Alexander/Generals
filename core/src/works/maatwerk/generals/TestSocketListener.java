package works.maatwerk.generals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketAdapter;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;

public class TestSocketListener extends WebSocketAdapter {

    @Override
    public boolean onOpen(WebSocket webSocket) {
        System.out.println(webSocket.isConnecting());

        return super.onOpen(webSocket);
    }

    @Override
    public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
        System.out.println(reason);
        return super.onClose(webSocket, code, reason);
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        Gdx.app.setLogLevel(Application.LOG_INFO);
        Gdx.app.log("Websockets", packet);
        return super.onMessage(webSocket, packet);
    }
}
