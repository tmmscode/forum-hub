package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.user.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserManager userManager;


    // Free access routes
    @PostMapping("/register")
    @Transactional
    public ResponseEntity createUser (@RequestBody @Valid NewUserDTO data, UriComponentsBuilder uriComponentsBuilder) {
        UserDetailsDTO createdUser = userManager.createUser(data);

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(createdUser.id()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO loginData){
        return ResponseEntity.ok(userManager.login(loginData));
    }

    // Auth verified routes
    // ADM Routes
    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity getAllActiveUsers() {
        return ResponseEntity.ok(userManager.getAllActiveUsers());
    }

    @GetMapping("/all")
    @Secured("ADMIN")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userManager.getAllUsers());
    }

    @GetMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity getUserDetails(@PathVariable Long id){
        UserDetailsDTO userDetails = userManager.getUserDetails(id);
        return ResponseEntity.ok(userDetails);
    }

    // You can set new administrators in this route, be careful!
    @PutMapping("/profile")
    @Transactional
    @Secured("ADMIN")
    public ResponseEntity updateUserProfile (@RequestBody @Valid UpdateUserProfileDTO data, @AuthenticationPrincipal UserDetails user){
        UserDetailsDTO updatedUser = userManager.updateUserProfile(data, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Basic users routes
    @PutMapping
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity updateUser (@RequestBody @Valid UpdateUserDTO data, @AuthenticationPrincipal UserDetails user){
        UserDetailsDTO updatedUser = userManager.updateUser(data, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/credentials")
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity updateUserCredentials (@RequestBody @Valid UpdateUserCredentialsDTO data, @AuthenticationPrincipal UserDetails user){
        UserDetailsDTO updatedUser = userManager.updateUserCredentials(data, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/deactivate")
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity deleteUser(@RequestBody @Valid DeactivateUserConfirmationDTO confirmationPassword, @AuthenticationPrincipal UserDetails user) {
        userManager.deleteUser(confirmationPassword, user);
        return ResponseEntity.noContent().build();
    }

}
