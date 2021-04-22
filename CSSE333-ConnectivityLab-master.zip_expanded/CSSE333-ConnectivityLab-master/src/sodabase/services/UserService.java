package sodabase.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		//TODO: Complete this method.
		String query="Select PasswordSalt, PasswordHash From [User] WHERE Username=?";
		PreparedStatement ps=null;

		if(this.dbService!=null) {
	        try {
				this.dbService.getConnection().setAutoCommit(false);
				ps = this.dbService.getConnection().prepareStatement(query);
				ps.setString(1, username);
		        ResultSet rs = ps.executeQuery();
		        byte[] passSalt=null;
		        String passHash=null;
		        while (rs.next()) {
		        	passSalt=rs.getBytes("PasswordSalt");
		        	passHash=rs.getString("PasswordHash");
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
		    } 

		return false;
	}

	public boolean register(String username, String password) {
		//Done: Task 6
		byte[] passS = getNewSalt();
		String hashPass=hashPassword(passS, password);
		CallableStatement cs=null;
		
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = CALL Register( ? , ? , ? )}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setBytes(3, passS);
			cs.setString(4, hashPass);

			cs.execute();
			int returnValue=cs.getInt(1);
			if(returnValue>=1) {
				 JOptionPane.showMessageDialog(null, "Registration Failed");
				 return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println("The system ran into a problem.");
			e.printStackTrace();
			return false;
		}
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {
		System.out.println(salt);
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
