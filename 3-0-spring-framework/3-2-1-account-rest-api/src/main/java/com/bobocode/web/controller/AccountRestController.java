package com.bobocode.web.controller;

import com.bobocode.dao.AccountDao;
import com.bobocode.dao.impl.InMemoryAccountDao;
import com.bobocode.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * todo: 1. Configure rest controller that handles requests with url "/accounts" OK
 * todo: 2. Inject {@link AccountDao} implementation OK
 * todo: 3. Implement method that handles GET request and returns a list of accounts OK
 * todo: 4. Implement method that handles GET request with id as path variable and returns account by id OK
 * todo: 5. Implement method that handles POST request, receives account as request body, saves account and returns it
 * todo:    Configure HTTP response status code 201 - CREATED
 * todo: 6. Implement method that handles PUT request with id as path variable and receives account as request body.
 * todo:    It check if account id and path variable are the same and throws {@link IllegalStateException} otherwise.
 * todo:    Then it saves received account. Configure HTTP response status code 204 - NO CONTENT
 * todo: 7. Implement method that handles DELETE request with id as path variable removes an account by id
 * todo:    Configure HTTP response status code 204 - NO CONTENT
 */
@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    private final AccountDao accountDao;

    @Autowired
    public AccountRestController(@Qualifier("accountDao") AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts() {
        return accountDao.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable int id) {
        return accountDao.findById(id);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        accountDao.save(account); // Salva a conta
        return ResponseEntity.status(HttpStatus.CREATED).body(account); // Retorna o ID da conta
    }


    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable long id, @RequestBody Account account) {
        if(account.getId() != id){
            throw new IllegalStateException("Account ID in request body does not match ID in path");
        }

        try {
            accountDao.save(account); // Chama o método de save do DAO
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (Exception e) {
            // Trate qualquer exceção que possa ocorrer
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAccount(@PathVariable long id) {
        try {
            // Encontre a conta pelo ID antes de removê-la
            Account account = accountDao.findById(id);
            if (account == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found se a conta não existir
            }

            accountDao.remove(account); // Chama o método de remove do DAO
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Retorna 204 No Content

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 em caso de erro
        }
    }


}
