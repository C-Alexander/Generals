package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import works.maatwerk.generals.models.FuckTeun;
import works.maatwerk.generals.responselisteners.AllGamesResponseListener;

import java.io.StringWriter;

public class NetworkTestsRunnable implements Runnable{
    public String packet;
    public FuckTeun teun;

    public void testWebSockets() {
        Gdx.app.debug("Network", "Beginning websocket test");
        WebSocket socket = WebSockets.newSocket("ws://52.28.233.213:9000/game");
        socket.setSerializeAsString(true);
        socket.addListener(new TestSocketListener());
        socket.connect();

        Json json = new Json(JsonWriter.OutputType.json);
        teun = new FuckTeun();
        teun.setTeLaat(true);
        teun.setTeun("ja");
        teun.setTriggerLevel(9001);
        packet = json.toJson(teun);

        Gdx.app.debug("Network", "Sending packet to websocket: " + packet.toString());
        socket.send(packet);

    }

    /**
     * Testing the http functions of libgdx
     */
    public void testRestAPI() {
        Gdx.app.debug("Network", "Testing REST API");

        //request to use for future networking
        Net.HttpRequest request = new Net.HttpRequest();
        testRESTGet(request);


        //post request
        testRESTPost(request);


    }

    public void testRESTGet(Net.HttpRequest request) {
        //get request
        request.setMethod(Net.HttpMethods.GET);
        request.setUrl("http://52.28.233.213:9000/games");
        Gdx.net.sendHttpRequest(request, new AllGamesResponseListener());
    }

    public void testRESTPost(Net.HttpRequest request) {
        request.setMethod(Net.HttpMethods.POST);
        request.setUrl("http://52.28.233.213:9000/games");
        request.setHeader("Content-Type", "application/json"); //needed so the server knows what to expect ;)

        Json json = getTestingJson();
        //put the object as a string in the request body
        //ok so this is ugly. First getWriter gets a JSonwriter -- we dont want that. Second gets the native java stringwriter.
        request.setContent(json.getWriter().getWriter().toString());

        //send!
        Gdx.net.sendHttpRequest(request, null);
    }

    public Json getTestingJson() {
        //creating a json body to post
        Json json = new Json(JsonWriter.OutputType.json);
        //write to a string
        json.setWriter(new StringWriter());
        //start creating the object
        json.writeObjectStart();
        json.writeValue("name", "Game 3");
        json.writeValue("host", "Job's Server (May or May Not be running)");
        json.writeObjectEnd();
        return json;
    }

    @Override
    public void run() {
        testRestAPI();
        testWebSockets();
    }
}