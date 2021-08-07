package com.thekittybutts.statsbot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Config {
    private String discord_token;
    private String twitter_consumer_key, twitter_consumer_secret, twitter_access_token, twitter_access_secret;
}
