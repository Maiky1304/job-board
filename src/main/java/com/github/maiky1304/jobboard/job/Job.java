package com.github.maiky1304.jobboard.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.maiky1304.jobboard.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jobs")
@NoArgsConstructor
@Getter
@Setter
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobType type = JobType.FULL_TIME;

    @Embedded
    @Column(nullable = false)
    private JobSalary salary;

    @JsonIgnoreProperties({
            "password",
            "locked",
            "enabled",
            "accountNonExpired",
            "accountNonLocked",
            "credentialsNonExpired",
            "username",
            "authorities",
            "role"
    })
    @ManyToOne
    @JoinColumn(
            name = "author_id"
    )
    private User author;

}
