package app.web.zyncky.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uid;

    @Column(name = "email_address", nullable = false, unique = true)
    String emailAddress;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "display_name")
    String displayName;

    @Column(name = "created_at")
    Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    Role role;
}
