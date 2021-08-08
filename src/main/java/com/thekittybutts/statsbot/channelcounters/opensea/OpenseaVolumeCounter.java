package com.thekittybutts.statsbot.channelcounters.opensea;

import com.thekittybutts.statsbot.Bot;

public class OpenseaVolumeCounter extends AbstractOpenseaCounter {
    private static final String NAME = "Volume";
    private static final String VOICE_ID = "873723483174076486";
    private static final String FLOAT_FORMAT = "%.1fΞ";

    public OpenseaVolumeCounter(Bot bot) {
        super(bot, NAME, VOICE_ID);
    }

    public void updateCounter(float value) {
        super.updateCounter(value, FLOAT_FORMAT);
    }
}