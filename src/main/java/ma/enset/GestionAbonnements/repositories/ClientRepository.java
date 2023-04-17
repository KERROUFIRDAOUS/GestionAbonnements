package ma.enset.GestionAbonnements.repositories;

import ma.enset.GestionAbonnements.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Page<Client> findByNomContains(String kw, Pageable pageable);
}
