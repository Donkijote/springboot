package api.web.server;

import org.springframework.data.jpa.repository.JpaRepository;

import api.web.models.Person;

public interface IPersonRepository extends JpaRepository<Person, Long> {

}