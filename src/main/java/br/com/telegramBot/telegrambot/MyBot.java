package br.com.telegramBot.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.apache.commons.lang3.StringUtils.containsAnyIgnoreCase;

@Component
public class MyBot extends TelegramLongPollingBot {
    private final String botAge = "1 week";
    private final String botName = "Ubot1Week";
    private final String botNick = "Ubotinho";
    private final String anRecognizeMessage = "Olá, não entendi a sua pergunta!";

    @Value("${telegram.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return "uBotsWeek1_bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = null;
        String text = update.getMessage().getText();

        if (containsAnyIgnoreCase(text, getNameQuestionPatterns())) {
            sendMessage = new SendMessage(Long.toString(update.getMessage().getChatId()), botName);

        } else if (containsAnyIgnoreCase(text, getAgeQuestionPatterns())) {
            sendMessage = new SendMessage(Long.toString(update.getMessage().getChatId()), botAge);

        } else if (containsAnyIgnoreCase(text, getAgeNickPatterns())) {
            sendMessage = new SendMessage(Long.toString(update.getMessage().getChatId()), botNick);

        } else if (containsAnyIgnoreCase(text, getWeekQuestionPatterns())) {
            sendMessage = new SendMessage(Long.toString(update.getMessage().getChatId()),botNick);

        } else {
            sendMessage = new SendMessage(Long.toString(update.getMessage().getChatId()), anRecognizeMessage);

        }

        send(sendMessage);
    }

    private String[] getWeekQuestionPatterns() { return new String[]{"semana", " semana "}; }

    private String[] getNameQuestionPatterns() {
        return new String[]{" nome?", " chama? ", " chamas?"};
    }

    private String[] getAgeQuestionPatterns() {
        return new String[]{" idade?", " idade ", " anos?", " anos "};
    }

    private String[] getAgeNickPatterns() {
        return new String[]{" apelido?", " apelido "};
    }

    private void send(SendMessage sendMessage) {
        if (sendMessage == null) {
            return;
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
