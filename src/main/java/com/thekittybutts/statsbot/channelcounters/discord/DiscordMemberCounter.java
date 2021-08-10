package com.thekittybutts.statsbot.channelcounters.discord;

import com.thekittybutts.statsbot.Bot;
import com.thekittybutts.statsbot.channelcounters.AbstractChannelCounter;
import net.dv8tion.jda.api.entities.GuildChannel;

import java.util.Optional;

public class DiscordMemberCounter extends AbstractChannelCounter {
    private static final String NAME = "Discord Members";
    private static final String VOICE_ID = "873609433362612294";


    public DiscordMemberCounter(Bot bot) {
        super(bot, NAME, VOICE_ID);
    }

    @Override
    public void run() {
        try {
            logger.info("Running Discord Member Query");
            Optional.of(bot)
                    .map(Bot::getJDA)
                    .map(jda -> jda.getVoiceChannelById(VOICE_ID))
                    .map(GuildChannel::getGuild)
                    .ifPresentOrElse(
                            guild -> updateCounter(guild.getMemberCount()),
                            () -> logger.error("Discord Exception: Failed to fetch discord member count"));
        } catch (Exception e) {
            logger.error("Discord Exception: ", e);
        }
    }
}
