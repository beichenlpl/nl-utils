import com.github.beichenlpl.nlutils.sql.DataSource;
import com.github.beichenlpl.nlutils.sql.SimpleSqlUtil;
import com.github.beichenlpl.nlutils.sql.SimpleConnectionPool;
import com.github.beichenlpl.nlutils.sql.annotation.ColName;
import com.github.beichenlpl.nlutils.sql.annotation.TableName;
import com.github.beichenlpl.nlutils.sql.enums.ColType;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.16
 */
public class SqlTest {

    @TableName("test")
    public static class Test {
        @ColName(value = "id", type = ColType.PRIMARY_KEY)
        private Integer id;

        @ColName("name")
        private String name;

        public Test() {
            super();
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Test test = (Test) object;
            return Objects.equals(id, test.id) && Objects.equals(name, test.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
    public static void main(String[] args) {
        try {
            DataSource dataSource = new DataSource();
            dataSource.setDriver("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            SimpleConnectionPool pool = new SimpleConnectionPool(dataSource, 1, 10, 10000L);

            SimpleSqlUtil simpleSqlUtil = new SimpleSqlUtil(pool);

            simpleSqlUtil.execute("CREATE TABLE IF NOT EXISTS `test` (`id` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci");

            TimeUnit.MILLISECONDS.sleep(100);

            Test test = new Test();
            test.setName("Tom");
            simpleSqlUtil.insert(test, Test.class);

            TimeUnit.MILLISECONDS.sleep(100);

            List<Test> list = simpleSqlUtil.query("select * from test", Test.class);
            System.out.println(list);

            TimeUnit.MILLISECONDS.sleep(100);

            simpleSqlUtil.update("update test set name=? where id=?", ps -> {
                ps.setString(1, "XiaoMing");
                ps.setInt(2, 1);
                return ps;
            });

            TimeUnit.MILLISECONDS.sleep(100);

            List<Test> list1 = simpleSqlUtil.query("select * from test", Test.class);
            System.out.println(list1);

            TimeUnit.MILLISECONDS.sleep(100);

            test.setId(1);
            simpleSqlUtil.updateByPrimaryKey(test, Test.class);

            TimeUnit.MILLISECONDS.sleep(100);

            List<Test> list2 = simpleSqlUtil.query("select * from test", Test.class);
            System.out.println(list2);

            TimeUnit.MILLISECONDS.sleep(100);

            simpleSqlUtil.execute("drop table test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
