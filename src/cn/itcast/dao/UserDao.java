package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的Dao
 */
public interface UserDao {
     List<User> findAll();
     User findUserByUsernameAndUserPassword(String username,String password);

     void addUser(User user);

     void deleteUser(int id);

    //根据id查找数据，返回一个User对象
     User findUserById(int id);
    //修改用户信息数据
     void updateUser(User user);
    //查询总的记录数
     int findTotalCount(Map<String, String[]> condition);

    /**
     * 分页查询每页记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
