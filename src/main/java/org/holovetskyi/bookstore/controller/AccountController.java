package org.holovetskyi.bookstore.controller;

import org.holovetskyi.bookstore.additional.classes.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

    private Account account;

    @Autowired
    public AccountController(Account account) {
        this.account = account;
    }

    @RequestMapping("/account")
    public String getAccount(Model model) {
        model.addAttribute("account", account);
        return "account";
    }
}
