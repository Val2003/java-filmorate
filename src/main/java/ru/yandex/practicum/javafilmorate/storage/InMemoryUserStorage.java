package ru.yandex.practicum.javafilmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.ObjAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.ObjDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("userStorage")
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users;
    private long idToNewUser;

    private InMemoryUserStorage() {
        users = new HashMap<>();
        idToNewUser = 1;
    }

    @Getter(lazy = true)
    private final static InMemoryUserStorage instance = new InMemoryUserStorage();


    private long generateUserId() {
        long idToSet = idToNewUser;
        idToNewUser++;
        return idToSet;
    }

    @Override
    public User addUser(User user) {

        if (users.containsValue(user)) {
            String exceptionMessage = "User already added";
            log.warn("Error when adding a new user. Exception message: {}", exceptionMessage);
            throw new ObjAlreadyExistsException("Пользователь уже добавлен");
        }
        user.setId(generateUserId());
        users.put(user.getId(), user);
        return user;

    }

    @Override
    public User getUser(Long id) {

        if (!users.containsKey(id)) {
            String exceptionMessage = String.format("User with requested id = %d does not exist", id);
            log.warn("User request failed. Exception message: {}",
                    exceptionMessage);
            throw new ObjDoesNotExistException(exceptionMessage);
        }

        return users.get(id);
    }

    @Override
    public User removeUser(Long id) {

        if (!users.containsKey(id)) {
            String exceptionMessage = String.format("User with requested id = %d does not exist", id);
            log.warn("User request failed. Exception message: {}",
                    exceptionMessage);
            throw new ObjDoesNotExistException(exceptionMessage);
        }

        return users.remove(id);
    }

    @Override
    public User updateUser(User user) {

        if (!users.containsKey(user.getId())) {
            String exceptionMessage = "User being updated does not exist";
            log.warn("Error updating user. Exception message: {}", exceptionMessage);
            throw new ObjDoesNotExistException(exceptionMessage);
        }
        users.remove(user.getId(), user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
