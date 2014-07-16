package utils;

public class StringUtils {
	
	/**
	 * Checks if the string is not empty or null
	 * @param string - the string to check 
	 * @return true if the string is not empty or null, otherwise false
	 */
	public static boolean isNotEmpty(String string){
		if (string != null && !string.trim().isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks if the string is empty, if it is true then returns null
	 * @param string - the string to check 
	 * @return null if the string is empty, otherwise its value
	 */
	public static String isEmptyThenNull(String string){
		if (string != null && string.trim().isEmpty()){
			return null;
		}
		else{
			return string;
		}			
	}

}
