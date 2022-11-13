package iFuture.team.AccountService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {
    Logger log = LogManager.getLogger();
    @Autowired
    AccountService accountService;
    @GetMapping(path = "/getId")
    public ResponseEntity getValue(@RequestParam("id") int id) {
        try {
            return ResponseEntity.ok(accountService.getAmount(id));
        } catch (NumberFormatException e) {
            log.info("failure");
            return ResponseEntity.badRequest().body("");
        }
    }

    @GetMapping(path = "/addValue")
    public ResponseEntity getValue(@RequestParam("id") int id, @RequestParam("value") long value){
        try {
            accountService.addAmount(id, value);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            log.info("failure");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
