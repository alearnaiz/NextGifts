package controller;

import java.security.InvalidParameterException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;
import utils.StringUtils;
import dao.UserDAO;
import dto.UserDTO;

@Controller
public class RegisterController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	@ResponseBody
	public UserDTO registerUser(@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastNames", required=true) String lastNames,
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password){
		if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastNames) 
				&& StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)){
			if (!userDAO.checkUsername(username)){
				String encodedPassword = new BASE64Encoder().encode(password.getBytes());
				Timestamp startDate = new Timestamp(System.currentTimeMillis());
				userDAO.create(firstName, lastNames, username, encodedPassword, startDate);
				String noEncodedHash = username+":"+encodedPassword;
				String hash = new BASE64Encoder().encode(noEncodedHash.getBytes());
				return new UserDTO(username, hash);
			}
			else{
				return null;
			}
		}
		else{
			throw new InvalidParameterException();
		}
	}
}
