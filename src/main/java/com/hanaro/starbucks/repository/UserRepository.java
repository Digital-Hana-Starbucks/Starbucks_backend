package com.hanaro.starbucks.repository;

import com.hanaro.starbucks.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, Integer> {
    List<Member> findByUserId(String userId);

    Optional<Member> findByUserIdAndUserPw(String userId, String userPw);
}
