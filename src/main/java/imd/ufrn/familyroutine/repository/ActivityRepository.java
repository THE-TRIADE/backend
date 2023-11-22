package imd.ufrn.familyroutine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Activity;
import java.util.List;


public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCategory_Id(Long categoryId);
}
