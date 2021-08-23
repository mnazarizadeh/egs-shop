package com.egs.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@Table(name = "comments", indexes = {
        @Index(columnList = "user_id"),
        @Index(columnList = "product_id")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", length = 1000, nullable = false)
    private String message;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
