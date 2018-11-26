package ua.levushevskiy.encoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.levushevskiy.encoder.model.dto.request.MessageRequest;
import ua.levushevskiy.encoder.model.dto.response.DecryptResponse;
import ua.levushevskiy.encoder.model.dto.response.EncryptResponse;
import ua.levushevskiy.encoder.service.EncryptService;

@RestController("/rsa")
public class RsaController {

    private final EncryptService encryptService;

    @Autowired
    public RsaController(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @PostMapping("/encode")
    public ResponseEntity<EncryptResponse> rsaEncode(@RequestBody MessageRequest messageRequest){
        return ResponseEntity.ok(encryptService.encode(messageRequest));
    }

    @PostMapping("/decode")
    public ResponseEntity<DecryptResponse> rsaDecode(@RequestBody MessageRequest messageRequest){
        return ResponseEntity.ok(encryptService.decode(messageRequest));
    }
}
