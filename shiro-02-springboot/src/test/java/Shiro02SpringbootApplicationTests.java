import com.slm.ShiroSpringBootStarter;
import com.slm.pojo.User;
import com.slm.service.UserService;

import com.slm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShiroSpringBootStarter.class)
public class Shiro02SpringbootApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void test1() {
        User user = userService.queryUserByName("root");
        System.out.println(user);
    }


}
