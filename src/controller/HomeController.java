package controller;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Gift;

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
import dto.HomeGiftDTO;

@Controller
public class HomeController {
	
	@Autowired
	private GiftDAO giftDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	@Transactional(readOnly=false)
	@ResponseBody
	public List<HomeGiftDTO> home(@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if(Security.existHash(hash, userDAO)){
				List<Gift> giftList = giftDAO.giftsHome();
				List<HomeGiftDTO> homeGiftDTOList = new ArrayList<HomeGiftDTO>();
				String myUsername = Security.getUsername(hash);
				for (Gift gift: giftList){
					HomeGiftDTO homeGiftDTO = new HomeGiftDTO();
					homeGiftDTO.setId(gift.getId());
					homeGiftDTO.setName(gift.getName());
					homeGiftDTO.setPublicHashtags(gift.getPublicHashtags());
					homeGiftDTO.setAnonymous(gift.isAnonymous());
					if (!gift.isAnonymous()){
						homeGiftDTO.setUsername(gift.getUsername());
					}
					if (myUsername.equals(gift.getUsername())){
						homeGiftDTO.setMine(true);
					}
					else{
						homeGiftDTO.setCopied(giftDAO.isCopied(gift.getId(), myUsername));
					}
					homeGiftDTOList.add(homeGiftDTO);
				}
				return homeGiftDTOList;
			}
			else{
				throw new InvalidParameterException();
			}
		}
		else{
			throw new InvalidParameterException();
		}	
	}
	
	@RequestMapping(value="/gifts_copy/create/{id}", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	@ResponseBody
	public int copy(@PathVariable int id, @RequestParam(value="privateNotes", required=false) String privateNotes,
			@RequestParam(value="privateHashtags", required=false) String privateHashtags, @RequestParam(value="gotIt", required=true) boolean gotIt,
			@RequestParam(value="delivered", required=true) boolean delivered,
			@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if(Security.existHash(hash, userDAO)){
				Timestamp startDate = new Timestamp(System.currentTimeMillis());
				Gift gift = giftDAO.get(id);
				int copiedGift = giftDAO.create(gift.getName(), gift.getImage(), gift.getDescription(), gift.getUrls(), gift.getShop(), gift.getPrice(), gift.getPublicHashtags(), 
						StringUtils.isEmptyThenNull(privateNotes), StringUtils.isEmptyThenNull(privateHashtags), gotIt, delivered, false, false, startDate, null, null, gift.getId(), Security.getUsername(hash));
				return copiedGift;
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
