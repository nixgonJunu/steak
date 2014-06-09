package com.nixgon.steak;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class SteakForkController {
	private static final Logger log = Logger.getLogger( SteakForkController.class.getName() );

	@RequestMapping(value = "/login")
	public String loginUser() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if ( user != null ) {
			return "redirect:steak";
		} else {
			return "redirect:" + userService.createLoginURL( "../steak" );
		}
	}

	@RequestMapping(value = "/steak")
	public String steak() {

		return "steak";
	}
}
