package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.CourseModel;
import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index (Model model)
    {
    	model.addAttribute ("title", "Homepage");
    	return "index";
    }


    @RequestMapping("/student/add")
    public String add (Model model)
    {
    	model.addAttribute ("title", "Add");
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
    	StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
    	model.addAttribute ("title", "View Student");
    	StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping("/course/view/{id_course}")
    public String viewCourse (Model model,
            @PathVariable(value = "id_course") String id_course)
    {
    	model.addAttribute ("title", "View Course");
    	CourseModel course = studentDAO.selectCourse (id_course);

        if (course != null) {
        	System.out.println(course.getStudents());
            model.addAttribute ("course", course);
            return "viewcourse";
        } else {
            model.addAttribute ("id_course", id_course);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
    	model.addAttribute ("title", "View All");
    	List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }
    
    


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	model.addAttribute ("title", "Delete");
    	StudentModel student = studentDAO.selectStudent (npm);

         if (student != null) {
             studentDAO.deleteStudent(npm);
             return "delete";
         } else {
             model.addAttribute ("npm", npm);
             return "not-found";
         }
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm)
    {
    	model.addAttribute ("title", "Update");
    	StudentModel student = studentDAO.selectStudent (npm);

         if (student != null) {
        	 model.addAttribute ("student", student);
             return "form-update";
         } else {
             model.addAttribute ("npm", npm);
             return "not-found";
         }
    }
    
//    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
//    public String updateSubmit (@RequestParam(value = "npm", required = false) String npm, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "gpa", required = false) double gpa) {
//    	studentDAO.updateStudent(npm, name, gpa);
//    	return "success-update";
//    }
    
    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (@ModelAttribute StudentModel student) {
    	studentDAO.updateStudent(student.getNpm(), student.getName(), student.getGpa());
    	return "success-update";
    }
    
    


}
