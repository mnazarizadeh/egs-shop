package com.egs.shop.web.rest.vm;

import com.egs.shop.model.constant.Constants;
import com.egs.shop.model.dto.UserDTO;
import com.egs.shop.validator.PasswordMatches;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class LoginVM {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    private boolean rememberMe;

}
