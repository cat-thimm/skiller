package com.anderscore.authorization.domain.user;

import com.anderscore.authorization.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public")
@Data
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER) // Roles immer mit dem user laden
    private Collection<Role> role = new ArrayList<>();
}
