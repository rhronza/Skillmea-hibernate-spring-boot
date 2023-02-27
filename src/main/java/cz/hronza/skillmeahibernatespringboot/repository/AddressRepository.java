package cz.hronza.skillmeahibernatespringboot.repository;

import cz.hronza.skillmeahibernatespringboot.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query(value = "select a from AddressEntity a")
    List<AddressEntity> selectAll();
}
