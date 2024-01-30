package com.github.beichenlpl.test;

import com.github.beichenlpl.bean.User;
import com.github.beichenlpl.nlutils.base.common.BaseUtils;
import org.junit.Test;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.29
 */
public class BaseUtilTest {

    @Test
    public void cpPropsTest() {
        User u1 = new User().setName("Tom")
                .setAge(18)
                .setAddress("beijing")
                .setSex("m")
                .setPhone("1234567890")
                .setEmail("1234567890");

        User u2 = new User().setName("Sunny")
                .setAge(20);

        BaseUtils.cpProps(u1, u2, "name");

        System.out.println(u1 + "\n" + u2);
    }


    @Test
    public void isEmptyTest() {
        System.out.println(BaseUtils.isEmpty(new String[3][]));
    }
}
