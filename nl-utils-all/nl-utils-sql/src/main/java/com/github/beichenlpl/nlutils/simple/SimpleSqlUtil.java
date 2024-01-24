package com.github.beichenlpl.nlutils.simple;

import com.github.beichenlpl.nlutils.simple.annotation.ColName;
import com.github.beichenlpl.nlutils.simple.annotation.TableName;
import com.github.beichenlpl.nlutils.simple.callback.PrimaryKeyCallback;
import com.github.beichenlpl.nlutils.simple.enums.ColType;
import com.github.beichenlpl.nlutils.simple.func.PrepareStatementBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JDBC工具类
 *
 * @author lpl
 * @version 1.0
 * @since 2023.12.22
 */
public class SimpleSqlUtil {

    private final SimpleConnectionPool pool;


    /**
     * 构造方法
     *
     * @param pool 连接池对象
     */
    public SimpleSqlUtil(SimpleConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * 执行SQL语句，并返回是否执行成功的结果
     *
     * @param sql SQL语句
     * @return 执行结果
     */
    public Boolean execute(String sql) {
        SimpleConnectionPool.SimpleConnection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.getConnection();
            ps = connection.getConnection().prepareStatement(sql);
            return ps.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } finally {
            pool.releaseSource(ps, connection);
        }
    }

