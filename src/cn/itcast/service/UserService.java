package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();
    /**
     *登录查询
     * @return
     */
    public User login(User user);
    /**
     *保存对象
     * @return
     */
    public void addUser(User user);
    //根据id删除
    public void deleteUser(String id);

    //根据id查找数据，并返回一个User对象
    public User findUserById(String id);
    //修改用户信息数据
    public void updateUser(User user);
    //删除选中用户信息数据
    public void delSelectedUser(String[] uids);

    /**
     * 分页条件查询
     * @param _currentPage
     * @param _rows
     * @param condition
     * @return
     */
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition);
}
