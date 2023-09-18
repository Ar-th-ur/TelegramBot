package service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendBotServiceImp implements SendBotService {
    private final TelegramLongPollingBot executor;

    public SendBotServiceImp(TelegramLongPollingBot executor) {
        this.executor = executor;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        try {
            executor.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPhoto(Long chatId, InputFile file, InlineKeyboardMarkup markup, String caption) {
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), file);
        sendPhoto.setCaption(caption);
        sendPhoto.setReplyMarkup(markup);
        try {
            executor.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEditPhoto(Long chatId, Integer messageId, InputMediaPhoto media, InlineKeyboardMarkup markup) {
        EditMessageMedia messageMedia = new EditMessageMedia(media);
        messageMedia.setChatId(chatId);
        messageMedia.setMessageId(messageId);
        messageMedia.setReplyMarkup(markup);
        try {
            executor.execute(messageMedia);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEditMessage(Long chatId, Integer messageId, String text, InlineKeyboardMarkup markup) {
        EditMessageCaption editMessageText = new EditMessageCaption();
        editMessageText.setCaption(text);
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(markup);
        try {
            executor.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
