import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Response;

import core.Adapter;
import core.Route;
import core.Route.Pair;

public class Main {

    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        String url = Route.fullURL("breeds/2");
        Adapter adapter = new Adapter("", null);
        Response response = adapter.get(url);
        Object body = Adapter.toJSON(response, Object.class);
        System.out.println(body);

        Map<String, Object> mapBody = null;
        List<Object> listBody = null;

        try {
            mapBody = (Map) body;
        } catch (ClassCastException ignore) {
            listBody = (List) body;
        }

        System.out.println(mapBody);
        System.out.println(listBody);

        response.close();
    }
}
