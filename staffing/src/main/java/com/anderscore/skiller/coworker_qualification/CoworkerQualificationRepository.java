package com.anderscore.skiller.coworker_qualification;

import com.anderscore.skiller.coworker.Coworker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoworkerQualificationRepository extends JpaRepository<CoworkerQualification,CoworkerQualificationId > {

    List<CoworkerQualification> findCoworkerQualificationByCoworkerId(Long coworkerId);

    List<CoworkerQualification> findCoworkerQualificationBySkillId(Long skillId);
}
