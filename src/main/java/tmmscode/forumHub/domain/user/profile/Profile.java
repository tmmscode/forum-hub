package tmmscode.forumHub.domain.user.profile;

import jakarta.persistence.*;
import lombok.*;
import tmmscode.forumHub.domain.user.User;

import java.util.Set;

@Entity(name = "Profile")
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private Set<User> users;

    public Profile(NewProfileDTO data) {
        this.name = data.name();
    }
}
