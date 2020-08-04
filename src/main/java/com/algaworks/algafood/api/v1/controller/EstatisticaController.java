package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.model.EstatisticasModel;
import com.algaworks.algafood.api.v1.openapi.controller.EstatisticaControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("estatisticas")
public class EstatisticaController implements EstatisticaControllerOpenApi {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticasModel estatisticas() {
        var estatisticasModel = new EstatisticasModel();

        estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diaria"));

        return estatisticasModel;
    }

    @GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String offset) {
        return vendaQueryService.consultarVendasDiarias(filter, offset);
    }

    @GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    @Override
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter,
                                                            @RequestParam(required = false, defaultValue = "+00:00") String offset) {
        byte[] bytes = vendaReportService.emitirVendasDiarias(filter, offset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytes);
    }
}
