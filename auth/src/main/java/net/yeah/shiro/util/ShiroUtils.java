package net.yeah.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroUtils {
	public static Subject login(String conf, String username, String password) {
		// 读取配置文件,初始化SecurityManager工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(conf);
		// 获取securityManager实例
		SecurityManager securityManager = factory.getInstance();
		// 将SecurityManager实例绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 获取用户
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			// 登陆
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}
}
