package edu.miu.cs.cs425.fairfieldlibrarywebapp.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Role otherRole = (Role) obj;
        if (this.roleId == null) {
            if (otherRole.roleId != null)
                return false;
        }
        return (this.roleId.equals(otherRole.roleId)
                && this.name.equals(otherRole.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.roleId, this.name);
        // int result = roleId != null ? roleId.hashCode() : 0;
        // result = 31 * result + (name != null ? name.hashCode() : 0);
        // return result;
    }
}
