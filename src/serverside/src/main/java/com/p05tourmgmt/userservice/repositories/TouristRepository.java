package com.p05tourmgmt.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.p05tourmgmt.userservice.entities.Tourist;




import java.util.Optional;

public interface TouristRepository extends JpaRepository<Tourist, Integer> {

    @org.springframework.data.jpa.repository.Query("select t from Tourist t where t.uid.uid = :uid")
    public Tourist getTouristByUid(@Param("uid") int uid);

    @org.springframework.data.jpa.repository.Query("select t from Tourist t where t.uid.uname = :uname and t.uid.password = :password")
    public Optional<Tourist> findByUnameAndPassword(@Param("uname") String uname, @Param("password") String password);
}
