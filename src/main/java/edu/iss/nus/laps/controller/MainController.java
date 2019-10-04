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
import edu.iss.nus.laps.model.Manager;
import edu.iss.nus.laps.model.Users;

import edu.iss.nus.laps.repository.LapsRepository;

import edu.iss.nus.laps.repository.LeaveRepository;
import edu.iss.nus.laps.repository.ManagerRepo;





@Controller

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

	@Autowired
	private ManagerRepo managerrepo;
	

	@GetMapping("/login")

    public ModelAndView login(Users users) {

        return new ModelAndView("login");

    }

	

	

	@GetMapping("/admin")

    public String admin(Model model,@RequestParam(value="username") String username) {

		 Users userdetails = repo.findByUsername(username);

		 List<Users> plist= repo.findAll();
		 
		 //List<Manager> managerlist = managerrepo.findAll();

		 model.addAttribute("plist",plist);
		 
		 //model.addAttribute("managerlist",managerlist);

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

    public String logout(Users users) {

        return "login";

    }



	

	//___________________________________ADMIN___________________________________________

	



	@PostMapping("/adduser")

    public String adduser(Users users) {

		repo.save(users);		

		return "redirect:admin?username="+users.getUserdetails();

    }

	

	

	@PostMapping("/delete")

	public String deleteuser(Users users)

	{

		List<Leaveapplication> deleteuser = leaverepo.findByUsername(users.getUsername());

		for(Leaveapplication al : deleteuser)

		{

			leaverepo.delete(al);

		}

		repo.delete(users.getUsername());



	    return "redirect:admin?username="+users.getUserdetails();

	}

	

	

	@PostMapping("/update")

	public String edituser(Users users)

	{

		 repo.modify(users.getEmpname(), users.getRole_name(), users.getPassword(),users.getUsername());

		 return "redirect:admin?username="+users.getUserdetails();

	}

	

	

	//____________________________________MANAGER_____________________________________________

	

	

	@PostMapping("/addpublicholiday")

	public String adddynamicdate(Leaveapplication leaveapp,Users users) {

		

		arraylist.add(leaveapp.getNewdate());

		return "redirect:manager?username="+users.getUserdetails();

	}



	

	@PostMapping("/leavestatus")

    public String statusofapplication(Users userdetails,Leaveapplication lapp) {



		 //leaverepo.leaveapplication(lapp.getManagerComment(), lapp.getRadio(),lapp.getLeaveid());

		 return "redirect:manager?username="+userdetails.getUserdetails();

	}





	//____________________________________Employee_____________________________________________



	

	@PostMapping("/leaveapplication")

	public String leaveapplication(Leaveapplication lapp)

	{

		

		LocalDate startDate = lapp.getStartDate();

		LocalDate endDate = lapp.getEndDate();

		boolean noStop = true; int count = 0,all = 0, weekends = 0, pubholidays = 0;

		if (endDate.getMonthValue() >= startDate.getMonthValue())

		{

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

			lapp.setNoOfLeave(count);



		else

			lapp.setNoOfLeave(all);			

		

		lapp.setPublicholidays(pubholidays);

		lapp.setWeekends(weekends);

		leaverepo.save(lapp);

		

	   return "redirect:employee?username="+lapp.getUsername();			

	}



	@PostMapping("/deleteapplication")

	public String deleteapp(Leaveapplication lapp)

	{

	   leaverepo.delete(lapp.getLeaveid());

	   return "redirect:employee?username="+lapp.getUsername();

	}





}











var noStop = true; 
var count = 0,weekends = 0;
if(endDate.getMonth() >= startDate.getMonth())
{
  var tempDate = startDate;
  while(tempDate < endDate)
  {
    if(startDate.getDay() != 0)
      count++;
    else if(startDate.getDay() != 6)
      count+= 0.5;

    tempDate.setDate(tempDate.getDate() + 1);
  }
}
