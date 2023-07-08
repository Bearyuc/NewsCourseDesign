package news;

public class PrintMenu {


	//新闻用户登录页面
	public void NewsLoginPageMenu() {
		System.out.println("\t--------欢迎进入头条新闻平台--------");
		System.out.println("\t请选择登录方式：");
		System.out.println("\t管理员登录(1)");
		System.out.println("\t普通游客登录(2)");
		System.out.println("\t普通游客注册(3)");
		System.out.println("\t退出头条新闻平台(0)");
		System.out.println("\t===================================");
		System.out.print("\t请输入编号(0~3):");
	}


	//管理员登录页面
	public void AdministratorLoginPage() {
		System.out.println("\t----------欢迎管理员登陆----------");
		System.out.print("\t输入管理员账号:");
//		System.out.print("\t输入管理员密码:");
	}


	//管理员功能页面
	public void AdministratorFunctionPage() {
		System.out.println("\t欢迎管理员登录，请选择业务：");
		System.out.println("\t下载今日新闻到数据库(1)");
		System.out.println("\t显示近期新闻(2)");
		System.out.println("\t根据编码删除新闻(3)");
		System.out.println("\t显示新闻分类(4)");
		System.out.println("\t增加新闻分类(5)");
		System.out.println("\t退出管理系统(0)");
		System.out.print("\t请输入编号(0~5):");
	}


	//游客登录
	public void AccountPasswordLogin() {
		System.out.println("\t--------欢迎您登录新闻平台--------");
		System.out.print("\t请输入用户名:");
//		System.out.print("\t请输入密码:");
	}


	//新用户注册页面
	public void NewUserRegistrationPage() {
		System.out.println("\t--------欢迎您注册新闻平台--------");
		System.out.print("\t请输入用户名:");
//		System.out.print("\t请输入密码:");
//		System.out.print("\t请确认密码:");
//		System.out.print("\t请输入手机号:");
	}


	//用户功能页面
	public void UserFunctionPage() {
		System.out.println("\t欢迎登录，请选择：");
		System.out.println("\t查看今日新闻(1)");
		System.out.println("\t查看过往新闻(2)");
		System.out.println("\t输入新闻编号，下载新闻(3)");
		System.out.println("\t输入新闻编号，查看新闻(4)");
		System.out.println("\t输入新闻分类编号，查看新闻信息(5)");
		System.out.println("\t退出管理系统(0)");
		System.out.print("\t请输入编号(0~5):");
	}


}