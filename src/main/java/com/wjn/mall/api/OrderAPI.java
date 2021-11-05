package com.wjn.mall.api;

import com.wjn.mall.api.param.MallSaveOrderParam;
import com.wjn.mall.api.vo.OrderListVO;
import com.wjn.mall.api.vo.ShoppingCartItemVO;
import com.wjn.mall.api.vo.UserAddressVO;
import com.wjn.mall.common.Constants;
import com.wjn.mall.config.annotation.TokenUser;
import com.wjn.mall.config.exception.MallException;
import com.wjn.mall.entity.User;
import com.wjn.mall.service.impl.OrderService;
import com.wjn.mall.service.impl.ShoppingCartService;
import com.wjn.mall.service.impl.UserAddressService;
import com.wjn.mall.util.PageResult;
import com.wjn.mall.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderAPI {

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private UserAddressService userAddressService;

    @Resource
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public Result saveOrder(@RequestBody MallSaveOrderParam mallSaveOrderParam, @TokenUser User user) throws MallException {
        List<ShoppingCartItemVO> items = shoppingCartService.getItemsByItemIds(Arrays.asList(mallSaveOrderParam.getCartItemIds()), user.getUserId());

        UserAddressVO address = userAddressService.getAddressById(mallSaveOrderParam.getAddressId(), user.getUserId());

        return Result.getSuccessResult(orderService.saveOrder(items,address, user.getUserId()));
    }

    @GetMapping("/order/{orderNo}")
    public Result orderDetailPage(@PathVariable("orderNo") String orderNo, @TokenUser User user){
        return Result.getSuccessResult(orderService.getDetail(orderNo,user.getUserId()));
    }

    @GetMapping("/order")
    public Result orderList(@RequestParam(required = false) Integer pageNumber,
                            @RequestParam(required = false) Integer status,
                            @TokenUser User user){
        Integer offset=null;
        Integer limit=null;
        if(pageNumber!=null){
            offset= (pageNumber-1)* Constants.orderSearchPageRecordNum;
            limit=Constants.orderSearchPageRecordNum;
        }
        return Result.getSuccessResult(new PageResult< OrderListVO >(
                orderService.getOrderList(offset,limit, user.getUserId()),
                orderService.getOrderTotalNum(user.getUserId()),
                Constants.orderSearchPageRecordNum,
                pageNumber
                ));
    }

    @PutMapping("/order/{orderNo}/cancel")
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, @TokenUser User user) {
        orderService.cancelOrder(orderNo, user.getUserId());
        return Result.getSuccessResult();
    }

    @PutMapping("/order/{orderNo}/finish")
    public Result finishOrder(@PathVariable("orderNo") String orderNo, @TokenUser User user){
        orderService.finishOrder(orderNo, user.getUserId());
        return Result.getSuccessResult();
    }

    @GetMapping("/paySuccess")
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        return Result.getSuccessResult();
    }

}
