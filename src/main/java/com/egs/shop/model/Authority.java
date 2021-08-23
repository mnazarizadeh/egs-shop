package com.egs.shop.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Authorities")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements Serializable {

    private static final long serialVersionUID = 2345695746405409023L;

    @Id
    @Column(name = "name", length = 50)
    private String name;

}
