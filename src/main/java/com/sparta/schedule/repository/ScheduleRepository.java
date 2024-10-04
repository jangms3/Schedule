package com.sparta.schedule.repository;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 저장
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 자동 생성된 키 값을 담는 객체 와 보관.insert 쿼리를 실행하고 id 값을 조회하기 위한 코드
        String sql = "INSERT INTO schedules (task, creator, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTask());
            ps.setString(2, schedule.getCreator());
            ps.setString(3, schedule.getPassword());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getCreatedAt()));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(schedule.getUpdatedAt()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);
        return schedule;
    }

    // 전체 일정 조회
    public List<ScheduleResponseDto> findAll() {
        String sql = "SELECT * FROM schedules";
        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Schedule schedule = new Schedule();
                Long id = rs.getLong("id");
                String task = rs.getString("task");
                String creator = rs.getString("creator");
                String password = rs.getString("password");
                java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
                java.sql.Timestamp updatedAt = rs.getTimestamp("created_at");

                // ScheduleResponseDto 객체로 반환
                return new ScheduleResponseDto(schedule);
            }
        });
    }


    // 일정 수정
    public void update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedules SET task = ?, creator = ?, password = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTask(), requestDto.getCreator(), requestDto.getPassword(), java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()), id);
    }

    // 일정 삭제
    public void delete(Long id) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            // 만약 결과가 있을 경우
            if (resultSet.next()) {
                // Schedule 객체 생성 및 필드 설정
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("id"));
                schedule.setTask(resultSet.getString("task"));
                schedule.setCreator(resultSet.getString("creator"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                schedule.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                return schedule;
            } else {
                // 결과가 없을 경우 null 반환 (필요에 따라 예외 처리 가능)
                return null;
            }
        }, id);
    }
}
