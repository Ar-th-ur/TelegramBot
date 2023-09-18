package service;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface SendBotService {
    void sendMessage(Long chatId, String message);

    void sendPhoto(Long chatId, InputFile file, InlineKeyboardMarkup markup, String caption);

    void sendEditPhoto(Long chatId, Integer messageId, InputMediaPhoto media, InlineKeyboardMarkup markup);

    void sendEditMessage(Long chatId, Integer messageId, String text, InlineKeyboardMarkup markup);
}
