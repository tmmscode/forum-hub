package tmmscode.forumHub.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.user.profile.Profile;
import tmmscode.forumHub.domain.user.profile.ProfileRepository;
import tmmscode.forumHub.domain.user.profile.UserProfileAction;
import tmmscode.forumHub.domain.user.validations.creation.ValidateUserCreation;
import tmmscode.forumHub.domain.user.validations.update.ValidateUserUpdate;
import tmmscode.forumHub.infra.security.JWTTokenDTO;
import tmmscode.forumHub.infra.security.TokenService;

import java.util.List;

@Service
public class UserManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private List<ValidateUserCreation> validateUserCreation;

    @Autowired
    private List<ValidateUserUpdate> validateUserUpdate;

    public UserDetailsDTO createUser(NewUserDTO data) {
        validateUserCreation.forEach(v -> v.validate(data));

        User creatingUser = new User(data);
        Profile userProfile = profileRepository.findProfileByName("USER").get();
        creatingUser.addProfile(userProfile);
        creatingUser.setPassword(passwordEncoder.encode(data.password()));
        userRepository.save(creatingUser);
        return new UserDetailsDTO(creatingUser);
    }

    public JWTTokenDTO login(UserLoginDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var jwtToken = tokenService.generateToken((User) authentication.getPrincipal());

        return new JWTTokenDTO(jwtToken);
    }

    public List<UserSimplifiedDTO> getAllActiveUsers() {
        List<User> userList = userRepository.findExistingUsers();
        return userList.stream()
                .map(UserSimplifiedDTO::new)
                .toList();
    }

    public List<UserSimplifiedDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserSimplifiedDTO::new)
                .toList();
    }

    public UserDetailsDTO getUserDetails(Long id) {
        var selectedUser = userRepository.getReferenceById(id);
        return new UserDetailsDTO(selectedUser);
    }

    public UserDetailsDTO updateUserProfile(UpdateUserProfileDTO data, UserDetails user) {
        validateUserUpdate.forEach(v -> v.validate(data.userId()));

        if (!profileRepository.existsById(data.profileId())){
            throw new BusinessRulesException("O perfil de usuário não existe");
        }

        Profile profile = profileRepository.getReferenceById(data.profileId());
        User selectedUser = userRepository.getReferenceById(data.userId());

        if(data.action() == UserProfileAction.ADD){
            selectedUser.addProfile(profile);
        }

        if (data.action() == UserProfileAction.REMOVE) {
            selectedUser.removeProfile(profile);
        }

        return new UserDetailsDTO(selectedUser);
    }

    public UserDetailsDTO updateUser(UpdateUserDTO data, UserDetails user) {
        User requester = (User) user;

        User selectedUser = userRepository.getReferenceById(requester.getId());
        selectedUser.update(data);

        return new UserDetailsDTO(selectedUser);
    }

    public UserDetailsDTO updateUserCredentials(UpdateUserCredentialsDTO data, UserDetails user) {
        User requester = (User) user;
        User selectedUser = userRepository.findById(requester.getId()).get();

        String updatedEmail = null;
        String updatedPassword = null;

        if(data.email() != null){
            updatedEmail = data.email();
        }

        if(data.password() != null) {
            updatedPassword = passwordEncoder.encode(data.password());
        }

        UpdateUserCredentialsDTO hashed = new UpdateUserCredentialsDTO(updatedEmail, updatedPassword);

        selectedUser.updateCredentials(hashed);
        return new UserDetailsDTO(selectedUser);
    }

    public void deleteUser(DeactivateUserConfirmationDTO confirmation, UserDetails user){
        User requester = (User) user;
        User selectedUser = userRepository.findById(requester.getId()).get();

        if(passwordEncoder.matches(confirmation.password(), selectedUser.getPassword())){
            selectedUser.delete();
        } else {
            throw new BusinessRulesException("Wrong password");
        }
    }
}
