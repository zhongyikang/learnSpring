package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
@RunWith(SpringRunner.class)
/**
 * @RunWith是一个Junit注释。
 * 不是很懂， 提供了SpringRunner.class.
 * SpringRunner是SpringJUnit4ClassRunner的别名。
 * @author 钟益康
 *
 */
@SpringBootTest
/**
 * 表示是SpringBoot的测试类。
 * @author 钟益康
 *
 */

public class TacoCloudApplicationTests {
	
    @Test
    public void contextLoads() {
    	
    }
}
