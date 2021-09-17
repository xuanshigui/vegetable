package com.vege.dao;

import com.vege.model.User;
import com.vege.utils.MD5Utils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl extends BaseDao implements UserDao {

    private static String SQL_INSERT = "INSERT INTO tb_sysuser (username, password, realname, salt, telephone, email,note) VALUES('%s', '%s','%s', '%s','%s', '%s', '%s')";
    private static String SQL_DELETE = "DELETE FROM tb_sysuser WHERE userid=%s";
    private static String SQL_UPDATE = "UPDATE tb_sysuser SET username='%s', password='%s', realname='%s', telephone='%s', email='%s', note='%s' WHERE userid=%s";
    private static String SQL_QUERY = "SELECT * FROM tb_sysuser WHERE 1=1 %s %s";
    private static String SQL_QUERY_TOTAL = "SELECT count(*) as total FROM tb_sysuser WHERE 1=1 %s";
    private static String SQL_QUERY_BYID = "SELECT userid as userId, username as userName, password, realname as realName, salt, telephone as phone, email,note FROM tb_sysuser WHERE userid = %s";

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean add(User data) {
        String sql = String.format(UserDaoImpl.SQL_INSERT, data.getUserName(), data.getPassword(), data.getRealName(),MD5Utils.getRandomSalt(), data.getPhone(), data.getEmail(), data.getNote());

        return exec(sql);
    }

    public boolean delete(String userid) {
        String sql = String.format(UserDaoImpl.SQL_DELETE, userid);

        return exec(sql);
    }

    public boolean update(User data) {
        String sql = String.format(UserDaoImpl.SQL_UPDATE, data.getUserName(), data.getPassword(), data.getRealName(),data.getPhone(), data.getEmail(), data.getNote(), data.getUserId());

        return exec(sql);
    }

    public User queryById(String userid){

        String sql = String.format(UserDaoImpl.SQL_QUERY_BYID, userid);
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        User user = jdbcTemplate.queryForObject(sql,rowMapper);
        return user;
    }

    public List<User> query(Map<String, String> condition) {
        String where = buildWhere(condition);
        String limit = buildLimit(condition);
        String sql = String.format(UserDaoImpl.SQL_QUERY, where, limit);

        List<User> result = new ArrayList<>();
        try {
            List rows = jdbcTemplate.queryForList(sql);
            for (Object row1 : rows) {
                Map row = (Map) row1;
                User user = new User();
                user.setUserId(Integer.parseInt(String.valueOf(row.get("userid"))));
                user.setUserName(String.valueOf(row.get("username")));
                user.setPassword(String.valueOf(row.get("password")));
                user.setRealName(String.valueOf(row.get("realname")));
                user.setSalt(String.valueOf(row.get("salt")));
                user.setPhone(String.valueOf(row.get("telephone")));
                user.setEmail(String.valueOf(row.get("email")));
                user.setNote(String.valueOf(row.get("note")));
                result.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int queryTotal(Map<String, String> condition) {
        String where = buildWhere(condition);
        String sql = String.format(UserDaoImpl.SQL_QUERY_TOTAL, where);

        return getTotal(sql);
    }

}
