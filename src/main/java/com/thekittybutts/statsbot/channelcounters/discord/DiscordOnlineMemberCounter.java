package com.thekittybutts.statsbot.channelcounters.discord;

import com.thekittybutts.statsbot.Bot;
import com.thekittybutts.statsbot.channelcounters.AbstractChannelCounter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Optional;

public class DiscordOnlineMemberCounter extends AbstractChannelCounter {
    private static final Logger LOGGER = LoggerFactory.getLogger("DISCORD");
    private static final String NAME = "Online Butts";
    private static final String VOICE_ID = "873623236104028180";
    private static final String GUILD_ID = "859535876479385630";
    private static final EnumSet<OnlineStatus> onlineSet = EnumSet.of(OnlineStatus.ONLINE, OnlineStatus.IDLE, OnlineStatus.DO_NOT_DISTURB);

    public DiscordOnlineMemberCounter(Bot bot) {
        super(LOGGER, bot, NAME, VOICE_ID);
    }

    @Override
    public void run() {
        try {
            logger.info("Running Online Member Query");
            Optional.of(bot)
                    .map(Bot::getJDA)
                    .map(jda -> jda.getGuildById(GUILD_ID))
                    .map(Guild::getMembers)
                    .ifPresentOrElse(
                            members -> {
                                int count = (int) members.stream().filter(member -> !member.getUser().isBot() && onlineSet.contains(member.getOnlineStatus())).count();
                                updateCounter(count);
                            },
                            () -> logger.error("Exception: Failed to fetch online discord member count")
                    );
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
    }
}
