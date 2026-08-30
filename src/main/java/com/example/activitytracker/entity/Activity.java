package com.example.activitytracker.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    @Enumerated(EnumType.STRING)
    @Column( nullable=false)
    private ActivityType activityType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;
    private LocalDate startDate;
    private LocalDate endDate;

}
