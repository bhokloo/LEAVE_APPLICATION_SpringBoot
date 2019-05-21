package edu.iss.nus.laps.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.iss.nus.laps.model.Users;
import edu.iss.nus.laps.repository.LapsRepository;

@Controller
@SessionAttributes("userdetails")
public class MainController {
	
	@Autowired
	private LapsRepository repo;
	
	
	@GetMapping("/login")
    public String login(Model model) {
		model.addAttribute("admin", new Users());
        return "login";
    }

	@GetMapping("/logout")
    public String logout(Model model, HttpSession session) {
		session.removeAttribute("userdetails");
		model.addAttribute("admin", new Users());
        return "login";
    }


	@RequestMapping(path="/admin", method = RequestMethod.POST)
    public ModelAndView user(@ModelAttribute Users admin) {
	    List<Users> userdetails = repo.findByUsername(admin.getUsername());
	    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!" + userdetails);
	    ModelAndView model = new ModelAndView("login");
	    if(!userdetails.isEmpty())
	    {
		    for(Users al : userdetails)
			{
		    	if(al.getRole_name().equals("admin") || al.getRole_name().equals("manager"))
		    	{
		    		 List<Users> plist= repo.findAll();
		    		 model.addObject("plist",plist);
		    		 model.setViewName("admin");
		    	}
		    	else
		    	{
		    		List<Users> plist= repo.findByUsername(al.getUsername());
		    		model.addObject("plist",plist);
		    		model.setViewName("employee");
		    	}
			}
		 
		    model.addObject("userdetails",userdetails);
		    return model;
	    }
	    model.addObject("admin", new Users());
    	return model;
    
    }
	

	@RequestMapping(path="/adduser", method = RequestMethod.POST)
    public ModelAndView adduser(@SessionAttribute("userdetails") Users userdetails,@ModelAttribute Users users) {
		repo.save(users);
	    ModelAndView modelview= new ModelAndView("admin");
	    List<Users> plist= repo.findAll();
	    modelview.addObject("plist",plist);
	    modelview.addObject("userdetails",userdetails);
		return modelview;
    }
	
	@GetMapping("delete/{username}")
	public ModelAndView deleteuser(@SessionAttribute("userdetails") Users userdetails,@PathVariable("username") String username)
	{
		List<Users> deleteuser = repo.findByUsername(username);
		for(Users al : deleteuser)
		{
			 repo.delete(al);
		}
		ModelAndView modelview= new ModelAndView("admin");
		List<Users> plist= repo.findAll();
		modelview.addObject("plist",plist);
	    modelview.addObject("userdetails",userdetails);
		return modelview;
		
	}
	
	
	@PostMapping("/{username}")
	public ModelAndView edituser(@SessionAttribute("userdetails") Users userdetails,@ModelAttribute Users d,@PathVariable("username") String username)
	{
		repo.modify(d.getUsername(), d.getEmpname(), d.getRole_name(), d.getPassword(), username);
		ModelAndView modelview= new ModelAndView("admin");
		List<Users> plist= repo.findAll();
		modelview.addObject("plist",plist);
	    modelview.addObject("userdetails",userdetails);
		return modelview;
		
	}
	
}
