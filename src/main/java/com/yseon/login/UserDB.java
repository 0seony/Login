package com.yseon.login;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public class UserDB {

	public boolean createTable() {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "d:/tomcat/userinfo.db",
					config.toProperties());
			// use
			String query = "CREATE TABLE usertable (no INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, pwd TEXT, name TEXT, birthday TEXT, address TEXT, created TEXT, updated TEXT)";
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();

			// close

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String insertData(UserInfo userinfo) {
		String resultString = "";
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "d:/tomcat/userinfo.db",
					config.toProperties());
			// use
			userinfo.pwd = sha256(userinfo.pwd);

			String query1 = "SELECT * FROM usertable WHERE id=" + "'" + userinfo.id + "'";
			PreparedStatement preparedStatement = connection.prepareStatement(query1);
			ResultSet resultdata = preparedStatement.executeQuery();
			if (resultdata.next()) {
				resultString = "중복된 아이디가 존재합니다.";
			} else {
				String query = "INSERT INTO usertable (id,pwd,name,birthday,address,created,updated)"
						+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, userinfo.id);
				preparedStatement.setString(2, userinfo.pwd);
				preparedStatement.setString(3, userinfo.name);
				preparedStatement.setString(4, userinfo.birthday);
				preparedStatement.setString(5, userinfo.address);
				preparedStatement.setString(6, userinfo.created);
				preparedStatement.setString(7, userinfo.updated);
				int result = preparedStatement.executeUpdate();
				if (result < 1) {
					resultString = "error";
				}else {
					resultString = "회원가입이 완료되었습니다.";
				}
			}
			preparedStatement.close();

			// close
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	public boolean userlogin(String id, String pwd) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "d:/tomcat/userinfo.db",
					config.toProperties());
			// use
			pwd = this.sha256(pwd);
			
			//아이디, 패스워드 있는지 검사
			String query = "SELECT * FROM usertable WHERE id=? AND pwd=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pwd);
			ResultSet resultdata = preparedStatement.executeQuery();
			if (resultdata.next()) {
				preparedStatement.close();
				connection.close();
				return true;
			} else {
				preparedStatement.close();
				connection.close();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
	
	public String sha256(String msg) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(msg.getBytes());

			StringBuilder builder = new StringBuilder();
			for (byte b : md.digest()) {
				builder.append(String.format("%02x", b));
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String selectData() {
		String resultString = "";
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "d:/tomcat/userinfo.db",
					config.toProperties());
			// use
			String query = "SELECT * FROM usertable";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int no = resultSet.getInt("no");
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String birthday = resultSet.getString("birthday");
				String address = resultSet.getString("address");
				String created = resultSet.getString("created");
				String updated = resultSet.getString("updated");
				resultString += "<tr>";
				resultString += "<td>" + no + "</td><td>" + id + "</td><td>" + name + "</td><td>" + birthday
						+ "</td><td>" + address + "</td><td>" + created + "</td><td>" + updated + "</td>";
				resultString += "<td><a href='update?no=" + no + "'>수정</td>";
				resultString += "<td><a href='delete?no=" + no + "'>삭제</td>";
				resultString += "</tr>";
			}
			preparedStatement.close();

			// close
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	public UserInfo detalisData(int no) {
		UserInfo resultData = new UserInfo();
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "d:/tomcat/userinfo.db",
					config.toProperties());

			// use
			String query = "SELECT * FROM usertable WHERE no=" + no;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				resultData.no = resultSet.getInt("no");
				resultData.id = resultSet.getString("id");
				resultData.pwd = resultSet.getString("pwd");
				resultData.name = resultSet.getString("name");
				resultData.birthday = resultSet.getString("birthday");
				resultData.address = resultSet.getString("address");

			}
			preparedStatement.close();

			// close
			connection.close();

		} catch (Exception e) {
		}
		return resultData;
	}

	public boolean updateData(UserInfo userinfo) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "d:/tomcat/userinfo.db",
					config.toProperties());
			// use
			String query = "UPDATE usertable SET id=?, name=?, birthday=?, address=?, updated=? WHERE no="
					+ userinfo.no;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userinfo.id);
			preparedStatement.setString(2, userinfo.name);
			preparedStatement.setString(3, userinfo.birthday);
			preparedStatement.setString(4, userinfo.address);
			preparedStatement.setString(5, userinfo.updated);

			int result = preparedStatement.executeUpdate();
			if (result < 1) {
				return false;
			}
			preparedStatement.close();

			// close
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteData(int no) {
		try {
			// open
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + "d:/tomcat/userinfo.db",
					config.toProperties());

			// use
			String query = "DELETE FROM usertable WHERE no=" + no;
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			statement.close();

			// close
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
