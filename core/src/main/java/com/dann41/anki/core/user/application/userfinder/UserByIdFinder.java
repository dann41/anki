package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;

public class UserByIdFinder {

    private final UserRepository userRepository;

    public UserByIdFinder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FindUserResponse execute(FindUserByIdQuery query) {
        UserId userId = new UserId(query.userId());
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        return new FindUserResponse(user.userId(), user.userName(), user.passwordHash());
    }
}
