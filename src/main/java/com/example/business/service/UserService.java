package com.example.business.service;

import com.example.business.model.User;
import com.example.business.repository.UserRepository;
import com.example.exception.exception.ListUsersIsEmptyException;
import com.example.exception.exception.UserNotFoundException;
import com.example.web.dto.UserDto;
import com.example.web.dto.mapping.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public String createUser(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(entity);
        logger.debug("Created user: {}", entity);
        return savedUser.getId().toString();
    }

    @Transactional
    public List<UserDto> allUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new ListUsersIsEmptyException();
        }
        List<UserDto> listOfUsers = allUsers.stream().map(userMapper::toDto).collect(Collectors.toList());
        logger.debug("Get all users from repository: {}", allUsers.stream().collect(Collectors.toList()));
        return listOfUsers;
    }

    @Transactional
    public UserDto getUserById(String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new UserNotFoundException(UUID.fromString(id))
        );
        logger.debug("Get user from repository: {}", user);
        return userMapper.toDto(user);
    }

    @Transactional
    public String updateUser(String id, UserDto userDto) {
        UUID uuid = UUID.fromString(id);

        User newUser = userRepository.findById(uuid).orElseThrow(
                () -> new UserNotFoundException(uuid)
        );
        logger.debug("Get users for updated: {}", newUser);

        User updatedUser = userMapper.toEntity(userDto);

        newUser.setName(updatedUser.getName());
        newUser.setEmail(updatedUser.getEmail());
        newUser.setAge(updatedUser.getAge());
        logger.debug("Return user after updated: {}", newUser);

        return userRepository.save(newUser).getId().toString();
    }

    @Transactional
    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);

        User deletedUser = userRepository.findById(uuid).orElseThrow(
                () -> new UserNotFoundException(uuid)
        );
        logger.debug("Get users for deleted: {}", deletedUser);

        userRepository.delete(deletedUser);
    }

}
