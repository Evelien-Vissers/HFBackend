//De Jpa Repository biedt standaard CRUD-operaties zoals save(), findAll(), findById() en deleteById(), zonder dat dit expliciet wordt geimplementeerd.
//'User' specificeert de entiteit waarvoor de repository is bedoeld en 'Long' specificeert het type van de primaire sleutel van de entiteit (id)

package main.java.com.novi.repositories;
import main.java.com.novi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Zoek een gebruiker op basis van e-mailadres
    Optional<User> findByEmail(String email);

}
