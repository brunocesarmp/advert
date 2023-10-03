package dev.brunocesar.imovelsimplificado.advert.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdvertRequest {

    @NotBlank(message = "Título deve ser informado")
    @Size(min = 3, max = 50, message = "Título deve possuir entre {min} e {max} caracteres")
    private String title;

    @NotBlank(message = "Descrição deve ser informada")
    @Size(min = 3, max = 150, message = "Descrição deve possuir entre {min} e {max} caracteres")
    private String description;

    @NotBlank(message = "Estado do anúncio deve ser informado")
    private String state;

    @Positive(message = "Valor referente ao anúncio deve ser acima de 0")
    @NotNull(message = "Valor referente ao anúncio deve ser informado")
    private Double value;

    @NotBlank(message = "Tipo do anúncio deve ser informado")
    private String type;

}
