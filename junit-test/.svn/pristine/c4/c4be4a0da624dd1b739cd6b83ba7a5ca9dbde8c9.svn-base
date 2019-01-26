
单元测试

1、单元测试是集成测试之前的准备工作，只有经过单元测试之后，后期进行集成测试的时候才会很顺利的完成。

2、单元测试有专门的组件：
	junit
	我们这里使用的是junit4.x版本。

3、在实际开发中程序员除了实现自己的逻辑代码之外，需要对自己编写的业务代码负责，保证其能够正常有效的执行，
在没有单元测试这个概念之前，我们一般就是通过编写main方法进行测试。目前大部分（99%以上）使用的都是单元测试
组件进行测试，每个程序员除了编写业务代码还需测试，测试之后还要形成单元测试报告(word excel txt...)。

	哪年哪月哪日测试了哪个类的哪个方法，传的具体参数是什么，实际值是什么，期望值是什么，测试通过还是未通过。

4、一般情况下单元测试主要测试的是MVC架构模式当中的业务层service，不会对dao进行单独测试，因为dao不是一个独立
的事务，也不会直接测试Controller，因为Controller依赖servlet api。控制器servlet脱离了容器之后无法运行。
	class UserServlet extends HttpServlet{
		public void service(HttpServletRequest request,HttpServletResponse response){
			.....
			request接口.xxxx????
			response接口.xxxx????
		}
	}
	UserServlet userServlet = new UserServlet();
	//request和response无法实例化，所以这个方法无法调用。
	userServlet.service(request,response);

5、单元测试主要写什么呢？
	写单元测试用例。（junit test case）

6、一个单元测试用例怎么写呢？
	步骤：
		- 和src同级目录下新建一个test（必须是source folder）
		- 单元测试用例和被测试的service类最好在同一个包下（大部分公司都是这样做的）
		- 单元测试用例的名字和被测试的service类名最好是对应的，在单元测试用例名字后面以Test结尾
		- 单元测试用例类当中测试方法写几个呢？
			一般方法的数量和被测试的类方法数量相同。
			并且（重点）测试用例当中的“测试方法名”有格式要求：
				testSum
				testDivide
				test+原始方法名(首字母大写)
		- 测试用例当中的方法有要求：必须满足这个格式
			@Test
			public void testSum(){
			}
		- 
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	