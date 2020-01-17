# JDBC相关
##### 1 mvn Spring pom
##### 4 Spring-Example
##### 5 单数据源
##### 6 多数据源
##### 7 连接池Druid
##### 8 Spring JDBC访问数据库
+ core、JdbcTemplate等相关核心接口和类
+ datasource,数据源相关的辅助类
+ object，将基本的JDBC操作封装成对象
+ support，错误码等其他辅助工具

通过注解定义Bean

+ @Component (通用注解通用Bean)
+ @Repository（数据库操作）
+ @Service（业务的服务）
+ @Controller (Spring MVC)
+ @RestController (Rest For Service)


1. 简单的JDBC操作
    + JdbcTemplate
       + query
       + queryForObject
       + queryForList
       + update
       + executeAndReturnKey


###### 简单的JDBC操作

	@Slf4j
	@Repository
	public class FooDao {
	
	    @Autowired
	    private JdbcTemplate jdbcTemplate;
	
	    @Autowired
	    private SimpleJdbcInsert simpleJdbcInsert;
	
	    public void insertData() {
	        Arrays.asList("a","b").forEach(bar -> {
	            jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)", bar);
	        });
	
	        HashMap<String, String> row = new  HashMap<>();
	        row.put("BAR","d");
	        Number id = simpleJdbcInsert.executeAndReturnKey(row);
	        log.info("ID of d: {}", id.longValue());
	    }
	
	    public void listData() {
	        log.info("Count:{}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO", Long.class));
	        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
	        list.forEach(row->log.info("Bar:{}",row));
	
	        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO", new RowMapper<Foo>() {
	            @Override
	            public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
	                return Foo.builder()
	                        .id(resultSet.getLong(1))
	                        .bar(resultSet.getString(2))
	                        .build();
	            }
	        });
	
	    }
	}

   
2. SQL的批处理
    + JdbcTemplate
       + batchUpdate
       + BatchPreparedStatementSetter
    + NamedParameterJdbcTemplate
       + batchUpdate
       + SqlParameterSourceUtils.createBatch
       
	   
###### SQL的批处理	   
	   
	@Repository
	public class BatchFooDao {
	
			    @Autowired
	    private JdbcTemplate jdbcTemplate;
	    @Autowired
	    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		
		    public void batchInsert() {
		        jdbcTemplate.batchUpdate("INSERT INTO FOO (BAR) VALUES (?)", new BatchPreparedStatementSetter() {
		            @Override
		            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
		                preparedStatement.setString(1,"b-"+i);
		            }
		
		            @Override
		            public int getBatchSize() {
		                return 2;
		            }
		        });
		
		        List<Foo> list = new ArrayList<>();
		        list.add(Foo.builder().id(100L).bar("b-100").build());
		        list.add(Foo.builder().id(101L).bar("B-101").build());
		        namedParameterJdbcTemplate.batchUpdate("INSERT INTO FOO (ID,BAR) VALUES(:id,:bar)", SqlParameterSourceUtils.createBatch(list));
		    }
	} 

##### 9 Spring 的事务抽象

+ JDBC、Hibernate/myBatis
+ DataSource/JTA

##### 11 基于注解的配置方式

开启事务注解的方式

+ @EnableTransactionManagement  //注解开启事务
+ <tx:annotation-driven>        //xml开启事务

一些配置

+ proxyTargetClass //基于接口的还是基于类的，有接口使用这种方式(true/false)
+ mode AOP mode选择 默认Java类就好
+ order AOP 拦截的顺序 - 默认最低优先级（自己做的AOP拦截会在事务启动后执行）

@Transcational

+ transactionManager /DatasourceTransactionManager
+ propagation //传播性
+ isolation   //隔离型
+ timeout     //超时
+ readOnly    //只读
+ 怎么判断回滚   


基于注解的配置方式
![](./Resource/Transaction.png)

###### 编程式事务

![](./Resource/ProgrammaticTransaction.png)

example -> 11.ProgrammaticTransactionDemo

###### 声明式事务

![](./Resource/Declarativtransaction.png)

example -> 11.DeclarativeTransaction

	((FooService) (AopContext.currentProxy())).insertThenRollBack();

##### 12 Spring的JDBC异常抽象

![](./Resource/JDBC_Exception.png)

![](./Resource/JDBC_Exception_Code.png)

![](./Resource/JDBC_Exception_Custom_code.png)

###### 整理笔记📒
Spring 的常用注解

####### Java Config的相关注解
+ @Configuration    //标记当前类是Java配置类
+ @ImportResource   //配置以外的xml文件信息注入
+ @ComponentScan   //Spring容器扫描哪一些package下的Bean配置
+ @Bean //在一个Java Config 的类当中，方法被Bean标记返回一个Bean的配置，存在于Application Context中
+ @ConfigurationProperties //将配置绑定进来，方便使用配置

####### 定义相关注解
+ @Component (通用注解通用Bean)
+ @Repository（数据库操作）
+ @Service（业务的服务）
+ @Controller (Spring MVC)
+ @RestController (Response + Controller Rest For Service)
+ @RequestMapping 方法在那些URL下的映射

####### 注入相关注解
+ @Autowired  
+ @Qualifier  按照类型查找注入进来，可以指定名字
+ @Resource   根据名字进行注入
+ @Value      注入常量或者表达式

####### Actuator 提供的Endpoint

	/actuator/health  健康检查
	/actuator/bean    查看容器中所有的Beans
	/actuator/mapping 查看Web的URL映射
	/actuator/env     查看环境

默认是/actuator/health和/actuator/info可以访问，其他的需要开启

	management.endpoints.web.exposure.include=*
	或者指定多个
	management.endpoints.web.exposure.include=health,bean

	
！！！！！生产环境需要谨慎	
