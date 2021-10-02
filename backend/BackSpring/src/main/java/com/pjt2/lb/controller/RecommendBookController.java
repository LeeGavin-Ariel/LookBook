package com.pjt2.lb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pjt2.lb.common.auth.LBUserDetails;
import com.pjt2.lb.common.response.BaseResponseBody;
import com.pjt2.lb.entity.User;
import com.pjt2.lb.response.BookListInfoRes;
import com.pjt2.lb.response.UserInfoGetRes;
import com.pjt2.lb.service.RecommendBookService;

@CrossOrigin(
        origins = {"http://localhost:3000", "https://j5a502.p.ssafy.io/"},
        allowCredentials = "true", 
        allowedHeaders = "*", 
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.OPTIONS}
)
@RequestMapping("/recommends")
@RestController
public class RecommendBookController {
	
	@Autowired
	RecommendBookService recommendBookService;

	@GetMapping("/user")
	public ResponseEntity<?> geuserBasedCFList(Authentication authentication) {
		try {
			User user;
			
			LBUserDetails userDetails = (LBUserDetails) authentication.getDetails(); 
			user = userDetails.getUser();

			List<BookListInfoRes> userBasedCFList = recommendBookService.getUserBasedCFListInfo(user.getUserEmail(), 20);

			Map<String, List> map = new HashMap<String, List>();
			map.put("userBasedCFList", userBasedCFList);
			
			return ResponseEntity.status(200).body(map);
		} catch(NullPointerException e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(new UserInfoGetRes(400, "만료된 토큰입니다."));
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(new UserInfoGetRes(500, "Internal Server Error"));
		}
	}

	
}
