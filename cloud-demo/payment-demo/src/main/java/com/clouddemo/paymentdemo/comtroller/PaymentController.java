package com.clouddemo.paymentdemo.comtroller;


import com.clouddemo.paymentdemo.common.EnumStatus;
import com.clouddemo.paymentdemo.common.Result;
import com.clouddemo.paymentdemo.entity.Payment;
import com.clouddemo.paymentdemo.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Say my name
 */
@RestController
@RequestMapping("/payment")
@Slf4j
@Tag(name = "账单管理")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/addPayment")
    @Operation(summary = "添加账单",description = "添加账单的id、账单号、金额")
    @Parameter
    public Result<String> addPayment(@RequestBody Payment payment){
        int result = paymentService.addPayment(payment);
        log.info("获取自增id"+payment.getId());
        if (result == 1 ){
            return  Result.success();
        }
        return Result.error(EnumStatus.ERROR.getValue(),EnumStatus.ERROR.getMessage());
    }

    @GetMapping("/getPayment/{id}")
    @Operation(summary = "通过id查询支付信息", description = "通过id查询支付信息")
    public Result<Payment> getPaymentById(
            @Parameter(description = "支付id", required = true, example = "1")
            @PathVariable("id") Integer id) {
        return Result.success(paymentService.getPaymentById(id));
    }


}
