package swing;

public class SwingMain {

	LoginFrame login;
	UserFrame FrameEx;
	MyPageFrame MyPage;
	AdminFrame AdminPage;

	public SwingMain() {
		
		this.login = new LoginFrame();
		this.login.setMain(this);
	}

	public void showFrameTest() {
		login.dispose();
		this.FrameEx = new UserFrame();
	}
	
	public void showAdminPage() {
		login.dispose();
		this.AdminPage = new AdminFrame();
	}
	
}
