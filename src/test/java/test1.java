import com.xupt.ttms.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class test1 {
    @Autowired
    UserService userService;
    @Test
    public void test() {
        System.out.println(userService.getUserByUsernameAndPwd("user2","1111"));
    }
}
