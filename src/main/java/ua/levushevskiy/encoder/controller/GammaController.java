package ua.levushevskiy.encoder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.levushevskiy.encoder.model.dto.response.MessageResponse;
import ua.levushevskiy.encoder.service.GammaService;

@RestController
@RequestMapping("/api")
public class GammaController {

    private final GammaService gammaService;

    public GammaController(GammaService gammaService) {
        this.gammaService = gammaService;
    }

    @PostMapping("/gamma/encode")
    public ResponseEntity<MessageResponse> encode(@RequestParam String key, @RequestParam String messageRequest) {
        return ResponseEntity.ok(new MessageResponse(gammaService.encode(messageRequest, key)));
    }

    @PostMapping("/gamma/period")
    public ResponseEntity<MessageResponse> period(@RequestParam long num) {
        return ResponseEntity.ok(new MessageResponse(gammaService.period(num)));
    }

    @PostMapping("/gamma/generate")
    public ResponseEntity<MessageResponse> generate(@RequestParam Integer countNum, @RequestParam Long startValue) {
        return ResponseEntity.ok(new MessageResponse(gammaService.generateGamma(countNum, startValue)));
    }

    @PostMapping("/gamma/decode")
    public ResponseEntity<MessageResponse> decode(@RequestParam String key, @RequestParam String messageRequest) {
        return ResponseEntity.ok(new MessageResponse(gammaService.decode(messageRequest, key)));
    }


}
