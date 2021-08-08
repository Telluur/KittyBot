package com.thekittybutts.statsbot.channelcounters;

import com.thekittybutts.statsbot.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public abstract class AbstractChannelCounter implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger("COUNTER");
    protected final Bot bot;
    protected final String name;
    protected final String voiceChannelID;
    protected int currentValue = 0;


    protected AbstractChannelCounter(Bot bot, String name, String voiceChannelID) {
        this.bot = bot;
        this.voiceChannelID = voiceChannelID;
        this.name = name;
    }

    protected void updateCounter(int value) {
        if (currentValue != value) {
            currentValue = value;
            logger.info("Updating {} [{}]", name, value);
            String s = String.format("%s: %d", name, value);
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
