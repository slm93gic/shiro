import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.omg.CORBA.portable.UnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quickstart {


    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);
    private static final String iniResoursPath = "classpath:shiro.ini";


    public static void main(String[] args) {
        //使用类路径根目录下的shiro.ini文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniResoursPath);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //获取当前正在执行的用户
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();

        String key = "smoeKey";
        String value = "aValue";
        session.setAttribute(key, value);

        String aValue = (String) session.getAttribute(key);
        if (aValue.equals(value)) {
            System.out.println("获取到正确的值![" + value + "]");
        }

        //测试当前的用户是否已经被认证，即是否已经登录！
        if (!currentUser.isAuthenticated()) {//是否认证
            String userName = "周杰伦";
            String password = "123456";
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            token.setRememberMe(true);

            try {
                currentUser.login(token);
            } catch (UnknownException e) {
                System.out.println("没有用户名为：" + token.getPrincipal());

            } catch (IncorrectCredentialsException ice) {
                System.out.println("账户或密码不正确!" + token.getPrincipal());

            } catch (LockedAccountException lae) {
                System.out.println("账号" + token.getPrincipal() + "被锁定!请与管理员联系以解除锁定");
            }

        }

        System.out.println("用户[" + currentUser.getPrincipal() + "]登录成功!");


        //是否存在某以角色
        if (currentUser.hasRole("歌星")) {
            System.out.println("歌星,角色!");
        } else {
            System.out.println("Hello,其他角色.");
        }

        //是否有操作行为权限，粗粒度
        if (currentUser.isPermitted("把妹")) {
            System.out.println("你是帅哥！你可以把妹！哎哟不错哦");
        } else {
            System.out.println("对不起，你不帅你不能把妹");
        }

        //是否有操作行为权限 细粒度
        if (currentUser.isPermitted("拍电影")) {
            System.out.println("您是“导演”，你可以怕巨牛批的电影！");
        } else {
            System.out.println("Sorry,你不够优秀，拍不了");
        }
        //alldone-logout!
        currentUser.logout();
        System.exit(0);
    }

}