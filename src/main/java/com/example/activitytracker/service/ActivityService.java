package com.example.activitytracker.service;
import com.example.activitytracker.dto.ActivityRequest;
import com.example.activitytracker.entity.Activity;
import com.example.activitytracker.exception.ResourceNotFoundException;
import com.example.activitytracker.repository.ActivityRepository;
import com.example.activitytracker.repository.AppUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.activitytracker.entity.AppUser;
import java.util.List;
import java.time.LocalDate;
import com.example.activitytracker.entity.ActivityStatus;
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final AppUserRepository appUserRepository;

    public ActivityService(ActivityRepository activityRepository, AppUserRepository appUserRepository) {
        this.activityRepository = activityRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity getActivityById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı."));
        return activityRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Faaliyet bulunamadı."));
    }

    public Activity createActivity(ActivityRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı."));
        Activity activity = new Activity();
        activity.setTitle(request.getTitle());
        activity.setDescription(request.getDescription());
        activity.setCompleted(request.isCompleted());
        activity.setActivityType(request.getActivityType());
        activity.setStartDate(request.getStartDate());
        activity.setEndDate(request.getEndDate());
        if (request.getStartDate() != null &&
                request.getEndDate() != null &&
                request.getEndDate().isBefore(request.getStartDate())) {

            throw new IllegalArgumentException(
                    "Bitiş tarihi başlangıç tarihinden önce olamaz."
            );
        }
        LocalDate today = LocalDate.now();

        if (request.getStartDate() != null &&
                request.getStartDate().isAfter(today)) {

            activity.setStatus(ActivityStatus.PLANNED);

        } else if (request.getEndDate() != null &&
                request.getEndDate().isBefore(today)) {

            activity.setStatus(ActivityStatus.COMPLETED);
            activity.setCompleted(true);

        } else {

            activity.setStatus(ActivityStatus.ACTIVE);
            activity.setCompleted(false);
        }
        activity.setUser(user);
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Long id, ActivityRequest request) {
        Activity activity = getActivityById(id);
        activity.setTitle(request.getTitle());
        activity.setDescription(request.getDescription());
        activity.setCompleted(request.isCompleted());
        activity.setActivityType(request.getActivityType());
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        Activity activity = getActivityById(id);
        activityRepository.delete(activity);
    }

    public List<Activity> getAllForCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        AppUser user = appUserRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı."));
        List<Activity> activities = activityRepository.findByUser(user);
        LocalDate today = LocalDate.now();

        for (Activity activity : activities) {

            LocalDate startDate = activity.getStartDate();
            LocalDate endDate = activity.getEndDate();

            if (startDate != null && startDate.isAfter(today)) {
                activity.setStatus(ActivityStatus.PLANNED);
                activity.setCompleted(false);

            } else if (endDate != null && endDate.isBefore(today)) {
                activity.setStatus(ActivityStatus.COMPLETED);
                activity.setCompleted(true);

            } else {
                activity.setStatus(ActivityStatus.ACTIVE);
                activity.setCompleted(false);
            }
        }
        return activityRepository.saveAll(activities);
    }
}