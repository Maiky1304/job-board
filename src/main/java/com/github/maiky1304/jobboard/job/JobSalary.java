package com.github.maiky1304.jobboard.job;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class JobSalary {

    @Column(name = "min_salary", nullable = false)
    private int minimum;

    @Column(name = "max_salary", nullable = false)
    private int maximum;

}
