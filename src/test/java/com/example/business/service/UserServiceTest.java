//package com.example.business.service;
//
//import com.example.business.model.User;
//import com.example.repository.UserRepository;
//import com.example.exception.exception.ListUsersIsEmptyException;
//import com.example.exception.exception.UserNotFoundException;
//import com.example.web.dto.UserDto;
//import com.example.web.dto.mapping.UserMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class UserServiceTest {
//
//    private UserRepository userRepository;
//    private UserMapper userMapper;
//    private UserService userService;
//    private UserDto dto;
//    private User entity;
//    private List<User> users;
//    private List<UserDto> usersDto;
//    private List<User> emptyListUsers;
//    private List<User> emptyListUsersDto;
//    private UUID validId;
//    private UUID fakeId;
//
//    @Disabled
//    @BeforeEach
//    void setUp() {
//         userRepository = mock(UserRepository.class);
//         userMapper = mock(UserMapper.class);
//         userService = new UserService(userRepository, userMapper);
//         validId = UUID.fromString("cd2ee758-effd-45e5-b479-6bbafd8db05f");
//         fakeId = UUID.fromString("111ee758-effd-45e5-b479-6bbafd8db05f");
//         LocalDateTime timeCreatedDilara = LocalDateTime.now();
//         LocalDateTime timeCreatedIvan = LocalDateTime.now();
//         LocalDateTime timeCreatedEldar = LocalDateTime.now();
//
//         dto = new UserDto("Dilara", 21, "dilara@mail.ru");
//         entity = new User(validId, "Dilara", "dilara@mail.ru", 21, timeCreatedDilara);
//
//         users = List.of(new User(UUID.fromString("cd2ee758-effd-45e5-b479-6bbafd8db051"), "Ivan", "ivan@mail.ru", 22, timeCreatedIvan),
//                new User(UUID.fromString("cd2ee758-effd-45e5-b479-6bbafd8db052"), "Eldar", "eldar@mail.ru", 23, timeCreatedEldar),
//                entity);
//
//         usersDto = List.of(dto,
//                 new UserDto("Ivan", 22,"ivan@mail.ru"),
//                 new UserDto("Eldar", 23,"eldar@mail.ru"));
//
//         emptyListUsers = new ArrayList<>();
//         emptyListUsersDto = new ArrayList<>();
//    }
//
//    @Test
//    void whenCreateUserWithValidData() {
//        //given
//        when(userMapper.toEntity(dto)).thenReturn(entity);
//        when(userRepository.save(entity)).thenReturn(entity);
//
//        //when
//        String id = userService.createUser(dto);
//
//        //then
//        verify(userMapper).toEntity(dto);
//        verify(userRepository).save(entity);
//        assertEquals(entity.getId(), UUID.fromString(id));
//    }
//
//    @Test
//    void whenGetAllUsers() {
//        //given
//        when(userRepository.findAll()).thenReturn(users);
//        when(userMapper.toDto(users.get(0))).thenReturn(usersDto.get(0));
//        when(userMapper.toDto(users.get(1))).thenReturn(usersDto.get(1));
//        when(userMapper.toDto(users.get(2))).thenReturn(usersDto.get(2));
//
//        //when
//        List<UserDto> result = userService.allUsers();
//
//        //then
//        verify(userRepository).findAll();
//        verify(userMapper, times(users.size())).toDto(any(User.class));
//        assertEquals(usersDto, result);
//    }
//
//    @Test
//    void whenGetAllUsersWithEmptyListThenGetException() {
//        //given
//        when(userRepository.findAll()).thenReturn(Collections.emptyList());
//
//        //when
//        assertThrows(ListUsersIsEmptyException.class, () -> userService.allUsers());
//
//        //then
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    void whenGetUserById() {
//        //given
//        when(userRepository.findById(validId)).thenReturn(Optional.ofNullable(entity));
//        when(userMapper.toDto(entity)).thenReturn(dto);
//
//        //when
//        UserDto result = userService.getUserById(validId.toString());
//
//        //then
//        verify(userRepository).findById(validId);
//        verify(userMapper).toDto(entity);
//        assertEquals(dto, result);
//    }
//
//    @Test
//    void whenGetUserWhichDoesNotExistThenGetException() {
//        //given
//        when(userRepository.findById(fakeId)).thenReturn(Optional.empty());
//
//        //when
//        assertThrows(UserNotFoundException.class, () -> userService.getUserById(fakeId.toString()));
//
//        //then
//        verify(userRepository).findById(fakeId);
//    }
//
//    @Test
//    void whenUpdateUserById() {
//        //given
//        when(userMapper.toEntity(dto)).thenReturn(entity);
//        when(userRepository.findById(validId)).thenReturn(Optional.ofNullable(entity));
//        when(userRepository.save(entity)).thenReturn(entity);
//
//        //when
//        String result = userService.updateUser(validId.toString(), dto);
//
//        //then
//        verify(userRepository).findById(validId);
//        verify(userRepository).save(entity);
//        assertEquals(entity.getId().toString(), result);
//    }
//
//    @Test
//    void whenUpdateUserWhichDoesNotExistThenGetException() {
//        //given
//        when(userRepository.findById(fakeId)).thenReturn(Optional.empty());
//
//        //when
//        assertThrows(UserNotFoundException.class, () -> userService.updateUser(fakeId.toString(), dto));
//
//        //then
//        verify(userRepository).findById(fakeId);
//    }
//
//    @Test
//    void whenDeletedUserById() {
//        //given
//        when(userRepository.findById(validId)).thenReturn(Optional.ofNullable(entity));
//
//        //when
//        userService.deleteUser(validId.toString());
//
//        //then
//        verify(userRepository).findById(validId);
//        verify(userRepository).delete(entity);
//    }
//
//    @Test
//    void whenDeletedUserDoesNotExistsGetException() {
//        //given
//        when(userRepository.findById(fakeId)).thenReturn(Optional.empty());
//
//        //when
//        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(fakeId.toString()));
//
//        //then
//        verify(userRepository).findById(fakeId);
//    }
//
//}