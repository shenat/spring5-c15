package com.sat.domain;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor//用的lombok的自动生成参数构造器
public class Taco {
	private Long id;
	private String name;
	private List<Ingredient> ingredients;
	private Date createdAt;
	
}
