package com.parpiiev.time.utils.dto.mappers;

import com.parpiiev.time.model.Activity;
import com.parpiiev.time.model.User;
import com.parpiiev.time.model.UsersActivity;
import com.parpiiev.time.repository.ActivityRepository;
import com.parpiiev.time.repository.UserRepository;
import com.parpiiev.time.utils.dto.UsersActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserActivityMapper implements DtoMapper<UsersActivityDTO, UsersActivity>{

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @Override
    public UsersActivityDTO mapToDto(UsersActivity usersActivity) {
        UsersActivityDTO dto = new UsersActivityDTO();
        dto.setId(usersActivity.getId());
        dto.setUser_id(usersActivity.getUser().getId());
        dto.setActivity_id(usersActivity.getActivity().getId());
        dto.setTime(usersActivity.getTime());
        return dto;
    }

    @Override
    public UsersActivity mapFromDto(UsersActivityDTO usersActivityDto) {
        UsersActivity usersActivity= new UsersActivity();
//        usersActivity.setId(usersActivityDto.getId());

//        Optional<User> userOptional = userDao.getById(usersActivityDto.getUser_id());
        Optional<User> userOptional = userRepository.findById(usersActivityDto.getUser_id());
        if (userOptional.isPresent()){
            usersActivity.setUser(userOptional.get());
        } else {
            User user = new User();
            user.setUserId(usersActivityDto.getUser_id());
            usersActivity.setUser(user);
        }

//        Optional<Activity> activityOptional = activityDao.getById(usersActivityDto.getActivity_id());
        Optional<Activity> activityOptional = activityRepository.findById(usersActivityDto.getActivity_id());
        if (activityOptional.isPresent()){
            usersActivity.setActivity(activityOptional.get());
        } else {
            Activity activity = new Activity();
            activity.setId(usersActivityDto.getActivity_id());
            usersActivity.setActivity(activity);
        }

        usersActivity.setTime(usersActivityDto.getTime());
        return usersActivity;
    }
}
