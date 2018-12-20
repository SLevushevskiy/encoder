package ua.levushevskiy.encoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.levushevskiy.encoder.model.dto.response.MessageResponse;
import ua.levushevskiy.encoder.service.ElGamalService;

@RestController
@RequestMapping("/api")
public class ElGamalController {

    private final ElGamalService elGamalService;

    @Autowired
    public ElGamalController(ElGamalService elGamalService) {
        this.elGamalService = elGamalService;
    }

    @PostMapping("/gamal/encode")
    public ResponseEntity<MessageResponse> encode(@RequestParam String messageRequest) {
        return ResponseEntity.ok(new MessageResponse(elGamalService.encode(messageRequest)));
    }

    @PostMapping("/gamal/decode")
    public ResponseEntity<MessageResponse> decode(@RequestParam String messageRequest, @RequestParam Long r, @RequestParam Long s) {
        return ResponseEntity.ok(new MessageResponse(elGamalService.decode(messageRequest, r, s).toString()));
    }
}
