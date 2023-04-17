package ma.enset.GestionAbonnements.web;

import lombok.AllArgsConstructor;
import ma.enset.GestionAbonnements.entities.Abonnement;
import ma.enset.GestionAbonnements.repositories.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class AbonnementController {
    @Autowired
    AbonnementRepository abonnementRepository;

    @GetMapping("/user/abonnements/client/{id}")
    public String afficherAbonnementsClient(@PathVariable Long id, Model model) {
        List<Abonnement> abonnements = abonnementRepository.findByClientId(id);
        model.addAttribute("abonnements", abonnements);
        return "abonnementsClient";
    }

    @GetMapping("/user/abonnements/{id}/form-charger-solde")
    public String afficherFormulaireChargerSolde(@PathVariable("id") Long idAbonnement, Model model) {
        model.addAttribute("abonnement", abonnementRepository.findById(idAbonnement).orElseThrow());
        return "formChargerSolde";
    }

    @PostMapping("/user/abonnements/{id}/charger-solde")
    public String chargerSoldeAbonnement(@PathVariable("id") Long idAbonnement,
                                         @RequestParam("montant") double montant) {
        Abonnement abonnement = abonnementRepository.findById(idAbonnement).orElseThrow();
        Long idClient = abonnement.getClient().getId();
        abonnementRepository.chargerSoldeAbonnement(idAbonnement, montant);
        return "redirect:/user/abonnements/client/" + idClient;
    }


}
