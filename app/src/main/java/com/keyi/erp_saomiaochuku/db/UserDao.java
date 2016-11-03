package com.keyi.erp_saomiaochuku.db;

/**
 * Created by Administrator on 2016/6/6.
 */
import android.content.Context;

import java.sql.SQLException;
import java.util.List;


public class UserDao
{
    private Context context;

    public UserDao(Context context)
    {
        this.context = context;
    }
    //添加行
    public void add(User user)
    {
        try
        {
            DBHelper.getHelper(context).getUserDao().create(user);
        } catch (SQLException e)
        {
        }
    }
    //删除行
    public void delete(List<User> user){
        try
        {
            DBHelper.getHelper(context).getUserDao().delete(user);
        } catch (SQLException e)
        {
        }
    }
    //更新行
    public void updateUser(User user)
    {
        DBHelper helper = DBHelper.getHelper(context);
        try
        {
            helper.getUserDao().update(user);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //按id删除行
    public void deleteUser(int id)
    {
        DBHelper helper = DBHelper.getHelper(context);
        try
        {
            helper.getUserDao().deleteById(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //查询全部数据
    public  List<User> queryAll(Context context) {
        try {
            List<User> users = DBHelper.getHelper(context).getUserDao().queryForAll();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
