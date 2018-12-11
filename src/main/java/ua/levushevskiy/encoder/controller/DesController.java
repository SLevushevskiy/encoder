package ua.levushevskiy.encoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.levushevskiy.encoder.model.dto.response.DesResponse;
import ua.levushevskiy.encoder.service.DesService;

@RestController
@RequestMapping("/api")
public class DesController {

    private final DesService desService;

    @Autowired
    public DesController(DesService desService) {
        this.desService = desService;
    }

    @PostMapping("/des/encode")
    public ResponseEntity<DesResponse> encode(@RequestParam String key, @RequestParam String messageRequest) {
        return ResponseEntity.ok(desService.encode(key, messageRequest));
    }

}
