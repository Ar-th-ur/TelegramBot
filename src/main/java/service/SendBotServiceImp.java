package service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.invoices.CreateInvoiceLink;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.ButtonUtils;

import java.util.List;

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
        media.setParseMode(ParseMode.MARKDOWN);
        try {
            executor.execute(messageMedia);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getInvoiceLink(Long chatId, String title, String payload, int price) {
        CreateInvoiceLink sendInvoice = CreateInvoiceLink.builder()
                .currency("RUB")
                .providerToken("410694247:TEST:8fbddfa7-6c7f-43f2-8ed1-63af9094d320")
                .title("Набор «" + title + "»")
                .description("Товар для игры Fornite на ваш аккаунт")
                .payload(payload)
                .price(new LabeledPrice("Цена", price))
                .build();
        try {
            return executor.execute(sendInvoice);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void send(SendMessage sendMessage) {
        try {
            executor.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        try {
            executor.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Long chatId, String message, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(markup);
        try {
            executor.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEditMessage(Long chatId, Integer messageId, String message) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(message);
        try {
            executor.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
