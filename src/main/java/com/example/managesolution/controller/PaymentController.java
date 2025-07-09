package com.example.managesolution.controller;

import com.example.managesolution.data.dto.MemberExpiredDTO;
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
    public String list(@RequestParam(value = "unpaidKeyword", required = false) String unpaidKeyword,
                       @RequestParam(value = "expiredKeyword", required = false) String expiredKeyword,
                       @RequestParam(value = "historyKeyword", required = false) String historyKeyword,
                       Model model) {
        List<MemberExpiredDTO> memberExpiredDTO = paymentService.getExpiredMembers(expiredKeyword);
        model.addAttribute("unpaidMembers", paymentService.getUnpaidMembers(unpaidKeyword));
        model.addAttribute("expiredMembers", memberExpiredDTO);
        model.addAttribute("paymentHistory", paymentService.getPaymentHistory(historyKeyword));
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
