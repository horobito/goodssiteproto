package com.prototype.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {


    @Query(value =
            "select r.*,u.username AS reviewerName\n" +
            "from review r\n" +
            "    left join user u on u.id=r.reviewer_id\n" +
            "where r.ordered_product_id =:productId\n" +
            "  and r.registration_date<:cursor\n" +
            "  and r.reviewer_id = u.id and r.is_deleted=false\n" +
            "order by r.registration_date desc limit :size",
            nativeQuery = true)
     List<Tuple> findProductReview(@Param("productId")Long productId,
                                   @Param("cursor") LocalDateTime cursor,
                                   @Param("size") int size);



}
