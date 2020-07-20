package com.ruhail.inventoryservice.repository.search;

import com.ruhail.inventoryservice.domain.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Category entity.
 */
public interface CategorySearchRepository extends ElasticsearchRepository<Category, Long> {
}
