package iFuture.team.AccountService.Repository.Entety;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "value")
    private long value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity entity)) return false;
        return getId() == entity.getId() && getValue() == entity.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue());
    }
}
