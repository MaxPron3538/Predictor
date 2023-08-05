package main.model.repositories;

import main.model.entities.TypeBusiness;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeBusinessRepository extends CrudRepository<TypeBusiness,Integer> {

    List<TypeBusiness> findAll();
    Optional<TypeBusiness> findByCode(int code);
}
