package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mapper.MerchantMapper;
import com.itheima.pojo.Merchant;
import com.itheima.service.MerchantService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MerchantSercivelmpl implements MerchantService {

    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public Page<Merchant> getMerchantList(Integer pageNum, Integer pageSize, String merchantName) {
        // 构建查询条件
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        // 商家名称模糊查询（非必填）
        if (merchantName != null && !merchantName.trim().isEmpty()) {
            wrapper.like(Merchant::getMerName, merchantName);
        }
        // 执行分页查询
        Page<Merchant> page = new Page<>(pageNum, pageSize);
        return merchantMapper.selectPage(page, wrapper);
    }
}
