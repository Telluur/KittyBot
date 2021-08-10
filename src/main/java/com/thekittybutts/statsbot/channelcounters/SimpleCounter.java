package com.thekittybutts.statsbot.channelcounters;

import com.thekittybutts.statsbot.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCounter extends AbstractChannelCounter {
    private static final Logger LOGGER = LoggerFactory.getLogger("SIMPLE");
    private static final String NAME = "Simple Counter";
    private static final String VOICE_ID = "873300182836596796";
    int counter = 0;


    public SimpleCounter(Bot bot) {
        super(LOGGER, bot, NAME, VOICE_ID);
    }

    @Override
    public void run() {
        int count = counter++;
        updateCounter(count);
    }
}
