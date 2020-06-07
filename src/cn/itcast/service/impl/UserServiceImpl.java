package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();
    /**
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        //调用dao完成查询
        return dao.findAll();
    }

    //调用dao完成登录
    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndUserPassword(user.getUsername(),user.getPassword());
    }
    //调用dao完成添加
    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }
    //调用dao完成删除
    @Override
    public void deleteUser(String id) {
        dao.deleteUser(Integer.parseInt(id));

    }
    //根据id查找用户数据，返回User对象
    @Override
    public User findUserById(String id) {
        return dao.findUserById(Integer.parseInt(id));
    }
    //修改用户信息数据
    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }
    //删除选中用户信息
    @Override
    public void delSelectedUser(String[] uids) {
        if(uids!=null && uids.length>0){
            //遍历数组
            for (String id : uids) {
                dao.deleteUser(Integer.parseInt(id));
            }
        }


    }

    /**
     * 分页查询
     * @param _currentPage
     * @param _rows
     * @param condition
     * @return
     */
    @Override
        public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows= Integer.parseInt(_rows);

        if(currentPage<=0){
            currentPage=1;
        }
        //1.创建空的PageBean对象
        PageBean<User> pb=new PageBean<User>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //3.调用dao方法，获取总记录数
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //4.调用dao方法，获取需要显示的list集合
        //5.计算开始记录的索引
        int start=(currentPage - 1) * rows;
        List<User> list=dao.findByPage(start,rows,condition);
        pb.setList(list);
        //6.计算总页数
        int  totalPage= (totalCount % rows) == 0  ? totalCount / rows :(totalCount / rows) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

}
