package com.egs.shop.model.dto;

import com.egs.shop.model.constant.Constants;
import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.validator.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@PasswordMatches
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 8;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @NotBlank
    @Pattern(regexp = Constants.PASSWORD_REGEX)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

}
