package com.github.maiky1304.jobboard.job;

import com.github.maiky1304.jobboard.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(
            name = "author_id",
            nullable = false
    )
    private User author;

}
