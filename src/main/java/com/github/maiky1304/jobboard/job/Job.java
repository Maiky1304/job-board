package com.github.maiky1304.jobboard.job;

import com.github.maiky1304.jobboard.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jobs")
@NoArgsConstructor
@Getter @Setter
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Job(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
