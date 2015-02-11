package com.github.fromi.openidconnect;

import java.security.Principal;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.fromi.openidconnect.security.UserInfo;

@Controller
public class SampleSecuredController extends WebMvcConfigurerAdapter {
    @Resource
    private OAuth2RestOperations restTemplate;
	
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");

    }
    
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
    
	@RequestMapping("/test")
    public ModelAndView test(Principal principal) {
		 Object auth = ((Authentication) principal)
		.getPrincipal();
        return new ModelAndView("test");
    }
    
    @RequestMapping("/googleconnect")
    public String googleconnect() {
        return "redirect:/test";

    }
}
