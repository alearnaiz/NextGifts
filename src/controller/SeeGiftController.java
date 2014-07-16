package controller;

import java.io.IOException;
import java.security.InvalidParameterException;

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
import dto.GiftDTO;
import dto.MyGiftDTO;
import dto.NotMyGiftDTO;

@Controller
public class SeeGiftController {
	
	@Autowired
	private GiftDAO giftDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/gifts/{id}", method=RequestMethod.GET)
	@Transactional(readOnly=false)
	@ResponseBody
	public GiftDTO getGift(@PathVariable int id, 
			@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if(Security.existHash(hash, userDAO)){
				Gift gift = giftDAO.get(id);
				String myUsername = Security.getUsername(hash);
				if (myUsername.equals(gift.getUsername())){
					// Own gift
					MyGiftDTO myGiftDTO = new MyGiftDTO();
					myGiftDTO.setName(gift.getName());
					myGiftDTO.setDescription(gift.getDescription());
					myGiftDTO.setUrls(gift.getUrls());
					myGiftDTO.setShop(gift.getShop());
					myGiftDTO.setPrice(gift.getPrice());
					myGiftDTO.setPublicHashtags(gift.getPublicHashtags());
					myGiftDTO.setAnonymous(gift.isAnonymous());
					myGiftDTO.setUsername(gift.getUsername());
					myGiftDTO.setYours(true);
					myGiftDTO.setPrivateNotes(gift.getPrivateNotes());
					myGiftDTO.setPrivateHashtags(gift.getPrivateHashtags());
					myGiftDTO.setGotIt(gift.isGotIt());
					myGiftDTO.setDelivered(gift.isDelivered());
					myGiftDTO.setSpread(gift.isSpread());
					myGiftDTO.setCopied(gift.getGiftId() != null);
					return myGiftDTO;
				}
				else{
					// It is not my gift
					NotMyGiftDTO notMyGiftDTO = new NotMyGiftDTO();
					notMyGiftDTO.setName(gift.getName());
					notMyGiftDTO.setDescription(gift.getDescription());
					notMyGiftDTO.setUrls(gift.getUrls());
					notMyGiftDTO.setShop(gift.getShop());
					notMyGiftDTO.setPrice(gift.getPrice());
					notMyGiftDTO.setPublicHashtags(gift.getPublicHashtags());
					if (gift.isAnonymous()){
						notMyGiftDTO.setAnonymous(true);
					}
					else{
						notMyGiftDTO.setUsername(gift.getUsername());
					}
					notMyGiftDTO.setYours(false);
					notMyGiftDTO.setCopied(giftDAO.isCopied(gift.getId(), myUsername));
					return notMyGiftDTO;
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
