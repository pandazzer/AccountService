package iFuture.team.AccountService;

import iFuture.team.AccountService.Repository.Entety.Entity;
import iFuture.team.AccountService.Repository.Repository;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DefAccountService implements AccountService{
    @Autowired
    @Getter
    private Repository repository;
    Logger log = LogManager.getLogger();
    AtomicInteger getAmountCountClient = new AtomicInteger(0);
    AtomicInteger addAmountCountClient = new AtomicInteger(0);
    @Setter
    int totalClients = 0;
    Map<Integer, Long> hash = new HashMap<>();// здесь можно подключить redis, но для экономии используем Map'у

    public DefAccountService() {
        new Statistic().start();
    }

    @Override
    public Long getAmount(Integer id) {
        if (hash.containsKey(id)){
            getAmountCountClient.incrementAndGet();
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
        getAmountCountClient.incrementAndGet();
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
        addAmountCountClient.incrementAndGet();
    }

    @Override
    public void resetStatistic() {
        totalClients = 0;
    }

    class Statistic extends Thread {
        public void run(){
            while (true){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                totalClients += getAmountCountClient.get() + addAmountCountClient.get();
                log.info("getAmount clients: " + getAmountCountClient);
                log.info("addAmount clients: " + addAmountCountClient);
                log.info("totalClients: " + totalClients);
                getAmountCountClient.set(0);
                addAmountCountClient.set(0);
            }
        }
    }
}
