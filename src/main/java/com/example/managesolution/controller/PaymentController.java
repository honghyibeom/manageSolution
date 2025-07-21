package com.example.managesolution.controller;

import com.example.managesolution.data.dto.payment.response.MemberExpiredDTO;
import com.example.managesolution.data.dto.payment.response.PaymentDTO;
import com.example.managesolution.service.MembershipService;
import com.example.managesolution.service.PaymentService;
import com.example.managesolution.service.PtPackageService;
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
    private final PtPackageService ptPackageService;
    private final MembershipService membershipService;

    // 결제관리 페이지 조회
    @GetMapping("")
    public String list(@RequestParam(value = "unpaidKeyword", required = false) String unpaidKeyword,
                       @RequestParam(value = "expiredKeyword", required = false) String expiredKeyword,
                       @RequestParam(value = "historyKeyword", required = false) String historyKeyword,
                       @RequestParam(defaultValue = "1") int page,
                       Model model) {

        int pageSize = 10;
        int totalCount = paymentService.countAll(historyKeyword);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        System.out.println("totalCount: " + totalCount);
        System.out.println("totalPages: " + totalPages);
        List<MemberExpiredDTO> memberExpiredDTO = paymentService.getExpiredMembers(expiredKeyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("unpaidMembers", paymentService.getUnpaidMembers(unpaidKeyword));
        model.addAttribute("expiredMembers", memberExpiredDTO);
        model.addAttribute("paymentHistory", paymentService.getPaymentHistory(historyKeyword, page, pageSize));
        return "payment/list";
    }

    // 마감 회원 삭제
    @PostMapping("/{id}/delete/expired")
    public String registerProduct(@PathVariable Long id) {
        membershipService.deleteExpiredMembershipAndPtPackage(id);
        return "redirect:/payment";
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
        unpaidList.forEach(dto -> {
            if ("MEMBERSHIP".equals(dto.getProductType())) {
                membershipService.deleteUnpaidMemberShip(dto.getMemberId());
            } else if ("PT".equals(dto.getProductType())) {
                ptPackageService.deleteUnpaidPtPackages(dto.getMemberId());
            }
        });
        return ResponseEntity.ok().build();
    }

}
