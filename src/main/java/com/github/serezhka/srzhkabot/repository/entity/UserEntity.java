package com.github.serezhka.srzhkabot.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 19.05.2018
 */
@Entity
@Table(name = "user", schema = "srzhkabot")
public class UserEntity implements Serializable {

    private int id;
    private long chatId;
    private boolean participates;
    private String name;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public boolean isParticipates() {
        return participates;
    }

    public void setParticipates(boolean participates) {
        this.participates = participates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
