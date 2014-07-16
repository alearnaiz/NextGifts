package controller;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import model.Gift;

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
import dto.MySimplifiedGiftDTO;

@RequestMapping("/gifts")
@Controller
public class MyGiftsController {
	
	@Autowired
	private GiftDAO giftDAO;

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	@Transactional(readOnly=true)
	@ResponseBody
	public List<MySimplifiedGiftDTO> getAllByUsername(
			@RequestParam(value="hash", required=true) String hash) throws IOException{
		if (StringUtils.isNotEmpty(hash)){
			if (Security.existHash(hash, userDAO)){
				List<MySimplifiedGiftDTO> mySimplifiedGiftDTOList = new ArrayList<MySimplifiedGiftDTO>();
				List<Gift> giftList = giftDAO.getAllByUsername(Security.getUsername(hash));
				for (Gift gift : giftList){
					MySimplifiedGiftDTO mySimplifiedGiftDTO = new MySimplifiedGiftDTO();
					mySimplifiedGiftDTO.setId(gift.getId());
					mySimplifiedGiftDTO.setName(gift.getName());
					mySimplifiedGiftDTO.setGotIt(gift.isGotIt());
					mySimplifiedGiftDTO.setDelivered(gift.isDelivered());
					mySimplifiedGiftDTO.setCopied(gift.getGiftId() != null);
					mySimplifiedGiftDTOList.add(mySimplifiedGiftDTO);
				}
				return mySimplifiedGiftDTOList;
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
