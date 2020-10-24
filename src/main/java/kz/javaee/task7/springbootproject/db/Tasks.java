package kz.javaee.task7.springbootproject.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    private Long id;
    private String name;
    private String description;
    private Date deadlineDate; // java.sql.Date or String
    private String isCompleted;
}
