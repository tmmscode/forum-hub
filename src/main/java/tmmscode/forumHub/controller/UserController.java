package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.user.NewUserDTO;
import tmmscode.forumHub.domain.user.User;
import tmmscode.forumHub.domain.user.UserDetailsDTO;
import tmmscode.forumHub.domain.user.UserRepository;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity showAllUsers() {
             return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity createCourse (@RequestBody @Valid NewUserDTO data, UriComponentsBuilder uriComponentsBuilder) {
        User newUser = new User(data);
        userRepository.save(newUser);

        return ResponseEntity.ok(new UserDetailsDTO(newUser));
    }
}
