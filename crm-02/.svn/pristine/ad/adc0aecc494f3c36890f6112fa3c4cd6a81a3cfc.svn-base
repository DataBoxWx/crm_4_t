crm-01
	1、配置eclipse关联Tomcat(JavaEE6/Servlet3.1)
	2、配置工作区字符集：UTF-8
	3、调整了一下字体。
	4、将mysql的jar包，以及mybatis的jar包，还有log4j的jar包全部引入到项目当中。
	5、引入相关的配置文件：
		mybatis-config.xml
		SqlMapper.xml
		log4j.properties
		jdbc.properties
	6、引入相关的工具类
		SqlSessionUtil
		TransactionHandler
		UUIDGenerator
		DateUtil
		Const
	7、把CRM中的所有html全部拷贝到WebContent目录下。
	
crm-02
	
	1、数据字典表相关功能：
		1.1、该模块的功能都是传统请求，没有ajax请求。
		1.2、什么是数据字典表呢？有什么用？
			整个系统当中有很多位置都是由下拉列表的，这些下拉列表的数据不能固定，
			不能硬编码，不能写死到前端页面上，应该是可以在后台进行维护的，针对于
			下拉列表当中的数据进行增删改查，这个过程称为数据字典表的维护。
		1.3、数据字典表功能模块的表的设计：
			* 工具：PowerDesigner（进行PDM设计【物理数据模型】）
			* 需要几张表？什么关系？
				机构类型：
					一级部门 <option value="?">?</option>
					二级部门
					三级部门
				性别：
					男
					女
				等级：
					高
					中
					低
			* 两张表：
				字典类型（机构类型、性别、等级）（一）
				字典值（一级部门、二级部门、男、女、高、中、低、三级部门....）（多）
			* 在多的那张表当中添加外键（FK），这个外键引用一的那一方的主键。
			* 谈一谈外键的事儿？
				在实际开发中一般为了程序的执行效率，不建议添加外键约束。
				A表使用B表当中的某个字段，在A表当中检索数据的时候，由于使用了外键机制，数据库
				会自动从B表当中也会检索数据，检索效率较低，影响用户体验，项目开发中有一种优化
				策略：不添加外键约束。
				
				不添加外键约束怎么保证数据的完整性和有效性呢？
					注意，可以在前端页面设计的时候，使用下拉列表的机制。
		1.4、字典类型“创建”：
			思路：
				前端：
					表单验证：
						编码不能为空（前端验证）
						编码不能含有中文，只能含有数字或者字母（前端验证）
						编码不能重复（后台验证ajax）
					验证要求：
						失去焦点验证
						获得焦点的时候清空错误信息
						错误信息要求红色字体，12px，显示到输入框的下方
						最终用户点击保存按钮的时候，要求所有表单项必须全部合法才能提交请求。
				后台：
		
		1.5、将字典类型模块的所有html修改为jsp，然后解决所有的404错误：
		
			* 在jsp中添加：
				<%@page contentType="text/html;charset=UTF-8"%>
				<base href="???"> base标签不属于JSP，属于HTML中的语法机制。
				<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
				base标签只对当前页面中不以“/”开始的路径起作用。
				
			* 重命名：xxx.html修改为xxx.jsp
			
			* 解决所有404错误。
		
			
			
					
			
			 
					























			
		
		