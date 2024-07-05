package tmmscode.forumHub.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.user.profile.NewProfileDTO;
import tmmscode.forumHub.domain.user.profile.ProfileDetails;
import tmmscode.forumHub.domain.user.profile.ProfileManager;

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
    public ResponseEntity createProfile(@RequestBody @Valid NewProfileDTO data, UriComponentsBuilder uriComponentsBuilder){
        ProfileDetails createdProfile = profileManager.createProfile(data);

        var uri = uriComponentsBuilder.path("/profiles/{id}").buildAndExpand(createdProfile.id()).toUri();
        return ResponseEntity.created(uri).body(createdProfile);
    }
}
