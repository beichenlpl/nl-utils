package com.github.beichenlpl.nlutils.simple;

import java.util.Objects;

/**
 * 数据源类
 *
 * @author lpl
 * @version 1.0
 * @since 2023.12.22
 */
public class DataSource {
    private String driver;   // 驱动类名
    private String url;  // 数据源URL
    private String user;  // 数据源用户名
    private String password;  // 数据源密码

    public DataSource() {}

    /**
     * 构造方法
     *
     * @param url 数据源URL
     * @param user 数据源用户名
     * @param password 数据源密码
     */
    public DataSource(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * 获取驱动类名
     *
     * @return 驱动类名
     */
    public String getDriver() {
        return driver;
    }

    /**
     * 设置驱动类名
     *
     * @param driver 驱动类名
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * 获取数据源URL
     *
     * @return 数据源URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置数据源URL
     *
     * @param url 数据源URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取数据源用户名
     *
     * @return 数据源用户名
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置数据源用户名
     *
     * @param user 数据源用户名
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取数据源密码
     *
     * @return 数据源密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置数据源密码
     *
     * @param password 数据源密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DataSource that = (DataSource) object;
        return Objects.equals(driver, that.driver) && Objects.equals(url, that.url) && Objects.equals(user, that.user) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, url, user, password);
    }
}
