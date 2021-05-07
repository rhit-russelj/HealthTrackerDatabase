
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class AppLogin {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();

	public static boolean login(String username, String password) {
		//TODO: Complete this method.
		String query="Select PasswordSalt, Hpassword From [HealthUser] WHERE Email=?";
		PreparedStatement ps=null;

	        try {
				Main.conn.getConnection().setAutoCommit(false);
				ps = Main.conn.getConnection().prepareStatement(query);
				ps.setString(1, username);
		        ResultSet rs = ps.executeQuery();
		        byte[] passSalt=null;
		        String passHash=null;
		        while (rs.next()) {
		        	passSalt=rs.getBytes("PasswordSalt");
		        	passHash=rs.getString("Hpassword");
		        }
		        String newHash="";
		        if(passSalt!=null) {
		        	newHash = hashPassword(passSalt, password);
		        }
		        if(newHash.equals(passHash)) {
		        	return true;
		        }
		        else {
					JOptionPane.showMessageDialog(null, "Login Failed");
					 return false;
		        }
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Login Failed");
				e.printStackTrace();
			
		    } 

		return false;
	}

	public static boolean register(String username, String sex, String dob, String email, String password) {
		//Done: Task 6
		byte[] passS = getNewSalt();
		String hashPass=hashPassword(passS, password);
		SimpleDateFormat unformattedDobString = new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat parsedDateStringFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date parsedDate;
		CallableStatement cs=null;
		
		try {
			java.util.Date unformatetedDob=unformattedDobString.parse(dob);
			String parsedDateString=parsedDateStringFormat.format(unformatetedDob);
			parsedDate=Date.valueOf(parsedDateString);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Date not valid");
			return false;
		}
		
		try {
			cs = Main.conn.getConnection().prepareCall("{ ? = CALL RegisterUser( ? , ? , ?, ?, ?, ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, sex);
			cs.setDate(4, parsedDate);
			cs.setString(5, email);
			cs.setString(6, hashPass);
			cs.setBytes(7, passS);

			cs.execute();
			int returnValue=cs.getInt(1);
			if(returnValue>=1) {
				 JOptionPane.showMessageDialog(null, "Registration Failed"+returnValue);
				 return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println("The system ran into a problem.");
			e.printStackTrace();
			return false;
		}
	}
	
	public static byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public static String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public static String hashPassword(byte[] salt, String password) {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
