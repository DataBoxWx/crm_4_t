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
						编码不能重复（后台验证ajax）--> 这里的ajax验证需要使用ajax同步方式。{ async : false }
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
			
crm-03

	1、添加一个过滤器（解决POST请求乱码）
	
	2、用户在字典值列表页面上点击“创建”，初始化创建页面：
		字典类型编码位置有一个下拉列表需要从数据库中取数据。
	
	3、字典值的新增：
		2.1、表单验证：
			字典类型编码不能为空，必须选择一个
			字典值不能为空
			在同一个字典类型下，字典值具有唯一性
			排序号可以为空，但不为空的时候，要求必须是正整数。
			
		2.2、保存字典值	
					
			
crm-04
	
	1、部门维护：
		* 设计数据库表：tbl_dept（PowerDesigner）
		* 将部门维护相关的所有html修改为jsp，解决404错误。
		* 创建部门：
		
			- modal窗口（模态窗口弹出之后，窗口点亮，窗口下面无法操作！）
			我们这里使用的modal窗口是Bootstrap3 UI框架提供的。
			modal窗口弹出：
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createDeptModal">
					<span class="glyphicon glyphicon-plus"></span> 创建</button>
			modal窗口关闭：
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			调用js代码隐藏modal：
				$("#createDeptModal").modal("hide");
			调用js代码显示modal：
				$("#createDeptModal").modal("show");
				
			- 创建部门发送ajax请求：
				/crm/settings/dept/save.do
			
crm-05
	
	1、发送ajax请求展示部门列表数据：
		页面全部加载完毕之后单独发送ajax get请求查询部门的所有信息（这里不实现分页查询！）
	
	2、jackson组件的使用：
		解析json字符串的组件。

crm-06
	
	1、部门管理中的复选框全选和取消全选！
		给后期动态生成的元素绑定事件必须使用jQuery的on函数：
			$(父元素).on(事件,被绑定事件的元素对应的选择器,回调函数);
			
	2、编辑
		* 保证一次只能修改一条记录
		* 用户点击更新按钮更新数据。

crm-07
	
	1、删除部门
		* 至少选中一条数据
		* 删除之前需要用户确认是否删除
		* 发送ajax post请求删除：
			/settings/dept/delete.do?code=xx&code=xx&code=xxx&code=xx.....
			
crm-08
	
	1、设计用户表：tbl_user（PowerDesigner）
	
	2、将用户维护相关的html修改为jsp，解决404错误。
		
	3、用户点击“创建”按钮，弹出新增用户的modal窗口：
		- 用户点击创建按钮之后发送ajax get请求：/settings/dept/getAll.do
	
	4、给失效时间绑定一个日历插件：
		* 日历插件很多，我们这里使用的是bootstrap3的datetimepicker插件。
		* 可以先创建一个独立的项目，在该项目当中对datetimepicker插件进行学习，学会之后直接拷贝到CRM项目当中。

	5、发送ajax post请求，保存用户信息。
		密码采用密文：MD5加密之后再存储到数据库表当中。
	
crm-09
	
	1、json的输出交给专门的data.jsp完成。
	
crm-10

	1、提供一个OutJson.java工具类，负责响应JSON。


crm-11
	
	1、实现用户登录功能（先不实现十天内免登录）
		- 登录页面login.html修改为login.jsp
		- 登录页面打开之后，光标自动定位到“用户名”
		- 用户点击登录按钮可以登录，并且用户直接回车也可以登录
		- 登录的时候用户名和密码不能为空
		- 登录发送ajax post请求（登录失败之后必须显示失败原因：要求失败信息显示时红色字体，12px）
		- 登录成功直接跳转到工作台首页：workbench/index.jsp 【document.location.href="workbench/index.jsp"】
		- 登录失败，显示错误信息：
			$.post(
				url , 
				data ,
				function(json){
					//{"success" : true}
					//{"success" : false , "errorMsg":"账户被锁定，请联系管理员"}
				}
			);
		- 后台：
			controller{
				获取用户名和密码,获取客户端IP地址
				调用service登录，登录之后返回“用户对象”
				将用户对象绑定到session
				响应json
			}
			
			service{
				先判断用户名和密码是否正确
				再判断是否锁定
				再判断是否失效
				再判断IP地址是否受限
				保证：出问题之后把错误信息返回。。。。
			}
			
	2、获取最原始的异常：
		catch(Exception e){
			throw e.getCause();
		}
		
		
