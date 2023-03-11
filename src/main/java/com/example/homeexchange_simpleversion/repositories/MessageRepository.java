package com.example.homeexchange_simpleversion.repositories;

import com.example.homeexchange_simpleversion.models.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByToUser_UsernameOrderByMessageCreatedDesc(String username);
}
