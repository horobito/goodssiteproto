package com.prototype.view.domain;

import com.prototype.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ViewRepository extends JpaRepository<Product, Long>{




    @Query(
            value =
                    "select product_with_reviewInfo.product_id,\n" +
                            "       product_with_reviewInfo.product_name,\n" +
                            "       product_with_reviewInfo.seller_id,\n" +
                            "       product_with_reviewInfo.seller_name,\n" +
                            "       product_with_reviewInfo.average_score,\n" +
                            "       product_with_reviewInfo.review_count,\n" +
                            "       product_with_reviewInfo.remaining_stocks,\n" +
                            "       product_with_reviewInfo.is_stock_infinite,\n" +
                            "       product_with_reviewInfo.is_sold_out,\n" +
                            "       product_with_reviewInfo.is_deleted,\n" +
                            "       IFNULL(group_concat(c.category_id, '#',c.category_name separator '&'), '') as category_info,\n" +
                            "       product_with_reviewInfo.product_price,\n" +
                            "       product_with_reviewInfo.image_url,\n" +
                            "       product_with_reviewInfo.registration_time\n" +
                            "from (select p.*, u.username as seller_name, IFNULL(SUM(r.product_score)/COUNT(*) , 0) as average_score, count(r.product_score) as review_count\n" +
                            "      from product p\n" +
                            "               left join review r on r.ordered_product_id=p.product_id and r.is_deleted =false\n" +
                            "               left join user u on u.id = p.seller_id\n" +
                            "      where u.id = p.seller_id\n" +
                            "      group by p.product_id order by average_score desc limit :size) as product_with_reviewInfo\n" +
                            "         left join (select cpr.* from category_product_relation cpr) as cpr on cpr.product_id = product_with_reviewInfo.product_id\n" +
                            "         left join (select c.* from category c) as c on c.category_id= cpr.category_id group by product_with_reviewInfo.product_id order by average_score desc",
            nativeQuery = true
    )
    List<Tuple> findOrderByScore(@Param("size") int size);



    @Query(
            value = "select product_with_reviewInfo.product_id,\n" +
                    "       product_with_reviewInfo.product_name,\n" +
                    "       product_with_reviewInfo.seller_id,\n" +
                    "       product_with_reviewInfo.sellername,\n" +
                    "       product_with_reviewInfo.average_score,\n" +
                    "       product_with_reviewInfo.review_count,\n" +
                    "       product_with_reviewInfo.remaining_stocks,\n" +
                    "       product_with_reviewInfo.is_stock_infinite,\n" +
                    "       product_with_reviewInfo.is_sold_out,\n" +
                    "       product_with_reviewInfo.is_deleted,\n" +
                    "       IFNULL(group_concat(c.category_id, '#',c.category_name separator '&'), '') as category_info,\n" +
                    "       product_with_reviewInfo.product_price,\n" +
                    "       product_with_reviewInfo.image_url,\n" +
                    "       product_with_reviewInfo.registration_time\n" +
                    "from (select product.*, IFNULL(SUM(rev.product_score)/count(*),0) as average_score, count(rev.product_score) as review_count\n" +
                    "      from (select product.*, u.username as sellername\n" +
                    "            from product product\n" +
                    "                     left join user u on u.id = product.seller_id and u.is_deleted = false\n" +
                    "            where product.registration_time<:cursor and product.is_deleted= false and u.id = product.seller_id\n" +
                    "            order by registration_time desc limit :size) as product\n" +
                    "               left join review rev on rev.ordered_product_id = product_id and rev.is_deleted = false\n" +
                    "      group by product.product_id ) as product_with_reviewInfo\n" +
                    "         left join (select cpr.* from category_product_relation cpr) as cpr on cpr.product_id = product_with_reviewInfo.product_id\n" +
                    "         left join (select c.* from category c) as c on c.category_id= cpr.category_id group by product_with_reviewInfo.product_id order by registration_time desc",
            nativeQuery = true
    )
    List<Tuple> findOrderByRegistTime(@Param("cursor") LocalDateTime dateTime, @Param("size") int size);



    @Query(
            value = "select product_with_reviewInfo.product_id,\n" +
                    "       product_with_reviewInfo.product_name,\n" +
                    "       product_with_reviewInfo.seller_id,\n" +
                    "       product_with_reviewInfo.seller_name,\n" +
                    "       product_with_reviewInfo.average_score,\n" +
                    "       product_with_reviewInfo.review_count,\n" +
                    "       product_with_reviewInfo.remaining_stocks,\n" +
                    "       product_with_reviewInfo.is_stock_infinite,\n" +
                    "       product_with_reviewInfo.is_sold_out,\n" +
                    "       product_with_reviewInfo.is_deleted,\n" +
                    "       IFNULL(group_concat(c.category_id, '#',c.category_name separator '&'), '') as category_info,\n" +
                    "       product_with_reviewInfo.product_price,\n" +
                    "       product_with_reviewInfo.image_url,\n" +
                    "       product_with_reviewInfo.registration_time\n" +
                    "from (select p.*, u.username as seller_name, IFNULL(SUM(r.product_score)/COUNT(*) , 0) as average_score, count(r.product_score) as review_count\n" +
                    "      from (select p.* from product p where p.product_id=:productId) as p\n" +
                    "               left join review r on r.ordered_product_id=p.product_id and r.is_deleted =false\n" +
                    "               left join user u on u.id = p.seller_id\n" +
                    "      where u.id = p.seller_id\n" +
                    "      group by p.product_id) as product_with_reviewInfo\n" +
                    "         left join (select cpr.* from category_product_relation cpr) as cpr on cpr.product_id = product_with_reviewInfo.product_id\n" +
                    "         left join (select c.* from category c) as c on c.category_id= cpr.category_id group by product_with_reviewInfo.product_id;", nativeQuery = true
    )
    List<Tuple> findByProductId(@Param("productId") Long id);

}
