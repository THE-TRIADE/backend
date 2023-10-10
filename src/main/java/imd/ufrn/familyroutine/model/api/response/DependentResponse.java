package imd.ufrn.familyroutine.model.api.response;

import java.sql.Date;

public class DependentResponse {
    private Long id;
    private String name;
    private String cpf;
    private Date birthDate;
    private Long familyGroupId;
    public DependentResponse(Long id, String name, String cpf, Date birthDate, Long familyGroupId) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.familyGroupId = familyGroupId;
    }
    public DependentResponse() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public Long getFamilyGroupId() {
        return familyGroupId;
    }
    public void setFamilyGroupId(Long familyGroupId) {
        this.familyGroupId = familyGroupId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DependentResponse other = (DependentResponse) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}

