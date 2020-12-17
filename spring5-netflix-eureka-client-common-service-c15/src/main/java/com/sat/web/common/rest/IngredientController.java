package com.sat.web.common.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sat.bean.Ingredient;
import com.sat.component.IpConfiguration;
import com.sat.data.interfaces.IngredientRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path="/ingredients", produces="application/json")
@CrossOrigin(origins="*")
@Slf4j
public class IngredientController {

  private IngredientRepository repo;

  @Autowired
  public IngredientController(IngredientRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public Iterable<Ingredient> allIngredients() {
    return repo.findAll();
  }
  
  //c15增加断路器注解
  @HystrixCommand(fallbackMethod="defaultById",
			commandProperties = {
					//设置方法延迟，单位ms，默认是1秒,超过限制执行替代方法
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="500")
					//方法执行超时关闭，也就是说上面的参数失效
					//@HystrixProperty(name="execution.timeout.enabled",value="false")
					//给定时间范围内，方法被调用的次数
					//@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="30"),
					//给定时间内，方法调用的错误百分比
					//@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="25"),
					//这个就是上面所说的给定时间
					//@HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value="20000"),
					//限定的时间范围内，它会一直处于打开状态，在此之后将进入半开状态，进入半开状态之后，将会再次尝试失败的原始方法
					//@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="60000")

				}
		)
  @GetMapping("/{id}")
  public Optional<Ingredient> byId(@PathVariable String id) throws InterruptedException {
	  log.info("开始执行byId方法");
	  //c15增加，让其超时
	  Thread.currentThread().sleep(600);
	  log.info("结束执行byId方法");
	  return repo.findById(id);
  }
  
  //c15增加，断路器备用方法
  public Optional<Ingredient> defaultById(String id) throws InterruptedException {
	  log.info("执行defaultById方法，id:COTO");
	  return repo.findById("COTO");
  }
  
  
  @PutMapping("/{id}")
  public void updateIngredient(@PathVariable String id, @RequestBody Ingredient ingredient) {
    if (!ingredient.getId().equals(id)) {
      throw new IllegalStateException("Given ingredient's ID doesn't match the ID in the path.");
    }
    repo.save(ingredient);
  }
  
  @PostMapping
  public ResponseEntity<Ingredient> postIngredient(@RequestBody Ingredient ingredient) {
    Ingredient saved = repo.save(ingredient);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create("http://localhost:"+IpConfiguration.getPort()+"/ingredients/" + ingredient.getId()));
    return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
  }
  
  @DeleteMapping("/{id}")
  public void deleteIngredient(@PathVariable String id) {
    repo.deleteById(id);
  }
  
}
