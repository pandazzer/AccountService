package iFuture.team.AccountService.Repository.Entety;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
