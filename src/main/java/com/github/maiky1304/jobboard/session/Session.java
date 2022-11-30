package com.github.maiky1304.jobboard.session;

import com.github.maiky1304.jobboard.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String token;

    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Session(String token, User user) {
        this.token = token;
        this.user = user;
    }

}
