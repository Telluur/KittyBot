package com.thekittybutts.statsbot.channelcounters.twitter;

import com.thekittybutts.statsbot.Bot;
import com.thekittybutts.statsbot.Config;
import com.thekittybutts.statsbot.channelcounters.AbstractChannelCounter;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFollowerCounter extends AbstractChannelCounter {
    private static final String NAME = "Twitter Followers";
    private static final String VOICE_ID = "873609460659126322";
    private static final String TWITTER_HANDLE = "thekittybutts";
    private final TwitterFactory twitterFactory;

    public TwitterFollowerCounter(Bot bot) {
        super(bot, NAME, VOICE_ID);

        Config config = bot.getConfig();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(config.getTwitter_consumer_key())
                .setOAuthConsumerSecret(config.getTwitter_consumer_secret())
                .setOAuthAccessToken(config.getTwitter_access_token())
                .setOAuthAccessTokenSecret(config.getTwitter_access_secret());
        this.twitterFactory = new TwitterFactory(cb.build());
    }


    @Override
    public void run() {
        try {
            logger.info("Running Twitter Query");
            Twitter twitter = twitterFactory.getInstance();
            int followersCount = twitter.showUser(TWITTER_HANDLE).getFollowersCount();
            updateCounter(followersCount);
        } catch (Exception e) {
            logger.error("Twitter Exception: ", e);
        }
    }
}
