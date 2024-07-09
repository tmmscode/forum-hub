package tmmscode.forumHub.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tmmscode.forumHub.domain.user.profile.Profile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
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
    private Set<Profile> profile = new HashSet<>();

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profile.stream()
                .map(profile -> new SimpleGrantedAuthority(profile.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
