package com.todo.app.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.ModelAndView;

import com.todo.app.DTO.TodoUser;
import com.todo.app.Entities.Todo;
import com.todo.app.Entities.Userr;
import com.todo.app.Repos.TodoRepo;
import com.todo.app.Repos.UserRepo;

@Controller
public class MainController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    TodoRepo todoRepo;

    @GetExchange("/")
    public ModelAndView jeknas() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new Userr());
        mv.setViewName("yo");
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView register(Userr user) {
        userRepo.save(user);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("RegisterSuccess");
        return mv;
    }

    @GetMapping("/loginNav")
    public ModelAndView loginNav() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Login");
        mv.addObject("user", new Userr());
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login(Userr user) {

        Userr login = userRepo.findByEmail(user.getEmail().trim())
                .orElseThrow(() -> new IllegalArgumentException("user with such email does not exist"));

        if (login.getPassword().equals(user.getPassword().trim())) {
            System.out.println("n");
            ModelAndView mv = new ModelAndView();
            TodoUser hehe = new TodoUser(null, null, login.getId());
            mv.addObject("user", login);
            mv.addObject("todos", login.getTasks());
            mv.addObject("todo", hehe);
            mv.setViewName("Todo");
            return mv;
        } else {
            System.out.println("password is incorrect");
            ModelAndView mv = new ModelAndView();
            mv.setViewName("Login");
            mv.addObject("user", new Userr());
            return mv;
        }
    }

    @PostMapping("/addTodo")
    public ModelAndView addTodo(TodoUser todo) {
        Userr user = userRepo.findById(todo.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Todo newTodo = new Todo(todo.getName(), todo.getDiscription());
        todoRepo.save(newTodo);

        List<Todo> todoList = user.getTasks();
        todoList.add(newTodo);
        user.setTasks(todoList);
        userRepo.save(user);

        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.addObject("todos", user.getTasks());
        mv.addObject("todo", new TodoUser(null, null, user.getId()));
        mv.setViewName("Todo");
        return mv;
    }

    @PostMapping("/todos/{id}")
    public ModelAndView deleteTodo(@PathVariable long id, Userr user) {
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
    
        List<Todo> userTasks = user.getTasks();
        userTasks.removeIf(t -> t.getId() == id);
        user.setTasks(userTasks);
        userRepo.save(user);
        
        todoRepo.deleteById(id);
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.addObject("todos", user.getTasks());
        mv.addObject("todo", new TodoUser(null, null, user.getId()));
        mv.setViewName("Todo");
        return mv;
    }

}
