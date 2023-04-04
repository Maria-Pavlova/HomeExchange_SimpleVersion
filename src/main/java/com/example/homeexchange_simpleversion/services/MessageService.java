package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.MessageModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.MessageView;
import com.example.homeexchange_simpleversion.models.entities.Message;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.repositories.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, ModelMapper modelMapper,
                          UserService userService) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Transactional
    public void sendMessage(MessageModel messageModel, UserDetails userDetails, String username) {

        Message message = modelMapper.map(messageModel, Message.class);
        User fromUser = userService.findByUsername(userDetails.getUsername()).get();
        User toUser = userService.findByUsername(username).get();
        message.setFromUser(fromUser);
        message.setToUser(toUser);
        messageRepository.save(message);

        fromUser.getSentMessages().add(message);
        toUser.getReceivedMessages().add(message);
        userService.saveUser(fromUser);
        userService.saveUser(toUser);
    }


    public List<MessageView> getMessages(String name) {
        return messageRepository.findAllByToUser_UsernameOrderByMessageCreatedDesc(name)
                .stream()
                .map(message -> {
                            MessageView messageView = modelMapper.map(message, MessageView.class);
                            messageView.setFromUserUsername(message.getFromUser().getUsername());
                            messageView.setFromUserEmail(message.getFromUser().getEmail());
                            return messageView;
                        }
                )
                .toList();
    }
}
