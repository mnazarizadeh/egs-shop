package com.egs.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@Table(name = "products", indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "category_id")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "rate", nullable = false)
    private float rate;

    @Column(name = "rates_count", nullable = false)
    private long ratesCount;

    @Column(name = "comments_count", nullable = false)
    private long commentsCount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
