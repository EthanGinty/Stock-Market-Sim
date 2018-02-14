package com.techelevator.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techelevator.database.UserDAO;
import com.techelevator.pojo.User;

@Controller
@SessionAttributes("currentUser")
public class AuthenticationController {

	private UserDAO userDAO;

	@Autowired
	public AuthenticationController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@RequestMapping(path="/login", method=RequestMethod.GET)
	public String displayLoginForm() {
		return "login";
	}
	
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public String login(@RequestParam String userName, 
						@RequestParam String password, 
						@RequestParam(required=false) String destination,
						ModelMap model, HttpSession session) {
		if(userDAO.searchForUsernameAndPassword(userName, password)) {
			session.invalidate();
			User user = new User();
			user = userDAO.getUserByUsername(userName);
			Map<Long, BigDecimal> userAvailableBalances = userDAO.getAvailableBalancesByUserId(user.getUserId());
			user.setAvailableBalanceForGames(userAvailableBalances);
			model.put("currentUser", user);
			if(destination != null && !destination.isEmpty() && destination.startsWith("http://localhost:8080/capstone/")) {
				return "redirect:" + destination;
			} else {
				return "redirect:/users/dashboard";				
			}
		} else {
			return "redirect:/badLogin";
		}
	}
	
	@RequestMapping(path="/badLogin", method=RequestMethod.GET)
	public String displayBadLogin() {
		return "badLogin";
	}

	@RequestMapping(path="/logout", method=RequestMethod.POST)
	public String logout(ModelMap model, HttpSession session) {
		model.remove("currentUser");
		session.removeAttribute("currentUser");
		session.removeAttribute("selectedGame");
		return "redirect:/";
	}
}
