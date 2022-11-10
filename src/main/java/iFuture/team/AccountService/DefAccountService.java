package iFuture.team.AccountService;

import iFuture.team.AccountService.Repository.Entety.Entity;
import iFuture.team.AccountService.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class DefAccountService implements AccountService{
    @Autowired
    Repository repository;
    Map<Integer, Long> hash = new HashMap<>();  // здесь можно подключить redis, но для экономии используем Map'у
    @Override
    public Long getAmount(Integer id) {
        if (hash.containsKey(id)){
            return hash.get(id);
        }
        Long result = 0L;
        result = repository.findByid(id).getValue();

        return result;
    }

    @Override
    public void addAmount(Integer id, Long value) {
        hash.put(id, value);
        Entity entity = new Entity(id, value);
        repository.save(entity);
    }
}
