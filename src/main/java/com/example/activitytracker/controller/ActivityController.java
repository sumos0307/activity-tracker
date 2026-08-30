package com.example.activitytracker.controller;
import com.example.activitytracker.dto.ActivityRequest;
import  jakarta.validation.Valid;
import com.example.activitytracker.entity.Activity;
import com.example.activitytracker.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityController {
    private final ActivityService activityService;
    public ActivityController (ActivityService activityService){
        this.activityService=activityService;
    }
    @GetMapping
    public ResponseEntity<List<Activity>>getAllActivities(){
       return ResponseEntity.ok(activityService.getAllForCurrentUser());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id){
        return ResponseEntity.ok(activityService.getActivityById(id));
    }
    @PostMapping
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody ActivityRequest request){
        Activity createActivity=activityService.createActivity(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(createActivity);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id,@Valid @RequestBody ActivityRequest request
    ){
     return ResponseEntity.ok(activityService.updateActivity(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
            activityService.deleteActivity(id);
            return ResponseEntity.noContent().build();
        }
}
