package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Logger;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.net.ExtendedNet;
import works.maatwerk.generals.responselisteners.AllGamesResponseListener;

import java.io.StringWriter;

public class Generals extends Game {

	@Override
	public void create() {
		//log everything... spammy
		Gdx.app.setLogLevel(Logger.INFO);
		testRestAPI();
		testWebSockets();


		this.setScreen(new PlayingScreen());
	}

	private void testWebSockets() {
		WebSocket socket = WebSockets.newSocket("ws://localhost:9000/game");
		socket.connect();

	}

	/**
	 * Testing the http functions of libgdx
	 */
	private void testRestAPI() {
		//request to use for future networking
		Net.HttpRequest request = new Net.HttpRequest();
		testRESTGet(request);


		//post request
		testRESTPost(request);


	}

	private void testRESTGet(Net.HttpRequest request) {
		//get request
		request.setMethod(Net.HttpMethods.GET);
		request.setUrl("http://localhost:9000/games");
		Gdx.net.sendHttpRequest(request, new AllGamesResponseListener());
	}

	private void testRESTPost(Net.HttpRequest request) {
		request.setMethod(Net.HttpMethods.POST);
		request.setUrl("http://localhost:9000/games");
		request.setHeader("Content-Type", "application/json"); //needed so the server knows what to expect ;)

		Json json = getTestingJson();
		//put the object as a string in the request body
		//ok so this is ugly. First getWriter gets a JSonwriter -- we dont want that. Second gets the native java stringwriter.
		request.setContent(json.getWriter().getWriter().toString());

		//send!
		Gdx.net.sendHttpRequest(request, null);
	}

	private Json getTestingJson() {
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


}
