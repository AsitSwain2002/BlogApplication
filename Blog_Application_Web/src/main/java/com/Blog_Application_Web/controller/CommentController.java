package com.Blog_Application_Web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.Blog_Application_Web.Dto.CommentDto;
import com.Blog_Application_Web.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/comment")
	public RedirectView saveComment(@ModelAttribute CommentDto commentDto, @RequestParam int userId,
			@RequestParam int postId, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			commentService.saveComment(commentDto, userId, postId);
			List<CommentDto> allCommnents = commentService.fetchAllCommentBy(postId);
			session.setAttribute("comments", allCommnents);
			return new RedirectView("/postPage?id=" + postId);

		} else {
			return new RedirectView("/loginPage");
		}
	}

	@GetMapping("/deleteComment")
	public String deleteComment(@RequestParam int id, @RequestParam int postId) {
	    commentService.deleteCommentById(id);
	    return "redirect:/postPage?id=" + postId;
	}
}
