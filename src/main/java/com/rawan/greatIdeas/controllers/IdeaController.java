package com.rawan.greatIdeas.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rawan.greatIdeas.models.Idea;
import com.rawan.greatIdeas.models.User;
import com.rawan.greatIdeas.services.IdeaService;
import com.rawan.greatIdeas.services.UserService;

@Controller
public class IdeaController {
	private IdeaService ideaService;
	public IdeaController(IdeaService ideaService) {
		this.ideaService = ideaService;

	}
	
	@Autowired
	private  UserService userService;
	
	@RequestMapping("/ideas/new")
	public String createForm(Model model, @ModelAttribute("idea") Idea idea) {
		return "new.jsp";

	}

	@RequestMapping(value = "/ideas/new", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "new.jsp";
		}
		Long uID = (Long) session.getAttribute("userId");
		String userName = userService.findUserById(uID).getName();
		idea.setCreatedBy(userName);
		idea.setLikes(0);
		ideaService.createIdea(idea);
		return "redirect:/ideas";
	}

	// show Idea
	@RequestMapping(value = "/ideas/{id}")
	public String show(@PathVariable("id") Long id, Model model, HttpSession session) {
		Idea idea = ideaService.findIdea(id);
		Long uID = (Long) session.getAttribute("userId");
		User user = userService.findUserById(uID);
		model.addAttribute("user", user);
		model.addAttribute("idea", idea);
		return "show.jsp";
	}

	@RequestMapping(value = "/ideas/{id}/delete", method = RequestMethod.POST)
	public String destroy(@PathVariable("id") Long id) {
		ideaService.deleteIdea(id);
		return "redirect:/ideas";
	}

	@RequestMapping(value = "/ideas/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		Idea idea = ideaService.findIdea(id);
		model.addAttribute("idea", idea);
		return "edit.jsp";
	}

	@RequestMapping(value = "/ideas/{id}/edit", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, HttpSession session, @PathVariable("id") Long id) {
		if (result.hasErrors()) {
			return "edit.jsp";
		} else {
			Idea i = ideaService.findIdea(id);
			Long uID = (Long) session.getAttribute("userId");
			String userName = userService.findUserById(uID).getName();
			if (! i.getCreatedBy().equals(userName))
				return "redirect:/ideas";
			i.setTitle(idea.getTitle());
			ideaService.updateIdea(i);
			return "redirect:/ideas/" + i.getId();
		}
	}
	
	@RequestMapping(value = "/ideas/{id}/like")
	public String like(@PathVariable("id") Long id, Model model,HttpSession session) {
		Idea idea = ideaService.findIdea(id);
		Long UID=(Long) session.getAttribute("userId");
		User user = userService.findUserById(UID);
    	List<User> users =idea.getUsers();
    	idea.setLikes(idea.getLikes()+1);
    	users.add(user);
    	idea.setUsers(users);
		ideaService.updateIdea(idea);
		return "redirect:/ideas";
		}
	
	@RequestMapping(value = "/ideas/{id}/Unlike")
	public String Unlike(@PathVariable("id") Long id, Model model,HttpSession session) {
		Idea idea = ideaService.findIdea(id);
		User user = userService.findUserById((Long) session.getAttribute("userId"));
    	List<User> users =idea.getUsers();
    	idea.setLikes(idea.getLikes()-1);
    	users.remove(user);
    	idea.setUsers(users);
		ideaService.updateIdea(idea);
		return "redirect:/ideas";
		}


}
