package com.thekittybutts.statsbot.channelcounters.discord;

import com.thekittybutts.statsbot.Bot;
import com.thekittybutts.statsbot.channelcounters.AbstractChannelCounter;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DiscordMemberCounter extends AbstractChannelCounter {
    private static final Logger LOGGER = LoggerFactory.getLogger("DISCORD");
    private static final String NAME = "Discord Members";
    private static final String VOICE_ID = "873609433362612294";


    public DiscordMemberCounter(Bot bot) {
        super(LOGGER, bot, NAME, VOICE_ID);
    }

    @Override
    public void run() {
        try {
            logger.info("Running Total Member Query");
            Optional.of(bot)
                    .map(Bot::getJDA)
                    .map(jda -> jda.getVoiceChannelById(VOICE_ID))
                    .map(GuildChannel::getGuild)
                    .ifPresentOrElse(
                            guild -> updateCounter(guild.getMemberCount()),
                            () -> logger.error("Exception: Failed to fetch discord member count"));
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
    }
}
