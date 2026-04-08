package com.itheima.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.pojo.Merchant;

public interface MerchantService {
    Page<Merchant> getMerchantList(Integer pageNum, Integer pageSize, String merchantName);
}
