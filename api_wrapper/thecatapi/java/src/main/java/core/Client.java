package core;

import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private final Adapter adapter;

    public Client(String apiKey, Logger logger) {
        this.adapter = new Adapter(apiKey, logger);
    }
}
