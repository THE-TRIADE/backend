package imd.ufrn.familyroutine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imd.ufrn.familyroutine.model.GroupActivity;
import imd.ufrn.familyroutine.repository.GroupActivityRepository;

@Service
public class GroupActivityService {

    @Autowired
    private GroupActivityRepository groupActivityRepository;   

    protected GroupActivity createGroupActivity(GroupActivity groupActivity) {
        return this.groupActivityRepository.save(groupActivity);
    }

    protected void deleteGroupActivityById(Long groupActivityId) {
        this.groupActivityRepository.deleteById(groupActivityId);
    }
}
