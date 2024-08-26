package com.bednarz.usmobile.api;


import com.bednarz.usmobile.application.service.MdnApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mdn")
@RequiredArgsConstructor
public class MdnController {

    private final MdnApplicationService mdnApplicationService;

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMdn(@RequestParam String mdn,
                                            @RequestParam String fromUserId,
                                            @RequestParam String toUserId) {
        mdnApplicationService.transferMdn(mdn, fromUserId, toUserId);
        return ResponseEntity.ok().build();
    }
}