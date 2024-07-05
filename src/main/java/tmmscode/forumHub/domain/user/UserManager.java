package tmmscode.forumHub.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.user.profile.Profile;
import tmmscode.forumHub.domain.user.profile.ProfileRepository;
import tmmscode.forumHub.domain.user.profile.UserProfileAction;

import java.util.List;

@Service
public class UserManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;


    public List<UserSimplifiedDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserSimplifiedDTO::new)
                .toList();
    }

    public List<UserSimplifiedDTO> getAllActiveUsers() {
        List<User> userList = userRepository.findExistingUsers();
        return userList.stream()
                .map(UserSimplifiedDTO::new)
                .toList();
    }

    public UserDetailsDTO getUserDetails(Long id) {
        if(userRepository.existsById(id)){
            var selectedUser = userRepository.getReferenceById(id);
            return new UserDetailsDTO(selectedUser);
        } else {
            throw new RuntimeException("O usuário não existe!");
        }
    }

    public UserDetailsDTO createUser(NewUserDTO data) {
        if(userRepository.findUserByEmail(data.email()).isPresent()){
            throw new RuntimeException("O email informado já está em uso");
        }

        User creatingUser = new User(data);
        userRepository.save(creatingUser);
        return new UserDetailsDTO(creatingUser);
    }

    public UserDetailsDTO updateUser(UpdateUserDTO data, Long id) {
        // verificar se o usuarío que fez a requisição é o mesmo do id a ser modificado
        // se não for o mesmo usuário, verificar se o usuário possui permissão para realizar a mudança
        verifyExistingUser(id);

        User selectedUser = userRepository.getReferenceById(id);
        selectedUser.update(data);
        return new UserDetailsDTO(selectedUser);
    }

    public UserDetailsDTO updateUserProfile(UpdateUserProfileDTO data, Long id) {
        // verificar se o usuário possui permissão para fazer essa mudança
        if (!profileRepository.existsById(data.profileId())){
            throw new RuntimeException("O perfil de usuário não existe");
        }
        Profile profile = profileRepository.getReferenceById(data.profileId());
        User selectedUser = userRepository.getReferenceById(id);

        if(data.action() == UserProfileAction.ADD){
            selectedUser.addProfile(profile);
        }

        if (data.action() == UserProfileAction.REMOVE) {
            selectedUser.removeProfile(profile);
        }

        return new UserDetailsDTO(selectedUser);
    }

    public UserDetailsDTO updateUserCredentials(UpdateUserCredentialsDTO data, Long id) {
        // verificar se o usuarío que fez a requisição é o mesmo do id a ser modificado
        verifyExistingUser(id);
        User selectedUser = userRepository.getReferenceById(id);
        String updatedEmail = null;
        String updatedPassword = null;

        if(data.email() != null){
            updatedEmail = data.email();
        }

        if(data.password() != null) {
//             updatedPassword = hashPassword(data.password());
            updatedPassword = data.password();
        }

        UpdateUserCredentialsDTO hashed = new UpdateUserCredentialsDTO(updatedEmail, updatedPassword);

        selectedUser.updateCredentials(hashed);
        return new UserDetailsDTO(selectedUser);
    }

    public void deleteUser(Long id){
        verifyExistingUser(id);
        User selectedUser = userRepository.getReferenceById(id);
        selectedUser.delete();
    }


    public void verifyExistingUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("O usuário não existe");
        } else if(userRepository.isDeleted(id).isPresent()) {
            throw new RuntimeException("O usuário foi desativado");
        }
    }
}
