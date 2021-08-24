package com.egs.shop.model.dto;

import com.egs.shop.model.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {

    @JsonIgnore
    private long id;

    @NotBlank
    @Pattern(regexp = Constants.USERNAME_REGEX)
    @Size(min = 3, max = 50)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    private boolean blocked;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean activated;

    private Set<String> authorities;

}
