package com.egs.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RateDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Min(1)
    @Max(5)
    private int point;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProductDTO product;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long productId;
}
