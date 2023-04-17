package ma.enset.GestionAbonnements.web;

import lombok.AllArgsConstructor;
import ma.enset.GestionAbonnements.entities.Client;
import ma.enset.GestionAbonnements.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

@Controller
@AllArgsConstructor
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping(path = "/user/index")
    public String clients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Client> clientPage = clientRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listClients", clientPage.getContent());
        model.addAttribute("pages", new int[clientPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "clients";
    }

    @GetMapping("/user/profil/{id}")
    public String viewClientProfile(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client", client);
        return "profil";
    }

    @GetMapping("/admin/delete")
    public String delete(Long id, String keyword, int page){
        clientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

        @GetMapping("/admin/formClients")
    public String formClients(Model model){
        model.addAttribute("client",new Client());
        return "formClients";
    }

    @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Client client, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()) return "formClients";
        clientRepository.save(client);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editClient")
    public String editClient(Model model, Long id, String keyword, int page){
        Client client = clientRepository.findById(id).orElse(null);
        if(client==null) throw new RuntimeException("client introuvable");
        model.addAttribute("client",client);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page",page);
        return "editClient";
    }
}
