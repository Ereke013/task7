package kz.javaee.task7.springbootproject.controllers;

import kz.javaee.task7.springbootproject.db.DBManager;
import kz.javaee.task7.springbootproject.db.Tasks;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;

@Controller
public class homecontroller {
    @GetMapping(value = "/")
    public String index(Model model, @RequestParam(name = "task_search_name", defaultValue = "") String name,
                        @RequestParam(name = "deadline_from", defaultValue = "1111-02-02", required = false) String date_from,
                        @RequestParam(name = "deadline_to", defaultValue = "2021-02-02", required = false) String date_to,
                        @RequestParam(name = "isCompleted", defaultValue = "NONE", required = false) String complete) {
        ArrayList<Tasks> tasks = DBManager.getTasks();
        ArrayList<Tasks> tasks2 = new ArrayList<>();

        tasks2.addAll(tasks);

        if (!name.equals("") || !complete.equals("NONE") || !date_from.equals("1111-02-02") || !date_to.equals("2021-02-02")) {
            tasks2.clear();
            if (!complete.equals("NONE")) {
                if (!name.equals("")) {
                    if (!date_from.equals("1111-02-02") && !date_to.equals("2021-02-02")) {
                        for (Tasks task : tasks) {
                            if (task.getName().contains(name) && task.getIsCompleted().equals(complete) && task.getDeadlineDate().compareTo(Date.valueOf(date_from)) >= 0 && task.getDeadlineDate().compareTo(Date.valueOf(date_to)) <= 0) {
                                tasks2.add(task);
                            }
                        }
                    } else {
                        for (Tasks task : tasks) {
                            if (task.getName().contains(name) && task.getIsCompleted().equals(complete)) {
                                tasks2.add(task);
                            }
                        }
                    }
                } else {
                    for (Tasks task : tasks) {
                        if (task.getIsCompleted().equals(complete)) {
                            tasks2.add(task);
                        }
                    }
                }
            } else {
                if (!date_from.equals("1111-02-02") && !date_to.equals("2021-02-02")) {
                    for (Tasks task : tasks) {
                        if (task.getDeadlineDate().compareTo(Date.valueOf(date_from)) >= 0 && task.getDeadlineDate().compareTo(Date.valueOf(date_to)) <= 0) {
                            tasks2.add(task);
                        }
                    }
                } else {
                    for (Tasks task : tasks) {
                        if (task.getName().contains(name)) {
                            tasks2.add(task);
                        }
                    }
                }
            }
        }

        model.addAttribute("tasks", tasks2);
        model.addAttribute("task_complete", complete);
        return "index";
    }

    @PostMapping(value = "/addTask")
    public String addTask(@RequestParam(name = "task_name", defaultValue = "No Task") String name,
                          @RequestParam(name = "task_description", defaultValue = "No Description") String description,
                          @RequestParam(name = "task_deadline", defaultValue = "1111-02-02") Date date,
                          @RequestParam(name = "isCompleted", defaultValue = "NO") String completed) {
        DBManager.addTask(new Tasks(null, name, description, date, completed));
        return "redirect:/";
    }

    @GetMapping(value = "/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id) {
        Tasks task = DBManager.getTask(id);
        model.addAttribute("task", task);
        return "details";
    }

    @PostMapping(value = "/saveTask")
    public String saveTask(@RequestParam(name = "task_name", defaultValue = "No Task") String name,
                           @RequestParam(name = "task_description", defaultValue = "No Description") String description,
                           @RequestParam(name = "task_deadline", defaultValue = "1111-02-02") Date date,
                           @RequestParam(name = "isCompleted", defaultValue = "NO") String isCompleted,
                           @RequestParam(name = "id") Long id) {

        DBManager.saveTask(new Tasks(id, name, description, date, isCompleted));
        return "redirect:/";
    }

    @GetMapping(value = "/deleteTask/{id}")
    public String deleteTask(@PathVariable(name = "id") Long id) {
        System.out.println("id = " + id);
        DBManager.deleteTask(id);
        return "redirect:/";
    }
}
