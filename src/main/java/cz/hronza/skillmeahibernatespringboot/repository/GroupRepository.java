package cz.hronza.skillmeahibernatespringboot.repository;

import cz.hronza.skillmeahibernatespringboot.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

}
