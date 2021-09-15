package com.gmall.web.controller;

import com.gmall.product.client.ProductFeignClient;
import com.gmall.search.SearchParam;
import com.gmall.search.client.SearchFeignClient;
import com.gmall.util.common.result.RetVal;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.function.Supplier;

/**
 * @version v1.0
 * @ClassName WebIndexController
 * @Description TODO
 * @Author Q
 */
@Controller
public class WebIndexController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WebIndexController.class);
    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SearchFeignClient searchFeignClient;

    @GetMapping({"/", "index.html"})
    public String index(Model model) {
        List categoryList = productFeignClient.getCategoryList();
        model.addAttribute("list", categoryList);
        return "index/index";
    }

    @GetMapping("/search.html")
    public String searchProduct(SearchParam searchParam, Model model) {
        RetVal<Map> searchResponseVoRetVal = searchFeignClient.searchProduct(searchParam);
        Map searchResponseVoMap = searchResponseVoRetVal.getData();
        System.out.println("searchResponseVo = " + searchResponseVoMap);
        log.debug("searchResponseVo: {}", searchResponseVoMap);
        model.addAllAttributes(searchResponseVoMap);
        System.out.println(model.getAttribute("brandVoList"));
        model.addAttribute("searchParam", searchParam);

        model.addAttribute("urlParam", splicingUrlParam(searchParam));

        model.addAttribute("brandNameParam", pageBrandName(searchParam.getBrandName()));
        model.addAttribute("propsParamList", pageProps(searchParam.getProps()));
        model.addAttribute("orderMap", pageSortInfo(searchParam.getOrder()));
        return "search/index";
    }

    private Map<String, Object> pageSortInfo(String order) {
        Map<String, Object> orderMap = new HashMap<>();
        if (!StringUtils.isEmpty(order)) {
            String[] orderParams = order.split(":");
            if (orderParams.length == 2) {
                orderMap.put("type", orderParams[0]);
                orderMap.put("sort", orderParams[1]);
            }
        } else {
            orderMap.put("type", 1);
            orderMap.put("sort", "desc");
        }
        return orderMap;
    }

    private List<Map<String, String>> pageProps(String[] props) {
        List<Map<String, String>> propList = new ArrayList<>();
        Supplier<Map<String, String>> mapSupplier = HashMap::new;
        if (!ObjectUtils.isEmpty(props)) {
            Arrays.stream(props).forEach(prop -> {
                String[] propPrarms = prop.split(":");
                if (propPrarms.length == 3) {
                    Map<String, String> propMap = mapSupplier.get();
                    propMap.put("propertyKeyId", propPrarms[0]);
                    propMap.put("propertyKeyValue", propPrarms[1]);
                    propMap.put("propertyKey", propPrarms[2]);
                    propList.add(propMap);
                }
            });
        }
        return propList;
    }

    private String pageBrandName(String brandName) {
        if (!StringUtils.isEmpty(brandName)) {
            String[] brandNameParams = brandName.split(":");
            return brandNameParams.length == 2 ? "品牌:" + brandNameParams[1] : "";
        }
        return "";
    }


    private String splicingUrlParam(SearchParam searchParam) {
        StringBuilder splicingUrlParam = new StringBuilder();

        String keyword = searchParam.getKeyword();
        if (!StringUtils.isEmpty(keyword)) {
            splicingUrlParam.append("keyword=").append(keyword);
        }

        Long category1Id = searchParam.getCategory1Id();
        if (!ObjectUtils.isEmpty(category1Id)) {
            splicingUrlParam.append("category1Id=").append(category1Id);
        }

        Long category2Id = searchParam.getCategory2Id();
        if (!ObjectUtils.isEmpty(category2Id)) {
            splicingUrlParam.append("category2Id=").append(category2Id);
        }

        Long category3Id = searchParam.getCategory3Id();
        if (!ObjectUtils.isEmpty(category3Id)) {
            splicingUrlParam.append("category3Id=").append(category3Id);
        }

        String brandName = searchParam.getBrandName();
        if (!StringUtils.isEmpty(brandName) && brandName.length() > 0) {
            splicingUrlParam.append("&brandName=").append(brandName);
        }

        String[] props = searchParam.getProps();
        if (!ObjectUtils.isEmpty(props)) {
            Arrays.stream(props).forEach(prop -> {
                splicingUrlParam.append("&props=").append(prop);
            });
        }
        //
        return "search.html?" + splicingUrlParam.toString();
    }
}
