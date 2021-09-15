package com.gmall.search.repository;

import com.gmall.search.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @version v1.0
 * @ClassName ProductRepository
 * @Description TODO
 * @Author Q
 */
public interface ProductRepository extends ElasticsearchRepository<Product, Long> {
}
