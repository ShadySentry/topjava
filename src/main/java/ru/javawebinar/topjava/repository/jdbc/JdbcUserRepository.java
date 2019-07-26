package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final ResultSetExtractor<List<User>> usersExtractor = new ResultSetExtractor<List<User>>() {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> usersList = new ArrayList<>();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));

                User existedUser = usersList.stream().filter(user -> user.getId().compareTo(u.getId()) == 0).findFirst().orElse(null);

                if (existedUser == null) {
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRegistered(rs.getTimestamp("registered"));
                    u.setEnabled(rs.getBoolean("enabled"));
                    u.setCaloriesPerDay(rs.getInt("calories_per_day"));
                    u.setRoles(List.of(Role.getFromString(rs.getString("role"))));
                    usersList.add(u);
                } else {
                    existedUser.addRole(Role.getFromString(rs.getString("role")));
                }
            }
            return usersList;
        }
    };


    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
//        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
//
//        if (user.isNew()) {
//            Number newKey = insertUser.executeAndReturnKey(parameterSource);
//            user.setId(newKey.intValue());
//        } else if (namedParameterJdbcTemplate.update(
//                "UPDATE users SET name=:name, email=:email, password=:password, " +
//                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
//            return null;
//        }
//        return user;
        String sqlQuery;
        if(user.isNew()){
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            sqlQuery = "insert into users (name,email,password,registered,enabled,calories_per_day) values (?,?,?,?,?,?)";
        }else{
            sqlQuery = "update users set name=?,email=?,password=?,registered=?,enabled=?,calories_per_day=? where id=?";
            jdbcTemplate.update(sqlQuery,
                    user.getName(), user.getEmail(),user.getPassword(),user.getRegistered(),user.isEnabled(),user.getCaloriesPerDay(),user.getId());
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?",user.getId());

        }
            jdbcTemplate.batchUpdate("insert into user_roles (user_id,role) values (?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
//                    ps.setString(1,user.getName());
//                    ps.setString(2,user.getEmail());
//                    ps.setString(3,user.getPassword());
//                    ps.setTimestamp(4, Timestamp.from(user.getRegistered().toInstant()));
//                    ps.setBoolean(5,user.isEnabled());
//                    ps.setInt(6,user.getCaloriesPerDay());
//                    if(!user.isNew()){
//                        ps.setInt(7,user.getId());
//                    }
                    ps.setInt(1,user.getId());
                    int counter=0;
                    for(Role role:user.getRoles()){
                        if(counter==i){
                            ps.setString(2,role.name());
                        }
                        counter++;

                    }
                }

                @Override
                public int getBatchSize() {
                    return user.getRoles().size();
                }
            });


        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * from users u left join user_roles r on r.user_id=u.id where u.id=?",usersExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("select * from users u left join user_roles r on r.user_id=u.id where u.email=?",usersExtractor,email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u left join user_roles r on u.id=r.user_id order by name, email", usersExtractor);
    }
}
