package com.example.managesolution.controller;

import com.example.managesolution.data.dto.payment.response.MemberExpiredDTO;
import com.example.managesolution.data.dto.payment.response.PaymentDTO;
import com.example.managesolution.service.PaymentService;
import com.example.managesolution.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;

    // 결제관리 페이지 조회
    @GetMapping("")
    public String list(@RequestParam(value = "startDate", required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                       @RequestParam(value = "endDate", required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(defaultValue = "1") int page,
                       Model model) {

        int pageSize = 10;
        // 결제 내역 페이징
        int totalCount = paymentService.countAll(keyword,startDate, endDate);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 서비스 호출 시 keyword + 시간을 같이 넘기도록 변경
        List<MemberExpiredDTO> memberExpiredDTO = paymentService.getExpiredMembers(keyword, startDate, endDate);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("expiredMembers", memberExpiredDTO);
        model.addAttribute("unpaidMembers", paymentService.getUnpaidMembers(keyword, startDate, endDate));
        model.addAttribute("paymentHistory", paymentService.getPaymentHistory(keyword,startDate, endDate, page, pageSize));
        return "payment/list";
    }


//    ------------------------------------RestAPI---------------------------------

    // 미결제 회원 결제 등록
    @ResponseBody
    @PostMapping("/unpaid/register")
    public ResponseEntity<Void> registerUnpaidPayments(@RequestBody List<PaymentDTO> payments) {
        paymentService.insertPaymentsAndActivate(payments);
        return ResponseEntity.ok().build();
    }

    // 미결제 회원 삭제
    @ResponseBody
    @PostMapping("/unpaid/delete")
    public ResponseEntity<Void> deleteUnpaidProducts(@RequestBody List<PaymentDTO> unpaidList) {
        subscriptionService.deleteUnpaid(unpaidList);
        return ResponseEntity.ok().build();
    }

}
