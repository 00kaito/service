package com.bednarz.usmobile.api;


import com.bednarz.usmobile.application.service.MdnApplicationService;
import com.bednarz.usmobile.infrastructure.shared.ValidMdn;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mdn")
@RequiredArgsConstructor
@Validated
public class MdnController {

    private final MdnApplicationService mdnApplicationService;

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferMdn(@RequestParam @ValidMdn String mdn,
                                            @RequestParam @NotBlank(message = "fromUserId can not be empty") String fromUserId,
                                            @RequestParam @NotBlank(message = "toUserId can not be empty") String toUserId) {
        mdnApplicationService.transferMdn(mdn, fromUserId, toUserId);
        return ResponseEntity.ok().build();
    }
}