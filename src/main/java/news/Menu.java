package news;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Scanner;
import java.util.List;


public class Menu {

	//头条新闻平台菜单

	//输入创建
	Scanner in = new Scanner(System.in);

	//输出菜单类的创建
	PrintMenu print = new PrintMenu();

	//新闻用户登录页面
	public void NewsLoginPage() {
		boolean break0 = false;
		boolean return0 = false;//用来判断是否编号出错
		boolean find = false;
		int cnt = 1;//判断第几次登录 如果是第2次那么就会显示 找回密码
		print.NewsLoginPageMenu();
		while (!break0) {
			if(return0) print.NewsLoginPageMenu();
			int step1 = in.nextInt();
			switch (step1) {
			case 1:
				int cnt1 = 2;
				print.AdministratorLoginPage();
				String user1 = in.next();
				System.out.print("\t输入管理员密码:");
				String password1 = in.next();
				AdminDatabaseChecker checker = new AdminDatabaseChecker();
        		int result = checker.checkUser(user1, password1);
				while(result == 0){
					cnt1 --;
					if(cnt1 == 0) break;
					System.out.println("\t还有" + cnt1 + "次机会");
					print.AdministratorLoginPage();
					user1 = in.next();
					System.out.print("\t输入管理员密码:");
					password1 = in.next();
					result = checker.checkUser(user1, password1);
				}
				if(cnt1 == 0){
					System.out.println("\t------登陆失败------");
					return0 = true;
					break;// 默认管理员可以自行查询数据故没有找回功能
				}
				System.out.println("\t------登陆成功------");
				boolean adminbreak = false;
				int adminstep2;
				while(adminbreak == false) {
					print.AdministratorFunctionPage();
					adminstep2 = in.nextInt();
					switch (adminstep2) {
						case 1:
							// 从四个api每个10条接入数据库
							ConnectAPIHeadline.collectDataFromAPI();
							ConnectAPIAmuse.collectDataFromAPI();
							ConnectAPIFinancial.collectDataFromAPI();
							ConnectAPIArea.collectDataFromAPI();
							System.out.println("\t------------------------");
							try {
    								// 暂停执行 2 秒钟
									Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
    							e.printStackTrace();
							}
							break;
						case 2:
							//显示近期新闻 理想目标是根据时间筛选出最近3天的要导入时间包
							FindRecentnewsHealine headline = new FindRecentnewsHealine();
							headline.main();
							FindRecentnewsAmuse amuse = new FindRecentnewsAmuse();
							amuse.main();
							FindRecentnewsFinancial financial = new FindRecentnewsFinancial();
							financial.main();
							FindRecentnewsArea area = new FindRecentnewsArea();
							area.main();
							System.out.println("\t------------------------");
							try {
    								// 暂停执行 2 秒钟
									Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
    							e.printStackTrace();
							}
							break;
						case 3:
							System.out.print("\t请输入要删除新闻的编号:");
							String uniqueKey = in.next();
							boolean deleted1 = DeleteHeadline.deleteNewsByUniqueKey(uniqueKey);
							boolean deleted2 = DeleteFinancial.deleteNewsByUniqueKey(uniqueKey);
							boolean deleted3 = DeleteArea.deleteNewsByUniqueKey(uniqueKey);
							boolean deleted4 = DeleteAmuse.deleteNewsByUniqueKey(uniqueKey);

							if (!deleted1 && !deleted2 && !deleted3 && !deleted4) {
								System.out.println("\t未找到与 uniquekey 相关的记录：" + uniqueKey);
							} else {
    							System.out.println("\t成功删除与 uniquekey 相关的记录：" + uniqueKey);
							}
							System.out.println("\t------------------------");
							try {
    								// 暂停执行 2 秒钟
									Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
    							e.printStackTrace();
							}
							break;
						case 4:
							String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
							String username = "root";
							String password = "123456";

							List<String> tableNames = DatabaseUtils.getTableNames(dbUrl, username, password);

							System.out.println("\t数据库中的表名：");
							for (String tableName : tableNames) {
    							System.out.println("\t" + tableName);
							}

							System.out.println("\t------------------------");
							try {
    								// 暂停执行 2 秒钟
									Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
    							e.printStackTrace();
							}
							break;
						case 5:
							System.out.print("\t请输入要增加的分类名:");
							String Tablename = in.next();
							TableCreator.createTable(Tablename);
							try {
    								// 暂停执行 2 秒钟
									Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
    							e.printStackTrace();
							}
							break;
						case 0:
							adminbreak = true;
							System.out.println("\t您已退出！！！");
							break;
						default:
							System.out.println("\t编号出错！请重新输入：");
					}
				}
				break0 = true;
				break;
			case 2:
				int cnt2 = 2;
				if(find){
					System.out.print("\t是否找回密码 yes or no：");
					String find_ = in.next();
					if(find_.equals("yes")) {
						System.out.print("\t请输入用户名:");
						String name = in.next();
						System.out.print("\t请输入手机号:");
						String no = in.next();
						while(!Refound.checkUser(name,no)){
							System.out.println("\t用户名或手机号有误");
							System.out.print("\t请重新输入用户名：");
							name = in.next();
							System.out.print("\t请重新输入手机号：");
							no = in.next();
						}
						System.out.print("\t请输入密码:");
						String password3 = in.next();
						System.out.print("\t请确认密码:");
						String password33 = in.next();
						while(!password3.equals(password33)){
							System.out.println("\t两次密码不一致");
							System.out.print("\t请再次输入密码:");
							password3 = in.next();
							System.out.print("\t请再次确认密码:");
							password33 = in.next();
						}
						boolean success = Changepassword.updatePassword(name, password3);
						if (success) {
            				System.out.println("\t密码更新成功");
							find = false;
        				}
					}
				}
				try {
					// 暂停执行 2 秒钟
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// 处理中断异常
					e.printStackTrace();
				}
				print.AccountPasswordLogin();
				String user2 = in.next();
				System.out.print("\t请输入密码:");
				String password2 = in.next();
				while(!NormalTableChecker.checkUser(user2, password2) && cnt2 !=0){
					System.out.println("\t用户名或密码错误");
					System.out.println("\t还有 " + cnt2 + " 次机会");
					cnt2 --;
					print.AccountPasswordLogin();
					user2 = in.next();
					System.out.print("\t请输入密码:");
					password2 = in.next();
				}
				if(cnt2 == 0){
					System.out.println("\t----------登陆失败---------");
					find = true;
					return0 = true;
					try {
						// 暂停执行 2 秒钟
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// 处理中断异常
						e.printStackTrace();
					}
					break;
				}
				System.out.println("\t----------登陆成功---------");
				int userstep2;
				boolean accountbreak = false;
				while(!accountbreak) {
					print.UserFunctionPage();
					userstep2 = in.nextInt();
					switch (userstep2) {
						case 1:
							Headlinenow headline1 = new Headlinenow();
							headline1.main();
							Amusenow amuse1 = new Amusenow();
							amuse1.main();
							Financialnow financial1 = new Financialnow();
							financial1.main();
							Areanow area1 = new Areanow();
							area1.main();
							try {
								// 暂停执行 2 秒钟
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
								e.printStackTrace();
							}
							break;
						case 2:
							Headlinepast headline2 = new Headlinepast();
							headline2.main();
							Amusepast amuse2 = new Amusepast();
							amuse2.main();
							Financialpast financial2 = new Financialpast();
							financial2.main();
							Areapast area2 = new Areapast();
							area2.main();
							try {
								// 暂停执行 2 秒钟
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
								e.printStackTrace();
							}
							break;
						case 3:
							System.out.print("\t请输入需下载新闻编号：");
							String nid = in.next();
							boolean foundh = DownloadHeadline.saveHeadlineData(nid);
							boolean foundf = DownloadFinancial.saveHeadlineData(nid);
							boolean foundar = DownloadArea.saveHeadlineData(nid);
							boolean foundam = DownloadAmuse.saveHeadlineData(nid);
							if (!foundh && !foundf && !foundar &&!foundam) {
    							System.out.println("\t未找到与uniquekey匹配的记录");
							}
							try {
								// 暂停执行 2 秒钟
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
								e.printStackTrace();
							}
							break;
						case 4:
							System.out.print("\t请输入需查询新闻编号：");
							String uid = in.next();
							boolean openh = Headlineopen.openURLFromDB(uid);
							boolean openf = Financialopen.openURLFromDB(uid);
							boolean openar = Areaopen.openURLFromDB(uid);
							boolean openam = Amuseopen.openURLFromDB(uid);
							if(!openh && !openf && !openar && !openam)
								System.out.println("\t未找到与uniquekey匹配的记录");
							else
								System.out.println("\t成功查询到新闻");
							try {
								// 暂停执行 2 秒钟
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// 处理中断异常
								e.printStackTrace();
							}
							break;
						case 5:
							String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
							String username = "root";
							String password = "123456";

							List<String> tableNames = DatabaseUtils.getTableNames(dbUrl, username, password);

							System.out.println("\t数据库中的表名：");
							for (String tableName : tableNames) {
    							System.out.println("\t" + tableName);
							}
							System.out.print("\t请输入分类名称：");
							String cag = in.next();
							FindTable.findTableInfo(cag);
							break;
						case 0:
							accountbreak = true;
							System.out.println("\t您已退出！！！");
							break;
						default:
							System.out.println("\t编号出错！请重新输入：");
					}
				}
				break0 = true;
				break;
			case 3:
				//新用户注册
				print.NewUserRegistrationPage();
				String user3 = in.next();
				while(!UserChecker.isUserUnique(user3)){
					System.out.print("\t此用户名不合法");
					System.out.print("\t请再次输入用户名:");
					user3 = in.next();
				}//存在则返回false;
				System.out.print("\t请输入密码:");
				String password3 = in.next();
				System.out.print("\t请确认密码:");
				String password33 = in.next();
				while(!password3.equals(password33)){
					System.out.println("\t两次密码不一致");
					System.out.print("\t请再次输入密码:");
					password3 = in.next();
					System.out.print("\t请再次确认密码:");
					password33 = in.next();
				}
				System.out.print("\t请输入手机号:");
				String phone = in.next();
				NormalTableWriter.insertData(user3, password3, phone);
				return0 = true;
				try {
					// 暂停执行 2 秒钟
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// 处理中断异常
					e.printStackTrace();
				}
				break;
			case 0:
				//退出头条新闻平台
				System.out.println("\t您已退出！！！");
				break0 = true;
				break;
			default:
				try {
					return0 = true;
					Robot robot = new Robot();
					System.out.println("\t编号出错！请重新输入：");
					System.out.println("\t三秒后将自动跳转至主界面！");
					//延迟3秒
					robot.delay(3000);
				} catch (AWTException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}