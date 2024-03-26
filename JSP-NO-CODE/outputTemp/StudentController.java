package scaffold.framework.demo.controller;

import org.springframework.stereotype.Controller ;
import org.springframework.ui.Model ;
import org.springframework.web.bind.annotation.GetMapping ;
import org.springframework.web.bind.annotation.PathVariable ;
import org.springframework.web.bind.annotation.PostMapping ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestParam ;

import scaffold.framework.demo.entity.Student ;

import scaffold.framework.demo.service.StudentService ;

import scaffold.framework.demo.service.PromotionService ;

import scaffold.framework.demo.entity.PromotionModel ;

@Controller
@RequestMapping("/student")        

public class StudentController {

    private StudentService studentService ;

    private PromotionModel promotionModel ;

    public StudentController(StudentService studentService

            ,PromotionService promotionService

        ) {

        this.studentService = studentService;        

        this.PromotionService = promotion;

    }        

    @GetMapping("/list")
    public String listStudents(Model model) {

        model.addAttribute("students", studentService.findAll());

        return "pages/student/list";
    }        

    @GetMapping("/add")
    public String showAddStudentForms(Model model) {

        model.addAttribute("promotions", promotionService.findAll());

        return "pages/student/list";
    }        

    @PostMapping("/add")
    public String addStudent(

            @RequestParam String promotionId

            , Student student){

        Promotion promotion = new Promotion();
        promotion.setId(Integer.parseInt(promotionId));
        student.setPromotion(promotion);

        studentService.save(student);
        return "redirect:/students/list";
    }    

    @GetMapping("/edit{id}")
    public String showEditStudentForm(@PathVariable id id, Model model) {
        Student student = studentService.findById(id);

        List<Promotion> promotions = promotionService.findAll();
        promotions.remove(student.getPromotion());     
        model.addAttribute("promotions", promotions);

        model.addAttribute("student", student);
        return "pages/student/edit";
    }        

    @PostMapping("/edit")
    public String editStudent(

            @RequestParam String promotionId

            , Student student){

        if (student.getId() != null          

               && promotionId != null

            ){

            Promotion promotion = new Promotion();
            promotion.setId(promotionId);    
            student.setPromotion(promotion);    

            studentService.save(student);
        }
        return "redirect:/students/list";
    }        

}

