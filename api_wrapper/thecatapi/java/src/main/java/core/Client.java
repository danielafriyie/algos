package core;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import model.Model;
import model.Breed;
import model.Image;
import model.Category;
import core.query.ImageSearchQueryBuilder;

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
    public List<Breed> breeds(int page, int limit) throws Exception {
        HttpUrl.Builder builder = HttpUrl.parse(Route.fullURL("breeds")).newBuilder();
        builder.addQueryParameter("page", String.valueOf(page));
        builder.addQueryParameter("limit", String.valueOf(limit));

        String url = builder.build().toString();
        try (Response response = adapter.get(url);) {
            List<Map<String, Object>> data = (List) Adapter.toJSON(response, Object.class);
            List<Breed> output = Model.createList(data, Breed.class);

            return output;
        }
    }

    public List<Breed> breeds() throws Exception {
        return breeds(0, 10);
    }

    public List<Category> categories() throws Exception {
        String url = Route.fullURL("categories");
        try (Response response = adapter.get(url);) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            List<Map<String, Object>> data = (List) Adapter.toJSON(response, Object.class);

            @SuppressWarnings("all")
            List<Category> output = Model.createList(data, Category.class);

            return output;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<Image> imageSearch(ImageSearchQueryBuilder builder) throws Exception {
        String query = builder.build();
        String url = Route.fullURL("images/search?") + query;

        try (Response response = adapter.get(url);) {
            List<Image> images = new ArrayList<>();
            List<Map<String, Object>> data = Adapter.toJSON(response, List.class);

            for (Map<String, Object> m : data) {
                List<Map<String, Object>> breedList = (List) m.get("breeds");
                List<Breed> breeds = Model.createList(breedList, Breed.class);

                List<Map<String, Object>> catList = (List) m.get("categories");
                List<Category> categories = Model.createList(catList, Category.class);

                Image image = new Image(String.valueOf(m.get("id")), breeds, categories, m);
                images.add(image);
            }

            return images;
        }
    }

    public List<Image> imageSearch() throws Exception {
        return imageSearch(new ImageSearchQueryBuilder());
    }

    public Image imageUpload(String image, String subID) throws IllegalAccessException, IOException {
        if (adapter.getApiKey().equals(""))
            throw new IllegalAccessException("API Key not set!");

        String url = Route.fullURL("images/upload");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", image, RequestBody.create(new File(image), MediaType.parse("image/png")));
        if (subID != null) {
            builder.addFormDataPart("sub_id", subID);
        }

        try (Response response = adapter.post(url, builder.build())) {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = Adapter.toJSON(response, Map.class);
            return new Image(String.valueOf(data.get("id")), new ArrayList<>(), new ArrayList<>(), data);
        }
    }

    public Image imageUpload(String image) throws IllegalAccessException, IOException {
        return imageUpload(image, null);
    }
}
