package web.controller;



import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
        addUsers();
    }

    private void addUsers() {
        userService.create(new User("Arkadiy", "Gumelya","arkgum@gmail.com"));
        userService.create(new User("Konstantin", "Golubev","kostya@gmail.com"));
        userService.create(new User("Svetlana", "Mineeva","sveta@gmail.com"));
        userService.create(new User("Misha", "Krasheninnikov","misha@gmail.com"));
    }

    @GetMapping("/all")
    public String getAll(Model model){
        List<User> userList = userService.getAll();
        model.addAttribute("userList", userList);
        return "users/userlist";
    }
    @GetMapping("/{id}")
    public String get(@PathVariable("id") long id, Model model){
        User user = userService.get(id);
        System.out.println(user.getName());
        model.addAttribute("user",user);
        return "users/show";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("user") User user){
        return "users/new";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("user")User user){
        userService.create(user);
        return "redirect:/user/all";
    }

    @PostMapping("/{id}/del")
    public String delete(@PathVariable("id") long id){
        userService.delete(id);
        return "redirect:/user/all";
    }

    @GetMapping("/{id}/edit")
    public String editForm(Model model,@PathVariable("id")long id){
        model.addAttribute("user",userService.get(id));
        return "users/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@ModelAttribute("user")User user){
        userService.update(user.getId(), user);
        return "redirect:/user/all";
    }



}
