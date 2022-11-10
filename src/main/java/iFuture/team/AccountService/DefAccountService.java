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
        Long result;
        Entity entity;
        entity = repository.findByid(id);
        if (entity == null){
            result = 0L;
        } else {
            result = entity.getValue();
        }
        return result;
    }

    @Override
    public void addAmount(Integer id, Long value) {
        Entity entity = repository.findByid(id);
        Long resultValue;
        if (entity == null){
            resultValue = value;
        } else {
            resultValue = entity.getValue() + value;
        }
        Entity resultEntity = new Entity(id, resultValue);
        repository.save(resultEntity);
    }
}
