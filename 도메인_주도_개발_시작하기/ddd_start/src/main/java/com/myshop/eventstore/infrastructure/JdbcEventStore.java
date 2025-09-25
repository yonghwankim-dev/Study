package com.myshop.eventstore.infrastructure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myshop.eventstore.api.EventEntry;
import com.myshop.eventstore.api.EventStore;
import com.myshop.eventstore.error.PayloadConvertException;

@Component
public class JdbcEventStore implements EventStore {

	private final ObjectMapper objectMapper;
	private final JdbcTemplate jdbcTemplate;

	public JdbcEventStore(ObjectMapper objectMapper, JdbcTemplate jdbcTemplate) {
		this.objectMapper = objectMapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Object event) {
		EventEntry entry = new EventEntry(
			event.getClass().getName(),
			"application/json",
			toJson(event)
		);
		jdbcTemplate.update(
			"INSERT INTO evententry (type, content_type, payload, timestamp) values (?, ?, ?, ?)",
			ps -> {
				ps.setString(1, entry.getType());
				ps.setString(2, entry.getContentType());
				ps.setString(3, entry.getPayload());
				ps.setLong(4, entry.getTimestamp());
			});
	}

	private String toJson(Object event) {
		try {
			return objectMapper.writeValueAsString(event);
		} catch (Exception e) {
			throw new PayloadConvertException(e);
		}
	}

	@Override
	public List<EventEntry> get(long offset, long limit) {
		return jdbcTemplate.query(
			"SELECT * FROM evententry ORDER BY id LIMIT ?, ?",
			ps -> {
				ps.setLong(1, offset);
				ps.setLong(2, limit);
			},
			(rs, rowNum) -> new EventEntry(
				rs.getLong("id"),
				rs.getString("type"),
				rs.getString("content_type"),
				rs.getString("payload"),
				rs.getLong("timestamp")
			)
		);
	}
}
