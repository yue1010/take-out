package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.pojo.Merchant;
import com.itheima.service.MerchantService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Resource
    private MerchantService merchantService;

    @GetMapping("/list")
    public Map<String, Object> merchantList(
            @RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "merchantName", required = false) String merchantName
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            Page<Merchant> page = merchantService.getMerchantList(pageNum, pageSize, merchantName);

            result.put("code", 0);
            result.put("message", "操作成功");
            Map<String, Object> data = new HashMap<>();
            data.put("total", page.getTotal());
            data.put("items", page.getRecords());
            result.put("data", data);
        } catch (Exception e) {
            log.error("获取商家列表失败", e);
            result.put("code", 1);
            result.put("message", "获取商家列表失败：" + e.getMessage());
            result.put("data", null);
        }
        return result;
    }
}


