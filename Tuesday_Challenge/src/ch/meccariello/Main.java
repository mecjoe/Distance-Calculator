package ch.meccariello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	
	
	static Scanner sc = new Scanner(System.in);
	static Statement stmnt = null;
	static Connection conn = null;
	static String ortschaft1 = null;
	static String ortschaft2 = null;
	static String plz1 = null;
	static String plz2 = null;
	static String kanton1 = null;
	static String kanton2 = null;
	static int koordinate_b1 = 0;
	static int koordinate_b2 = 0;
	static int koordinate_l1 = 0;
	static int koordinate_l2 = 0;
	static String ort = null;
	
	public static void main(String[] args) {
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/","java", "csharpisbetter");
			System.out.print("Enter a zip code or a town name: ");
			ort = sc.nextLine();
			stmnt = conn.createStatement();
	        ResultSet rs = stmnt.executeQuery("SELECT * FROM tuesday_challenge.ortschaften WHERE Ortschaft='" + ort + "' OR PLZ='" + ort + "';");
	        
	        if (!rs.next()) {
        		System.err.println("ERROR: Ivalid town name or zip code");
        		System.exit(-1);
        	} else {
        		ortschaft1 = rs.getString("Ortschaft");
        		plz1 = rs.getString("PLZ");
        		kanton1 = rs.getString("Kanton");
        		koordinate_b1 = rs.getInt("Koordinate_B");
        		koordinate_l1 = rs.getInt("Koordinate_L");
        	}
	        
	        System.out.print("Enter the second city: ");
			ort = sc.nextLine();
			stmnt = conn.createStatement();
	        rs = stmnt.executeQuery("SELECT * FROM tuesday_challenge.ortschaften WHERE Ortschaft='" + ort + "' OR PLZ='" + ort + "';");
			
        	if (!rs.next()) {
        		System.err.println("ERROR: Ivalid town name or zip code");
        		System.exit(-1);
        	} else {
        		ortschaft2 = rs.getString("Ortschaft");
        		plz2 = rs.getString("PLZ");
        		kanton2 = rs.getString("Kanton");
        		koordinate_b2 = rs.getInt("Koordinate_B");
        		koordinate_l2 = rs.getInt("Koordinate_L");
        	}
        	
	        System.out.println("These two towns are located " + (Math.round(Math.sqrt((Math.pow((koordinate_l1 - koordinate_l2), 2) + Math.pow((koordinate_b1 - koordinate_b2), 2)))) / 1000.000) + "km apart.");
	        
	        System.exit(0);
	        
	        
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			sc.close();
		}
	}
}