package com.ruhail.inventoryservice.web.rest;

import com.ruhail.inventoryservice.InventoryserviceApp;

import com.ruhail.inventoryservice.domain.Product;
import com.ruhail.inventoryservice.repository.ProductRepository;
import com.ruhail.inventoryservice.repository.search.ProductSearchRepository;
import com.ruhail.inventoryservice.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static com.ruhail.inventoryservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryserviceApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_CREATED_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_USER = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_USER = "BBBBBBBBBB";

    private static final Double DEFAULT_PRODUCT_BUYING_PRICE = 1D;
    private static final Double UPDATED_PRODUCT_BUYING_PRICE = 2D;

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRODUCR_SELLING_PRICE = 1D;
    private static final Double UPDATED_PRODUCR_SELLING_PRICE = 2D;

    @Autowired
    private ProductRepository productRepository;

    /**
     * This repository is mocked in the com.ruhail.inventoryservice.repository.search test package.
     *
     * @see com.ruhail.inventoryservice.repository.search.ProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductSearchRepository mockProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productRepository, mockProductSearchRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .createdUser(DEFAULT_CREATED_USER)
            .lastModificationDate(DEFAULT_LAST_MODIFICATION_DATE)
            .lastModifiedUser(DEFAULT_LAST_MODIFIED_USER)
            .productBuyingPrice(DEFAULT_PRODUCT_BUYING_PRICE)
            .productName(DEFAULT_PRODUCT_NAME)
            .producrSellingPrice(DEFAULT_PRODUCR_SELLING_PRICE);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCreatedUser()).isEqualTo(DEFAULT_CREATED_USER);
        assertThat(testProduct.getLastModificationDate()).isEqualTo(DEFAULT_LAST_MODIFICATION_DATE);
        assertThat(testProduct.getLastModifiedUser()).isEqualTo(DEFAULT_LAST_MODIFIED_USER);
        assertThat(testProduct.getProductBuyingPrice()).isEqualTo(DEFAULT_PRODUCT_BUYING_PRICE);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getProducrSellingPrice()).isEqualTo(DEFAULT_PRODUCR_SELLING_PRICE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdUser").value(hasItem(DEFAULT_CREATED_USER.toString())))
            .andExpect(jsonPath("$.[*].lastModificationDate").value(hasItem(DEFAULT_LAST_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUser").value(hasItem(DEFAULT_LAST_MODIFIED_USER.toString())))
            .andExpect(jsonPath("$.[*].productBuyingPrice").value(hasItem(DEFAULT_PRODUCT_BUYING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].producrSellingPrice").value(hasItem(DEFAULT_PRODUCR_SELLING_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.createdUser").value(DEFAULT_CREATED_USER.toString()))
            .andExpect(jsonPath("$.lastModificationDate").value(DEFAULT_LAST_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedUser").value(DEFAULT_LAST_MODIFIED_USER.toString()))
            .andExpect(jsonPath("$.productBuyingPrice").value(DEFAULT_PRODUCT_BUYING_PRICE.doubleValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.producrSellingPrice").value(DEFAULT_PRODUCR_SELLING_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .createdUser(UPDATED_CREATED_USER)
            .lastModificationDate(UPDATED_LAST_MODIFICATION_DATE)
            .lastModifiedUser(UPDATED_LAST_MODIFIED_USER)
            .productBuyingPrice(UPDATED_PRODUCT_BUYING_PRICE)
            .productName(UPDATED_PRODUCT_NAME)
            .producrSellingPrice(UPDATED_PRODUCR_SELLING_PRICE);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCreatedUser()).isEqualTo(UPDATED_CREATED_USER);
        assertThat(testProduct.getLastModificationDate()).isEqualTo(UPDATED_LAST_MODIFICATION_DATE);
        assertThat(testProduct.getLastModifiedUser()).isEqualTo(UPDATED_LAST_MODIFIED_USER);
        assertThat(testProduct.getProductBuyingPrice()).isEqualTo(UPDATED_PRODUCT_BUYING_PRICE);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProducrSellingPrice()).isEqualTo(UPDATED_PRODUCR_SELLING_PRICE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).deleteById(product.getId());
    }

    @Test
    @Transactional
    public void searchProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        when(mockProductSearchRepository.search(queryStringQuery("id:" + product.getId())))
            .thenReturn(Collections.singletonList(product));
        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdUser").value(hasItem(DEFAULT_CREATED_USER)))
            .andExpect(jsonPath("$.[*].lastModificationDate").value(hasItem(DEFAULT_LAST_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedUser").value(hasItem(DEFAULT_LAST_MODIFIED_USER)))
            .andExpect(jsonPath("$.[*].productBuyingPrice").value(hasItem(DEFAULT_PRODUCT_BUYING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].producrSellingPrice").value(hasItem(DEFAULT_PRODUCR_SELLING_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }
}
