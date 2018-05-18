package com.github.serezhka.srzhkabot.repository;

import com.github.serezhka.srzhkabot.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 19.05.2018
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findByParticipatesIsTrue();
}
