package service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import com.pengrad.telegrambot.request.*;

import java.io.File;

public class SendBotServiceImp implements SendBotService {
    private final TelegramBot executor;

    public SendBotServiceImp(TelegramBot executor) {
        this.executor = executor;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        executor.execute(sendMessage);
    }

    @Override
    public void sendPhoto(Long chatId, File file, InlineKeyboardMarkup markup, String caption) {
        SendPhoto sendPhoto = new SendPhoto(chatId, file);
        sendPhoto.caption(caption);
        sendPhoto.replyMarkup(markup);
        executor.execute(sendPhoto);
    }

    @Override
    public void sendEditMessage(Long chatId, Integer messageId, InputMediaPhoto media, InlineKeyboardMarkup markup) {
        EditMessageMedia messageMedia = new EditMessageMedia(chatId, messageId, media);
        messageMedia.replyMarkup(markup);
        executor.execute(messageMedia);
    }

    @Override
    public void sendEditMessage(Long chatId, Integer messageId, String text, InlineKeyboardMarkup markup) {
        EditMessageText edit = new EditMessageText(chatId, messageId, text);
        edit.replyMarkup(markup);
        executor.execute(edit);
    }
}
