package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    List<CidadeModel> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade invalido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade nao encontrada", response = Problem.class)
    })
    CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1")
                       Long cidadeId);

    @ApiOperation("Cadastra uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidadade cadastrada")
    })
    CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representacao de uma nova cidade")
                          CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade nao encontrada", response = Problem.class)
    })
    CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1")
                          Long cidadeId,
                          @ApiParam(name = "corpo", value = "Representacao de uma cidade com os novos dados")
                          CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluida"),
            @ApiResponse(code = 404, message = "Cidade nao encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma cidade", example = "1")
                 Long cidadeId);
}
