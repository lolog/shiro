package net.yeah.shiro;

import java.util.Arrays;

import net.yeah.shiro.util.ShiroUtils;

import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class AuthApp {
	@Test
	public void hasRoleTest() {
		Subject currentSubject = ShiroUtils.login("classpath:shiro_auth.ini", "user", "user");
		
		System.out.println(currentSubject.hasRole("role1"));
		System.out.println(currentSubject.hasRole("role2"));
		System.out.println(currentSubject.hasRole("role3"));
	}
	
	@Test
	public void hasRolesTest() {
		Subject currentSubject = ShiroUtils.login("classpath:shiro_auth.ini", "user", "user");
		
		boolean[] roleAuths = currentSubject.hasRoles(Arrays.asList(new String[]{"role1", "role2", "role3"}));
		for (boolean roleAuth: roleAuths) {
			System.out.println(roleAuth);
		}
	}
	
	@Test
	public void checkRolesTest() {
		Subject currentSubject = ShiroUtils.login("classpath:shiro_auth.ini", "user", "user");
		
		currentSubject.checkRole("role1");
		currentSubject.checkRoles(new String[]{"role1", "role3"});
	}
}
