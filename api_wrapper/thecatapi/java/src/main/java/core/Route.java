package core;

public class Route {
    private static final String scheme = "https";
    private static final String hostname = "api.thecatapi.com";
    private static final String version = "v1";
    private static final String BASE_URL = scheme + "://" + hostname + "/" + version;

    public static String fullURL(String path, Pair... params) {
        String url = path.startsWith("/") ? BASE_URL + path : BASE_URL + "/" + path;
        for (Pair p : params) {
            String k = "{" + p.key() + "}";
            while (url.contains(k)) {
                url = url.replace(k, p.value());
            }
        }

        return url;
    }

    public record Pair(String key, String value) {
    }
}
