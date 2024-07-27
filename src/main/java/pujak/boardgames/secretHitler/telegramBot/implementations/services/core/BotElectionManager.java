package pujak.boardgames.secretHitler.telegramBot.implementations.services.core;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.telegramBot.SecretHitlerBot;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks.CandidateChoosingCallback;
import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameStorage;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.CallbackStorage;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.CallbackWaiter;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks.ElectionCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class BotElectionManager implements ElectionManager {

    @Autowired
    private SecretHitlerBot secretHitlerBot;

    @Autowired
    private CallbackStorage<ElectionCallback> electionCallbackStorage;

    @Autowired
    private CallbackStorage<CandidateChoosingCallback> choosingCallbackStorage;

    @Autowired
    private CallbackWaiter<String, ElectionCallback> electionCallbackWaiter;

    @Autowired
    private CallbackWaiter<String, CandidateChoosingCallback> choosingCallbackWaiter;

    @Autowired
    private GameStorage gameStorage;

    @Override
    public ArrayList<String> getVotes(List<Player> voters, ArrayList<String> variants, String message) {
        var receiverIds = voters.stream().map(Player::getTelegramId).toList();
        var inlineKeyboardVariants = new ArrayList<InlineKeyboardButton>();
        for (var variant: variants){
            var variantKeyboard = InlineKeyboardButton.builder().text(variant).callbackData(variant).build();
            inlineKeyboardVariants.add(variantKeyboard);
        }

        var callbackList = getCallbacks(voters, variants);

        for (var id: receiverIds){
            try {
                var keyboard = InlineKeyboardMarkup.builder().keyboard((List)inlineKeyboardVariants).build();
                var sendMessage = SendMessage.builder().text(message).chatId(String.valueOf(id)).replyMarkup(keyboard).build();
                secretHitlerBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        var res = new ArrayList<String>();

        try {
            res = electionCallbackWaiter.getCallbacks(callbackList).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    @Override
    public Long getChosenVariant(long receiverId, Map<Long, String> data) {
        var roomId = gameStorage.getRoomByUserId(receiverId).getHostId();
        var inlineKeyboardVariants = new ArrayList<InlineKeyboardButton>();

        var callbacks = new ArrayList<CandidateChoosingCallback>();

        for (var item: data.keySet()){
            var callback = new CallbackData(roomId, receiverId, item.toString());
            var variantKeyboard = InlineKeyboardButton.builder().text(data.get(item)).callbackData(callback.toString()).build();
            inlineKeyboardVariants.add(variantKeyboard);
            choosingCallbackStorage.registerCallback(new CandidateChoosingCallback(callback));
        }

        try {
            var keyboard = InlineKeyboardMarkup.builder().keyboard((List)inlineKeyboardVariants).build();
            var sendMessage = SendMessage.builder().text("Choose one of them:").chatId(String.valueOf(receiverId)).replyMarkup(keyboard).build();
            secretHitlerBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        try {
            return Long.valueOf(choosingCallbackWaiter.getCallback(callbacks).get());
        }catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<ElectionCallback> getCallbacks(List<Player> voters, ArrayList<String> variants){
        var room = gameStorage.getRoomByUser(voters.getFirst());

        var res = new ArrayList<ElectionCallback>();

        for (var voter: voters){
            for (var variant: variants){
                res.add(new ElectionCallback(new CallbackData(room.getHostId(), voter.getTelegramId(), variant)));
            }
        }

        return res;
    }
}
