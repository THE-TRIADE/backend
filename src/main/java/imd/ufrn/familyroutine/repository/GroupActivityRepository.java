package imd.ufrn.familyroutine.repository;

import java.util.Optional;

import imd.ufrn.familyroutine.model.GroupActivity;

public interface GroupActivityRepository {

    Optional<GroupActivity> findById(Long id);
    
    GroupActivity save(GroupActivity activity);

    void deleteAll();
    
    void deleteById(Long id);
}