    /**
     * 执行批量SQL语句
     *
     * @param sql SQL语句
     * @param psBuilder PreparedStatement构造器
     * @return 执行的批处理结果
     */
    public int[] executeBatch(String sql, PrepareStatementBuilder psBuilder) {
        SimpleConnectionPool.SimpleConnection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.getConnection();
            ps = psBuilder.build(connection.getConnection().prepareStatement(sql));
            return ps.executeBatch();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } finally {
            pool.releaseSource(ps, connection);
        }
    }
    
    
    /**
     * 查询结果集并返回List集合
     *
     * @param sql SQL语句
     * @param clazz 泛型类型
     * @param args 参数
     * @return 查询结果
     */
    public <T> List<T> query(String sql, Class<T> clazz, Object... args) {
        SimpleConnectionPool.SimpleConnection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.getConnection();
            ps = connection.getConnection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ResultSet resultSet = ps.executeQuery();

            List<T> result = new ArrayList<>();
            Field[] fields = clazz.getDeclaredFields();
            while (resultSet.next()) {
                Constructor<T> constructor = clazz.getConstructor();
                T t = constructor.newInstance();
                for (Field field : fields) {
                    ColName colName = field.getAnnotation(ColName.class);
                    if (Objects.nonNull(colName)) {
                        Map<String, Method> writeMethod = getWriteMethod(clazz);
                        writeMethod.get(colName.value()).invoke(t, resultSet.getObject(colName.value()));
                    }
                }
                result.add(t);
            }

            return result;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } finally {
            pool.releaseSource(ps, connection);
        }
    }

    /**
     * 查询表的记录数
     *
     * @param clazz 类型
     * @return 记录数
     */
    public <T> Long count(Class<T> clazz) {
        String countSql = "select count(*) from " + clazz.getAnnotation(TableName.class).value();
        return count(countSql);
    }

    /**
     * 查询带有参数的SQL语句的记录数
     *
     * @param countSql SQL语句
     * @param args 参数
     * @return 记录数
     */
    public Long count(String countSql, Object... args) {
        SimpleConnectionPool.SimpleConnection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.getConnection();
            ps = connection.getConnection().prepareStatement(countSql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getLong(1) : 0L;
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } finally {
            pool.releaseSource(ps, connection);
        }
    }

    /**
     * 将结果集转化为泛型对象
     *
     * @param sql  SQL语句
     * @param clazz 泛型类型
     * @param args 参数
     * @return 转化后的对象
     */
    public <T> T queryToObject(String sql, Class<T> clazz, Object... args) {
        return query(sql, clazz, args).get(0);
    }

    /**
     * 插入数据
     *
     * @param sql SQL语句
     * @param psBuilder PreparedStatement构造器
     * @return 受影响的行数
     */
    public int insert(String sql, PrepareStatementBuilder psBuilder) {
        return insert(sql, psBuilder, null);
    }

    /**
     * 插入数据
     *
     * @param t 泛型对象
     * @param clazz 泛型类型
     * @return 受影响的行数
     */
    public <T> int insert(T t,  Class<T> clazz) {
        return insert(t, clazz, null);
    }

    /**
     * 插入数据
     *
     * @param t 泛型对象
     * @param clazz 泛型类型
     * @param primaryKeyCallback 主键回调
     * @return 受影响的行数
     */
    public <T> int insert(T t,  Class<T> clazz, PrimaryKeyCallback primaryKeyCallback) {
        try {
            TableName tableName = clazz.getAnnotation(TableName.class);
            Map<ColName, Object> fieldMap = getAvailableFields(t, clazz);

            String col = "(" + fieldMap.keySet().stream().map(ColName::value).collect(Collectors.joining(",")) + ")";

            Object[] args = fieldMap.values().toArray();

            StringBuilder sqlBuilder = new StringBuilder("insert into ");
            if (Objects.nonNull(tableName)) {
                sqlBuilder.append(tableName.value())
                        .append(col)
                        .append(" values(");

                for (int i = 0; i < args.length; i++) {
                    if (i < args.length - 1) {
                        sqlBuilder.append("?,");
                    } else {
                        sqlBuilder.append("?)");
                    }
                }
            }

            return insert(sqlBuilder.toString(), ps -> {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
                return ps;
            }, primaryKeyCallback);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }


    /**
     * 插入数据
     *
     * @param sql       SQL语句
     * @param psBuilder PreparedStatement构造器
     * @param primaryKeyCallback 主键回调接口
     * @return 受影响的行数
     */
    public int insert(String sql, PrepareStatementBuilder psBuilder, PrimaryKeyCallback primaryKeyCallback) {
        SimpleConnectionPool.SimpleConnection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.getConnection();
            ps = Objects.nonNull(primaryKeyCallback) ?
                    psBuilder.build(connection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) :
                    psBuilder.build(connection.getConnection().prepareStatement(sql));

            int updateLine = ps.executeUpdate();
            if (Objects.nonNull(primaryKeyCallback)) {
                ResultSet keys = ps.getGeneratedKeys();
                primaryKeyCallback.setPrimaryKey(keys.getObject(1, primaryKeyCallback.getClazz()));
            }
            return updateLine;
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } finally {
            pool.releaseSource(ps, connection);
        }
    }

    
    public <T> int updateByPrimaryKey(T t, Class<T> clazz) {
        try {
            TableName tableName = clazz.getAnnotation(TableName.class);
            Map<ColName, Object> fieldMap = getAvailableFields(t, clazz);

            List<ColName> primaryKeyList =  fieldMap.keySet().stream()
                    .filter(item -> Objects.equals(item.type(), ColType.PRIMARY_KEY))
                    .collect(Collectors.toList());
            List<ColName> updateFields = fieldMap.keySet().stream()
                    .filter(item -> Objects.equals(item.type(), ColType.DEFAULT))
                    .collect(Collectors.toList());

            if (primaryKeyList.size() != 1) {
                throw new IllegalStateException("No primary key found!");
            }

            Object[] args = new Object[updateFields.size()];
            for (int i = 0; i < updateFields.size(); i++) {
                args[i] = fieldMap.get(updateFields.get(i));
            }

            StringBuilder sqlBuilder = new StringBuilder("update ");
            if (Objects.nonNull(tableName)) {
                sqlBuilder.append(tableName.value())
                        .append(" set ");
                for (int i = 0; i < updateFields.size(); i++) {
                    if (i < updateFields.size() - 1) {
                        sqlBuilder.append(updateFields.get(i).value()).append("=?,");
                    } else {
                        sqlBuilder.append(updateFields.get(i).value()).append("=?");
                    }
                }

                sqlBuilder.append(" where ")
                        .append(primaryKeyList.get(0).value())
                        .append("=?");
            }

            return update(sqlBuilder.toString(), ps -> {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
                ps.setObject(args.length + 1, fieldMap.get(primaryKeyList.get(0)));
                return ps;
            }, args);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
    
    
    /**
     * 更新数据
     *
     * @param sql SQL语句
     * @param psBuilder PreparedStatement构造器
     * @param args  参数
     * @return 更新的行数
     */
    public int update(String sql, PrepareStatementBuilder psBuilder, Object... args) {
        SimpleConnectionPool.SimpleConnection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.getConnection();
            ps = psBuilder.build(connection.getConnection().prepareStatement(sql));
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } finally {
            pool.releaseSource(ps, connection);
        }
    }



    /**
     * 获取写入方法的Map
     *
     * @param clazz 类型
     * @return 写入方法的Map
     * @throws NoSuchMethodException 方法不存在异常
     */
    private <T> Map<String, Method> getWriteMethod(Class<T> clazz) throws NoSuchMethodException {
        Map<String, Method> retMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            ColName colName = field.getAnnotation(ColName.class);
            if (Objects.nonNull(colName)) {
                retMap.put(colName.value(), clazz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), field.getType()));
            }
        }

        return retMap;
    }

    /**
     * 获取读取方法的Map
     *
     * @param clazz 类型
     * @return 读取方法的Map
     * @throws NoSuchMethodException 方法不存在异常
     */
    private <T> Map<String, Method> getReadMethod(Class<T> clazz) throws NoSuchMethodException {
        Map<String, Method> retMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            ColName colName = field.getAnnotation(ColName.class);
            if (Objects.nonNull(colName)) {
                retMap.put(colName.value(), clazz.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)));
            }
        }

        return retMap;
    }

    private <T> Map<ColName, Object> getAvailableFields(T t, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<ColName, Object> fieldMap = new HashMap<>();
        Map<String, Method> readMethod = getReadMethod(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ColName colName = field.getAnnotation(ColName.class);
            if (Objects.nonNull(colName)) {
                Object val = readMethod.get(colName.value()).invoke(t);
                if (Objects.nonNull(val)) {
                    fieldMap.put(colName, val);
                }
            }
        }
        return fieldMap;
    }
}
