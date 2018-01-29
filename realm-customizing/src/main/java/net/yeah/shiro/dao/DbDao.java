package net.yeah.shiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import net.yeah.shiro.pojo.Role;
import net.yeah.shiro.pojo.User;

public class DbDao {
	String userSql ="select * from shiro_users where username = ?";
	String userRoleSql ="select ru.*, ur.*,sr.rolename from shiro_users ru join shiro_users_roles ur on ru.id=ur.user_id join shiro_roles sr on ur.role_id where username = ?";
	String permSql = "select sp.permissionname from shiro_roles_permissions srp join shiro_permissions sp on srp.permission_id=sp.id where srp.role_id in ";
	
	public User getByUserName(Connection conn, String userName) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(userSql);
		stmt.setString(1, userName);
		ResultSet result = stmt.executeQuery();
		
		if (result.next()) {
			User user = new User();
			user.setUserId(result.getInt("id"));
			user.setUserName(result.getString("username"));
			user.setPassword(result.getString("password"));
			
			return user;
		}
		return null;
	}
	
	public Set<Role> getRolesByUserName(Connection conn, String userName) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(userRoleSql);
		stmt.setString(1, userName);
		ResultSet result = stmt.executeQuery();
		
		Set<Role> roles = new HashSet<Role>();
		while (result.next()) {
			Role role = new Role();
			role.setRoleId(result.getInt("user_id"));
			role.setRoleName(result.getString("rolename"));
			roles.add(role);
		}
		return roles;
	}
	
	public Set<String> getPermissionsByRoleId(Connection conn, Set<Role> roles) throws SQLException {
		String inSql = "";
		for (@SuppressWarnings("unused") Role role: roles) {
			inSql += "?,";
		}
		if (inSql.length() == 0) {
			return null;
		}
		inSql = permSql + "(" + inSql.substring(0, inSql.length() - 1) + ")";
		PreparedStatement stmt = conn.prepareStatement(inSql);
		int index = 1;
		for (Role role: roles) {
			stmt.setInt(index++, role.getRoleId());
		}
		
		ResultSet result = stmt.executeQuery();
		
		Set<String> realms = new HashSet<String>();
		while (result.next()) {
			realms.add(result.getString("permissionname"));
		}
		return realms;
	}
}
