package ua.levushevskiy.encoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.levushevskiy.encoder.model.dto.response.DesResponse;
import ua.levushevskiy.encoder.model.dto.response.MessageResponse;
import ua.levushevskiy.encoder.service.AlphabetService;

@RestController
@RequestMapping("/api")
public class AlphabetController {

    private final AlphabetService alphabetService;

    @Autowired
    public AlphabetController(AlphabetService alphabetService) {
        this.alphabetService = alphabetService;
    }

    @PostMapping("/alphabet/crypt")
    public ResponseEntity<MessageResponse> crypt(@RequestParam String key, @RequestParam String messageRequest, @RequestParam(required = false) Boolean encode) {
        return ResponseEntity.ok(new MessageResponse(alphabetService.crypt(key, messageRequest,encode)));
    }
}
