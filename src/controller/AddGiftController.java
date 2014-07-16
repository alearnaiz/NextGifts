package controller;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.Security;
import utils.StringUtils;
import dao.GiftDAO;
import dao.UserDAO;

@Controller
public class AddGiftController {
	
	@Autowired
	private GiftDAO giftDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/gifts/create", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	@ResponseBody
	public void create(@RequestParam(value="name", required=true) String name, 
			@RequestParam(value="image", required=false) String image, @RequestParam(value="description", required=false) String description,
			@RequestParam(value="urls", required=false) String urls, @RequestParam(value="shop", required=false) String shop, @RequestParam(value="price", required=false) Double price, 
			@RequestParam(value="publicHashtags", required=false) String publicHashtags, @RequestParam(value="privateNotes", required=false) String privateNotes,
			@RequestParam(value="privateHashtags", required=false) String privateHashtags, @RequestParam(value="gotIt", required=true) boolean gotIt,
			@RequestParam(value="delivered", required=true) boolean delivered, 
			@RequestParam(value="spread", required=true) boolean spread,
			@RequestParam(value="anonymous", required=true) boolean anonymous, 
			@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if(Security.existHash(hash, userDAO)){
				if (StringUtils.isNotEmpty(name)){
					Timestamp startDate = new Timestamp(System.currentTimeMillis());
					Timestamp spreadDate = null;
					if (spread && gotIt && delivered){
						spreadDate = new Timestamp(System.currentTimeMillis());
					}
					giftDAO.create(name, StringUtils.isEmptyThenNull(image), StringUtils.isEmptyThenNull(description), StringUtils.isEmptyThenNull(urls), StringUtils.isEmptyThenNull(shop), 
							price, StringUtils.isEmptyThenNull(publicHashtags), StringUtils.isEmptyThenNull(privateNotes), 
							StringUtils.isEmptyThenNull(privateHashtags), gotIt, delivered, spread, anonymous, startDate, null, spreadDate, null, Security.getUsername(hash));
				}
				else{
					throw new InvalidParameterException();
				}
			}
			else{
				throw new InvalidParameterException();
			}
		}
		else{
			throw new InvalidParameterException();
		}	
	}
	
}
