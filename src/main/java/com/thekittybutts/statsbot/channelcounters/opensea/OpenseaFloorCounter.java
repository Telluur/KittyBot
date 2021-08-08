package com.thekittybutts.statsbot.channelcounters.opensea;

import com.thekittybutts.statsbot.Bot;

public class OpenseaFloorCounter extends AbstractOpenseaCounter {
    private static final String NAME = "Floor";
    private static final String VOICE_ID = "873723449363824650";
    private static final String FLOAT_FORMAT = "%.3f Ξ";

    protected OpenseaFloorCounter(Bot bot) {
        super(bot, NAME, VOICE_ID);
    }

    public void updateCounter(float value) {
        super.updateCounter(value, FLOAT_FORMAT);
    }
}
