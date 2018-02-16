package works.maatwerk.generals.responselisteners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class AllGamesResponseListener implements Net.HttpResponseListener {

    @Override
public void handleHttpResponse(Net.HttpResponse httpResponse) {
Gdx.app.log("Networking", httpResponse.toString());

JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());

Gdx.app.log("JSON", "Name: " + json.child().getString("name"));

Gdx.app.log("JSON","Second Host: " + json.child().next().getString("host"));
}

    @Override
public void failed(Throwable t) {
Gdx.app.error("Networking", t.getMessage(), t);
}

    @Override
public void cancelled() {
Gdx.app.error("Networking","Cancelled... why ");
}
}
