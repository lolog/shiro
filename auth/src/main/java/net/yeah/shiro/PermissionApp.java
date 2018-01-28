package net.yeah.shiro;

import net.yeah.shiro.util.ShiroUtils;

import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class PermissionApp {
	@Test
	public void isPermittedTest() {
		Subject currentSubject = ShiroUtils.login("classpath:shiro_permission.ini", "user", "user");
		
		System.out.println(currentSubject.isPermitted("module:add"));
		System.out.println(currentSubject.isPermitted("module2:add"));
	}
	
	@Test
	public void isPermittedAllTest() {
		Subject currentSubject = ShiroUtils.login("classpath:shiro_permission.ini", "user", "user");
		
		System.out.println(currentSubject.isPermittedAll("module:add", "module:update"));
	}
	
	@Test
	public void checkPermissionTest() {
		Subject currentSubject = ShiroUtils.login("classpath:shiro_permission.ini", "user", "user");
		
		currentSubject.checkPermission("module:add");
		currentSubject.checkPermissions("module:add", "module:update");
	}
}
