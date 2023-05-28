package ru.yandex.practicum.javafilmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidPathVariableOrParameterException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;
import ru.yandex.practicum.javafilmorate.util.StringValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public User addNewUserToStorage(User user) {
        setUserNameIfNeeded(user);
        return userStorage.addUser(user);
    }

    public List<User> getAllUsersFromStorage() {
        return userStorage.getAllUsers();
    }

    public User updateUserInStorage(User user) {
        return userStorage.updateUser(user);
    }

    public boolean doesUserExistInStorage(long id) {
        return userStorage.doesUserExist(id);
    }

    public User getUserFromStorage(long id) {
        checkUserExistence(id);
        return userStorage.getUser(id);
    }

    public void addUsersToFriendsInStorage(long id, long friendId) {
        checkUserExistence(id);
        checkUserExistence(friendId);
        User user = userStorage.getUser(id);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }

        if (!user.getFriends().add(friendId)) {
            throw new EntityAlreadyExistsException("Users are already friends with each other");
        }

        User friend = userStorage.getUser(friendId);

        if (friend.getFriends() == null) {
            friend.setFriends(new HashSet<>());
        }

        friend.getFriends().add(id);
    }

    public void deleteUsersFromFriendsInStorage(long id, long friendId) {
        checkUserExistence(id);
        checkUserExistence(friendId);
        User user = userStorage.getUser(id);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }

        if (!user.getFriends().remove(friendId)) {
            throw new EntityDoesNotExistException("Users are already friends with each other. Deletion is not possible");
        }

        User friend = userStorage.getUser(friendId);

        if (friend.getFriends() == null) {
            friend.setFriends(new HashSet<>());
        }

        friend.getFriends().remove(id);
    }

    public List<User> getUserFriendsFromStorage(long id) {
        checkUserExistence(id);
        Set<Long> userFriends = userStorage.getUser(id).getFriends();

        if (userFriends == null) {
            return new ArrayList<>();
        }

        return userFriends.stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUsersCommonFriendsFromStorage(long id, long otherId) {
        checkUserExistence(id);
        checkUserExistence(otherId);
        Set<Long> userFriends = userStorage.getUser(id).getFriends();

        if (userFriends == null) {
            return new ArrayList<>();
        }

        Set<Long> friendsFriends = userStorage.getUser(otherId).getFriends();

        return userFriends
                .stream()
                .filter(friendsFriends::contains)
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    private void checkUserExistence(long id) {

        if (id < 1) {
            String exceptionMessage = String.format("user with id = %d cannot exist", id);
            log.warn("reqest user error. Exception message: {}",
                    exceptionMessage);
            throw new InvalidPathVariableOrParameterException("id", exceptionMessage);
        }

        if (!doesUserExistInStorage(id)) {
            String exceptionMessage = String.format("user with  id = %d cannot exist", id);
            log.warn("reqest user error. Exception message: {}",
                    exceptionMessage);
            throw new EntityDoesNotExistException(exceptionMessage);
        }
    }

    private void setUserNameIfNeeded(User user) {

        if (StringValidator.isNullOrEmpty(user.getName())) {
            user.setName(user.getLogin());
        }
    }
}
