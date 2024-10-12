package lk.ijse.demo.dao;


import lk.ijse.demo.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository// component ekth meta annotate Component walin , e nisa mekath string walin manage wenw
public interface UserDao extends JpaRepository<UserEntity,String> {// jpa repository eka hadala thiynne siyaluma crud oprations wenuwen

}
