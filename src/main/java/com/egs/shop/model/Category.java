package com.egs.shop.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories", indexes = @Index(columnList = "title"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 50, unique = true, nullable = false)
    private String title;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

}
