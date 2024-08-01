package com.example.SpesaSpring.repository;

import com.example.SpesaSpring.dto.CarrelloDto;
import com.example.SpesaSpring.entities.Carrello;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Long> {
    CarrelloDto findByUseridAndGroceryid(long userid, long groceryid);

//  Modifying queries are usually not advisable since they can be separated by calling a findBy then setting the attribute
//  with setAttribute and then saving, the id of the object will automatically tell jpa to save it if there isn't one, or
//  update that specific entity.

//  The transactional @ is used in the service and not here because the repository does not have any business logic while
//  the service does

//  The transaction is useful because if the session is lost or dropped the updates are lost while they are not completed
//  and the db is compromised, this @ prevents that
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Update carrello_new set quantity = quantity +1 where userid = ?1 and groceryid = ?2")
    void increaseQuantityCountByOneForUserid(long userid, long groceryid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Update carrello_new set quantity = quantity -1 where userid = ?1 and groceryid = ?2")
    void decreaseItem(long userid, long groceryid);

    List<CarrelloDto> findByUseridOrderByGroceryidAsc(long userid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from carrello_new where userid = ?1 and groceryid = ?2")
    void deleteByUseridAndGroceryid(long userid, long groceryid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from carrello_new where userid = ?1")
    void deleteByUserid(long userid);
}
