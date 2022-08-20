package uni.edu.registration.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by rasoolzadeh
 */
@Entity
@Table(name = "course_tbl")
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private int units;

    public Course() {
    }

    public Course(String title, int units) {
        this.title = title;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
