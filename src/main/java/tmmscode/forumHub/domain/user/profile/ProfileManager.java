package tmmscode.forumHub.domain.user.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileManager {
    @Autowired
    private ProfileRepository profileRepository;

    public List<ProfileDetails> getAllProfiles(){
        List<Profile> profilesList = profileRepository.findAll();
        return profilesList.stream()
                .map(ProfileDetails::new)
                .toList();
    }

    public ProfileDetails createProfile(NewProfileDTO data){
        if(profileRepository.findProfileByName(data.name()).isPresent()){
            throw new RuntimeException("Já existe um perfil com esse nome");
        }

        Profile creatingProfile = new Profile(data);
        profileRepository.save(creatingProfile);
        return new ProfileDetails(creatingProfile);
    }

    public ProfileDetails updateProfile(UpdateProfileDTO data, Long id) {
        if(!profileRepository.existsById(id)){
            throw new RuntimeException("Esse perfil de usuário não existe");
        }

        Profile updatingProfile = profileRepository.getReferenceById(id);
        updatingProfile.update(data);
        return new ProfileDetails(updatingProfile);
    }

    public void deleteProfile(Long id) {
        if(!profileRepository.existsById(id)){
            throw new RuntimeException("Esse perfil de usuário não existe");
        }

        Profile deletingProfile = profileRepository.getReferenceById(id);
        profileRepository.delete(deletingProfile);
    }
}
