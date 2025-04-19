package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.Cart;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUserId(Long user_id);

    @Query("SELECT ci FROM Cart c JOIN c.items ci WHERE c.id = :cartId")
    List<MenuItem> findByCartId(Long cartId);

    Optional<Cart> findByUser(User user);

}
