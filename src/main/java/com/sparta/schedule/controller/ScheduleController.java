package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    //일정 생성
    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        //RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // schedule  max id check + 중복 방지
        Long maxId = scheduleList.size() > 0 ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedule.setId(maxId);

        // DB 저장
        scheduleList.put(schedule.getId(), schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        return scheduleResponseDto;
    }

    //전체 일정 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getAllSchedule() {
        // map to list
        List<ScheduleResponseDto> responseList = scheduleList.values().stream()
                .map(ScheduleResponseDto::new).toList();
        return responseList;
    }

    //특정 일정 조회(ID로 조회)
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleList.get(id);
        if(schedule == null) {
            throw new NoSuchElementException("해당 일정이 존재하지 않습니다.");
        }
        return new ScheduleResponseDto(schedule);
    }

    // 특정 일정 조회 (날짜 yyyy-mm-dd)
    @GetMapping("/schedules/date/{date}")
    public List<ScheduleResponseDto> getScheduleByDate(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(date, formatter);

        return scheduleList.values().stream()
                .filter(schedule -> schedule.getCreatedAt().equals(targetDate))
                .map(ScheduleResponseDto::new).toList();
    }

    // 일정 수정하기 {id} 기반
    @PutMapping("/shedule/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleList.get(id);
        if(schedule == null) {
            throw new NoSuchElementException("해당 일정이 존재하지 않습니다.");
        }

        // 일정 수정
        schedule.setTask(requestDto.getTask());
        schedule.setCreator(requestDto.getCreator());
        schedule.setPassword(requestDto.getPassword());
        schedule.setUpdatedAt(LocalDateTime.now());


        //DB 갱신
        scheduleList.put(id, schedule);

        return new ScheduleResponseDto(schedule);
    }

    //일정 삭제
    @DeleteMapping("/schedule/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        if(!scheduleList.containsKey(id)) {
            throw new NoSuchElementException("해당 일정이 존재하지 않습니다.");
        }
        scheduleList.remove(id);
        return "삭-제";
    }

}
