package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.user.profile.NewProfileDTO;
import tmmscode.forumHub.domain.user.profile.ProfileDetails;
import tmmscode.forumHub.domain.user.profile.ProfileManager;
import tmmscode.forumHub.domain.user.profile.UpdateProfileDTO;

@RestController
@RequestMapping("profiles")
public class ProfileController {
    @Autowired
    private ProfileManager profileManager;

    @GetMapping
    public ResponseEntity getAllProfiles() {
        return ResponseEntity.ok(profileManager.getAllProfiles());
    }

    @PostMapping
    @Transactional
    public ResponseEntity createProfile(@RequestBody @Valid NewProfileDTO data, UriComponentsBuilder uriComponentsBuilder){
        ProfileDetails createdProfile = profileManager.createProfile(data);

        var uri = uriComponentsBuilder.path("/profiles/{id}").buildAndExpand(createdProfile.id()).toUri();
        return ResponseEntity.created(uri).body(createdProfile);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateProfile(@RequestBody @Valid UpdateProfileDTO data, @PathVariable Long id){
        ProfileDetails updatedProfile = profileManager.updateProfile(data, id);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProfile(@PathVariable Long id){
        profileManager.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
