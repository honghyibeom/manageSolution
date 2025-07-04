package com.example.managesolution.controller;

import com.example.managesolution.data.dto.PaymentDTO;
import com.example.managesolution.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("unpaidMembers", paymentService.getUnpaidMembers());
        model.addAttribute("expiredMembers", paymentService.getExpiredMembers());
        model.addAttribute("paymentHistory", paymentService.getPaymentHistory());
        return "payment/list";
    }

//    ------------------------------------RestAPI---------------------------------

    @ResponseBody
    @PostMapping("/unpaid/register")
    public ResponseEntity<Void> registerUnpaidPayments(@RequestBody List<PaymentDTO> payments) {
        paymentService.insertPaymentsAndActivate(payments);
        return ResponseEntity.ok().build();
    }

}
