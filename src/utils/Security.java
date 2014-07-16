package utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import dao.UserDAO;


public class Security {
	
	public static boolean existHash(String hash, UserDAO userDAO) throws IOException{
		String[] splitHash = getDecodedHash(hash);
		String username = splitHash[0];
		String encodedPassword = splitHash[1];
		return userDAO.checkUsernameAndPassword(username, encodedPassword);
	}
	
	public static String getUsername(String hash) throws IOException{
		String[] splitHash = getDecodedHash(hash);
		String username = splitHash[0];
		return username;
	}
	
	private static String[] getDecodedHash(String hash) throws IOException{
		byte[] decoded = new BASE64Decoder().decodeBuffer(hash);
		String decodedHash = new String(decoded);
		String[] splitHash = decodedHash.split(":");
		return splitHash;
	}
}
