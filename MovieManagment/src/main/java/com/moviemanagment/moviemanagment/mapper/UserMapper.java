package com.moviemanagment.moviemanagment.mapper;

import com.moviemanagment.moviemanagment.dto.request.SaveUser;
import com.moviemanagment.moviemanagment.dto.response.GetUser;
import com.moviemanagment.moviemanagment.dto.response.GetUserStatistic;
import com.moviemanagment.moviemanagment.persistance.entity.User;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmVersionAttributeType;

import java.util.List;

public class UserMapper {

    public static GetUser toGetDto(User entity){
        if (entity == null) return null;

        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                entity.getRatings() != null ? entity.getRatings().size() : 0
        );
    }

    public static List<GetUser> toGetDtoList(List<User> entityList){

        if (entityList == null) return null;

        return entityList.stream()
                .map(UserMapper::toGetDto)
                .toList();
    }

    public static User toEntity(SaveUser savedto){
        if (savedto == null) return null;
        User userEntity = new User();
        userEntity.setUsername(savedto.username());
        userEntity.setName(savedto.name());
        userEntity.setPassword(savedto.password());
        return userEntity;
    }

    public static void updateEntity(User oldUser, SaveUser userDto) {
        if (oldUser == null || userDto == null) return;
        oldUser.setName(userDto.name());
        oldUser.setPassword(userDto.password());
    }

    public static GetUserStatistic toGetStatisticDto(User user, int totalRatings, double averageRating, int lowestRating, int highestRating  ) {
        if (user == null) return null;
        return new GetUserStatistic(
                user.getUsername(),
                user.getCreatedAt(),
                totalRatings,
                averageRating,
                lowestRating,
                highestRating

        );
    }
}
