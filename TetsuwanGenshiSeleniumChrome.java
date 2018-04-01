
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mukudo
 */
public class TetsuwanGenshiSeleniumChrome {

    static final Logger logger = Logger.getLogger(TetsuwanGenshiSeleniumChrome.class.getName());
    /* 時刻を格納する変数 */
    static final LocalDateTime logLocalDt = LocalDateTime.now();
    static final String logTimeStr1 = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(logLocalDt);
    static final String filePath = "./log/TetsuwanGenshiSeleniumChrome_" + logTimeStr1 + ".log";

    public static void main(String[] args) {

        try {
            // ログ出力ファイルを指定する
            FileHandler fh = new FileHandler(filePath);
            // ログ出力フォーマットを指定する
            fh.setFormatter(new java.util.logging.SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ログ出力レベルをCONFIG以上に設定する
        logger.setLevel(Level.ALL);

        /* 途中経過表示用変数 */
        int no_of_nice = 0;
        int no_of_access = 0;
        int no_of_skip = 0;
        int no_of_nontitle = 0;
        int no_of_nonicebutton = 0;
        int no_of_alreadynice = 0;
        int no_of_nicefail = 0;
        int no_of_transferfail = 0;
        int no_of_clickfail = 0;

        //chromeドライバの設定
        System.setProperty("webdriver.chrome.driver", "./exe/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--purge-memory-button");
        options.addArguments("--headless");
//        options.addArguments("disable-accelerated-mjpeg-decode");
//        options.addArguments("disable-accelerated-video-decode");
//        DesiredCapabilities cap = DesiredCapabilities.chrome();
//        cap.setCapability("marionette", true);
        WebDriver driver = new ChromeDriver(options);
//        WebDriver driver = new ChromeDriver(cap);
//        driver.get("http://tetsuwangenshi.blog.so-net.ne.jp/");
        driver.get("https://blog.so-net.ne.jp/MyPage/blog/article/edit/list");
//        driver.findElement(By.linkText("ログイン")).sendKeys(Keys.CONTROL);
//        driver.findElement(By.linkText("ログイン")).click();
        WebDriverWait wait = new WebDriverWait(driver, 44);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SSO_COMMON_ID")));
        WebElement user = driver.findElement(By.name("SSO_COMMON_ID"));
        user.clear();
        user.sendKeys("odukum@qa3");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SSO_COMMON_PWD")));
        driver.findElement(By.name("SSO_COMMON_PWD")).sendKeys("7656198s");
        driver.findElement(By.id("loginformsubmit")).sendKeys(Keys.CONTROL);
        driver.findElement(By.id("loginformsubmit")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("鉄腕原子")));
//        System.out.println(" 鉄腕原子としてログイン ");
//        logger.log(Level.INFO, "鉄腕原子としてログイン：info");

        /* 接続先サーバー名を"localhost"で与えることを示している */
        String servername = "localhost";
//        String servername = "192.168.1.212:3306";

        /* 接続するデータベース名をsenngokuとしている */
        String databasename = "seleniumdb";

        /* データベースの接続に用いるユーザ名をrootユーザとしている */
//		String user_name = "root";
//        String user_name = "mouseMySQL";
        String user_name = "javaMySQL";

        /* データベースの接続に用いるユーザのパスワードを指定している */
        String password = "7656198s";

        /* 取り扱う文字コードをUTF-8文字としている */
        String serverencoding = "UTF-8";

        /* データベースをあらわすURLを設定している */
        String url = "jdbc:mysql://localhost/" + databasename;
//        String url = "jdbc:mysql://192.168.1.212:3306/" + databasename;

        /*
		 * MySQLの場合、URLの形式は次のようになります。 jdbc:mysql://(サーバ名)/(データベース名)
         */

 /*
		 * ↑データベースをあらわすURL（データベースURL)は、データベースに接続する場合に 必要となる情報をセットした文字列である。
		 * この文字列の構造は、"jdbc"、サブプロトコル、サブネームの３つの部分から構成される。
         */

 /* 接続を表すConnectionオブジェクトを初期化 */
        Connection con = null;

        try {

            /*
			 * クラスローダによりJDBCドライバを読み込んでいることを示している。
			 * 引数は、データベースにアクセスするためのJDBCドライバのクラス名である。
             */
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* DriverManagerクラスのgetConnectionメソッドを使ってデータベースに接続する。 */
            con = DriverManager.getConnection(url, user_name, password);

            System.out.println("Connected....");
//            logger.log(Level.INFO, "Connected....：info");

            /*
			 * データベースの接続後に、sql文をデータベースに直接渡すのではなく、
			 * sqlコンテナの役割を果たすオブジェクトに渡すためのStatementオブジェクトを作成する。
             */
            Statement st = con.createStatement();

            /* SQL文を作成する */
//            String sqlStr = "SELECT * FROM selenium_url where id > 0 order by id desc";
//            String sqlStr = "SELECT * FROM selenium_url where id > 0";
//            String sqlStr = "SELECT * FROM selenium_url where id > 0 order by url desc";
            String sqlStr = "SELECT * FROM selenium_url where id >= 0 order by url";
//            String sqlStr = "SELECT * FROM selenium_url_org where id >= 0 order by id";
//            String sqlStr = "SELECT * FROM selenium_url where id < 20950 order by id desc";

            /* SQL文を実行した結果セットをResultSetオブジェクトに格納している */
            ResultSet result = st.executeQuery(sqlStr);

            /* クエリ結果を1レコードずつ出力していく */
            while (result.next()) {
                /* 時刻を格納する変数 */
                LocalDateTime nowLocalDt = LocalDateTime.now();
                String localStr1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS").format(nowLocalDt);
                /* アクセス数 表示 */
                System.out.println(localStr1 + " access:" + no_of_access + " nice:" + no_of_nice + " skip:"
                        + no_of_skip + " non_title:" + no_of_nontitle + " no_nice_button:" + no_of_nonicebutton
                        + " already_nice:" + no_of_alreadynice + " nice_fail:" + no_of_nicefail + " transfer_fail:"
                        + no_of_transferfail + " click_fail:"
                        + no_of_clickfail);
//                logger.log(Level.INFO, "access:" + no_of_access + " nice:" + no_of_nice + " skip:"
//                        + no_of_skip + " non_title:" + no_of_nontitle + " no_nice_button:" + no_of_nonicebutton
//                        + " already_nice:" + no_of_alreadynice + " nice_fail:" + no_of_nicefail + " transfer_fail:"
//                        + no_of_transferfail + " click_fail:"
//                        + no_of_clickfail);
                /* getString()メソッドは、引数に指定されたフィールド名(列)の値をStringとして取得する */
                int blog_active_flg = result.getInt("active_flg");
                int blog_id = result.getInt("id");
                String blog_url = result.getString("url");
                String blog_title = result.getString("title");
                if (blog_active_flg != 0) {
                    System.out.println(blog_url + ", " + blog_title + "は有効でないためとばす");
//                    logger.log(Level.INFO, blog_url + ", " + blog_title + "は有効でないためとばす");
                    continue;
                } else {
                    System.out.println(blog_url + ", " + blog_title + "にアクセス");
//                    logger.log(Level.INFO, blog_url + ", " + blog_title + "にアクセス");
                    no_of_access++;
                }
                try {
                    driver.get(blog_url);
                    System.out.println(blog_url + " に移動 ");
//                    logger.log(Level.INFO, blog_url + " に移動 ");
                } catch (Exception e) {
                    System.out.println(blog_title + " URLの移動に失敗しました ");
                    logger.log(Level.WARNING, "{0} URL\u306e\u79fb\u52d5\u306b\u5931\u6557\u3057\u307e\u3057\u305f {1}", new Object[]{blog_id, blog_title, blog_url, e.toString()});
//                    e.printStackTrace();
                    no_of_transferfail++;
                    no_of_skip++;
                    logger.log(Level.INFO, "access:{0} nice:{1} skip:{2} non_title:{3} no_nice_button:{4} already_nice:{5} nice_fail:{6} transfer_fail:{7} click_fail:{8}", new Object[]{no_of_access, no_of_nice, no_of_skip, no_of_nontitle, no_of_nonicebutton, no_of_alreadynice, no_of_nicefail, no_of_transferfail, no_of_clickfail});
                    driver.navigate().back();
                    continue;
                }
                try {
                    wait.until(ExpectedConditions
                            .visibilityOfElementLocated(By.cssSelector(".articles-title:first-child a")));
                } catch (Exception e) {
                    System.out.println(blog_title + " 最初の記事のタイトルが見つかりませんでした ");
                    logger.log(Level.WARNING, "{0} \u6700\u521d\u306e\u8a18\u4e8b\u306e\u30bf\u30a4\u30c8\u30eb\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3067\u3057\u305f {1}", new Object[]{blog_id, blog_title, blog_url, e.toString()});
//                    e.printStackTrace();
                    no_of_nontitle++;
                    no_of_skip++;
                    continue;
                }
                System.out.println(blog_title + " 最初の記事のタイトル発見 ");
//                logger.log(Level.INFO, blog_title + " 最初の記事のタイトル発見 ");
                try {
                    driver.findElement(By.cssSelector(".articles-title:first-child a")).sendKeys(Keys.CONTROL);
                    driver.findElement(By.cssSelector(".articles-title:first-child a")).click();
                } catch (Exception e) {
                    System.out.println(blog_title + " 最初の記事のタイトルがクリックできませんでした ");
                    logger.log(Level.WARNING, "{0} \u30af\u30ea\u30c3\u30af\u3067\u304d\u307e\u305b\u3093\u3067\u3057\u305f {1}", new Object[]{blog_id, blog_title, blog_url, e.toString()});
//                    e.printStackTrace();
                    no_of_clickfail++;
                    no_of_skip++;
                    continue;
                }
                System.out.println(blog_title + " 個別記事に移動 ");
//                logger.log(Level.INFO, blog_title + " 個別記事に移動 ");
                try {
                    wait.until(ExpectedConditions
                            .presenceOfElementLocated(By.cssSelector("#myblog-nice-insert-area > input:nth-child(1)")));

                    WebElement nice_button = driver
                            .findElement(By.cssSelector("#myblog-nice-insert-area > input:nth-child(1)"));
                    boolean niceButtonDisplayed = nice_button.isDisplayed();
                    if (niceButtonDisplayed) {
                        System.out.println(" 個別記事のniceボタン発見 ");
//                        logger.log(Level.INFO, "個別記事のniceボタン発見");
                        nice_button.sendKeys(Keys.CONTROL);
                        nice_button.click();
                        try {
                            wait.until(ExpectedConditions.visibilityOfElementLocated(
                                    By.cssSelector("#myblog-nice-delete-owner-area > input:nth-child(1)")));
                        } catch (Exception e) {
                            System.out.println(blog_title + " niceの押下に失敗しました ");
                            logger.log(Level.WARNING, "{0} nice\u306e\u62bc\u4e0b\u306b\u5931\u6557\u3057\u307e\u3057\u305f {1}", new Object[]{blog_id, blog_title, blog_url, e.toString()});
//                            e.printStackTrace();
                            no_of_nicefail++;
                            no_of_skip++;
                            continue;
                        }
                        System.out.println(" 個別記事のnice押下 ");
//                        logger.log(Level.INFO, "個別記事のnice押下");
                        no_of_nice++;
                    } else {
                        System.out.println(" 個別記事のnice押下処理を飛ばします ");
//                        logger.log(Level.INFO, "個別記事のnice押下処理を飛ばします");
                        no_of_alreadynice++;
                        no_of_skip++;
                    }
                } catch (Exception e) {
                    System.out.println(blog_title + " niceボタンが見つかりませんでした ");
                    logger.log(Level.WARNING, "{0} nice\u30dc\u30bf\u30f3\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3067\u3057\u305f {1}", new Object[]{blog_id, blog_title, blog_url, e.toString()});
//                    e.printStackTrace();
                    no_of_nonicebutton++;
                    no_of_skip++;
                }
            }

            try {
                driver.get("http://tetsuwangenshi.blog.so-net.ne.jp/");

            } catch (Exception e) {
                System.out.println("自分の記事に戻るのに失敗しました ");
                logger.log(Level.WARNING, "\u81ea\u5206\u306e\u8a18\u4e8b\u306b\u623b\u308b\u306e\u306b\u5931\u6557\u3057\u307e\u3057\u305f {0}", e.toString());
//                    e.printStackTrace();
            } finally {
                /* 時刻を格納する変数 */
                LocalDateTime nowLocalDt = LocalDateTime.now();
                String localStr1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS").format(nowLocalDt);
                System.out.println(localStr1 + " access:" + no_of_access + " nice:" + no_of_nice + " skip:"
                        + no_of_skip + " non_title:" + no_of_nontitle + " no_nice_button:" + no_of_nonicebutton
                        + " already_nice:" + no_of_alreadynice + " nice_fail:" + no_of_nicefail + " transfer_fail:"
                        + no_of_transferfail + " click_fail:"
                        + no_of_clickfail);
                logger.log(Level.INFO, "access:{0} nice:{1} skip:{2} non_title:{3} no_nice_button:{4} already_nice:{5} nice_fail:{6} transfer_fail:{7} click_fail:{8}", new Object[]{no_of_access, no_of_nice, no_of_skip, no_of_nontitle, no_of_nonicebutton, no_of_alreadynice, no_of_nicefail, no_of_transferfail, no_of_clickfail});
            }
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("ブログトップへ戻る")));
            } catch (Exception e) {
                System.out.println("自分の記事の「ブログトップに戻る」を見つけられませんでした ");
                logger.log(Level.WARNING, "\u81ea\u5206\u306e\u8a18\u4e8b\u306e\u300c\u30d6\u30ed\u30b0\u30c8\u30c3\u30d7\u306b\u623b\u308b\u300d\u3092\u898b\u3064\u3051\u3089\u308c\u307e\u305b\u3093\u3067\u3057\u305f {0}", e.toString());
            }
            /*selenium driver 終了 */
            driver.quit();
            logger.log(Level.INFO, "selenium driver 終了");
            /* ResultSetオブジェクトを閉じる */
            result.close();

