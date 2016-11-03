package com.keyi.erp_saomiaochuku.db;

/**
 * Created by Administrator on 2016/6/6.
 */
import android.content.Context;

import java.sql.SQLException;
import java.util.List;


public class CodeDao
{
    private Context context;

    public CodeDao(Context context)
    {
        this.context = context;
    }

    public void add(Code user)
    {
        try
        {
            DBcodeHelper.getHelper(context).getUserDao().create(user);
        } catch (SQLException e)
        {
        }
    }

    public void delete(List<Code> user){
        try
        {
            DBcodeHelper.getHelper(context).getUserDao().delete(user);
        } catch (SQLException e)
        {
        }
    }

    public void updateUser(Code user)
    {
        DBcodeHelper helper = DBcodeHelper.getHelper(context);
        try
        {
            helper.getUserDao().update(user);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void deleteUser(int id)
    {
        DBcodeHelper helper = DBcodeHelper.getHelper(context);
        try
        {
            helper.getUserDao().deleteById(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
   //List<User> users = DBHelper.getHelper(context).getUserDao().queryForAll();
}
