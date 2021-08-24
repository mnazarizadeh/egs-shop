package com.egs.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String title;

    @DecimalMin("0.0")
    private double price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    private boolean enabled;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private float avgRate;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CategoryDTO category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long commentsCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long ratesCount;

}
