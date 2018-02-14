package com.techelevator.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techelevator.database.GameDAO;
import com.techelevator.database.UserDAO;
import com.techelevator.pojo.Game;
import com.techelevator.pojo.User;

@Controller
@SessionAttributes("currentUser")
public class UserController {

	private UserDAO userDAO;
	private GameDAO gameDAO;

	@Autowired
	public UserController(UserDAO userDAO, GameDAO gameDAO) {
		this.userDAO = userDAO;
		this.gameDAO = gameDAO;
	}

	@RequestMapping(path="/register", method=RequestMethod.GET)
	public String displayNewUserForm() {
		return "register";
	}
	
	@RequestMapping(path="/register", method=RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute User newUser, ModelMap model, HttpSession session) {
		userDAO.saveUser(newUser.getUserName(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName());
		User user = new User();
		user = userDAO.getUserByUsername(newUser.getUserName());
		session.invalidate();
		model.put("currentUser", user);
		return "redirect:/users/dashboard";
	}
	
	@RequestMapping(path="/users/dashboard", method=RequestMethod.GET)
	public String displayDashboard(ModelMap model, HttpSession session) {
		session.removeAttribute("selectedGame");
		User currentUser = (User)session.getAttribute("currentUser");
		if(currentUser != null) {
			long userId = currentUser.getUserId();
			if(!gameDAO.getAllGamesForUser(userId).isEmpty()){
				List<Game> games = gameDAO.getAllGamesForUser(userId);
				
				boolean isCurrentGame = false;
				LocalDate currentDate = LocalDate.now();
				for(Game game : games) {
					if(currentDate.isAfter(game.getEndDate())) {
						isCurrentGame = false;
					} else {
						isCurrentGame = true;
					}
					game.setCurrentGame(isCurrentGame);
				}
				model.put("userGames", games);
				
				
			}
			return "userDashboard";
		} else {
			return "redirect:/login";
		}

	}
	
	@RequestMapping(path="/users/changePassword", method=RequestMethod.GET)
	public String displayChangePasswordForm(ModelMap model, HttpSession session) {
		User currentUser = (User)session.getAttribute("currentUser");
		if(currentUser != null) {
			return "changePassword";			
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(path="/users/changePassword", method=RequestMethod.POST)
	public String changePassword(HttpSession session, @RequestParam(required=false) String password) {
		User currentUser = (User)session.getAttribute("currentUser");
		if(currentUser != null) {
			userDAO.updatePassword(currentUser.getUserName(), password);
			return "redirect:/users/dashboard";			
		} else {
			return "redirect:/login";
		}
	}
}
