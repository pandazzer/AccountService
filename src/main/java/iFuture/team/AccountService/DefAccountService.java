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
    private static int TIMER_FOR_STATISTIC_LOG = 5000;
    Logger log = LogManager.getLogger();
    AtomicInteger getAmountCountClientInTime = new AtomicInteger(0);
    AtomicInteger addAmountCountClientInTime = new AtomicInteger(0);
    @Setter
    int totalGetAmount = 0;
    int totalAddAmount = 0;
    Map<Integer, Long> hash = new HashMap<>();// здесь можно подключить redis, но для экономии используем Map'у

    public DefAccountService() {
        new Statistic().start();
    }

    @Override
    public Long getAmount(Integer id) {
        if (hash.containsKey(id)){
            getAmountCountClientInTime.incrementAndGet();
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
        getAmountCountClientInTime.incrementAndGet();
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
        addAmountCountClientInTime.incrementAndGet();
    }

    @Override
    public void resetStatistic() {
        totalAddAmount = 0;
        totalGetAmount = 0;
    }

    class Statistic extends Thread {
        public void run(){
            while (true){
                try {
                    Thread.sleep(TIMER_FOR_STATISTIC_LOG);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                totalAddAmount += addAmountCountClientInTime.get();
                totalGetAmount += getAmountCountClientInTime.get();
                log.info("getAmount clients in per second: " + getAmountCountClientInTime.get() * 1000/TIMER_FOR_STATISTIC_LOG);
                log.info("total getAmount: " + totalGetAmount);
                log.info("addAmount clients in per second: " + addAmountCountClientInTime.get() * 1000/TIMER_FOR_STATISTIC_LOG);
                log.info("total addAmount: " + totalAddAmount);
                getAmountCountClientInTime.set(0);
                addAmountCountClientInTime.set(0);
            }
        }
    }
}
