package com.thekittybutts.statsbot.channelcounters.opensea;

import com.thekittybutts.statsbot.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Optional;

public abstract class AbstractOpenseaCounter {
    protected static final Logger logger = LoggerFactory.getLogger("OPENSEA");

    protected final Bot bot;
    protected final String name;
    protected final String voiceChannelID;
    protected String currentFormatted = "";

    protected AbstractOpenseaCounter(Bot bot, String name, String voiceChannelID) {
        this.bot = bot;
        this.voiceChannelID = voiceChannelID;
        this.name = name;
    }

    protected void updateCounter(float value, String floatFormat) {
        String newFormatted = String.format(Locale.US, floatFormat, value);
        if (!currentFormatted.equals(newFormatted)) {
            currentFormatted = newFormatted;
            logger.info("Updating {} [{}]", name, newFormatted);
            String s = String.format("%s: %s", name, newFormatted);
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
