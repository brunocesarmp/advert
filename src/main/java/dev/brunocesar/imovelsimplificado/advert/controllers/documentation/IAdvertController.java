package dev.brunocesar.imovelsimplificado.advert.controllers.documentation;

import dev.brunocesar.imovelsimplificado.advert.controllers.handler.ApplicationErrorResponse;
import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.SendInterestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@Tag(name = "Advert", description = "Endpoints para busca de Anúncios (Adverts)")
public interface IAdvertController {

    @Operation(
            summary = "Listar Anúncios de Locação",
            description = "Endpoint responsável por realizar a busca e a listagem de anúncios de imóveis para locação")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem efetuada com sucesso",
                    useReturnTypeSchema = true),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
    })
    Page<AdvertResponse> listLeases(String state, int pag, String ord, String dir);

    @Operation(
            summary = "Listar Anúncios de Venda",
            description = "Endpoint responsável por realizar a busca e a listagem de anúncios de imóveis para venda")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem efetuada com sucesso",
                    useReturnTypeSchema = true),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
    })
    Page<AdvertResponse> listSales(String state, int pag, String ord, String dir);

    @Operation(
            summary = "Enviar interesse por Anúncio",
            description = "Endpoint responsável por realizar a notificação ao Anunciante pelo interesse por imóvel")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Interesse registrado com sucesso",
                    useReturnTypeSchema = true),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quando houver falha nos dados enviados",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
    })
    SendInterestResponse sendInterest(String advertUuid, AdvertInterestRequest request);
}
