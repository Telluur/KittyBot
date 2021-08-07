package com.thekittybutts.statsbot.channelcounters;

import com.thekittybutts.statsbot.Bot;

public class SimpleCounter extends AbstractChannelCounter {
    private static final String NAME = "Simple Counter";
    private static final String VOICE_ID = "873300182836596796";
    int counter = 0;


    public SimpleCounter(Bot bot) {
        super(bot, NAME, VOICE_ID);
    }

    @Override
    public void run() {
        int count = counter++;
        updateCounter(count);
    }
}
