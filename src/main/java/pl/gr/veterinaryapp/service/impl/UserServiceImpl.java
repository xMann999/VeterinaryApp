package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gr.veterinaryapp.exception.IncorrectDataException;
import pl.gr.veterinaryapp.exception.ResourceNotFoundException;
import pl.gr.veterinaryapp.model.dto.MessageDto;
import pl.gr.veterinaryapp.model.dto.UserDto;
import pl.gr.veterinaryapp.model.entity.Role;
import pl.gr.veterinaryapp.model.entity.VetAppUser;
import pl.gr.veterinaryapp.repository.UserRepository;
import pl.gr.veterinaryapp.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public List<VetAppUser> getAllUsers() {
        log.trace("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public VetAppUser getUser(long id) {
        log.debug("Searching for user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id."));
    }

    @Override
    @Transactional
    public VetAppUser createUser(UserDto user) {
        log.debug("Checking if the given data is correct");
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new IncorrectDataException("Username exists.");
                });
        VetAppUser newVetAppUser = new VetAppUser();
        newVetAppUser.setUsername(user.getUsername());
        log.debug("Encoding password");
        newVetAppUser.setPassword(encoder.encode(user.getPassword()));
        newVetAppUser.setRole(new Role(user.getRole()));
        log.debug("Saving a new user: {}", user);
        return userRepository.save(newVetAppUser);
    }

    @Override
    @Transactional
    public MessageDto deleteUser(long id) {
        log.debug("Searching for user with id: {}", id);
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Wrong id."));
        log.debug("Attempting to remove user with id: {}", id);
        userRepository.delete(user);
        return new MessageDto(HttpStatus.OK, "User has been successfully removed");
    }
}
