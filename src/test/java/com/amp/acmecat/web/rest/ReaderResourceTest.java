package com.amp.acmecat.web.rest;

import com.amp.acmecat.AcmeCategoriesApp;
import com.amp.acmecat.domain.Product;
import com.amp.acmecat.repository.ProductRepository;
import com.amp.acmecat.web.rest.errors.ExceptionTranslator;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.amp.acmecat.web.rest.TestUtil.createFormattingConversionService;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AcmeCategoriesApp.class)
public class ReaderResourceTest {
    @Autowired
    private Validator validator;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    private ReaderResource readerResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        readerResource = Mockito.spy(new ReaderResource(productRepository));

        this.mockMvc = MockMvcBuilders.standaloneSetup(readerResource).setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Test
    public void testNoBody() throws Exception {
        this.mockMvc.perform(post("/api/products").contentType(TestUtil.APPLICATION_JSON_UTF8).content("")).andExpect(status().is4xxClientError());
    }

    @Test
    public void testReadProducts() throws Exception {
//        Mockito.when(productRepository.insert(new Product("test", "categoryTest"))).thenReturn();
        String goodProduct = readFile("goodProduct.json");
        Gson gson = new Gson();

        List<Product> product = gson.fromJson(goodProduct, new TypeToken<List<Product>>(){}.getType());
        ResponseEntity<List<Product>> listResponseEntity = new ResponseEntity<>(product, HttpStatus.OK);
        Mockito.doReturn(listResponseEntity).when(readerResource).readAllProducts();

        this.mockMvc.perform(get("/api/products"))
            .andExpect(status().is2xxSuccessful()) ;
    }

    private String readFile(String name) throws IOException {
        return IOUtils.toString(new FileInputStream("src/test/resources/" + name), StandardCharsets.UTF_8);
    }
}
