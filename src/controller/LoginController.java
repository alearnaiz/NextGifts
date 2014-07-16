package controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;
import utils.Security;
import utils.StringUtils;
import dao.UserDAO;
import dto.UserDTO;

@Controller
public class LoginController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	@Transactional(readOnly=true)
	@ResponseBody
	public UserDTO login(@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password){
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)){
			String encodedPassword = new BASE64Encoder().encode(password.getBytes());
			if(userDAO.checkUsernameAndPassword(username, encodedPassword)){
				String noEncodedHash = username+":"+encodedPassword;
				String hash = new BASE64Encoder().encode(noEncodedHash.getBytes());
				return new UserDTO(username, hash);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	@RequestMapping(value="/checkhash", method=RequestMethod.GET)
	@Transactional(readOnly=true)
	@ResponseBody
	public String checkHash(@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if(Security.existHash(hash, userDAO)){
				return Security.getUsername(hash);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
}
