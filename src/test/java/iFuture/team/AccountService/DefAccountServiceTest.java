package iFuture.team.AccountService;

import iFuture.team.AccountService.Repository.Entety.Entity;
import iFuture.team.AccountService.Repository.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefAccountServiceTest {

    static DefAccountService defAccountService = Mockito.spy(DefAccountService.class);
    @BeforeAll
    static void beforeAll() {
        Repository repository = Mockito.mock(Repository.class);
        Mockito.when(repository.findByid(1)).thenReturn(new Entity(1, 10L));
        Mockito.when(defAccountService.getRepository()).thenReturn(repository);
    }

    @Test
    void getAmount() {
        assertEquals(10L, defAccountService.getAmount(1));
        assertEquals(10L, defAccountService.hash.get(1));
    }

    @Test
    void addAmount() {
        defAccountService.addAmount(2, 1L);
        Mockito.verify(defAccountService.getRepository()).save(new Entity(2, 1L));
        assertTrue(defAccountService.hash.containsKey(2));
    }
}