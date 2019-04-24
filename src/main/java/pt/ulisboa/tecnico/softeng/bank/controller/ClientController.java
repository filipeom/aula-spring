package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

@Controller
@RequestMapping(value = "/banks/{code}/clients")
public class ClientController {
  private static Logger logger = LoggerFactory.getLogger(ClientController.class);

  @RequestMapping(method = RequestMethod.GET)
  public String clientForm(Model model, @PathVariable String code) {
    logger.info("clientForm");

    Bank bank = Bank.getBankByCode(code);

    if (bank == null) {
      model.addAttribute("error", "Error: it does not exist a bank with the code " + code);
      model.addAttribute("bank", new Bank());
      model.addAttribute("banks", Bank.banks);
      return "banks";
    }
    model.addAttribute("client", new Client());
    model.addAttribute("bank", bank);
    return "clients";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String clientSubmit(Model model, @PathVariable String code, @ModelAttribute Client client) {
    logger.info("clientSubmit bankCode:{}, clientName:{}", code , client.getName());

    Bank bank = Bank.getBankByCode(code);

    new Client(bank, client.getId(), client.getName(), client.getAge());

    return "redirect:/banks/" + code + "/clients";
  }

  @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
  public String showClient(Model model, @PathVariable String code, @PathVariable String id) {
    logger.info("clientSubmit bankCode:{}, clientId:{}", code , id);

    Bank bank = Bank.getBankByCode(code);

    Client client = bank.getClientById(id);

    model.addAttribute("client", client);
    return "client";
  }
}
