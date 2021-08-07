package com.thekittybutts.statsbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.yaml.snakeyaml.Yaml;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException, FileNotFoundException {
        Yaml yaml = new Yaml();
        Config config = yaml.load(new FileInputStream("yaml/config.yaml"));


        JDA jda = JDABuilder.create(config.getDiscord_token(), EnumSet.allOf(GatewayIntent.class))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Bot(config))
                .build();
        jda.awaitReady();
    }
}
