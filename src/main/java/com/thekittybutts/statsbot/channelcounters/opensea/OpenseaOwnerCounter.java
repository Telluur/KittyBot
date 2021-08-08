package com.thekittybutts.statsbot.channelcounters.opensea;

import com.thekittybutts.statsbot.Bot;

public class OpenseaOwnerCounter extends AbstractOpenseaCounter {
    private static final String NAME = "KittyButt Owners";
    private static final String VOICE_ID = "873746503007617024";
    private static final String FLOAT_FORMAT = "%.0f";

    protected OpenseaOwnerCounter(Bot bot) {
        super(bot, NAME, VOICE_ID);
    }

    public void updateCounter(float value) {
        super.updateCounter(value, FLOAT_FORMAT);
    }
}
