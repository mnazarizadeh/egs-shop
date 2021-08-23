package com.egs.shop.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rates", indexes = {
        @Index(columnList = "user_id"),
        @Index(columnList = "product_id")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "point", nullable = false)
    @Range(min = 1, max = 5)
    private int point;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
}
