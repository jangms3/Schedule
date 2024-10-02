package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {
    private String task;
    private String creator;
    private String password;
    private String createdAt;
}