crm-12

	1、实现十天内免登录：
	
		* 什么效果？
			用户第1次登录的时候，输入了正确的用户名和密码，然后选择了十天内免登录，登录成功，使用系统，最后退出系统。
			用户第2+次访问该系统的时候，不显示登录页面，直接进入工作台。
			
		* 怎么实现？
			* 使用cookie机制
			* 不可选方案：设置session的超时时间。（虽然可以实现，但是这种方式服务器压力太大。）
				session超时默认30分钟。
				可配置：web.xml文件中配置。
				让session对象存活10天，是一种非常不明智的选择：
					第一：你不知道这个用户是否还在电脑前。
					第二：假设有1万个用户访问该系统，则系统中有1万个session对象，而且还要存活长达10天的时间。
			* cookie中存储用户名和密码，注意安全，为了保证安全，密码存储的是MD5加密之后的数据。
			
		* cookie的经典案例有哪些？
			- 购物车
				* 购物车在客户端状态的保存
				注意：购物车在服务器端的状态保存需要依靠HttpSession
			- 十天内免登录
			- HttpSession本身的实现原理依靠cookie
				浏览器禁用cookie，session还能用吗？想用怎么办？
					第一种方案：强迫用户必须开启cookie（可选的）
					第二种方案：URL重写机制：【编程难度太大，不可取】
						http://uri;jsessionid=fdsa12fd1s23a1fd2s1fd....
		
		* cookie涉及到的语法：
			- 怎么创建Cookie
				Cookie cookie = new Cookie(name,value); //name和value都是字符串
			- cookie的有效时间或存储位置
				= 0		删除cookie
				> 0		存储到硬盘文件当中
				< 0		不存储
				没有设置有效期，会保存到浏览器内存当中。浏览器关闭cookie消失。
			- cookie和请求路径之间的关系
				cookie和请求路径之间是一种绑定关系。
			- 服务器怎么向浏览器响应cookie
				response.addCookie(cookie);
			- 浏览器提交cookie给服务器，服务器中怎么接收这些cookie
				Cookie[] cookies = request.getCookies();
		
crm-13
	
	1、登录拦截的过滤器。
		拦截什么资源？不拦截什么？
			拦截：
				*.jsp
				*.do
			不拦截：
				welcome.do		欢迎页面
				login.do		登录行为
				login.jsp		弹出登录页面
				
	2、登录页面必须显示在顶层窗口中。
		登录页面不存在嵌套，任何登录页面必须是在顶级窗口中显示。
	
	3、登录成功之后，页面的右上角显示当前登录的用户信息。
		
crm-14
	
	1、将市场活动相关的html修改为jsp，解决404错误。
	
	2、设计市场活动相关的表
	
	3、创建市场活动：
	
		* 初始化创建市场活动的modal
			- 所有者动态
			- 日历控件（开始日期和结束日期）

crm-15
	
	1、保存市场活动！

