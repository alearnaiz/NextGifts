package controller;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.Security;
import utils.StringUtils;
import dao.GiftDAO;
import dao.UserDAO;

@Controller
public class EditGiftController {
	
	@Autowired
	private GiftDAO giftDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/gifts/edit/{id}", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	@ResponseBody
	public void edit(@PathVariable int id, @RequestParam(value="privateNotes", required=false) String privateNotes,
			@RequestParam(value="privateHashtags", required=false) String privateHashtags, @RequestParam(value="gotIt", required=true) boolean gotIt,
			@RequestParam(value="delivered", required=true) boolean delivered,
			@RequestParam(value="spread", required=true) boolean spread,
			@RequestParam(value="anonymous", required=true) boolean anonymous, 
			@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if(Security.existHash(hash, userDAO)){
				Timestamp spreadDate = null;
				if (spread && gotIt && delivered && !giftDAO.isSpread(id)){
					spreadDate = new Timestamp(System.currentTimeMillis());
					giftDAO.update(id, StringUtils.isEmptyThenNull(privateNotes), StringUtils.isEmptyThenNull(privateHashtags), gotIt, delivered, spread, anonymous, spreadDate, true);
				}
				else if ((!spread || !gotIt || !delivered) && giftDAO.isSpread(id)){
					giftDAO.update(id, StringUtils.isEmptyThenNull(privateNotes), StringUtils.isEmptyThenNull(privateHashtags), gotIt, delivered, spread, anonymous, spreadDate, true);
				}
				else{
					giftDAO.update(id, StringUtils.isEmptyThenNull(privateNotes), StringUtils.isEmptyThenNull(privateHashtags), gotIt, delivered, spread, anonymous, spreadDate, false);
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
	
	@RequestMapping(value="/gifts_copy/edit/{id}", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	@ResponseBody
	public void editCopy(@PathVariable int id, @RequestParam(value="privateNotes", required=false) String privateNotes,
			@RequestParam(value="privateHashtags", required=false) String privateHashtags, @RequestParam(value="gotIt", required=true) boolean gotIt,
			@RequestParam(value="delivered", required=true) boolean delivered, @RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if(Security.existHash(hash, userDAO)){
				giftDAO.updatePrivateStuff(id, StringUtils.isEmptyThenNull(privateNotes), StringUtils.isEmptyThenNull(privateHashtags), gotIt, delivered);
			}
			else{
				throw new InvalidParameterException();
			}
		}
		else{
			throw new InvalidParameterException();
		}	
	}
	
	@RequestMapping(value="/gifts/delete/{id}", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	@ResponseBody
	public void delete(@PathVariable int id,
			@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if (Security.existHash(hash, userDAO)){
				giftDAO.updateEndDate(id, new Timestamp(System.currentTimeMillis()));
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
