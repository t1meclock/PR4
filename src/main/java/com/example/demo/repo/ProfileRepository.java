package com.example.demo.repo;

import com.example.demo.models.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    List<Profile> findByNickname(String nickname);
    List<Profile> findByNicknameContains(String nickname);

}