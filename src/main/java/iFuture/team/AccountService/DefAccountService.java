package iFuture.team.AccountService;

import iFuture.team.AccountService.Repository.Entety.Entity;
import iFuture.team.AccountService.Repository.Repository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class DefAccountService implements AccountService{
    @Autowired
    @Getter
    private Repository repository;
    Map<Integer, Long> hash = new HashMap<>();  // здесь можно подключить redis, но для экономии используем Map'у
    @Override
    public Long getAmount(Integer id) {
        if (hash.containsKey(id)){
            return hash.get(id);
        }
        Long result;
        Entity entity = getRepository().findByid(id);
        if (entity == null){
            result = 0L;
        } else {
            result = entity.getValue();
        }
        hash.put(id, result);
        return result;
    }

    @Override
    public void addAmount(Integer id, Long value) {
        Entity entity = getRepository().findByid(id);
        Long resultValue;
        if (entity == null){
            resultValue = value;
        } else {
            resultValue = entity.getValue() + value;
        }
        hash.put(id, resultValue);
        Entity resultEntity = new Entity(id, resultValue);
        getRepository().save(resultEntity);
    }
}
