package com.example.activitytracker.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum ActivityType {

    MEETING,
    DEVELOPMENT,
    BUG_FIX,
    TESTING,
    CODE_REVIEW,
    RESEARCH,
    DOCUMENTATION,
    TRAINING,
    CUSTOMER_SUPPORT,
    OTHER;

}
