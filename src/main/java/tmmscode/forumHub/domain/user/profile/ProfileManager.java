package tmmscode.forumHub.domain.user.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.user.profile.validations.creation.ValidateProfileCreation;
import tmmscode.forumHub.domain.user.profile.validations.update.ValidateProfileUpdate;

import java.util.List;

@Service
public class ProfileManager {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private List<ValidateProfileCreation> validateProfileCreation;

    @Autowired
    private List<ValidateProfileUpdate> validateProfileUpdate;

    public List<ProfileDetails> getAllProfiles(){
        List<Profile> profilesList = profileRepository.findAll();
        return profilesList.stream()
                .map(ProfileDetails::new)
                .toList();
    }

    public ProfileDetails getProfile(Long id) {
        return new ProfileDetails(profileRepository.findById(id).get());
    }

    public ProfileDetails createProfile(NewProfileDTO data){
        validateProfileCreation.forEach(v -> v.validate(data));

        Profile creatingProfile = new Profile(data);
        profileRepository.save(creatingProfile);
        return new ProfileDetails(creatingProfile);
    }

    public ProfileDetails updateProfile(UpdateProfileDTO data, Long id) {
        validateProfileUpdate.forEach(v -> v.validate(id));

        Profile updatingProfile = profileRepository.getReferenceById(id);
        updatingProfile.update(data);
        return new ProfileDetails(updatingProfile);
    }

    public void deleteProfile(Long id) {
        validateProfileUpdate.forEach(v -> v.validate(id));

        Profile deletingProfile = profileRepository.getReferenceById(id);
        profileRepository.delete(deletingProfile);
    }

}
