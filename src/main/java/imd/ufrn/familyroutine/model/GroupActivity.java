package imd.ufrn.familyroutine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "group-activity")
public class GroupActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    
    public GroupActivity() {
    }

    public GroupActivity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
