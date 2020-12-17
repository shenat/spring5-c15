package com.sat.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sat.domain.Ingredient;
import com.sat.domain.Taco;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path="/design", 
				produces="application/json") //只处理accept是json的请求，多个值隔开
@CrossOrigin(origins="*")//允许那些地址可以跨域请求这个服务，这里是任何地址都可以
@Slf4j
public class TacoRestController {
	
	//c15增加断路器注释
	@HystrixCommand(fallbackMethod="defaultTacoById",
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
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) throws Exception{
		log.info("开始执行方法");
//		throw new Exception("故意异常抛出");
		//当前线程睡眠600ms
		Thread.currentThread().sleep(600);
		Taco taco = new Taco();
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
        Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
        Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
        ingredients.add(flourTortilla);
        ingredients.add(cornTortilla);
        ingredients.add(groundBeef);
        
		taco.setId(1l);
		taco.setCreatedAt(new Date());
		taco.setName("test");
		taco.setIngredients(ingredients);
		log.info("执行结束");
		return new ResponseEntity<Taco>(taco, HttpStatus.OK);
	}
	
	//断路器默认方法，只要保证参数类型一样即可
	public ResponseEntity<Taco> defaultTacoById(Long id){
		log.info("执行断路器默认方法");
		Taco taco = new Taco();
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
		Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
		Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
		ingredients.add(flourTortilla);
		ingredients.add(cornTortilla);
		ingredients.add(groundBeef);
		
		taco.setId(1l);
		taco.setCreatedAt(new Date());
		taco.setName("default");
		taco.setIngredients(ingredients);
		return new ResponseEntity<Taco>(taco, HttpStatus.OK);
	}
	
	
}
