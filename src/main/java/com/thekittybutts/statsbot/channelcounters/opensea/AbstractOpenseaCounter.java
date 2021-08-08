package com.thekittybutts.statsbot.channelcounters.opensea;

import com.thekittybutts.statsbot.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Optional;

public abstract class AbstractOpenseaCounter {
    protected static final Logger logger = LoggerFactory.getLogger("COUNTER");

    protected final Bot bot;
    protected final String name;
    protected final String voiceChannelID;
    protected float currentValue = 0;

    protected AbstractOpenseaCounter(Bot bot, String name, String voiceChannelID) {
        this.bot = bot;
        this.voiceChannelID = voiceChannelID;
        this.name = name;
    }

    protected void updateCounter(float value, String floatFormat) {
        if (currentValue != value) {
            currentValue = value;
            String formattedValue = String.format(Locale.US, floatFormat, value);
            logger.info("Updating {} [{}]", name, formattedValue);
            String s = String.format("%s: %s", name, formattedValue);
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
