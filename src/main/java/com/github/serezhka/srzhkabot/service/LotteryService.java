package com.github.serezhka.srzhkabot.service;

import com.github.serezhka.srzhkabot.repository.UserRepository;
import com.github.serezhka.srzhkabot.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 19.05.2018
 */
@Service
public class LotteryService {

    private final UserRepository userRepository;

    @Autowired
    public LotteryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateParticipant(int participantId, long chatId, String name, boolean participates) {
        UserEntity user = userRepository.findById(participantId).orElse(new UserEntity());
        user.setId(participantId);
        user.setChatId(chatId);
        user.setName(name);
        user.setParticipates(participates);
        userRepository.saveAndFlush(user);
    }

    public List<String> getWinners(int limit) {
        List<UserEntity> participants = userRepository.findByParticipatesIsTrue();
        Collections.shuffle(participants);
        return participants.stream().limit(limit).map(UserEntity::getName).collect(Collectors.toList());
    }

    public List<Long> getParticipants() {
        return userRepository.findByParticipatesIsTrue().stream().map(UserEntity::getChatId).collect(Collectors.toList());
    }
}