crm-16
	
	1、对市场活动进行分页查询：（只实现服务端，前端在后面的版本中实现。）
		
		* 前端页面向服务器提交哪些数据？
			pageNo		页码
			pageSize	每页显示的记录条数
			name		市场活动名称
			owner		所有者
			startDate	开始日期
			endDate		结束日期
			
			后台的控制器：Controller{
				//接收参数
				pageNo		页码
				pageSize	每页显示的记录条数
				name		市场活动名称
				owner		所有者
				startDate	开始日期
				endDate		结束日期
				//控制器怎么把这些数据传递给service？
				Map<String,Object> conditionMap; //条件Map
				/*
					Map<String,Object>
					key				value
					----------------------------------
					startIndex		(pageNo - 1) * pageSize
					pageSize		2
					name			????
					owner			????
					startDate		????
					endDate			????
				*/
			}
		
		* 后台服务端向前端响应什么样的JSON？
			{
				"total" : 100,
				"dataList" : [{市场活动1},{市场活动2},{市场活动3}]
			}
		
		* service执行分页查询之后返回什么？
			Map<String,Object> dataMap;
			key				value
			-----------------------------------------
			total			100
			dataList		ArrayList<Activity>
				
		
		* 分页查询的SQL语句怎么写？
		
			两条SQL：
			
			第一条：查询符合条件的总记录条数
				<select id="" parameterType="Map" resultType="Long">
					select
						count(*)
					from
						tbl_activity a
					join
						tbl_user u
					on
						a.owner = u.id
					<where>
						<if test="name != null and name != ''">
							and a.name like '%' #{name} '%'
						</if>
						<if test="owner != null and owner != ''">
							and u.name like '%' #{owner} '%'
						</if>
						<if test="startDate != null and startDate != ''">
							and a.startDate &gt;= #{startDate}
						</if>
						<if test="endDate != null and endDate != ''">
							and a.endDate &lt;= #{endDate}
						</if>
					</where>
				</select>
			第二条：查询符合条件的当前页数据
				<select id="" parameterType="Map" resultType="Activity">
					select
						a.id,a.name,u.name as owner,a.startDate,a.endDate
					from
						tbl_activity a
					join
						tbl_user u
					on
						a.owner = u.id
					<where>
						<if test="name != null and name != ''">
							and a.name like '%' #{name} '%'
						</if>
						<if test="owner != null and owner != ''">
							and u.name like '%' #{owner} '%'
						</if>
						<if test="startDate != null and startDate != ''">
							and a.startDate &gt;= #{startDate}
						</if>
						<if test="endDate != null and endDate != ''">
							and a.endDate &lt;= #{endDate}
						</if>
					</where>
					order by
						a.createTime desc
					limit
						#{startIndex} , #{pageSize}
				</select>
	
		* 目前在公司当中，有很多情况下都是只开发服务端，然后对外提供接口即可：这里的接口不是狭义interface.....
		
				开发者文档：
				
					接口地址：http://192.168.146.2:8080/crm/workbench/activity/page.do
					
					向服务器发送的参数：
						pageNo	页码
						pageSize 每页显示的记录条数
						name 市场活动名称
						owner 所有者
						startDate 开始日期
						endDate	结束日期
						
					返回的JSON：
						{
							"total": 4,
							"dataList": [{
								"id": "4c62c331a5614a03b07bba2dce957798",
								"owner": "管理员",
								"name": "百度推广03",
								"startDate": "2018-05-21",
								"endDate": "2018-05-29",
								"cost": null,
								"description": null,
								"createBy": null,
								"createTime": null,
								"editBy": null,
								"editTime": null
							}, {
								"id": "e5c4d30698b34d9ab883a06c3aba77c8",
								"owner": "管理员",
								"name": "百度推广02",
								"startDate": "2018-05-21",
								"endDate": "2018-05-29",
								"cost": null,
								"description": null,
								"createBy": null,
								"createTime": null,
								"editBy": null,
								"editTime": null
							}, {
								"id": "c024a7f7b58a4197bab9c4bd1bead328",
								"owner": "管理员",
								"name": "百度推广01",
								"startDate": "2018-05-21",
								"endDate": "2018-05-29",
								"cost": null,
								"description": null,
								"createBy": null,
								"createTime": null,
								"editBy": null,
								"editTime": null
							}]
						}
	
	
crm-17
	
	1、分页查询在CRM中有很多位置都需要使用，建议service的返回值最好封装一个VO对象。
		VO是JavaEE设计模式之一。（DAO也是：数据访问对象）
		VO: View Object（视图对象，该对象的数据最终会展示在页面上）
		
		class PaginationVO{
			Long total;
			List dataList;
		}
		
		DTO也是一个javaee设计模式之一。数据传输对象
		Controller -(DTO)-> Service -(DTO)-> Dao
		
crm-18
	
	1、市场活动的分页查询实现前端：（暂时不实现翻页功能）
		* 页面加载完毕之后显示第一页数据。（发送ajax get）
	
	2、使用bootstrap pagination插件实现翻页。
	
	3、用户点击“查询”按钮，提交查询条件进行分页查询。
	
	4、面试官经常这样问：
		你分页查询的时候/翻页的时候是怎么处理这个查询条件的？
			用户点击查询按钮的时候，将查询条件取出来放到隐藏域当中。
			每一次翻页的时候条件都从隐藏域中取。
			直到下一次用户再次点击查询按钮的时候，更新隐藏域中的条件。
	
crm-19
	
	1、修改市场活动
		* 全选和取消全选
		* 显示修改市场活动的modal窗口
		* 用户点击更新按钮，更新市场活动，更新结束之后，在哪一页回到那一页。 (ajax post)
		
crm-20
	
	1、删除市场活动：
		* 删除之前提示用户是否删除
		* 可以删除多条记录
		* 删除之后回到首页，复选框取消选中
	
crm-21
	
	1、点击市场活动的name，跳转到详情页面：
		传统请求。
		
