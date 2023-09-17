package service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;

import java.io.File;

public interface SendBotService {
    void sendMessage(Long chatId, String message);

    void sendPhoto(Long chatId, File file, InlineKeyboardMarkup markup, String caption);

    void sendEditMessage(Long chatId, Integer messageId, InputMediaPhoto media, InlineKeyboardMarkup markup);

    void sendEditMessage(Long chatId, Integer messageId, String text, InlineKeyboardMarkup markup);
}
