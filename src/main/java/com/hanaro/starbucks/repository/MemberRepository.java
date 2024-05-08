package com.hanaro.starbucks.repository;

import com.hanaro.starbucks.dto.member.PointResDto;
import com.hanaro.starbucks.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUserId(String userId);
    boolean existsByUserId(String userId);

    @Query("Select new com.hanaro.starbucks.dto.member.PointResDto(m.userPoint) from Member m where m.userIdx = :userIdx")
    PointResDto findPointByUserIdx(int userIdx);
}
