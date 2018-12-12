package ua.levushevskiy.encoder.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.levushevskiy.encoder.model.dto.response.MessageResponse;
import ua.levushevskiy.encoder.service.BruteForceService;
import ua.levushevskiy.encoder.service.HammingService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HammingController {

    private final HammingService hammingService;
    private final BruteForceService bruteForceService;

    @Autowired
    public HammingController(HammingService hammingService, BruteForceService bruteForceService) {
        this.hammingService = hammingService;
        this.bruteForceService = bruteForceService;
    }

    @ApiOperation(value = "English Alphabet")
    @PostMapping("/hamming/encode")
    public ResponseEntity<MessageResponse> encode(@RequestParam String messageRequest) {
        return ResponseEntity.ok(new MessageResponse(hammingService.encodeMessage(messageRequest)));
    }
    @ApiOperation(value = "English Alphabet")
    @PostMapping("/hamming/decode")
    public ResponseEntity<MessageResponse> decode(@RequestParam String messageRequest) {
        return ResponseEntity.ok(new MessageResponse(hammingService.decodeMessage(messageRequest)));
    }
    @ApiOperation(value = "English Alphabet")
    @PostMapping("/hamming")
    public ResponseEntity<List<MessageResponse>> test(@RequestParam String messageRequest, @RequestParam List<Integer> percent) {
        return ResponseEntity.ok(hammingService.test(messageRequest, percent));
    }

    @PostMapping("/brute/encode")
    public ResponseEntity<MessageResponse> encodeBrute(@RequestParam String messageRequest) {
        return ResponseEntity.ok(new MessageResponse(bruteForceService.encodeMessage(messageRequest)));
    }

    @PostMapping("/brute/decode")
    public ResponseEntity<MessageResponse> decodeBrute(@RequestParam String messageRequest) {
        return ResponseEntity.ok(new MessageResponse(bruteForceService.decodeMessage(messageRequest)));
    }

    @PostMapping("/brute")
    public ResponseEntity<List<MessageResponse>> testBrute(@RequestParam String messageRequest, @RequestParam List<Integer> percent) {
        return ResponseEntity.ok(bruteForceService.test(messageRequest,percent));
    }


}
