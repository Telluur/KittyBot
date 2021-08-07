package com.thekittybutts.statsbot.channelcounters;

import com.thekittybutts.statsbot.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public abstract class AbstractChannelCounter implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger("COUNTER");
    protected final Bot bot;
    private final String name;
    private final String voiceChannelID;
    private int count = 0;


    protected AbstractChannelCounter(Bot bot, String name, String voiceChannelID) {
        this.bot = bot;
        this.voiceChannelID = voiceChannelID;
        this.name = name;
    }

    void updateCounter(int value) {
        if (count != value) {
            count = value;
            logger.info("Updating {} [{}]", name, value);
            String s = String.format("[%d] %s", value, name);
            Optional.of(bot)
                    .map(Bot::getJDA)
                    .map(jda -> jda.getVoiceChannelById(voiceChannelID))
                    .map(GuildChannel::getManager)
                    .ifPresentOrElse(
                            channelManager -> channelManager.setName(s).queue(),
                            () -> logger.error("Failed to update counter {}", name));
        }
    }

}
