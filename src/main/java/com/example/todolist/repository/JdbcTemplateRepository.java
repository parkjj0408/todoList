package com.example.todolist.repository;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.entity.ToDoList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

    @Repository
    public class JdbcTemplateRepository implements ToDoRepository {
        private final JdbcTemplate jdbcTemplate;

        public JdbcTemplateRepository(DataSource dataSource) {

            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }

        public ResponseDto saveTodo(ToDoList toDoList) {
            LocalDateTime now = LocalDateTime.now();
            toDoList.setCreatedAt(now);
            toDoList.setUpdatedAt(now);

            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
            jdbcInsert.withTableName("todolist").usingGeneratedKeyColumns(new String[]{"id"});
            Map<String, Object> parameters = new HashMap();
            parameters.put("contents", toDoList.getContents());
            parameters.put("user", toDoList.getUser());
            parameters.put("password", toDoList.getPassword());
            parameters.put("create_at", Timestamp.valueOf(toDoList.getCreatedAt()));
            parameters.put("modified_at", Timestamp.valueOf(toDoList.getUpdatedAt()));
            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            return new ResponseDto(key.longValue(), toDoList.getContents(), toDoList.getUser(), toDoList.getCreatedAt(), toDoList.getUpdatedAt());
        }

        public List<ResponseDto> findAllTodo() {
            return this.jdbcTemplate.query("select * from todolist ORDER BY modified_at DESC", this.todolistRowMapper());
        }

        public Optional<ToDoList> findTodoById(Long id) {
            List<ToDoList> result = this.jdbcTemplate.query("select * from todolist where id = ?", this.todoRowMapperV2(), new Object[]{id});
            return result.stream().findAny();
        }

        public Optional<ToDoList> findTodoByIdForUpdate(Long id) {
            List<ToDoList> result = this.jdbcTemplate.query("SELECT * FROM todolist WHERE id = ?", this.todoRowMapperV2(), new Object[]{id});
            return result.stream().findAny();
        }

        public int updateTodoList(Long id, String contents, String user, String password) {
            return this.jdbcTemplate.update("update todolist set contents = ?,user = ?,password =? where id = ?", new Object[]{contents, user, password, id});
        }

        public void deleteList(Long id) {
            this.jdbcTemplate.update("DELETE FROM todolist WHERE id = ?", new Object[]{id});
        }

        private RowMapper<ResponseDto> todolistRowMapper() {
            return new RowMapper<ResponseDto>() {
                public ResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new ResponseDto(rs.getLong("id"), rs.getString("contents"), rs.getString("user"), rs.getTimestamp("create_at") != null ? rs.getTimestamp("create_at").toLocalDateTime() : null, rs.getTimestamp("modified_at") != null ? rs.getTimestamp("modified_at").toLocalDateTime() : null);
                }
            };
        }

        private RowMapper<ToDoList> todoRowMapperV2() {
            return new RowMapper<ToDoList>() {
                public ToDoList mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new ToDoList(rs.getLong("id"), rs.getString("contents"), rs.getString("user"), rs.getString("password"), rs.getTimestamp("create_at") != null ? rs.getTimestamp("create_at").toLocalDateTime() : null, rs.getTimestamp("modified_at") != null ? rs.getTimestamp("modified_at").toLocalDateTime() : null);
                }
            };
        }
    }


