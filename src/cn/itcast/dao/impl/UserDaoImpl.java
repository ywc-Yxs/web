package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jt=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {

        // * 使用JDBC操作数据库...
        //1.定义sql
        String sql="select * from user";
        List<User> users = jt.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    /**
     *查询User并登录
     */
    @Override
    public User findUserByUsernameAndUserPassword(String username, String password) {
        try {
            String sql="SELECT * FROM USER WHERE username=? and password=?";
            User user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *添加用户
     */
    @Override
    public void addUser(User user) {
        //1.定义sql
        String sql="insert into user values(null,?,?,?,?,?,?,null,null)";
        //2.执行sql
        jt.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());

    }
    /**
     *删除
     */
    @Override
    public void deleteUser(int id) {
        //1.定义sql语句
        String sql="DELETE FROM USER WHERE id=?;";
        //2.执行sql
        jt.update(sql,id);

    }
    //根据id查找数据，返回一个User对象
    @Override
    public User findUserById(int id) {
        String sql="select * from user where id=?";
        User user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }
    //修改用户信息数据
    @Override
    public void updateUser(User user) {
        String sql="update user set name=?,gender=?,age=?,address=?,qq=?,email=? where id=?";
        jt.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());

    }
    //查询总记录数
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //1.定义模板初始化sql
        String sql="select count(*) from user where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义以一个参数集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取values
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value !=null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//加？条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);
        Integer totalCount = jt.queryForObject(sb.toString(), Integer.class,params.toArray());
        return totalCount;
    }

    /**
     * 分页查询每页记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql="select * from user where 1=1";
        //创建
        StringBuilder sb=new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义以一个参数集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取values
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value !=null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//加？条件的值
            }
        }

        //添加分页的查询
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);
        sql=sb.toString();
        System.out.println(sql);
        System.out.println(params);
        return  jt.query(sql, new BeanPropertyRowMapper<User>(User.class), params.toArray());

    }
}
