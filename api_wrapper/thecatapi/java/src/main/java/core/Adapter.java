package core;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.HttpUrl;
import okhttp3.Call;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.google.gson.Gson;

public class Adapter {
    private final String apiKey;
    private final Logger logger;
    private final OkHttpClient client;

    public Adapter(String apiKey, Logger logger) {
        this.apiKey = apiKey;
        this.logger = logger != null ? logger : LogManager.getLogger();
        this.client = new OkHttpClient();
    }

    private Response request(Method method, String url, RequestBody payload) throws IOException {
        logger.debug(String.format("Making request, method: %s, url: %s", method, url));

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("x-api-key", apiKey);
        if (method != Method.GET) {
            builder.method(method.toString(), payload != null ? payload : RequestBody.create(null));
        }

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        int statusCode = response.code();
        if ((statusCode < 200) || (statusCode >= 300))
            throw new IOException(String.format("Status code: %s", statusCode));

        return response;
    }

    private Response request(Method method, String url) throws IOException {
        return request(method, url, null);
    }

    public Response get(String url) throws IOException {
        return request(Method.GET, url);
    }

    public Response put(String url) throws IOException {
        return request(Method.PUT, url);
    }

    public Response put(String url, RequestBody payload) throws IOException {
        return request(Method.PUT, url, payload);
    }

    public Response post(String url) throws IOException {
        return request(Method.POST, url);
    }

    public Response post(String url, RequestBody payload) throws IOException {
        return request(Method.POST, url, payload);
    }

    public Response delete(String url) throws IOException {
        return request(Method.DELETE, url);
    }

    public Response delete(String url, RequestBody payload) throws IOException {
        return request(Method.DELETE, url, payload);
    }

    public static <T> T toJSON(Response response, Class<T> klass) {
        @SuppressWarnings("all")
        String body = response.body().toString();
        return new Gson().fromJson(body, klass);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, ?> toJSON(Response response) {
        return toJSON(response, HashMap.class);
    }
}
