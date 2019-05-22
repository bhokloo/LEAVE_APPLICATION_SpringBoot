package edu.iss.nus.laps.controller;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import edu.iss.nus.laps.model.Leaveapplication;
import edu.iss.nus.laps.model.Users;
import edu.iss.nus.laps.repository.LapsRepository;
import edu.iss.nus.laps.repository.LeaveRepository;


@Controller
@SessionAttributes("userdetails")
public class MainController {


	public static ArrayList<String> arraylist = new ArrayList<String>() {
		{
	    add("2019-05-28");
		add("2019-06-05");
		add("2019-06-11");
		add("2019-06-14");
		add("2019-06-27");
		add("2019-07-03");
		add("2019-07-05");
		}

	};

	

	

	@Autowired
	private LapsRepository repo;

	@Autowired
	private LeaveRepository leaverepo;

	
	@GetMapping("/login")
    public ModelAndView login(Users users) {
        return new ModelAndView("login");
    }
	
	
	@GetMapping("/admin")
    public String admin(Model model,@RequestParam(value="username") String username) {
		System.out.println("++++++++++++++++++++++++++" + model);
		System.out.println("++++++++++++++++++++++++++" + username);
		 Users userdetails = repo.findByUsername(username);
		 List<Users> plist= repo.findAll();
		 model.addAttribute("plist",plist);
		 model.addAttribute("userdetails",userdetails);
         return "admin";
	}
	
	
	@GetMapping("/manager")
    public String manager(Model model,@RequestParam(value="username") String username) {
		
		 List<Leaveapplication> newlist= leaverepo.findAll();
		 Users userdetails = repo.findByUsername(username);
		 model.addAttribute("newlist",newlist);
		 model.addAttribute("userdetails",userdetails);
		 model.addAttribute("arraylist",arraylist);
         return "manager";
    }
	
	
	@GetMapping("/employee")
    public String employee(Model model,@RequestParam(value="username") String username) {
		List<Leaveapplication> leaveplist= leaverepo.findByUsername(username);
		Users userdetails = repo.findByUsername(username);
		model.addAttribute("leaveplist",leaveplist);
		model.addAttribute("userdetails",userdetails);
		model.addAttribute("arraylist",arraylist);
        return "employee";
    }


	@PostMapping("/user")
    public String user(@Valid Users users,BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) 
			return "login";

