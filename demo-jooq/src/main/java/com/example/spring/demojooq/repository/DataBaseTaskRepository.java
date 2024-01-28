package com.example.spring.demojooq.repository;

import com.example.spring.demojooq.model.Task;
import com.example.spring.demojooq.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.management.InstanceNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class DataBaseTaskRepository implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Task> findAll() {
        log.debug("Calling DataBaseTaskRepository.findAll");
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public Optional<Task> getById(Long id) {
        log.debug("Calling DataBaseTaskRepository.getById {}", id);

        String sql = "SELECT * FROM task WHERE id = ?";
        Task task = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new TaskRowMapper(), 1)
                )
        );
        return Optional.ofNullable(task);
    }

    @Override
    public Task save(Task task) {
        log.debug("Calling DataBaseTaskRepository.save task {}", task);

        task.setId(System.currentTimeMillis());
        String sql = "INSERT INTO task (title, description, priority, id) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getPriority(), task.getId());

        return task;
    }

    @Override
    @SneakyThrows
    public Task update(Task task) {
        log.debug("Calling DataBaseTaskRepository.update task {}", task);
        Task existedTask = getById(task.getId()).orElse(null);
        if (nonNull(existedTask)) {
            String sql = "UPDATE task SET title = ?, description = ?, priority = ? WHERE id = ?";
            jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getPriority(), task.getId());
            return task;
        }
        log.warn("Task with id {} not found!", task.getId());
        throw new InstanceNotFoundException(MessageFormat.format("Task with id {0} not found for update!", task.getId()));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DataBaseTaskRepository.delete task id {}", id);

        String sql = "DELETE FROM task WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsert(List<Task> tasks) {
        log.debug("Calling DataBaseTaskRepository.batchInsert tasks");

        String sql = "INSERT INTO task (title, description, priority, id) VALUES (?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Task task = tasks.get(i);
                ps.setString(1, task.getTitle());
                ps.setString(2, task.getDescription());
                ps.setInt(3, task.getPriority());
                ps.setLong(4, task.getId());
            }

            @Override
            public int getBatchSize() {
                return tasks.size();
            }
        });


    }

}
