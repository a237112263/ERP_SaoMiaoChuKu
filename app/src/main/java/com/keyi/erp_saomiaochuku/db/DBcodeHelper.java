package com.keyi.erp_saomiaochuku.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/6/6.
 */
public class DBcodeHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "sqlite-code.db";
    /**
     * userDao ，每张表对于一个
     */
    private Dao<Code, Integer> userDao;
    private RuntimeExceptionDao<Code, Integer> userRuntimeDao = null;

    private DBcodeHelper(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }
    public RuntimeExceptionDao<Code, Integer> getUserDataDao()
    {
        if (userRuntimeDao == null)
        {
            userRuntimeDao = getRuntimeExceptionDao(Code.class);
        }
        return userRuntimeDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database,
                         ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, Code.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try
        {
            TableUtils.dropTable(connectionSource, Code.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static DBcodeHelper instance;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DBcodeHelper getHelper(Context context)
    {
        if (instance == null)
        {
            synchronized (DBcodeHelper.class)
            {
                if (instance == null)
                    instance = new DBcodeHelper(context);
            }
        }

        return instance;
    }

    /**
     * 获得userDao
     *
     * @return
     * @throws SQLException
     */
    public Dao<Code, Integer> getUserDao() throws SQLException
    {
        if (userDao == null)
        {
            userDao = getDao(Code.class);
        }
        return userDao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();
        userDao = null;
    }
}
