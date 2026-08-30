package com.example.activitytracker.repository;

import com.example.activitytracker.entity.Activity;
import com.example.activitytracker.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUser(AppUser user);
    Optional<Activity> findByIdAndUser(Long id,AppUser user);
}
