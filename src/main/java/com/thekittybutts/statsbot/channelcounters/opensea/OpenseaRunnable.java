package com.thekittybutts.statsbot.channelcounters.opensea;

import com.google.gson.Gson;
import com.thekittybutts.statsbot.Bot;
import com.thekittybutts.statsbot.channelcounters.opensea.model.GraphQLResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenseaRunnable implements Runnable {
    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    protected static final Logger logger = LoggerFactory.getLogger("OPENSEA");
    private static final String OPENSEA_COLLECTION_SLUG = "the-kittybutts";
    private static final String URL = "https://api.opensea.io/graphql/";
    private static final String JSON_REQUEST = String.format("{\"query\": \"{\\n  collection(slug: \\\"%s\\\") {\\n floorPrice \\n stats{\\n      floorPrice\\n      totalVolume\\n      numOwners\\n    }\\n  }\\n}\",\"variables\":null}", OPENSEA_COLLECTION_SLUG);
    private final OpenseaFloorCounter floorCounter;
    private final OpenseaVolumeCounter volumeCounter;
    private final OpenseaOwnerCounter ownerCounter;

    public OpenseaRunnable(Bot bot) {
        floorCounter = new OpenseaFloorCounter(bot);
        volumeCounter = new OpenseaVolumeCounter(bot);
        ownerCounter = new OpenseaOwnerCounter(bot);
    }

    @Override
    public void run() {
        try {
            logger.info("Running Query");

            //Single graphql query for both counters
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON_REQUEST, MEDIA_TYPE);
            Request request = new Request.Builder()
                    .url(URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();
            logger.info("Response: {}", jsonResponse);

            //Update individual counters
            GraphQLResponse graphQLResponse = new Gson().fromJson(jsonResponse, GraphQLResponse.class);
            floorCounter.updateCounter(graphQLResponse.getData().getCollection().getFloorPrice());
            volumeCounter.updateCounter(graphQLResponse.getData().getCollection().getStats().getTotalVolume());
            ownerCounter.updateCounter(graphQLResponse.getData().getCollection().getStats().getNumOwners());
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
    }
}
