## Swagger文档

> 不管是knife4j还是swagger-bootstrap-ui，对外提供的地址依然是doc.html
>
> 本商城文档的访问路径：`域名+端口+/doc.html`，例如：http://localhost:8000/doc.html

​																													         注：8000为本商城网关的端口

>Knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案,前身是swagger-bootstrap-ui。

[swagger官网](https://swagger.io/)

[knife4j官方文档][https://xiaoym.gitee.io/knife4j/documentation/]

### 添加依赖

```
<knife4j.version>3.0.2</knife4j.version>

<dependency>
	<groupId>com.github.xiaoymin</groupId>
	<artifactId>knife4j-micro-spring-boot-starter</artifactId>
	<version>${knife4j.version}</version>
</dependency>
<dependency>
	<groupId>com.github.xiaoymin</groupId>
	<artifactId>knife4j-spring-boot-starter</artifactId>
	<version>${knife4j.version}</version>
</dependency>
```

### 添加配置类并开启

在本商城的每个微服务模块的**config**文件夹中，有swagger相应的配置类，以rbac模块为例，配置如下：

```java
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfiguration {

	@Bean
	public Docket baseRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.mall4j.cloud.rbac.controller")).paths(PathSelectors.any())
				.build();
	}

	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("mall4cloud商城接口文档").description("mall4cloud商城接口文档Swagger版").termsOfServiceUrl("")
				.contact(new Contact("广州市蓝海创新科技有限公司", "", "")).version("1.0").build();
	}
}
```

特别要注意的是，类中配置了api文件也就是controller包的路径，否则生成的文档无法成功扫描接口。

```java
.apis(RequestHandlerSelectors.basePackage("com.mall4j.cloud.rbac.controller"))
```

用`@Configuration`注解该类，让spring管理这个类

用`@Bean`标注方法等价于XML中配置bean

用`@EnableSwagger2`标识要开启`Swagger2`

用`@EnableKnife4j`标识要开启`Knife4j`

### 接口使用

在配置好之后，我们就可以对swagger进行使用,比如在`SpuTagController`类中

```java
@RestController("appSpuTagController")
@RequestMapping("/ua/spu_tag")
@Api(tags = "商品分组表")
public class SpuTagController {

    @Autowired
    private SpuTagService spuTagService;

    @GetMapping("/list")
    @ApiOperation(value = "获取商品分组列表", notes = "获取商品分组列表")
    @ApiImplicitParam(name = "shopId", value = "店铺id", dataType = "Long")
    public ServerResponseEntity<List<SpuTagVO>> list(@RequestParam(value = "shopId", defaultValue = "0") Long shopId) {
        List<SpuTagVO> categories = spuTagService.listByShopId(shopId);
        return ServerResponseEntity.success(categories);
    }
}
```

`@Api(tags="商品分组表")`定义标签分组接口，在这个类下定义的所有接口将位于这个标签之下

`@ApiOperation(value = "获取商品分组列表", notes = "获取商品分组列表")`定义具体的接口标题信息，notes可以为这个标签添加注释

`@ApiImplicitParam(name = "shopId", value = "店铺id", dataType = "Long")`对应的参数列表信息，后端返回给前端开发人员，这个接口需要传递什么参数及参数的说明

如有多个参数需要说明，可使用`@ApiImplicitParams()`下面可包含多个`@ApiImplicitParam()`

例如：

```java
@ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "分类ID", dataType = "Long"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", dataType = "Long")
    })
```

### 实体类

```java
public class ChangeShopCartItemDTO {

    @ApiModelProperty(value = "购物车ID", required = true)
    private Long shopCartItemId;

    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID", required = true)
    private Long spuId;

    @ApiModelProperty(value = "旧的skuId 如果传过来说明在变更sku", required = true)
    private Long oldSkuId;

    @NotNull(message = "skuId不能为空")
    @ApiModelProperty(value = "skuId", required = true)
    private Long skuId;

    @ApiModelProperty(value = "店铺ID，前端不用传该字段")
    private Long shopId;

    @NotNull(message = "商品个数不能为空")
    @ApiModelProperty(value = "商品个数", required = true)
    private Integer count;

    @ApiModelProperty(value = "商品是否勾选 true：勾选 ")
    private Boolean isCheck;
    
    一系列GET、SET、ToString方法。
}
```

`@ApiModelProperty(value = "购物车ID", required = true)`利用这个注解可以告诉前端开发人员该字段代表的含义以及是否必传。

### 常用注解

| 注解               | 作用                                 |
| ------------------ | ------------------------------------ |
| @Api               | 修饰整个类，描述Controller的作用     |
| @ApiOperation      | 描述一个类的一个方法，或者说一个接口 |
| @ApiParam          | 单个参数描述                         |
| @ApiModel          | 用对象来接收参数                     |
| @ApiProperty       | 用对象接收参数时，描述对象的一个字段 |
| @ApiResponse       | HTTP响应其中1个描述                  |
| @ApiResponses      | HTTP响应整体描述                     |
| @ApiIgnore         | 使用该注解忽略这个API                |
| @ApiError          | 发生错误返回的信息                   |
| @ApiImplicitParam  | 一个请求参数                         |
| @ApiImplicitParams | 多个请求参数                         |
