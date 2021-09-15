package com.gmall.cart.controller;

import com.gmall.cart.service.CartInfoService;
import com.gmall.entity.CartInfo;
import com.gmall.util.common.result.RetVal;
import com.gmall.util.common.util.AuthContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version v1.0
 * @ClassName CartInfoController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/cart")
public class CartInfoController {

    @Autowired
    private CartInfoService cartInfoService;

    /**
     * 添加到购物车
     *
     * @param skuId
     * @param skuNum
     * @param request
     * @return
     */
    @PostMapping("addToCart/{skuId}/{skuNum}")
    public RetVal addToCart(@PathVariable Long skuId, @PathVariable(required = false) Integer skuNum, HttpServletRequest request) {
        String userId = getUserId(request);
        cartInfoService.addToCart(userId, skuId, skuNum);
        return RetVal.ok();
    }

    /**
     * 展示购物车列表
     *
     * @param request
     * @return
     */
    @GetMapping("/getCartList")
    public RetVal getCartList(HttpServletRequest request) {
        //拿取用户Id
        String userId = AuthContextHolder.getUserId(request);
        //拿取临时用户ID
        String userTempId = AuthContextHolder.getUserTempId(request);
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId, userTempId);
        return RetVal.ok(cartInfoList);
    }

    /**
     * 勾选状态
     *
     * @param skuId
     * @param isChecked
     * @param request
     * @return
     */
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public RetVal checkCart(@PathVariable Long skuId, @PathVariable Integer isChecked, HttpServletRequest request) {
        String userId = getUserId(request);
        cartInfoService.checkCart(userId, skuId, isChecked);
        return RetVal.ok();
    }

    /**
     * 删除购物车内商品
     *
     * @param skuId
     * @param request
     * @return
     */
    @DeleteMapping("deleteCart/{skuId}")
    public RetVal deleteCart(@PathVariable Long skuId, HttpServletRequest request) {
        String userId = getUserId(request);
        cartInfoService.deleteCart(userId, skuId);
        return RetVal.ok();
    }

    /**
     * 一键清空购物车
     *
     * @param request
     * @return
     */
    @DeleteMapping("/clearCart")
    public RetVal clearCart(HttpServletRequest request) {
        String userId = getUserId(request);
        cartInfoService.clearCart(userId);
        return RetVal.ok();
    }

    /**
     * 获取购物车内选中的商品
     *
     * @param userId
     * @return
     */
    @GetMapping("/getCheckedCart/{userId}")
    public List<CartInfo> getCheckedCart(@PathVariable String userId) {
        return cartInfoService.getAllCheckedCartInfo(userId);
    }

    /**
     * 从数据库中查询购物车缓存信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/queryFromDbToRedis/{userId}")
    public List<CartInfo> queryFromDbToRedis(@PathVariable String userId) {
        return cartInfoService.getCartInfoListFromDB(userId);
    }


    private String getUserId(HttpServletRequest request) {
        //拿取用户id
        String userId = AuthContextHolder.getUserId(request);
        //如果没有登录，userId没有值
        if (StringUtils.isEmpty(userId)) {
            userId = AuthContextHolder.getUserTempId(request);
        }
        return userId;
    }

}
