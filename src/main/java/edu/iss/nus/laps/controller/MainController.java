package edu.iss.nus.laps.controller;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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



	@GetMapping("/logout")

    public String logout(Users users, HttpSession session) {

		session.removeAttribute("userdetails");

        return "login";

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

	

	

	@RequestMapping(path="/user", method = RequestMethod.POST)

    public ModelAndView user(@Valid Users users,BindingResult bindingResult) {

		ModelAndView model = new ModelAndView("login");

		if (bindingResult.hasErrors()) 

		{ 

			return model;

		} 

	    List<Users> userdetails = repo.findByUsername(users.getUsername());

	    if(!userdetails.isEmpty())

	    {

		    for(Users al : userdetails)

			{

		    	if(al.getRole_name().equals("admin"))

		    	{

		    		 List<Users> plist= repo.findAll();

		    		 model.addObject("plist",plist);

		    		 model.setViewName("admin");

		    	}

		    	else if(al.getRole_name().equals("manager"))

		    	{

		    		 List<Leaveapplication> newlist= leaverepo.findAll();

		    		 model.addObject("newlist",newlist);

		    		 model.addObject("userdetails",userdetails);

		    		 model.addObject("arraylist",arraylist);

		    		 model.setViewName("manager");

		    	}

		    	

		    	else

		    	{

		    		List<Users> plist= repo.findByUsername(al.getUsername());

		    		List<Leaveapplication> leaveplist= leaverepo.findByUsername(users.getUsername());

		    		model.addObject("leaveplist",leaveplist);

		    		model.addObject("arraylist",arraylist);

		    		model.setViewName("employee");

		    	}

			}

		 

		    model.addObject("userdetails",userdetails);

		    return model;

	    }

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

	

	@GetMapping("/delete/{username}")

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