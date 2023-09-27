package dev.brunocesar.imovelsimplificado.advert.controllers.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdvertInterestRequest {

    @NotBlank(message = "Nome deve ser informado")
    @Size(min = 3, max = 100, message = "Nome deve possuir entre {min} e {max} caracteres")
    private String name;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email deve ser informado")
    @Size(min = 3, max = 100, message = "Email deve possuir entre {min} e {max} caracteres")
    private String email;

    @NotBlank(message = "Telefone de contato deve ser informado")
    @Size(min = 10, max = 11, message = "Telefone de contato deve conter entre {min} e {max} números. Ex.: 11912345314")
    private String phone;

}
