package com.imooc.product.service.impl;

import com.imooc.product.dto.CartDTO;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.enums.ResultEnums;
import com.imooc.product.exception.ProductException;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;


    /*
     * @Author 99795
     * @Description //TODO
     * @Date 11:36 2019/5/3
     * @Param []
     * @return java.util.List<com.imooc.product.dataobject.ProductInfo>
     **/
    @Override
    public List<ProductInfo> findUpall() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList
        ) {
           Optional<ProductInfo> productInfoList= productInfoRepository.findById(cartDTO.getProductId());
           if(!productInfoList.isPresent()){
               throw  new ProductException(ResultEnums.PRODUCT_NOT_EXIST);
           }
           ProductInfo productInfo=productInfoList.get();
           //库存是否足够
            Integer result=productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result<0){
                throw  new ProductException(ResultEnums.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
