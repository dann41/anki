package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;

public class UserByUsernameFinder {

    private final UserRepository userRepository;

    public UserByUsernameFinder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FindUserResponse execute(FindUserByUsernameQuery query) {
        Username usernameVO = new Username(query.username());
        User user = userRepository.findByUsername(usernameVO);
        if (user == null) {
            throw new UserNotFoundException(usernameVO);
        }

        return new FindUserResponse(user.userId(), user.userName(), user.passwordHash());
    }
}
