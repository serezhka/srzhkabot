package com.github.serezhka.srzhkabot.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.generics.WebhookBot;
import org.telegram.telegrambots.starter.EnableTelegramBots;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 19.05.2018
 */
@Configuration
@EnableTelegramBots
@PropertySource("classpath:srzhkabot.properties")
public class SrzhkaBotConfig {

    @Value("${proxy.host:#{null}}")
    private String proxyHost;

    @Value("${proxy.port:#{null}}")
    private Integer proxyPort;

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (proxyHost != null && proxyPort != null) {
            requestConfigBuilder.setProxy(new HttpHost(proxyHost, proxyPort));
        }
        RequestConfig requestConfig = requestConfigBuilder.build();
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setRequestConfig(requestConfig);
        return defaultBotOptions;
    }

    @Bean
    public WebhookBot stub() {
        return new TelegramWebhookBot(defaultBotOptions()) {

            @Override
            public BotApiMethod onWebhookUpdateReceived(Update update) {
                return null;
            }

            @Override
            public String getBotUsername() {
                return null;
            }

            @Override
            public String getBotToken() {
                return null;
            }

            @Override
            public String getBotPath() {
                return null;
            }
        };
    }
}
