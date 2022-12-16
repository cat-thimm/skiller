package com.anderscore.skiller.skill;

import com.anderscore.skiller.coworker.Coworker;
import com.anderscore.skiller.staffing.StaffingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/skill")
public class SkillController {
    private final StaffingService staffingService;

    @Autowired
    public SkillController(StaffingService staffingService) {
        this.staffingService = staffingService;
    }

    @GetMapping
    public List<Skill> getSkills() {
        return staffingService.getSkills();
    }

    // FA8: Als Geschäftsführer soll ich Skills und Mitarbeiter zum System hinzufügen können.
    @Transactional
    @PostMapping
    public void registerNewSkill(@RequestBody Skill skill) {
        staffingService.addNewSkill(skill);
    }

    // FA3: Als Geschäftsführung muss ich einsehen können, welche Mitarbeiter einen bestimmten Skill besitzen
    @Transactional
    @GetMapping(path = "/{skillId}/coworkers")
    public List<Coworker> getCoworkersBySkill(@PathVariable Long skillId) {
        return staffingService.getCoworkersBySkill(skillId);
    }
}
