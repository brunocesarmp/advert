package dev.brunocesar.imovelsimplificado.advert.controllers.documentation;

import dev.brunocesar.imovelsimplificado.advert.controllers.handler.ApplicationErrorResponse;
import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.UploadImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Advert Portal", description = "Endpoints para gerenciar cadastro de Anúncios (Advert) - Portal Administrativo - De acordo com o Token do Anunciante")
public interface IPortalAdvertController {

    @Operation(
            summary = "Inserir novo Anúncio",
            description = "Endpoint responsável por realizar a criação de um novo Anúncio")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Anuncio criado com sucesso",
                    useReturnTypeSchema = true),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quando houver falha nos dados enviados",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Quando houver falha de autenticação",
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
    AdvertResponse save(AdvertRequest request, String advertiseToken);

    @Operation(
            summary = "Listar Anúncios",
            description = "Endpoint responsável por listar Anúncios")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Anuncios listados com sucesso",
                    useReturnTypeSchema = true),
            @ApiResponse(
                    responseCode = "401",
                    description = "Quando houver falha de autenticação",
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
    List<AdvertResponse> listByAdvertise(String advertiseToken);

    @Operation(
            summary = "Buscar Anúncio",
            description = "Endpoint responsável por buscar um Anuncio em específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Anuncio encontrado com sucesso",
                    useReturnTypeSchema = true),
            @ApiResponse(
                    responseCode = "401",
                    description = "Quando houver falha de autenticação",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Anúncio não encontrado",
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
    AdvertResponse get(String uuid, String advertiseToken);

    @Operation(
            summary = "Atualizar Anúncio",
            description = "Endpoint responsável por realizar a atualização de um Anúncio")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Anuncio atualizado com sucesso",
                    useReturnTypeSchema = true),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quando houver falha nos dados enviados",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "401",
                    description = "Quando houver falha de autenticação",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Anúncio não encontrado",
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
    AdvertResponse update(String uuid, AdvertRequest request, String advertiseToken);

    @Operation(
            summary = "Remover Anúncio",
            description = "Endpoint responsável por remover um Anuncio em específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Anuncio encontrado com sucesso"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Quando houver falha de autenticação",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Anúncio não encontrado",
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
    void delete(String uuid, String advertiseToken);

    @Operation(
            summary = "Inserir imagem no Anúncio",
            description = "Endpoint responsável por fazer upload de imagem em um Anuncio")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Upload de imagem feita com sucesso"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Quando houver falha de autenticação",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationErrorResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Anúncio não encontrado",
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
                    })
    })
    UploadImageResponse uploadImage(String advertUuid, String advertiseToken, MultipartFile file);
}
