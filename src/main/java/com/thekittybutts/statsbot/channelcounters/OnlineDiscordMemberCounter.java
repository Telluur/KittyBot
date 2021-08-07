package com.thekittybutts.statsbot.channelcounters;

import com.thekittybutts.statsbot.Bot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;

import java.util.EnumSet;
import java.util.Optional;

public class OnlineDiscordMemberCounter extends AbstractChannelCounter {
    private static final String NAME = "Online Butts";
    private static final String VOICE_ID = "873623236104028180";
    private static final String GUILD_ID = "859535876479385630";
    private static final EnumSet<OnlineStatus> onlineSet = EnumSet.of(OnlineStatus.ONLINE, OnlineStatus.IDLE, OnlineStatus.DO_NOT_DISTURB);

    public OnlineDiscordMemberCounter(Bot bot) {
        super(bot, NAME, VOICE_ID);
    }

    @Override
    public void run() {
        Optional.of(bot)
                .map(Bot::getJDA)
                .map(jda -> jda.getGuildById(GUILD_ID))
                .map(Guild::getMembers)
                .ifPresentOrElse(
                        members -> {
                            int count = (int) members.stream().filter(member -> !member.getUser().isBot() && onlineSet.contains(member.getOnlineStatus())).count();
                            updateCounter(count);
                        },
                        () -> logger.error("Failed to fetch discord member count")
                );
    }
}
