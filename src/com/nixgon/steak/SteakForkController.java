package com.nixgon.steak;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.nixgon.steak.model.SteakModel;
import com.nixgon.steak.model.SteakPipelineModel;
import com.nixgon.steak.model.SteakStageModel;

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
	public String steak( ModelMap model ) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		return "steak";
	}
}
