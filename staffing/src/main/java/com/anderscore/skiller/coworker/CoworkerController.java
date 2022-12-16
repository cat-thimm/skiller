package com.anderscore.skiller.coworker;

import com.anderscore.skiller.coworker_qualification.CoworkerQualification;
import com.anderscore.skiller.skill.Skill;
import com.anderscore.skiller.staffing.StaffingService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController // Kann REST Endpunkte ansprechen
@RequestMapping(path = "api/v1/coworker")
public class CoworkerController {
    private final StaffingService staffingService;

    @Autowired // StaffingService soll automatisch instanziiert und in Konstruktur injected werden
    public CoworkerController(StaffingService staffingService) {
        this.staffingService = staffingService;
    }

    @GetMapping
    public List<Coworker> getCoworkers() {
        return staffingService.getCoworkers();
    }

    // FA8: Als Geschäftsführer soll ich Skills und Mitarbeiter zum System hinzufügen können.
    @Transactional   // https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
    @PostMapping
    public void registerNewCoworker(@RequestBody Coworker coworker) {
        staffingService.addNewCoworker(coworker);
    }

    @Transactional
    @DeleteMapping(path = "{coworkerId}")
    public void deleteCoworker(@PathVariable("coworkerId") Long coworkerId) {
        staffingService.deleteCoworker(coworkerId);
    }

    @Transactional
    @PostMapping("/{coworkerId}/skill")
    public void addCoworkerQualification(
            @PathVariable Long coworkerId,
            @RequestBody Qualification qualification
    ) {
        staffingService.addCoworkerQualification(coworkerId, qualification.skills, qualification.acquiredAt);
    }

    // FA1 : Als Geschäftsführer muss ich einsehen können, welche Skills einzelne Mitarbeiter aktuell besitzen.
    @Transactional
    @GetMapping("/{coworkerId}/qualification")
    public List<Skill> getCoworkerQualification(@PathVariable Long coworkerId) {

        return staffingService.getCoworkerQualification(coworkerId);
    }

    // FA2 : Als Geschäftsführer muss ich einsehen können, welche Mitarbeiter in einem bestimmten Zeitraum verfügbar sind.
    @Transactional
    @GetMapping("/availability")
    public List<Coworker> getCoworkerAvailability(@RequestParam Date from, @RequestParam Date to) {

        return staffingService.getAvailableCoworkers(from, to);
    }

    // FA 6: Als Geschäftsführung kann ich Auswertungen darüber erhalten, welche Skills meine Mitarbeiter neuerlernt haben
    @Transactional
    @GetMapping(path = "/{coworkerId}/skills")
    public List<Skill> getNewSkillsFromCoworker(@PathVariable Long coworkerId, @RequestParam Date from) {
        return staffingService.getNewSkillsFromCoworker(coworkerId, from);
    }
}
