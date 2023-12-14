package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.util.SpeakerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository("speakerRepository")
public class SpeakerRepositoryImpl implements SpeakerRepository {

    private JdbcTemplate jdbcTemplate;

    public SpeakerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Speaker> findAll() {
        List<Speaker> speakers = jdbcTemplate.query("SELECT * FROM speaker", new SpeakerRowMapper());

        return speakers;
    }

//    @Override
//    public Speaker create(Speaker speaker) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement ps = con.prepareStatement("INSERT INTO speaker (name) values (?)", new String[]{"id"});
//                ps.setString(1, speaker.getName());
//                return ps;
//            }
//        }, keyHolder);
//
//        Number id = keyHolder.getKey();
//
//        return getSpeaker(id.intValue());
//
//
//            // If we do not want to return object
////        jdbcTemplate.update("INSERT INTO speaker (name) values (?)", speaker.getName());
////        return null;
//
//        // Using SimpleJdbcInsert
////        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
////
////        insert.setTableName("speaker");
////
////        List<String> columns = new ArrayList<>();
////        columns.add("name");
////
////        Map<String, Object> data = new HashMap<>();
////        data.put("name", speaker.getName());
////
////        insert.setGeneratedKeyName("id");
////
////        Number key = insert.executeAndReturnKey(data);
////
////        System.out.println(key);
////        return null;
//    }

    @Override
    public Speaker create(Speaker speaker) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        insert.setTableName("speaker");

        List<String> columns = new ArrayList<>();
        columns.add("name");

        Map<String, Object> data = new HashMap<>();
        data.put("name", speaker.getName());

        insert.setGeneratedKeyName("id");

        Number id = insert.executeAndReturnKey(data);

        return getSpeaker(id.intValue());
    }

    @Override
    public Speaker getSpeaker(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM speaker WHERE id = ?", new SpeakerRowMapper(), id);
    }

    @Override
    public Speaker update(Speaker speaker) {
        jdbcTemplate.update("UPDATE speaker SET name = ? where id = ?", speaker.getName(), speaker.getId());
        return speaker;
    }

    @Override
    public void update(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("update speaker set skill = ? where id = ?", pairs);
    }
}
