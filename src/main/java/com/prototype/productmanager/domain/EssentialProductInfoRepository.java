package com.prototype.productmanager.domain;

import com.prototype.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EssentialProductInfoRepository extends JpaRepository<Product, Long> {



    <T> List<T> findByProductId(Long productId, Class<T> type);

}
