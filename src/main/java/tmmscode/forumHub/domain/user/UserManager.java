package tmmscode.forumHub.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.user.profile.Profile;
import tmmscode.forumHub.domain.user.profile.ProfileRepository;
import tmmscode.forumHub.domain.user.profile.UserProfileAction;
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
        if(!userRepository.existsById(id)){
            throw new BusinessRulesException("O usuário não existe!");
        }
        var selectedUser = userRepository.getReferenceById(id);
        return new UserDetailsDTO(selectedUser);
    }

    public JWTTokenDTO login(UserLoginDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var jwtToken = tokenService.generateToken((User) authentication.getPrincipal());

        return new JWTTokenDTO(jwtToken);
    }

    public UserDetailsDTO createUser(NewUserDTO data) {
        if(userRepository.findUserByEmail(data.email()).isPresent()){
            throw new BusinessRulesException("O email informado já está em uso");
        }

        User creatingUser = new User(data);
        Profile userProfile = profileRepository.findProfileByName("USER").get();
        creatingUser.addProfile(userProfile);
        creatingUser.setPassword(passwordEncoder.encode(data.password()));
        userRepository.save(creatingUser);
        return new UserDetailsDTO(creatingUser);
    }

    public UserDetailsDTO updateUser(UpdateUserDTO data, Long id) {
        // verificar se o usuarío que fez a requisição é o mesmo do id a ser modificado
        // se não for o mesmo usuário, verificar se o usuário possui permissão para realizar a mudança
        // Verify admin
        verifyActiveUser(id);
        // esse verify fica incluso no admin ?

        User selectedUser = userRepository.getReferenceById(id);
        selectedUser.update(data);
        return new UserDetailsDTO(selectedUser);
    }

    public UserDetailsDTO updateUserProfile(UpdateUserProfileDTO data) {
        verifyActiveUser(data.userId());

        // verificar se o usuário possui permissão para fazer essa mudança
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

    public UserDetailsDTO updateUserCredentials(UpdateUserCredentialsDTO data, Long id) {
        // verificar se o usuarío que fez a requisição é o mesmo do id a ser modificado
        verifyActiveUser(id);


        User selectedUser = userRepository.findById(id).get();
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

    public void deleteUser(Long id){
        verifyActiveUser(id);
        User selectedUser = userRepository.getReferenceById(id);
        selectedUser.delete();
    }


    public void verifyActiveUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new BusinessRulesException("O usuário não existe");
        } else if(userRepository.isDeleted(id).isPresent()) {
            throw new BusinessRulesException("O usuário foi desativado");
        }
    }
}
