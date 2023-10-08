package imd.ufrn.familyroutine.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class FamilyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "guardianInFamilyGroup",
        joinColumns =  { @JoinColumn( name = "familyGroupId")},
        inverseJoinColumns = {@JoinColumn(name = "guardianId")})
    private Set<Guardian> guardians = new HashSet<>();

    public FamilyGroup(Long id, String name, Set<Guardian> guardians) {
        this.id = id;
        this.name = name;
        this.guardians = guardians;
    }

    public FamilyGroup(){}

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Set<Guardian> getGuardians() {
        return guardians;
    }

    public void setGuardians(Set<Guardian> guardians) {
        this.guardians = guardians;
    }
}