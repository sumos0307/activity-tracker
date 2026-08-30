package com.example.activitytracker.dto;

import com.example.activitytracker.entity.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
@Getter
@Setter
public class ActivityRequest {
    @NotBlank(message = "Başlık boş olamaz.")
    private String title;
    @NotBlank(message = "Açıklama boş olamaz.")
    private String description;
    private boolean completed;
    @NotNull(message = "Faaliyet türü seçilmelidir.")
    private ActivityType activityType;
    @NotNull(message = "Başlangıç tarihi zorunludur.")
    private LocalDate startDate;

    @NotNull(message = "Bitiş tarihi zorunludur.")
    private LocalDate endDate;
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
