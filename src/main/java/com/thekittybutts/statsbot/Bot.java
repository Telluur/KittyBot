package com.thekittybutts.statsbot;

import com.thekittybutts.statsbot.channelcounters.DiscordMemberCounter;
import com.thekittybutts.statsbot.channelcounters.OnlineDiscordMemberCounter;
import com.thekittybutts.statsbot.channelcounters.TwitterFollowerCounter;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Bot extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger("BOT");
    @Getter
    private final Config config;
    private final List<ScheduledFuture<?>> counterSchedules = new LinkedList<>();
    @Getter
    private JDA JDA;


    public Bot(Config config) {
        this.config = config;

    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.info("JDA API ready");
        this.JDA = event.getJDA();


        /*
        Rate limit on major API endpoints in discord are 2 every 10 minutes.
        We schedule our intervals at 6 minutes per call.
         */
        logger.info("Starting Counter ScheduledExecutorServices");
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);

        /*
        SimpleCounter sc = new SimpleCounter(this);
        ScheduledFuture<?> ssf = ses.scheduleAtFixedRate(sc, 0, 6, TimeUnit.MINUTES);
        counterSchedules.add(ssf);
        */

        DiscordMemberCounter dc = new DiscordMemberCounter(this);
        ScheduledFuture<?> dsf = ses.scheduleAtFixedRate(dc, 0, 6, TimeUnit.MINUTES);
        counterSchedules.add(dsf);

        TwitterFollowerCounter tc = new TwitterFollowerCounter(this);
        ScheduledFuture<?> tsf = ses.scheduleAtFixedRate(tc, 0, 6, TimeUnit.MINUTES);
        counterSchedules.add(tsf);

        OnlineDiscordMemberCounter oc = new OnlineDiscordMemberCounter(this);
        ScheduledFuture<?> osf = ses.scheduleAtFixedRate(oc, 0, 6, TimeUnit.MINUTES);
        counterSchedules.add(osf);
    }

    @Override
    public void onShutdown(ShutdownEvent event) {
        logger.info("Cancelling Counter ScheduledExecutorServices");
        counterSchedules.forEach(scheduledFuture -> scheduledFuture.cancel(false));
    }
}
