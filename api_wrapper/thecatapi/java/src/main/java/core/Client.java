package core;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import okhttp3.Response;
import okhttp3.HttpUrl;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import model.Model;
import model.Breed;
import model.Image;
import model.Category;

public class Client {
    private final Adapter adapter;

    public Client(String apiKey, Logger logger) {
        this.adapter = new Adapter(apiKey, logger);
    }

    public Client(String apiKey) {
        this(apiKey, null);
    }

    public Client() {
        this("");
    }

    public Breed breed(String id) throws IOException {
        String url = Route.fullURL("breeds/{id}", new Route.Pair("id", id));
        try (Response response = adapter.get(url);) {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = Adapter.toJSON(response, Map.class);

            return new Breed(String.valueOf(data.get("id")), data);
        }
    }

    @SuppressWarnings("all")
    public List<Breed> breeds(int page, int limit) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(Route.fullURL("breeds")).newBuilder();
        builder.addQueryParameter("page", String.valueOf(page));
        builder.addQueryParameter("limit", String.valueOf(limit));

        String url = builder.build().toString();
        try (Response response = adapter.get(url);) {
            List<Map<String, Object>> data = (List) Adapter.toJSON(response, Object.class);

            List<Breed> output = new ArrayList<>();
            for (Map<String, Object> m : data) {
                Breed breed = new Breed(String.valueOf(m.get("id")), m);
                output.add(breed);
            }

            return output;
        }
    }

    public List<Breed> breeds() throws IOException {
        return breeds(0, 10);
    }

    public List<Category> categories() throws IOException {
        String url = Route.fullURL("categories");
        try (Response response = adapter.get(url);) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            List<Map<String, Object>> data = (List) Adapter.toJSON(response, Object.class);

            List<Category> output = new ArrayList<>();
            for (Map<String, Object> m : data) {
                Category category = new Category(String.valueOf(m.get("id")), m);
                output.add(category);
            }

            return output;
        }
    }

    public static void main(String[] args) throws IOException {
        var client = new Client();
        var value = client.categories();
        System.out.println(value);
    }
}
