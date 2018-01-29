package net.yeah.shiro.realm;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import net.yeah.shiro.dao.DbDao;
import net.yeah.shiro.pojo.Role;
import net.yeah.shiro.pojo.User;
import net.yeah.shiro.util.DbUtil;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class LogRealm extends AuthorizingRealm {
	private DbDao dbDao = new DbDao();
	/**
	 * 1.验证当前登录的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String userName = token.getPrincipal().toString();
		
		try {
			Connection conn = DbUtil.getConn();
			User user = dbDao.getByUserName(conn, userName);
			if (user != null) {
				AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), user.getUserId() + "");
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 2. 为当前登录的用户授予权限和角色
	 * 补充：如果需要对url实现数据库配置，那么需要新增数据表，然后用filter拦截验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String userName = principals.getPrimaryPrincipal().toString();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		// realmName自己设置
		for (String realmName: principals.getRealmNames()) {
			System.out.println(realmName);
		}
		
		try {
			Connection conn = DbUtil.getConn();
			
			Set<Role> roles = dbDao.getRolesByUserName(conn, userName);
			
			info.setRoles(getRoles(roles));
			info.setStringPermissions(dbDao.getPermissionsByRoleId(conn, roles));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	private Set<String> getRoles(Set<Role> roles) {
		Set<String> rolesName = new HashSet<String>();
		for (Role role: roles) {
			rolesName.add(role.getRoleName());
		}
		return rolesName;
	}
}
