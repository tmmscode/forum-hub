package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.user.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserManager userManager;

    @GetMapping("/all")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userManager.getAllUsers());
    }

    @GetMapping
    public ResponseEntity getAllActiveUsers() {
        return ResponseEntity.ok(userManager.getAllActiveUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserDetails(@PathVariable Long id){
        UserDetailsDTO userDetails = userManager.getUserDetails(id);
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping
    @Transactional
    public ResponseEntity createUser (@RequestBody @Valid NewUserDTO data, UriComponentsBuilder uriComponentsBuilder) {
        UserDetailsDTO createdUser = userManager.createUser(data);

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(createdUser.id()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateUser (@RequestBody @Valid UpdateUserDTO data, @PathVariable Long id){
        UserDetailsDTO updatedUser = userManager.updateUser(data, id);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/profile/{id}")
    @Transactional
    public ResponseEntity updateUserProfile (@RequestBody @Valid UpdateUserProfileDTO data, @PathVariable Long id){
        UserDetailsDTO updatedUser = userManager.updateUserProfile(data, id);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/credentials/{id}")
    @Transactional
    public ResponseEntity updateUserCredentials (@RequestBody @Valid UpdateUserCredentialsDTO data, @PathVariable Long id){
        UserDetailsDTO updatedUser = userManager.updateUserCredentials(data, id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userManager.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
