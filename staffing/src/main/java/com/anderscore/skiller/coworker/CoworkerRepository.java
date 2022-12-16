package com.anderscore.skiller.coworker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Verantwortlich für Data Access Layer
public interface CoworkerRepository extends JpaRepository<Coworker, Long> {

    // SELECT * FROM coworker WHERE first_name = ?
    @Query("SELECT c FROM Coworker c WHERE c.firstName = ?1") // -> JPQL
    Optional<Coworker> findCoworkerByFirst_name(String firstName);

    @Query("SELECT c FROM Coworker c WHERE c.lastName = ?1") // -> JPQL
    Optional<Coworker> findCoworkerByLast_name(String lastName);
}
