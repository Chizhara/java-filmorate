package ru.yandex.practicum.filmorate.dao.impl;

import ru.yandex.practicum.filmorate.model.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelsParser {
    private ModelsParser() {
    }

    public static User parseUser(ResultSet rs) {
        try {
            Date birthday = rs.getDate("birthday");
            return User.builder()
                    .id(rs.getInt("user_id"))
                    .name(rs.getString("user_name"))
                    .login(rs.getString("login"))
                    .email(rs.getString("email"))
                    .birthday(birthday == null ? null : birthday.toLocalDate())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static MpaRate parseMpaRate(ResultSet rs) {
        try {
            return new MpaRate(rs.getInt("mpa_rate_id"), rs.getString("mpa_rate_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Genre parseGenre(ResultSet rs) {
        try {
            return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static FriendLink parseFriendLink(ResultSet rs) {
        try {
            Date initiatorBirthday = rs.getDate("initiator_birthday");
            User initiator = User.builder()
                    .id(rs.getInt("initiator_id"))
                    .name(rs.getString("initiator_name"))
                    .login(rs.getString("initiator_login"))
                    .email(rs.getString("initiator_mail"))
                    .birthday(initiatorBirthday == null ? null : initiatorBirthday.toLocalDate())
                    .build();
            Date recipientBirthday = rs.getDate("recipient_birthday");
            User recipient = User.builder()
                    .id(rs.getInt("recipient_id"))
                    .name(rs.getString("recipient_name"))
                    .login(rs.getString("recipient_login"))
                    .email(rs.getString("recipient_email"))
                    .birthday(recipientBirthday == null ? null : recipientBirthday.toLocalDate())
                    .build();

            return new FriendLink(initiator, recipient, rs.getBoolean("confirm"));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Film parseFilm(ResultSet rs) {
        try {
            Date releaseDate = rs.getDate("release_date");
            Film film = Film.builder()
                    .id(rs.getInt("film_id"))
                    .name(rs.getString("film_name"))
                    .description(rs.getString("description"))
                    .releaseDate(releaseDate == null ? null : releaseDate.toLocalDate())
                    .duration(rs.getInt("duration_in_minutes")).build();
            film.setMpa(new MpaRate(rs.getInt("mpa_rate_id"), rs.getString("mpa_rate_name")));

            return film;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
