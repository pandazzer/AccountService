package iFuture.team.AccountService.Repository;

import iFuture.team.AccountService.Repository.Entety.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Entity, Integer> {
    Entity findByid(int id);
}
