package imd.ufrn.familyroutine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.model.GroupActivity;
import imd.ufrn.familyroutine.repository.GroupActivityRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@Service
public class GroupActivityService {

    @Autowired
    private GroupActivityRepository groupActivityRepository;   

    protected GroupActivity createGroupActivity(GroupActivity groupActivity) {
        return this.groupActivityRepository.save(groupActivity);
    }

    protected GroupActivity findGroupActivityById(Long id){
        return this.groupActivityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Activity.class));
    }

    protected void deleteGroupActivityById(Long groupActivityId) {
        this.groupActivityRepository.deleteById(groupActivityId);
    }
}
