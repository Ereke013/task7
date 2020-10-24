package kz.javaee.task7.springbootproject.db;

import org.springframework.scheduling.config.Task;

import java.sql.Date;
import java.util.ArrayList;

public class DBManager {
    public static ArrayList<Tasks> tasksList = new ArrayList<>();

    static {
        tasksList.add(new Tasks(1L,"Complete task 7 from  Spring boot", "I must complete task 7 from Java EE today", Date.valueOf("2020-10-20"), "NO"));
        tasksList.add(new Tasks(2L,"Clear home and buy foods", "I must clear adn buy foods", Date.valueOf("2020-10-21"), "YES"));
        tasksList.add(new Tasks(3L,"Complete all home tasks", "sdvsdvsdvsdvsdvsdv dsv sv sv sv s",Date.valueOf("2020-10-09"), "NO"));
    }
    private static Long id = 4L;

    public static ArrayList<Tasks> getTasks(){
        return tasksList;
    }

    public static void addTask(Tasks task){
        task.setId(id);
        tasksList.add(task);
        id++;
    }

    public static Tasks getTask(Long id) {
        for (Tasks task : tasksList) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public static void deleteTask(Long id) {
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).getId().equals(id)) {
                tasksList.remove(i);
                break;
            }
        }
    }

    public static void saveTask(Tasks task) {
        for (Tasks tasks : tasksList) {
            if (tasks.getId() == task.getId()) {
                tasks.setName(task.getName());
                tasks.setDescription((task.getDescription()));
                tasks.setDeadlineDate(task.getDeadlineDate());
                tasks.setIsCompleted(task.getIsCompleted());
            }
        }
    }

    public static ArrayList<Tasks> filter(String name, String check){
        ArrayList<Tasks> tasks = new ArrayList<>();
        if(name!=null){
            for (Tasks task: tasksList){
                if(task.getName().contains(name) ){
                    tasks.add(task);
                }
            }
        }
        if(check.equals("NONE")){
            return tasksList;
        }
        return tasks;
    }
}