		Users userdetails = repo.findByUsername(users.getUsername());
	    if(userdetails != null)
	    {
		    	if(userdetails.getRole_name().equals("admin"))
		    		 return "redirect:admin?username="+users.getUsername();

		    	else if(userdetails.getRole_name().equals("manager"))
		    		return "redirect:manager?username="+users.getUsername();

		    	else
		    		return "redirect:employee?username="+users.getUsername();
	    }
	    else
    	return "login";   
	}
	
	
	@GetMapping("/logout")
    public String logout(Users users, HttpSession session) {
		session.removeAttribute("userdetails");
        return "login";
    }


	@PostMapping("/adduser")
    public String adduser(Users users) {
		repo.save(users);		
		return "redirect:admin?username="+users.getUserdetails();
    }
	
	
	@PostMapping("/delete")
	public ModelAndView deleteuser(Users users)
	{
		 repo.delete(users.getUsername());
		 return null;

	}
	
	
	@RequestMapping(path="/statusofapplication/{leaveid}", method = RequestMethod.POST)
    public ModelAndView statusofapplication(@SessionAttribute("userdetails") Users userdetails,@ModelAttribute Leaveapplication lapp,@PathVariable("leaveid") int leaveid) {

		 leaverepo.leaveapplication(lapp.getManagerComment(), lapp.getRadio(), leaveid);
		 ModelAndView model = new ModelAndView("manager");
		 List<Leaveapplication> newlist= leaverepo.findAll();
		 model.addObject("userdetails",userdetails);
		 model.addObject("newlist",newlist);
		 return model;
	}

	

	

	

	



	

	

	

	

	

	@PostMapping("/{username}")

	public ModelAndView edituser(@SessionAttribute("userdetails") Users userdetails,@ModelAttribute Users d,@PathVariable("username") String username)

	{

		repo.modify(d.getUsername(), d.getEmpname(), d.getRole_name(), d.getPassword(), username);

		//leaverepo.modify(d.getUsername(),username);

		ModelAndView modelview= new ModelAndView("admin");

		List<Users> plist= repo.findAll();

		modelview.addObject("plist",plist);

	    modelview.addObject("userdetails",userdetails);

		return modelview;

		

	}

	

	@GetMapping("dateadd")

	public ModelAndView adddynamicdate(@SessionAttribute("userdetails") Users userdetails,@ModelAttribute Leaveapplication lapp,@RequestParam(name ="newdate",required=false) String newdate) {

	

		if(newdate != null)

		{

			arraylist.add(newdate);

		}

		ModelAndView modelview= new ModelAndView("manager");

		List<Leaveapplication> newlist= leaverepo.findAll();

	    modelview.addObject("userdetails",userdetails);

	    modelview.addObject("newlist",newlist);

	    modelview.addObject("arraylist",arraylist);

		return modelview;

	}

	

	

	@RequestMapping(path="/leaveapplication", method = { RequestMethod.POST })

	public ModelAndView leaveapplication(@Valid Leaveapplication leaveapp,BindingResult bindingResult, @SessionAttribute("userdetails") Users userdetails,@ModelAttribute Leaveapplication lapp,@RequestParam(name ="leaveid",required=false) Integer leaveid)

	{



		ModelAndView modelview= new ModelAndView("employee");

		

		if(bindingResult.hasErrors())

		{

			List<Leaveapplication> leaveplist= leaverepo.findByUsername(userdetails.getUsername());

			System.out.println(bindingResult.toString());

			modelview.addObject("leaveplist",leaveplist);

			return modelview;

		}



		if(lapp.getStartDate() != null)

		{

		LocalDate startDate = leaveapp.getStartDate();

		LocalDate endDate = leaveapp.getEndDate();

		boolean noStop = true;

		int count = 0;

		int all = 0;

		int weekends = 0;

		int pubholidays = 0;

		if (endDate.getMonthValue() >= startDate.getMonthValue()){

			while (noStop) {

				if (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {

					weekends++;

					all++;

				}

			    else if(arraylist.contains(startDate.toString())){

			    	all++;

			    	pubholidays++;

			    }

				else

				{

					count++;

					all++;

				}

				if (startDate.getDayOfMonth() == endDate.getDayOfMonth() && startDate.getMonthValue() == endDate.getMonthValue()) {

					noStop = false;

				}

				startDate = startDate.plusDays(1);

			}

		}

		

		if(all <= 14)

		{

			lapp.setNoOfLeave(count);

		}

		else

		{

			lapp.setNoOfLeave(all);

			

		}

		

		lapp.setPublicholidays(pubholidays);

		lapp.setWeekends(weekends);

		lapp.setUsername(userdetails.getUsername());

		leaverepo.save(lapp);

		}

		

		List<Leaveapplication> leaveplist= leaverepo.findByUsername(userdetails.getUsername());

		modelview.addObject("leaveplist",leaveplist);

	    modelview.addObject("userdetails",userdetails);

	    modelview.addObject("arraylist",arraylist);

		return modelview;

		

	}

	

	

	@GetMapping("/deleteapp/{leaveid}")

	public ModelAndView deleteapp(@SessionAttribute("userdetails") Users userdetails,@PathVariable("leaveid") int leaveid)

	{

		List<Leaveapplication> deleteapplication = leaverepo.findByLeaveid(leaveid);

		for(Leaveapplication al : deleteapplication)

		{

			leaverepo.delete(al);

		}

		ModelAndView modelview= new ModelAndView("employee");

		List<Leaveapplication> leaveplist= leaverepo.findAll();

		modelview.addObject("leaveplist",leaveplist);

	    modelview.addObject("userdetails",userdetails);

	    modelview.addObject("arraylist",arraylist);

		return modelview;

		

	}

	



	@PostMapping("/editapp/{leaveid}")

	public ModelAndView editapp(@SessionAttribute("userdetails") Users userdetails,@ModelAttribute Leaveapplication d,@PathVariable("leaveid") int leaveid)

	{

		String leaveidd = Integer.toString(leaveid);

		if(leaveidd == null)

		{

			System.out.println(leaveidd);

		}

		else

		{

			System.out.println(leaveidd);

		}

		

		ModelAndView modelview= new ModelAndView("admin");

		List<Leaveapplication> leaveplist= leaverepo.findAll();

		modelview.addObject("leaveplist",leaveplist);

	    modelview.addObject("userdetails",userdetails);

	    modelview.addObject("arraylist",arraylist);

		return modelview;

		

	}

	

}