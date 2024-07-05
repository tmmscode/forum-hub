package tmmscode.forumHub.domain.user;

import jakarta.persistence.*;
import lombok.*;
import tmmscode.forumHub.domain.user.profile.Profile;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity(name = "User")
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> profile;

    public User(NewUserDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.active = true;
    }

    public void update(UpdateUserDTO data) {
        this.name = data.name();
    }

    public void addProfile(Profile profile) {
        this.profile.add(profile);
    }

    public void removeProfile(Profile profile) {
        this.profile.remove(profile);
    }

    public void updateCredentials(UpdateUserCredentialsDTO data) {
        if(data.email() != null){
            this.email = data.email();
        }
        if(data.password() != null){
            this.password = data.password();
        }
    }

    public void delete(){
        this.active = false;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
}