crm-22
	
	1、修改备注相关的样式：
		鼠标停留在div之后，直接显示的删除和修改备注的按钮为红色。
		删除
			$(".myHref").mouseover()
			$(".myHref").mouseout()
		
	2、页面加载完毕之后发送ajax请求，加载该市场活动下所有的备注信息。
		* 按照备注的创建时间升序。
		* 后期生成的元素绑定事件需要使用on函数。
	
	3、实现保存备注。
		
crm-23
	
	1、删除备注：
		根据备注id删除。
		注意：我们这里有两个位置的“删除”
	
	2、修改备注：
		* 弹出modal（自己画modal）
		* 更新备注（post ajax）
		
crm-24
	
	1、导出市场活动
		* 导出全部
		* 导出选中
		
	2、导出什么？
		导出excel，先研究excel的组成：
			一个excel文件对应workbook（工作簿）
			一个workbook当中有多个sheet（表）
			一个sheet表格当中有多个行row（行）
			一行上有多个单元格cell（单元格）
		几个对象？
			workbook
			sheet
			row
			cell
			
	3、使用apache的POI开源项目/工具/组件来完成excel文件的导入导出。
	
	4、注意：需要死记硬背吗？怎么进行excel的导出？
		从网络上找现成的代码
		然后读懂代码
		然后修改代码
		最终达到自己项目的要求。
	
	5、当前版本先实现“excel”的导出全部。
	
crm-25
	
	1、导出选中的市场活动
	
crm-26
	
	1、导入市场活动！
	
		* 第一步：先进行文件上传（从浏览器客户端传到服务器端）
			需要使用组件：apache commons-fileupload（SpringMVC自带的文件上传也是内置的这个开源项目）
			
		* 第二步：使用POI组件对excel文件进行解析（读取excel）
		
	
	2、导入市场活动的时候采用ajax异步方式：
	
		<form id="importActivityForm">
		<input type="file" name="filename"> <!--注意，要有一个name属性-->
		</form>
		
		$("#importBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/activity/import.do",
				data : new FormData($('#importActivityForm')[0]),
				processData: false, 
	            contentType: false, //不再使用传统的普通表单方式提交数据，采用multipart/form-data方式。
				success : function(json){
					if(json.success){
						$("#importActivityModal").modal("hide");
						display(1 , $("#activityPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("导入失败!");
					}
				}
			});
		});
		
		重点：
			processData: false, 
			contentType: false,
			data : new FormData($('#importActivityForm')[0]),
		
		FormData是支持HTML5的浏览器当中才会内置的对象。
		
crm-27
	
	1、将剩下的所有页面都修改成JSP，解决所有的404错误。
	
	2、将整个系统当中所有的表全部设计出来！！！！
	
		tbl_clue	线索表
		tbl_clue_remark 线索备注表
		tbl_clue_activity_relation 线索和市场活动关系表
		
		tbl_customer	客户表
		tbl_customer_remark 客户备注表
		
		tbl_contacts	联系人表
		tbl_contacts_remark 联系人备注表
		tbl_contacts_activity_relation 联系人和市场活动关系表
		
		tbl_tran 交易表
		tbl_tran 交易备注表
		tbl_tran_history 交易阶段历史表
		
	3、当前CRM系统当中有很多下拉列表，下拉列表：数据量小、所有用户共享、一般不会被修改。
	为了提高用户的体验，为了降低服务器的压力，建议在服务器启动阶段通过监听器将字典值全部加载
	到缓存（cache）中：ServletContext (application)
		Servlet监听器：
			Servlet上下文监听器。（ServletContextListener: init对应服务器启动 destroy对应服务器关闭）
	
	4、初始化系统数据字典表：
		tbl_dic_type.sql
		tbl_dic_value.sql
	
	5、用户打开“线索列表”页面的时候，从cache当中去除字典值，使用forEach展示下拉列表。
		
crm-28

	1、保存线索：
		* 用户点击创建按钮，发送ajax请求，显示所有者，定位所有者。
		* 给下次联系时间添加日历控件。
		* 用户点击保存按钮，保存线索。
	
	2、在线索列表页面将线索的id固定。
	
	3、展示线索明细：
		/workbench/clue/detail.do?id=xxx
		
crm-29
	
	1、线索明细页面加载完毕之后发送ajax get请求展示线索关联的市场活动列表。
	
	
crm-30
	
	1、解除关联（市场活动和线索的关联）
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	