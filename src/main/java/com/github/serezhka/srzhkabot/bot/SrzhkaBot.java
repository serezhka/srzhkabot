package com.github.serezhka.srzhkabot.bot;

import com.github.serezhka.srzhkabot.service.LotteryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 19.05.2018
 */
@Component
public class SrzhkaBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(SrzhkaBot.class);

    private final LotteryService lotteryService;

    @Value("${botName}")
    private String botName;

    @Value("${botToken}")
    private String botToken;

    @Value("${adminId}")
    private int adminId;

    @Value("#{'${allowedParticipantIds}'.split(',')}")
    private List<Integer> allowedParticipantIds;

    @Autowired
    public SrzhkaBot(DefaultBotOptions defaultBotOptions,
                     LotteryService lotteryService) {
        super(defaultBotOptions);
        this.lotteryService = lotteryService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.debug("Received update " + update);
        if (!update.hasMessage() || !update.getMessage().isUserMessage() || !update.getMessage().hasText() || update.getMessage().getText().isEmpty())
            return;
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        User user = update.getMessage().getFrom();
        Integer userId = user.getId();
        String userName = user.getFirstName() + " " + user.getLastName();

        if (adminId == userId) {
            if (message.equalsIgnoreCase("run")) {
                String winners = "Winners:\n" + String.join(",\n", lotteryService.getWinners(4));
                lotteryService.getParticipants().forEach(chat -> sendTextMessage(chat, winners));
                sendTextMessage(chatId, winners);
            }
        } else if (allowedParticipantIds.contains(userId)) {
            if (message.equalsIgnoreCase("yes")) {
                lotteryService.updateParticipant(userId, chatId, userName, true);
                sendTextMessage(chatId, "Registered!");
            } else if (message.equalsIgnoreCase("no")) {
                lotteryService.updateParticipant(userId, chatId, userName, false);
                sendTextMessage(chatId, "Unregistered!");
            } else {
                sendTextMessage(chatId, "Do you wanna fly? yes|no");
            }
        } else {
            sendTextMessage(chatId, "You're not part of Phoenix Capri team! Go f*ck yourself!");
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