            /* Statementオブジェクトを閉じる */
            st.close();

            /* Connectionオブジェクトを閉じる */
            con.close();
        } catch (SQLException e) {

            /* エラーメッセージ出力 */
            System.out.println("Connection Failed. : " + e.toString());
            logger.log(Level.WARNING, "Connection Failed. : {0}", e.toString());
//            e.printStackTrace();

            /* 例外を投げちゃうぞ */
            // throw new Exception();
        } catch (ClassNotFoundException e) {

            /* エラーメッセージ出力 */
            System.out.println("ドライバを読み込めませんでした " + e);
            logger.log(Level.WARNING, "\u30c9\u30e9\u30a4\u30d0\u3092\u8aad\u307f\u8fbc\u3081\u307e\u305b\u3093\u3067\u3057\u305f {0}", e.toString());
//            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {

            /* エラーメッセージ出力 */
            System.out.println("例外が発生しました " + e);
            logger.log(Level.WARNING, "\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f {0}", e.toString());
//            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {

                /* エラーメッセージ出力 */
                System.out.println("Exception2! :" + e.toString());
                logger.log(Level.WARNING, "\u5207\u65ad\u6642\u306b\u4f8b\u5916\u304c\u767a\u751f\u3057\u307e\u3057\u305f {0}", e.toString());
//                e.printStackTrace();

                /* 例外を投げちゃうぞ */
                // throw new Exception();
            }
            JOptionPane pane = new JOptionPane("処理が終了しました。" + " access: " + no_of_access + " nice: " + no_of_nice + " skip: " + no_of_skip + " non_title: " + no_of_nontitle + " no_nice_button: " + no_of_nonicebutton + " already_nice: " + no_of_alreadynice + " nice_fail: " + no_of_nicefail + " transfer_fail: " + no_of_transferfail + "c lick_fail: " + no_of_clickfail, JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane.createDialog(null, "AtMicK_Chrome");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            System.out.println("*** 終了メッセージ表示終了");
            dialog.dispose();
        }
    }
}
