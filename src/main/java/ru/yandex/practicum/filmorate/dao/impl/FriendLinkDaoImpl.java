package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendLinkDao;
import ru.yandex.practicum.filmorate.model.FriendLink;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FriendLinkDaoImpl implements FriendLinkDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void createFriendLink(int initiatorId, int recipientId) {
        log.debug("Вызов метода createFriendLink класса FriendLinkDaoImpl initiatorId = {}; recipientId = {}",
                initiatorId, recipientId);
        String sql = "INSERT INTO friend_links(initiator_user_id, recipient_user_id, confirm) " +
                "VALUES(:initiator_id, :recipient_id, :confirm)";

        jdbcOperations.update(sql,
                Map.of("initiator_id", initiatorId, "recipient_id", recipientId, "confirm", false));
    }

    @Override
    public void changeConfirmFriendLink(int initiatorId, int recipientId, boolean confirm) {
        log.debug("Вызов метода changeConfirmFriendLink класса FriendLinkDaoImpl initiatorId = {}; recipientId = {}",
                initiatorId, recipientId);
        String sql = "UPDATE friend_links SET confirm = :confirm " +
                "WHERE recipient_user_id = :initiator_Id AND initiator_user_id = :recipient_id";

        jdbcOperations.update(sql,
                Map.of("initiator_Id", initiatorId, "recipient_id", recipientId, "confirm", confirm));
    }

    @Override
    public Optional<FriendLink> getFriendLinkByUsersId(int userId, int friendId) {
        log.debug("Вызов метода getFriendLinkByUsersId класса FriendLinkDaoImpl userId = {}; friendId = {}",
                userId, friendId);
        String sql =
                "SELECT i.user_id AS initiator_id, i.user_name AS initiator_name, i.login AS initiator_login, " +
                        "i.email AS initiator_mail, i.birthday AS initiator_birthday," +
                        "r.user_id AS recipient_id, r.user_name AS recipient_name, r.login AS recipient_login, " +
                        "r.email AS recipient_email, r.birthday AS recipient_birthday, " +
                        "fl.confirm " +
                        "FROM friend_links AS fl " +
                        "INNER JOIN users AS r ON r.user_id = fl.recipient_user_id " +
                        "INNER JOIN users AS i ON i.user_id = fl.initiator_user_id " +
                        "WHERE fl.initiator_user_id = :user_id AND fl.recipient_user_id = :friend_id OR " +
                        "fl.initiator_user_id = :friend_id AND fl.recipient_user_id = :user_id";

        List<FriendLink> friendLink = jdbcOperations.query(sql,
                new MapSqlParameterSource(Map.of("user_id", userId, "friend_id", friendId)),
                (rs, rowNum) -> ModelsParser.parseFriendLink(rs));
        if (friendLink.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(friendLink.get(0));
    }

    @Override
    public List<User> getFriendsOfUserById(int userId) {
        String sql =
                "SELECT u.user_id, u.user_name, u.login, u.email, u.birthday FROM users AS u " +
                        "INNER JOIN friend_links ON u.user_id = friend_links.recipient_user_id " +
                        "WHERE :userId = friend_links.initiator_user_id " +
                        "UNION " +
                        "SELECT  u.user_id, u.user_name, u.login, u.email, u.birthday FROM users AS u " +
                        "INNER JOIN friend_links ON u.user_id = friend_links.initiator_user_id " +
                        "WHERE :userId = friend_links.recipient_user_id AND friend_links.confirm = true;";

        return jdbcOperations.query(sql, Map.of("userId", userId), (rs, rowNum) -> ModelsParser.parseUser(rs));
    }

    @Override
    public void removeFriendLink(FriendLink friendLink) {
        log.debug("Вызов метода removeFriendLink класса FriendLinkDaoImpl friendLink = {}",
                friendLink);
        String sql = "DELETE FROM friend_links " +
                "WHERE initiator_user_id = :initiator_id AND recipient_user_id = :recipient_id";

        jdbcOperations.update(sql,
                Map.of("initiator_id", friendLink.getInitiator().getId(), "recipient_id", friendLink.getRecipient().getId()));
    }

    @Override
    public List<User> getCommonFriendsList(int userId, int commonUserId) {
        String sql =
                "WITH common_friends AS (" +
                        "SELECT fl.recipient_user_id " +
                        "FROM friend_links AS fl " +
                        "WHERE :common_id = fl.initiator_user_id " +
                        "UNION " +
                        "SELECT fl.initiator_user_id " +
                        "FROM friend_links AS fl " +
                        "WHERE :common_id = fl.recipient_user_id AND fl.confirm = true " +
                        ")" +
                        "SELECT u.user_id, u.user_name, u.login, u.email, u.birthday " +
                        "FROM users AS u, common_friends AS cm " +
                        "INNER JOIN friend_links AS fl ON u.user_id = fl.recipient_user_id " +
                        "WHERE :user_id = fl.initiator_user_id AND u.user_id IN (cm.recipient_user_id) " +
                        "UNION " +
                        "SELECT  u.user_id, u.user_name, u.login, u.email, u.birthday " +
                        "FROM users AS u, common_friends AS cm " +
                        "INNER JOIN friend_links AS fl ON u.user_id = fl.initiator_user_id " +
                        "WHERE :user_id = fl.recipient_user_id AND fl.confirm = true AND u.user_id IN (cm.recipient_user_id) ";

        return jdbcOperations.query(sql, Map.of("user_id", userId, "common_id", commonUserId),
                (rs, rowNum) -> ModelsParser.parseUser(rs));
    }
}
