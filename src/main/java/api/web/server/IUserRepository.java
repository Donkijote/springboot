package api.web.server;

import org.springframework.data.jpa.repository.JpaRepository;

import api.web.models.Users;

public interface IUserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

    Users findByUserName(String userName);
}