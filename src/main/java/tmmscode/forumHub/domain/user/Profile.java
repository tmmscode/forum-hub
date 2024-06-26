package tmmscode.forumHub.domain.user;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToMany(mappedBy = "profile")
    private Set<User> users;
}
