package ma.enset.GestionAbonnements.repositories;

import jakarta.transaction.Transactional;
import ma.enset.GestionAbonnements.entities.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AbonnementRepository extends JpaRepository<Abonnement,Long> {
    List<Abonnement> findByClientId(Long id);
    //void chargerSoldeAbonnement(Long id, Double montant);
    @Transactional
    @Modifying
    @Query("UPDATE Abonnement a SET a.solde = a.solde + :montant WHERE a.id = :idAbonnement")
    void chargerSoldeAbonnement(@Param("idAbonnement") Long idAbonnement, @Param("montant") double montant);
}
