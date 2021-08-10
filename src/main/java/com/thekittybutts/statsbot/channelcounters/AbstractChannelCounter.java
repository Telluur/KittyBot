package com.thekittybutts.statsbot.channelcounters;

import com.thekittybutts.statsbot.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.slf4j.Logger;

import java.util.Optional;


public abstract class AbstractChannelCounter implements Runnable {
    protected final Logger logger;
    protected final Bot bot;
    protected final String name;
    protected final String voiceChannelID;
    protected int currentValue = 0;


    protected AbstractChannelCounter(Logger logger, Bot bot, String name, String voiceChannelID) {
        this.logger = logger;
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
                            () -> logger.error("Failed to update channel {}", name));
        }
    }
}
