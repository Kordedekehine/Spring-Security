package mysqlSecurity.secureDemo.controller;

import mysqlSecurity.secureDemo.model.Product;
import mysqlSecurity.secureDemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsCheck {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public List<Product> list(){
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product){
        Product saveProduct = productRepository.save(product);
        URI productURI = URI.create("/products/" + saveProduct);
        return ResponseEntity.created(productURI).body(saveProduct);
    }
}
