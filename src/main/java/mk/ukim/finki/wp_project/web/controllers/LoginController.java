package mk.ukim.finki.wp_project.web.controllers;


import mk.ukim.finki.wp_project.model.User;
import mk.ukim.finki.wp_project.model.exceptions.InvalidUsernameCredentialsException;
import mk.ukim.finki.wp_project.service.UserService;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(Model model){
        model.addAttribute("bodyContent","login");
        return "main_view";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model){
        User user = null;
        try {
            user = this.userService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        }catch (InvalidUsernameCredentialsException ex){
            model.addAttribute("hasError", true);
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }
}
